package worldwar;

import cn.ohyeah.itvgame.model.GameAttainment;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupConfirm;
import cn.ohyeah.stb.ui.PopupText;
import cn.ohyeah.stb.util.RandomValue;

public class StateMap implements Common {

	private WorldWarEngine engine;
	public int state;
	public static WWMap map;
	private Influence[] influences;
	private int influenceId;
	private Region region;
	private Region region_attack, region_embattled;
	private Region[] influenceRegions;
	public Player player;
	public static int playerIndex;
	public Player[] ais;
	public static boolean isSelectedAttack;
	public static boolean isSelectedEmbattled;
	private DrawGame showGame;
	private SaveGameAttainment saveAttainment;
	public PropManager pm;
	private int index;
	public boolean startGame;
	private Player currPlayer;
	private int playCount;
	//public int regionSelectedOrChoice = 0;
	
	//private String[] mapPaths ={"/map/1-6-1.wwm"};

	public StateMap(WorldWarEngine e) {
		this.engine = e;
		this.pm = e.pm;
		player = engine.player;
		showGame = e.showGame;
		saveAttainment = e.saveAttainment;
		state = STATE_SEL_MAP;
	}

	public void handle(KeyState key) {
		switch (state) {
		case STATE_SEL_MAP:
			handleSelectMap(key);
			break;
		case STATE_SEL_REGION:
			handleSelectRegion(key);
			break;
		default:
			break;
		}
	}

	/* ����ĳһ�������������� */
	public Region[] serachRegions(Influence inf) {
		Region[] regions = map.getRegions();
		int count = 0;
		for (int i = 0; i < regions.length; i++) {
			if (regions[i].getInfluenceId() == inf.getId()) {
				count++;
			}
		}
		if(count==0){
			return null;
		}
		influenceRegions = new Region[count];
		for (int i = 0, j = 0; i < regions.length; i++) {
			if (regions[i].getInfluenceId() == inf.getId()) {
				influenceRegions[j++] = regions[i];
			}
		}
		return influenceRegions;
	}

	public Region[] serachRegionsByInfId(int infId) {
		Influence inf = map.getInfluence(infId);
		return serachRegions(inf);
	}

	/* �жϱ����������ʿ�����Ƿ�С�ڻ���ڵ�ǰ���� */
	public boolean isCompareRegion(Region r, Region r2) {
		return r.compareSoldiers(r2) >= 0;
	}
	
	/*����ĳ�����������*/
	public Player serachAIByRegion(Region r){
		Player[] players = activePlayers();
		for(int i=0;i<players.length;i++){
			if(r.getInfluenceId()==(short)players[i].getInfluenceId()){
				return players[i];
			}
		}
		return null;
	}
	
	/*����ĳ�����������*/
	public Player serachAIByInfluence(Influence inf){
		Player[] players = activePlayers();
		if(players.length<=1 || inf==null){
			return null;
		}
		for(int i=0;i<players.length;i++){
			if(inf.getId()==(short)players[i].getInfluenceId()){
				return players[i];
			}
		}
		return null;
	}

	/* �����������������С�ĵз����������� */
	public Region findLeastRegion(Region[] rs, Region r) {
		int count = 0;
		for (int i = 0; i < rs.length; i++) {
			if (rs[i].getInfluenceId() != r.getInfluenceId()) {
				count++;
			}
		}
		Region[] _rs = new Region[count];
		for (int i = 0, j = 0; i < rs.length; i++) {
			if (rs[i].getInfluenceId() != r.getInfluenceId()) {
				_rs[j++] = rs[i];
			}
		}
		Region _r = null;
		if (_rs.length > 0) {
			_r = _rs[0];
		}
		for (int j = 1; j < _rs.length; j++) {
			if (_r.getSoldiers() > _rs[j].getSoldiers()) {
				_r = _rs[j];
			}
		}
		/*
		 * if(_r!=null){ System.out.println("��С����:"+_r.getSoldiers()); }
		 */
		return _r;
	}

	/* �ж�ĳһ�������������Ƿ�����ͬһ���� */
	public boolean isSameInfluence(Region r, Region r2) {
		if (r.getInfluenceId() == r2.getInfluenceId()) {
			return true;
		}
		return false;
	}

	/* �ڻ���ʹ������: �������������빥�������� */
	private boolean isAdjacency(Region r, Player p) {
		Region[] regions = serachRegionsByInfId(p.getInfluenceId());
		for (int i = 0; i < regions.length; i++) {
			if (map.isConnexcity(r, regions[i])) {
				return true;
			}
		}
		return false;
	}

	/* �����ʿ�����Ƿ����1 */
	public boolean isMoreOne(Region r) {
		if (r.getSoldiers() > 1) {
			return true;
		}
		return false;
	}

	/* ��Ϸ���� */
	private void gameOver(int i) {
		StateSuccessOrFail ss = new StateSuccessOrFail();
		int index = ss.processGameSuccessOrFail(i);
		if (index == 0) {
			engine.status = GAME_STATUS_PLAYING;
		} else if (index == 1) {
			ServiceWrapper sw = engine.getServiceWrapper();
			StateRanking sr = new StateRanking();
			sr.processRanking(sw.queryRankingList(0, 10));
			engine.status = GAME_STATUS_MAIN_MENU;
		} else {
			engine.status = GAME_STATUS_MAIN_MENU;
		}
		state = STATE_SEL_MAP;
		startGame = false;
		map = null;
		showGame.clearMapBG();
		showGame.clearFighting();
	}

	/*����û��ʹ�÷����������*/
	private Region serachNoPropRegion(Player ai){
		Region[] regions = serachRegions(map.getInfluence(ai.getInfluenceId()));
		if(regions!=null){
			for(int i=0;i<regions.length;i++){
				if(regions[i].getPropId()==0){
					return regions[i];
				}
			}
		}
		return null;
	}
	/*����û��ʹ��װ�׿������*/
	private Region serachNoProp2Region(Player ai){
		Region[] regions = serachRegions(map.getInfluence(ai.getInfluenceId()));
		if(regions!=null){
			for(int i=0;i<regions.length;i++){
				if(regions[i].getPropId()==0){
					return regions[i];
				}
			}
		}
		return null;
	}
	
	/*�жϵ����ܷ�ʹ��*/
	private boolean isUseProp(Player ai){
		if(showGame.cardCoord[0][1]>=DEFENSE_CARD && showGame.cardCoord[0][1]<=LANDMINE_CARD && serachNoPropRegion(ai)!=null){
			return true;
		}
		if(showGame.cardCoord[0][1]==ARMOURED_CARD && serachNoProp2Region(ai)!=null){
			return true;
		}
		if(showGame.cardCoord[0][1]==BOMBARD_CARD || showGame.cardCoord[0][1]==AIRATTACK_CARD){
			return true;
		}
		return false;
	}
	
	/*��Ϸ�ɹ���ʧ��*/
	private void gameFailOrSuccess(){
		/* ��Ϸʧ�� */
		if (isDestroyed(map.getInfluence(player.getInfluenceId()))) {
			player.setState(GAME_OVER);
			gameOver(1);
			pm.sysProps();
		}else{ 		//��Ϸ�ɹ�
			if(activePlayers().length<=1){
				int scores = rankListScores(player);
				ServiceWrapper sw = engine.getServiceWrapper();
				GameAttainment ga = sw.readAttainment(engine.attainmentId);
				if (((ga == null && scores > 0) || (ga.getScores() <= scores)
						&& scores > 0)) {
					saveAttainment.saveGameAttainment(scores);
				}
				gameOver(0);
				pm.sysProps();
			}
		}
	}
	
	/*AILogic*/
	private void AILogic(){
		Player[] ai = activePlayers();
		for (int i = 0; i < ai.length; i++) {
			int count = 0;
			if (ai[i].getState() == STATUS_ATTACK && ai[i].getId() != player.getId()) {

				/* ʹ�õ��� */
				if(serachPropsAI(ai[i]) && ai[i].getState()!=GAME_OVER){
					showGame.cardCoord[0][0] = 1;
				}
				if (serachPropsAI(ai[i]) && ai[i].getState()!=GAME_OVER && isUseProp(ai[i])) {
					AIUseProps(ai[i]);
				} else { // ��ʼ����
					if(timePass(1500)){
						System.out.println("AI[" + i + "]��ʼ����:");
						AIattack(ai[i]);
					}
				}

				if (AIAttackIsOver(ai[i])) {
					System.out.println("��������");
					ai[i].setState(STATUS_WAIT);
					/*����ʿ��*/
					SoldiersGrowth(map.getInfluence(ai[i].getInfluenceId()));
					StateSoldiersGrowth ssg = new StateSoldiersGrowth();
					ssg.processSoldiersGrowth(ai[i], this);
					SoldiersGrowth2(map.getInfluence(ai[i].getInfluenceId()));
					
					count++;
					int m=0;
					if (i < ai.length - 1) {
						m = i+1;
					}
					ai[m].setState(STATUS_ATTACK);
					if(ai[m].getId()!=player.getId()){
						System.out.println("AI����������:");
						pm.randomAssignProps(ai[m]);
					}else{
						System.out.println("��ҹ�����ʼ");
						player.setRounds(player.getRounds() + 1);
						showGame.cardCoord[0][0]=0;
						player.setInvestigateCard(false);  //�غϽ�����鿨Ч����ʧ
						player.setUsePropTimes(0);
						//startGame = false;
						pm.randomAssignProps(player); // ����������
						/* ������ؿ� */
						delHiddenCard(player);
					}
				}
			}
		}
	}
	
	private void handleSelectRegion(KeyState key) {

		StateAssignCard sc = new StateAssignCard();
		if (!startGame) {
			pm.randomAssignProps(player); // ����������
			sc.processAssignCard(map, activePlayers(), currPlayer,
					pm.card_assign_props, pm.game_props, region, this);
		}

		AILogic();
		gameFailOrSuccess(); 
		
		if (key.containsAndRemove(KeyCode.UP)) {
			if (player.getState() == STATUS_ATTACK) {
				region = map.moveUp(region);
			}
		}
		if (key.containsAndRemove(KeyCode.DOWN)) {
			if (player.getState() == STATUS_ATTACK) {
				region = map.moveDown(region);
			}
		}
		if (key.containsAndRemove(KeyCode.LEFT)) {
			if (player.getState() == STATUS_ATTACK) {
				region = map.moveLeft(region);
			}
		}
		if (key.containsAndRemove(KeyCode.RIGHT)) {
			if (player.getState() == STATUS_ATTACK) {
				region = map.moveRight(region);
			}
		}
		if (key.containsAndRemove(KeyCode.NUM9)) {
			if (player.getState() == STATUS_ATTACK) {
				player.setState(STATUS_WAIT);
				
				SoldiersGrowth(map.getInfluence(player.getInfluenceId()));
				StateSoldiersGrowth ssg = new StateSoldiersGrowth();
				ssg.processSoldiersGrowth(player, this);
				SoldiersGrowth2(map.getInfluence(player.getInfluenceId()));
				
				Player[] ai = activePlayers();
				if(player.getId()<ai.length-1){
					ai[player.getId()+1].setState(STATUS_ATTACK);
					pm.randomAssignProps(ai[player.getId()+1]);
				}else{
					ai[0].setState(STATUS_ATTACK);
					pm.randomAssignProps(ai[0]);
				}
			}

		}
		if (key.containsAndRemove(KeyCode.OK)) {
			attackOrUseCard();
		}

		if (key.containsAndRemove(KeyCode.NUM1)
				&& player.getState() == STATUS_ATTACK) {
			if (pm.getGamePropNums(currPlayer.getProps()) > 0) {
				if (showGame.cardCoord[0][0] == 1) {
					init();
				} else {
					init();
					showGame.cardCoord[0][0] = 1;
				}
			}
		}
		if (key.containsAndRemove(KeyCode.NUM2)
				&& player.getState() == STATUS_ATTACK) {
			if (pm.getGamePropNums(currPlayer.getProps()) > 0) {
				if (showGame.cardCoord[1][0] == 1) {
					init();
				} else {
					init();
					showGame.cardCoord[1][0] = 1;
				}
			}
		}
		if (key.containsAndRemove(KeyCode.NUM3)
				&& player.getState() == STATUS_ATTACK) {
			if (pm.getGamePropNums(pm.game_props) > 0) {
				if (showGame.cardCoord[2][0] == 1) {
					init();
				} else {
					init();
					showGame.cardCoord[2][0] = 1;
				}
			}
		}
		if (key.containsAndRemove(KeyCode.NUM4)
				&& player.getState() == STATUS_ATTACK) {
			if (pm.getGamePropNums(pm.game_props) > 1) {
				if (showGame.cardCoord[3][0] == 1) {
					init();
				} else {
					init();
					showGame.cardCoord[3][0] = 1;
				}
			}
		}
		if (key.containsAndRemove(KeyCode.NUM5)
				&& player.getState() == STATUS_ATTACK) {
			if (pm.getGamePropNums(pm.game_props) > 2) {
				if (showGame.cardCoord[4][0] == 1) {
					init();
				} else {
					init();
					showGame.cardCoord[4][0] = 1;
				}
			}
		}
		if (key.containsAndRemove(KeyCode.NUM6)
				&& player.getState() == STATUS_ATTACK) {
			if (pm.getGamePropNums(pm.game_props) > 3) {
				if (showGame.cardCoord[5][0] == 1) {
					init();
				} else {
					init();
					showGame.cardCoord[5][0] = 1;
				}
			}
		}
		if (key.containsAndRemove(KeyCode.NUM7)
				&& player.getState() == STATUS_ATTACK) {
			if (pm.getGamePropNums(pm.game_props) > 4) {
				if (showGame.cardCoord[6][0] == 1) {
					init();
				} else {
					init();
					showGame.cardCoord[6][0] = 1;
				}
			}
		}
		if (key.containsAndRemove(KeyCode.NUM8)){
			StateHelp sh = new StateHelp();
			sh.processHelp();
		}
		if (key.containsAndRemove(KeyCode.NUM0)) {
			PopupConfirm pc = UIResource.getInstance()
					.buildDefaultPopupConfirm();
			pc.setText("ȷ��Ҫ�˳���Ϸ��?");
			int index = pc.popup();
			if (index == 0) {
				/* ͬ������ */
				pm.sysProps();
				player.setUsePropTimes(0);
				engine.status = GAME_STATUS_MAIN_MENU;
				state = STATE_SEL_MAP;
				startGame = false;
				map = null;
				showGame.clearMapBG();
				init();
			}
		}
	}
	
	/* �ж��Ƿ���װ�׿� */
	/*private boolean isArmouredCard(Player p) {
		Influence inf = map.getInfluence(p.getInfluenceId());
		Region[] regions = serachRegions(inf);
		for (int i = 0; i < regions.length; i++) {
			if (regions[i].getPropId2() != 0) {
				return true;
			}
		}
		return false;
	}*/
	

	/* ���װ�׿� */
	private void delArmouredCard(Player p) {
		Influence inf = map.getInfluence(p.getInfluenceId());
		Region[] regions = serachRegions(inf);
		for (int i = 0; i < regions.length; i++) {
			if (regions[i].getPropId2() == AIRATTACK_CARD) {
				System.out.println("װ�׿������");
				regions[i].setPropId2(0);
			}
		}
	}
	

	/* ������ؿ� */
	private void delHiddenCard(Player p) {
		Influence inf = map.getInfluence(p.getInfluenceId());
		Region[] regions = serachRegions(inf);
		if(regions!=null){
			for (int i = 0; i < regions.length; i++) {
				if (regions[i].getPropId() == HIDDEN_CARD) {
					System.out.println("���ؿ������");
					regions[i].setPropId(0);
				}
			}
		}
	}
	

	/* ��������ʹ�ÿ�Ƭ */
	int _type, _i;
	private void attackOrUseCard() {
		boolean flag = false;
		for (int i = 0; i < showGame.cardCoord.length; i++) {
			if (showGame.cardCoord[i][0] == 1) {
				if (showGame.cardCoord[i][1] == ARMOURED_CARD) {
					delArmouredCard(player);
				}
				if(player.getUsePropTimes() < USE_CARD_TIMES){
					if (i < 2) { // �����Ƭ
						useCard(showGame.cardCoord[i][1], i, 0, player);
						flag = true;
					} else if(i>=2 && i<7) {
						useCard(showGame.cardCoord[i][1], i, 1, player);
						flag = true;
					}
				}else{
					PopupText pt = UIResource.getInstance().buildDefaultPopupText();
					pt.setText("һ���غ�ֻ��ʹ�����ſ�Ƭ!");
					pt.popup();
				}
			}
		}

		region.printInfo();

		if (!flag) {
			if (player.getState() == STATUS_ATTACK) {
				if (region.getInfluenceId() == influenceId
						&& region.getSoldiers() > 1) {
					isSelectedAttack = true;
					region_attack = region;
					initRegion();
					region_attack.setSelected(true);
				}
				if (region.getInfluenceId() != influenceId && isSelectedAttack
						&& map.isConnexcity(region_attack, region)) {
					isSelectedEmbattled = true;
					region_embattled = region;
					region_embattled.setSelected(true);
				}
				if (isSelectedAttack && isSelectedEmbattled) {
					StateFighting sf = new StateFighting();
					int i = sf.processFighting(region_attack, region_embattled, this);
					initRegion();
					StateExplosion se = new StateExplosion();
					if(i==1){
						se.processSoldiersGrowth(region_embattled, this);
					}
					if(i==-1){
						se.processSoldiersGrowth(region_attack, this);
					}

					if (region_embattled.getPropId() == DEFENSE_CARD) { // ������Ч����ʧ
						System.out.println("ʹ���˷�����");
						region_embattled.setPropId(0);
					}

					// װ�׿�ʹ��֮������
					if (region_attack.getPropId2()!=0) {
						System.out.println("ʹ����װ�׿�");
						region_attack.setPropId2(0);

					}
					/*if (isArmouredCard(player)) {
						System.out.println("ʹ����װ�׿�");
						player.setUsePropTimes(player.getUsePropTimes()+1);
						updateProps(showGame.cardCoord[_i][1], _type, player);
						delArmouredCard(player);
					}*/
				}
			}
		}
	}
	
	/*����������Ϊδѡ��״̬*/
	private void initRegion(){
		for(int i=0;i<map.getRegions().length;i++){
			map.getRegions()[i].setSelected(false);
		}
	}

	private void updateProps(int propId, int type, Player p) {
		if (type == 0) {
			int num = p.getProps()[propId - BASE_VALUE].getNums();
			p.getProps()[propId - BASE_VALUE].setNums(num - 1);
		} else {
			pm.game_props[propId - BASE_VALUE].setNums(pm.game_props[propId
					- BASE_VALUE].getNums() - 1);
			pm.props[propId - BASE_VALUE].setNums(pm.props[propId - BASE_VALUE]
					.getNums() - 1);
		}
	}

	/**
	 * ʹ�õ���
	 * @param propId ����ID
	 * @param i
	 * @param flag ������ʹ�õ��ߵı�־
	 * @param type 0,������� 1,����ĵ���
	 */
	private void useCard(int propId, int i, int type, Player p) {
		switch (propId) {
		case DEFENSE_CARD: // ������
			if (region.getInfluenceId() == p.getInfluenceId()
					&& !checkRegionProp(propId)) {
				updateProps(propId, type, p);
				region.setPropId(propId);
				showGame.cardCoord[i][0] = 0;
				p.setUsePropTimes(p.getUsePropTimes()+1);
			}
			break;
		case RETREAT_CARD: // ���˿�
			if (region.getInfluenceId() == p.getInfluenceId()
					&& !checkRegionProp(propId)) {
				updateProps(propId, type, p);
				region.setPropId(propId);
				showGame.cardCoord[i][0] = 0;
				p.setUsePropTimes(p.getUsePropTimes()+1);
			}
			break;
		case LANDMINE_CARD: // ���׿�
			if (region.getInfluenceId() == p.getInfluenceId()
					&& !checkRegionProp(propId)) {
				updateProps(propId, type, p);
				region.setPropId(propId);
				showGame.cardCoord[i][0] = 0;
				p.setUsePropTimes(p.getUsePropTimes()+1);
			}
			break;
		case ARMOURED_CARD: // װ�׿�
			if (region.getInfluenceId() == p.getInfluenceId()/*&& !checkRegionProp(propId)*/) {
				if (region.getSoldiers() > 1) {
					_type = type;
					_i = i;
					region.setPropId2(propId);
					showGame.cardCoord[i][0] = 0;
					player.setUsePropTimes(player.getUsePropTimes()+1);
					updateProps(propId, _type, p);
				}
			}
			break;
		case BOMBARD_CARD: // �ڻ���
			if (region.getInfluenceId() != p.getInfluenceId()
					&& isAdjacency(region, p)) {
				//if (region.getSoldiers() > 1) {
					if (region.getPropId() == DEFENSE_CARD) { // �ڻ����ͷ���������
						StateExplosion se = new StateExplosion();
						se.processSoldiersGrowth(region, this);
						region.setPropId(0);
					} else {
						int r = RandomValue.getRandInt(1, 7);
						StateExplosion se = new StateExplosion();
						se.processSoldiersGrowth(region, this);
						if (region.getSoldiers() > r) {
							region.setSoldiers((short) (region.getSoldiers() - r));
						} else {
							region.setSoldiers((short) 1);
						}
					}
					p.setUsePropTimes(p.getUsePropTimes()+1);
					updateProps(propId, type, p);
				//}
				showGame.cardCoord[i][0] = 0;
			}
			break;
		case AIRATTACK_CARD: // ��Ϯ��
			if (region.getInfluenceId() != p.getInfluenceId()) {
				//if (region.getSoldiers() > 1) {
					StateExplosion se = new StateExplosion();
					se.processSoldiersGrowth(region, this);
					region.setSoldiers((short) 1);
					p.setUsePropTimes(p.getUsePropTimes()+1);
					updateProps(propId, type, p);
				//}
				showGame.cardCoord[i][0] = 0;
			}
			break;
		case INVESTIGATE_CARD: // ��鿨
			if (!player.isInvestigateCard()) {
				updateProps(propId, type, p);
				showGame.cardCoord[i][0] = 0;
				player.setInvestigateCard(true);
				p.setUsePropTimes(p.getUsePropTimes()+1);
			}
			break;
		case HIDDEN_CARD: // ���ؿ�
			if (region.getInfluenceId() == p.getInfluenceId()
					&& !checkRegionProp(propId)) {
				updateProps(propId, type, p);
				showGame.cardCoord[i][0] = 0;
				region.setPropId(propId);
				p.setUsePropTimes(p.getUsePropTimes()+1);
			}
			break;
		case COVERT_CARD: // �߷���
			if (region.getInfluenceId() != p.getInfluenceId()) {
				updateProps(propId, type, p);
				showGame.cardCoord[i][0] = 0;
				//Region r = serachEnemyRegion(player);
				region.setInfluenceId((short) player.getInfluenceId());
				p.setUsePropTimes(p.getUsePropTimes()+1);
			}
			break;
		}
	}

	/* ������ҵз������� */
	/*private Region serachEnemyRegion(Player p) {
		Region[] rs = map.getRegions();
		Region[] regions = null;
		int j = 0;
		for (int i = 0; i < rs.length; i++) {
			if (rs[i].getInfluenceId() != p.getInfluenceId()) {
				j++;
			}
		}
		regions = new Region[j];
		for (int i = 0, m = 0; i < rs.length; i++) {
			if (rs[i].getInfluenceId() != p.getInfluenceId()) {
				regions[m] = rs[i];
				m++;
			}
		}
		int ran = 0;
		if (j != 0) {
			ran = RandomValue.getRandInt(regions.length - 1);
		}
		return regions[ran];
	}*/

	/* ���������Ƿ�ʹ�ù�ͬ���͵ĵ��� */
	private boolean checkRegionProp(int propId) {
		if (propId >= 44 && propId <= 46 && region.getPropId() != 0) {
			return true;
		}
		if (propId == HIDDEN_CARD && region.getPropId() != 0) { // ʹ�������ؿ�
			return true;
		}
		return false;
	}

	private void init() {
		for (int i = 0; i < showGame.cardCoord.length; i++) {
			showGame.cardCoord[i][0] = 0;
		}
	}

	/* �ж�ĳ�������Ƿ����� */
	private boolean isDestroyed(Influence inf) {
		if (serachRegions(inf)==null/* || serachRegions(inf).length <= 0*/) {
			return true;
		}
		return false;
	}

	/* ��ǰû�б���������� */
	private Player[] activePlayers() {
		int count = 0;
		for (int i = 0; i < ais.length; i++) {
			if (!isDestroyed(map.getInfluence(ais[i].getInfluenceId()))) {
				count++;
			}
		}
		Player[] players = new Player[count];
		if (!isDestroyed(map.getInfluence(player.getInfluenceId()))) {
			for (int i = 0, j = 0; j < ais.length; j++) {
				if (!isDestroyed(map.getInfluence(ais[j].getInfluenceId()))) {
					players[i++] = ais[j];
				}
				if (ais[j].getState() == STATUS_ATTACK) {
					currPlayer = ais[j];
				}
			}
		}
		return players;
	}

	/* ��ѯ�Ƿ��е��� */
	private boolean serachPropsAI(Player p) {
		int num = pm.getGamePropNums(p.getProps());
		if (num <= 0) {
			return false;
		}
		return true;
	}

	/* AI���� */
	public void AIattack(Player ai) {
		influenceRegions = serachRegions(map.getInfluence(ai.getInfluenceId()));
		Region r;
		for (int i = 0; i < influenceRegions.length; i++) {
			r = influenceRegions[i];
			StateFighting.endTime = System.currentTimeMillis();
			if (isRegionAttack(r)
					&& (StateFighting.endTime - StateFighting.startTime) > 1000) {
				region_attack = r;
				region_attack.setSelected(true);
				Region[] regions = map.getAdjacentRegions(region_attack);
				Region leastRegion = findLeastRegion(regions, region_attack);
				region_embattled = leastRegion;
				region_embattled.setSelected(true);
				region = region_attack;
				if (leastRegion.getPropId() != HIDDEN_CARD) { // �ж��Ƿ��������ؿ�
					StateFighting sf = new StateFighting();
					int index = sf.processFighting(region_attack, region_embattled, this);
					initRegion();
					//region = region_embattled;
					StateExplosion se = new StateExplosion();
					if(index==1){
						se.processSoldiersGrowth(region_embattled, this);
					}
					if(index==-1){
						se.processSoldiersGrowth(region_attack, this);
					}
					if (region_embattled.getPropId() == DEFENSE_CARD) { // ������Ч����ʧ
						region_embattled.setPropId(0);
					}

					// װ�׿�ʹ��֮������
					if (region_attack.getPropId2()!=0) {
						System.out.println("AIʹ����װ�׿�");
						region_attack.setPropId2(0);

					}
					influenceRegions = serachRegions(map.getInfluence(ai
							.getInfluenceId()));
				}
			}
		}
	}
	
	public long recordTime;
	public boolean timePass(int millisSeconds) {
		long curTime = System.currentTimeMillis();
		if (recordTime <= 0) {
			recordTime = curTime;
		} else {
			if (curTime - recordTime >= millisSeconds) {
				recordTime = 0;
				return true;
			}
		}
		return false;
	}

	/* AIʹ�õ��� */
	private void AIUseProps(Player ai) {
		if(timePass(1500)){
			region = serachNoPropRegion(ai);
			if(showGame.cardCoord[0][1]==AIRATTACK_CARD){   	//��Ϯ��
				region = serachMostRegion(ai);
				System.out.println("AIʹ�ÿ�Ϯ��");
				useCard(showGame.cardCoord[0][1], 1, 0, ai);
			}else if(showGame.cardCoord[0][1]==BOMBARD_CARD){	//�ڻ���
				region = serachMostAdjacencyRegion(ai);
				System.out.println("AIʹ���ڻ���");
				useCard(showGame.cardCoord[0][1], 1, 0, ai);
			}else if(showGame.cardCoord[0][1]==ARMOURED_CARD){	//װ�׿�
				region = serachNoProp2Region(ai);
				region.setPropId2(showGame.cardCoord[0][1]);
				System.out.println("AIʹ��װ�׿�");
				updateProps(showGame.cardCoord[0][1], 0, ai);
				showGame.cardCoord[0][0] = 0;
			}else{
				System.out.println("AIʹ�÷��ؿ�,propId:"+showGame.cardCoord[0][1]);
				useCard(showGame.cardCoord[0][1], 1, 0, ai);
			}
		}
	}
	
	/*�����������һ������
	private Region serachRegion(Player ai){
		Region[] regions = serachRegionsByInfId(ai.getInfluenceId());
		int r = RandomValue.getRandInt(regions.length-1);
		System.out.println("regions.length:"+regions.length);
		System.out.println("r:"+r);
		return regions[r];
	}*/
	
	/*�������ڵı������ĵз�ĳһ������*/
	private Region serachMostAdjacencyRegion(Player ai){
		Region[] regions = map.getRegions();
		Region r = null;
		for(int i=0;i<regions.length;i++){
			if((r==null || r.getSoldiers()<regions[i].getSoldiers()) 
					&& regions[i].getInfluenceId()!=ai.getInfluenceId()
					&& isAdjacency(regions[i], ai)){
				r = regions[i];
			}
		}
		return r;
	}
	
	/*�����з���������һ������*/
	private Region serachMostRegion(Player ai){
		Region[] regions = map.getRegions();
		Region r = null;
		for(int i=0;i<regions.length;i++){
			if((r==null || r.getSoldiers()<regions[i].getSoldiers()) && regions[i].getInfluenceId()!=ai.getInfluenceId()){
				r = regions[i];
			}
		}
		if(r!=null){
			r.printInfo2();
		}
		return r;
	}

	/* �ж�AI�����Ƿ���� */
	public boolean AIAttackIsOver(Player ai) {
		Region[] rs = serachRegions(map.getInfluence(ai.getInfluenceId()));
		if(rs.length<=0){
			return true;
		}
		for (int i = 0; i < rs.length; i++) {
			if (isRegionAttack(rs[i])) {
				return false;
			}
		}
		return true;
	}

	/* �ж�ĳһ�������Ƿ��ܽ��й��� */
	public boolean isRegionAttack(Region r) {

		// ��������1
		if (!isMoreOne(r)) {
			return false;
		}

		Region[] regions = map.getAdjacentRegions(r); // ���������ڵ�����
		Region leastRegion = findLeastRegion(regions, r); // �������������������ٱ���������

		// �ܱ��ез�����
		if (leastRegion == null) {
			return false;
		}

		// �ܱ������������ȸ����������
		if (!isCompareRegion(r, leastRegion)) {
			return false;
		}

		/* �ܱ������ȸ�������,���������ؿ� */
		if (leastRegion.getPropId() == HIDDEN_CARD) {
			return false;
		}

		return true;
	}

	/* ���Ҹ������������������� */
	public Region findLargeRegion(Region[] rs) {
		Region r = null;
		for (int i = 0; i < rs.length; i++) {
			r = rs[0];
			if (r.getSoldiers() < rs[i].getSoldiers()) {
				r = rs[i];
			}
		}
		return r;
	}

	private void handleSelectMap(KeyState key) {

		if (key.containsAndRemove(KeyCode.UP)) {
			index = (index + 5 - 1) % 5;
		}
		if (key.containsAndRemove(KeyCode.DOWN)) {
			index = (index + 1) % 5;
		}
		if (key.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			index = 0;
			engine.status = GAME_STATUS_MAIN_MENU; // �������˵�
			showGame.clearSelectMapMenu();
		}
		if (key.containsAndRemove(KeyCode.OK)) {
			int result = saveAttainment.readGameRecord(engine.attainmentId);
			if(result==0){
				engine.isFreshMan = false;
			}
			if(engine.isFreshMan){ //����
				StateFreshMan sfm = new StateFreshMan();
				sfm.processFreshMan();
			}
			
			try {
				int i = RandomValue.getRandInt(Resource.mapPaths[index].length-1);
				map = WWMap.loadWWMap(Resource.mapPaths[index][i]);
				//map = WWMap.loadWWMap(mapPaths[0]);
				influences = map.getInfluences();
				playCount = influences.length;
				assignForcs();
				ais[0].setState(STATUS_ATTACK);
				if(ais[0].getId()!=player.getId()){
					startGame = true;
					pm.randomAssignProps(ais[0]);
				}
				state = STATE_SEL_REGION;
				/*�ж��Ƿ�������*/
				if(engine.isFreshMan){
					saveAttainment.saveGameRecord();
					engine.isFreshMan = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				PopupText pt = UIResource.getInstance().buildDefaultPopupText();
				pt.setText("���ص�ͼʧ�ܣ�" + e.getMessage());
				pt.popup();
			}
			index = 0;
			// pm.queryAllProps();
			startGame = false;
			showGame.clearSelectMapMenu();
		}
	}

	/* �������� */
	private void assignForcs() {
		ais = new Player[influences.length];
		influenceId = RandomValue.getRandInt(0, influences.length);
		region = map.getFirstRegion(influences[influenceId]);
		for (int i = 0; i < influences.length; i++) {
			influences[i].setColor(Resource.setColorValue(i));
			if (influenceId == influences[i].getId()) {
				player.setId(i);
				playerIndex = i;
				player.setInfluenceId(influences[i].getId());
				player.setColor(influences[i].getColor());
				player.setState(STATUS_WAIT);
				player.setRounds(1);
				Propety[] p = new Propety[9];
				pm.initProps(p);
				player.setProps(p);
				player.setUsePropTimes(0);
				ais[i] = player;
			} else {
				ais[i] = new Player();
				ais[i].setId(i);
				Propety[] prop = new Propety[9];
				pm.initProps(prop);
				ais[i].setProps(prop);
				ais[i].setInfluenceId(influences[i].getId());
				ais[i].setColor(influences[i].getColor());
				ais[i].setState(STATUS_WAIT);
			}
		}
	}
	
	/*private Player getPlayerById(int id){
		for(int i=0;i<ais.length;i++){
			if(ais[i].getId()==id){
				return ais[i];
			}
		}
		return null;
	}*/
	
	/* �������ּ��㹫ʽ */
	private int rankListScores(Player p) {

		System.out.println("getSoldiersInfluence(p):" + getSoldiersInfluence(p));
		System.out.println("p.getRounds():" + p.getRounds());
		System.out.println("playCount:" + playCount);

		// ������������=������������=�ܱ���*10+(50-�غ���)*100+�����*300(ÿ����0)
		return getSoldiersInfluence(p) * 10 + (50 - p.getRounds()) * 100 + playCount * 300;

	}

	/* ĳһ�����ܱ��� */
	private int getSoldiersInfluence(Player p) {
		Region[] regions = serachRegionsByInfId(p.getInfluenceId());
		int count = 0;
		for (int i = 0; i < regions.length; i++) {
			count += regions[i].getSoldiers();
		}
		return count;
	}

	/* ʿ������ */
	public void SoldiersGrowth(Influence inf) {
		Player p = serachAIByInfluence(inf);
		if(p==null){
			return;
		}
		Region[] regions = serachRegions(inf);
		int count = regions.length + p.getSoliders();
		//System.out.println("regions.length=" + regions.length);
		//System.out.println("soliders=" + p.getSoliders());
		//System.out.println("count=" + count);
		p.setSoliders(0);
		Region r;
		for (int i = 0; i < regions.length; i++) {
			r = regions[i];
			int n;
			if (count > 0) {
				if (count >= 3) {
					n = RandomValue.getRandInt(4); // 0-3�������
				} else {
					n = RandomValue.getRandInt(count); // 0-count�������
				}
				if ((r.getSoldiers() + n) > 8) {
					n = 8 - r.getSoldiers();
				}
				r.setGrowthSoldiers((short)n);
				count -= n;
			}

			/* �������һ��ʿ����û�з�������ʣ���ʿ����ȫ�ָ����һ������ */
			if (count > 0 && r == regions[regions.length - 1]) {
				if ((r.getSoldiers() + count) > 8) {
					r.setGrowthSoldiers((short)(8-r.getSoldiers()));
				} else {
					r.setGrowthSoldiers((short)(count));
				}
				//System.out.println("r.nums:" + r.getGrowthSoldiers());
			}
		}
	}

	private void SoldiersGrowth2(Influence inf){
		Region[] regions = serachRegions(inf);
		for(int i=0;i<regions.length;i++){
			regions[i].setSoldiers((short)(regions[i].getSoldiers()+regions[i].getGrowthSoldiers()));
			regions[i].setGrowthSoldiers((short)0);
		}
	}
	public void show(SGraphics g) {
		switch (state) {
		case STATE_SEL_MAP:
			showSelectMap(g);
			break;
		case STATE_SEL_REGION:
			showSelectRegion(g);
			break;
		default:
			break;
		}
	}

	public void showSelectRegion(SGraphics g) {
		g.setColor(0XFFFFFF);
		g.fillRect(0, 0, engine.getScreenWidth(), engine.getScreenHeight());
		map.drawMap(g, activePlayers(), currPlayer, pm.game_props, showGame,
				region, this);
		// map.drawRegion(g, region, 0XFF0000);
		/*
		 * short[] adjacentRegions = region.getAdjacentRegions(); for (int i =
		 * 0; i < adjacentRegions.length; ++i) { map.drawRegion(g,
		 * map.getRegion(adjacentRegions[i]), 0XF010F0); }
		 */
	}

	private void showSelectMap(SGraphics g) {
		/*
		 * g.setColor(0); g.fillRect(0, 0, engine.getScreenWidth(),
		 * engine.getScreenHeight()); mapMenu.show(g);
		 */
		showGame.drawSelectMapMenu(g, index);
	}
}
