package worldwar;

import cn.ohyeah.itvgame.model.OwnProp;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.game.StateRecharge;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupConfirm;
import cn.ohyeah.stb.ui.PopupText;
import cn.ohyeah.stb.util.RandomValue;

public class PropManager implements Common{
	
	public WorldWarEngine engine;
	public Propety[] props;   			//用户总道具数
	public Propety[] card_assign_props;	//卡片分配中的道具
	public Propety[] game_props;		//游戏卡槽中的道具
	
	public PropManager(WorldWarEngine e){
		this.engine = e;
		props = engine.props;
		card_assign_props = engine.card_assign_props;
		game_props = engine.game_props;
	}
	
	/*查询道具*/
	public void queryAllProps(){
		initProps(props);
		initProps(card_assign_props);
		initProps(game_props);
		
		ServiceWrapper sw = engine.getServiceWrapper();
		OwnProp[] propList = sw.queryOwnPropList();
		if(propList==null){
			return;
		}
		for(int i=0;i<propList.length;i++){
			if(propList[i].getPropId()==44){
				props[0].setNums(propList[i].getCount());
				card_assign_props[0].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==45){
				props[1].setNums(propList[i].getCount());
				card_assign_props[1].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==46){
				props[2].setNums(propList[i].getCount());
				card_assign_props[2].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==47){
				props[3].setNums(propList[i].getCount());
				card_assign_props[3].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==48){
				props[4].setNums(propList[i].getCount());
				card_assign_props[4].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==49){
				props[5].setNums(propList[i].getCount());
				card_assign_props[5].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==50){
				props[6].setNums(propList[i].getCount());
				card_assign_props[6].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==51){
				props[7].setNums(propList[i].getCount());
				card_assign_props[7].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==52){
				props[8].setNums(propList[i].getCount());
				card_assign_props[8].setNums(propList[i].getCount());
			}
		}
		
		//card_assign_props = props;  
		
		/*for(int i=0;i<card_assign_props.length;i++){
			System.out.println("道具名称=="+card_assign_props[i].getName());
			System.out.println("道具数量=="+props[i].getNums());
		}*/
	}
	
	/*计算道具数量*/
	public int getGamePropNums(Propety[] props){
		int count = 0;
		for(int i=0;i<props.length;i++){
			int m = props[i].getNums();
			while(m>0){
				count++;
				m--;
			}
		}
		return count;
	}
	
	/*初始道具设为0*/
	public void initProps(Propety[] p){
		for(int i=0;i<p.length;i++){
			Propety prop = new Propety();
			p[i] = prop;
			p[i].setId(i);
			p[i].setNums(0);
			if(i==0){
				p[i].setName("防御卡");
				p[i].setPrice(20);
				p[i].setPropId(44);
			}
			if(i==1){
				p[i].setName("撤退卡");
				p[i].setPrice(20);
				p[i].setPropId(45);
			}
			if(i==2){
				p[i].setName("地雷卡");
				p[i].setPrice(20);
				p[i].setPropId(46);
			}
			if(i==3){
				p[i].setName("隐藏卡");
				p[i].setPrice(20);
				p[i].setPropId(47);
			}
			if(i==4){
				p[i].setName("炮击卡");
				p[i].setPrice(30);
				p[i].setPropId(48);
			}
			if(i==5){
				p[i].setName("装甲卡");
				p[i].setPrice(30);
				p[i].setPropId(49);
			}
			if(i==6){
				p[i].setName("侦查卡");
				p[i].setPrice(30);
				p[i].setPropId(50);
			}
			if(i==7){
				p[i].setName("空袭卡");
				p[i].setPrice(50);
				p[i].setPropId(51);
			}
			if(i==8){
				p[i].setName("策反卡");
				p[i].setPrice(50);
				p[i].setPropId(52);
			}
		}
	}
	
	/*分配道具*/
	public boolean assignProps(int cardX,int cardY){
		if(cardY==0 && cardX==0){
			if(card_assign_props[0].getNums()>0){
				card_assign_props[0].setNums(card_assign_props[0].getNums()-1);
				game_props[0].setNums(game_props[0].getNums()+1);
				return true;
			}
		}
		if(cardY==0 && cardX==1){
			if(card_assign_props[1].getNums()>0){
				card_assign_props[1].setNums(card_assign_props[1].getNums()-1);
				game_props[1].setNums(game_props[1].getNums()+1);
				return true;
			}
		}
		if(cardY==0 && cardX==2){
			if(card_assign_props[2].getNums()>0){
				card_assign_props[2].setNums(card_assign_props[2].getNums()-1);
				game_props[2].setNums(game_props[2].getNums()+1);
				return true;
			}
		}
		if(cardY==1 && cardX==0){
			if(card_assign_props[3].getNums()>0){
				card_assign_props[3].setNums(card_assign_props[3].getNums()-1);
				game_props[3].setNums(game_props[3].getNums()+1);
				return true;
			}
		}
		if(cardY==1 && cardX==1){
			if(card_assign_props[4].getNums()>0){
				card_assign_props[4].setNums(card_assign_props[4].getNums()-1);
				game_props[4].setNums(game_props[4].getNums()+1);
				return true;
			}
		}
		if(cardY==1 && cardX==2){
			if(card_assign_props[5].getNums()>0){
				card_assign_props[5].setNums(card_assign_props[5].getNums()-1);
				game_props[5].setNums(game_props[5].getNums()+1);
				return true;
			}
		}
		if(cardY==2 && cardX==0){
			if(card_assign_props[6].getNums()>0){
				card_assign_props[6].setNums(card_assign_props[6].getNums()-1);
				game_props[6].setNums(game_props[6].getNums()+1);
				return true;
			}
		}
		if(cardY==2 && cardX==1){
			if(card_assign_props[7].getNums()>0){
				card_assign_props[7].setNums(card_assign_props[7].getNums()-1);
				game_props[7].setNums(game_props[7].getNums()+1);
				return true;
			}
		}
		if(cardY==2 && cardX==2){
			if(card_assign_props[8].getNums()>0){
				card_assign_props[8].setNums(card_assign_props[8].getNums()-1);
				game_props[8].setNums(game_props[8].getNums()+1);
				return true;
			}
		}
		return false;
	/*	for(int i=0;i<props.length;i++){
			System.out.println("props=="+props[i].getName());
			System.out.println("props=="+props[i].getNums());
		}
		for(int i=0;i<card_assign_props.length;i++){
			System.out.println("card_assign_props=="+card_assign_props[i].getName());
			System.out.println("card_assign_props=="+card_assign_props[i].getNums());
		}
		for(int i=0;i<game_props.length;i++){
			System.out.println("game_props=="+game_props[i].getName());
			System.out.println("game_props=="+game_props[i].getNums());
		}*/
	}
	
	private boolean buyProp(int propId, int propCount){
		String propName = props[propId-44].getName();
		int price = props[propId-44].getPrice();
		if (engine.getEngineService().getBalance() >= price) {
			ServiceWrapper sw = engine.getServiceWrapper();
			sw.purchaseProp(propId, propCount, "购买"+propName);
			PopupText pt = UIResource.getInstance().buildDefaultPopupText();
			if (sw.isServiceSuccessful()) {
				pt.setText("购买"+propName+"成功");
			}
			else {
				pt.setText("购买"+propName+"失败, 原因: "+sw.getServiceMessage());
				
			}
			pt.popup();
			return sw.isServiceSuccessful();
		}
		else {
			PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
			pc.setText("游戏币不足,是否充值");
			if (pc.popup() == 0) {
				StateRecharge recharge = new StateRecharge(engine);
				recharge.recharge();
			}
			return false;
		}
	}
	
	/*随机获取的道具*/
	private int[] ranProp = {0, 1, 2, 4, 5, 7};
	
	/*购买道具*/
	public void purchaseProp(int shopx,int shopy){
		
		if(shopy==0 && shopx==0){
			if (buyProp(44, 1)) {
				props[0].setNums(props[0].getNums()+1);
				card_assign_props[0].setNums(card_assign_props[0].getNums()+1);
			}
		}
		if(shopy==0 && shopx==1){
			if (buyProp(45, 1)) {
				props[1].setNums(props[1].getNums()+1);
				card_assign_props[1].setNums(card_assign_props[1].getNums()+1);
			}
		}
		if(shopy==0 && shopx==2){
			if (buyProp(46, 1)) {
				props[2].setNums(props[2].getNums()+1);
				card_assign_props[2].setNums(card_assign_props[2].getNums()+1);
			}
		}
		if(shopy==1 && shopx==0){
			if (buyProp(47, 1)) {
				props[3].setNums(props[3].getNums()+1);
				card_assign_props[3].setNums(card_assign_props[3].getNums()+1);
			}
		}
		if(shopy==1 && shopx==1){
			if (buyProp(48, 1)) {
				props[4].setNums(props[4].getNums()+1);
				card_assign_props[4].setNums(card_assign_props[4].getNums()+1);
			}
		}
		if(shopy==1 && shopx==2){
			if (buyProp(49, 1)) {
				props[5].setNums(props[5].getNums()+1);
				card_assign_props[5].setNums(card_assign_props[5].getNums()+1);
			}
		}
		if(shopy==2 && shopx==0){
			if (buyProp(50, 1)) {
				props[6].setNums(props[6].getNums()+1);
				card_assign_props[6].setNums(card_assign_props[6].getNums()+1);
			}
		}
		if(shopy==2 && shopx==1){
			if (buyProp(51, 1)) {
				props[7].setNums(props[7].getNums()+1);
				card_assign_props[7].setNums(card_assign_props[7].getNums()+1);
			}
		}
		if(shopy==2 && shopx==2){
			if (buyProp(52, 1)) {
				props[8].setNums(props[8].getNums()+1);
				card_assign_props[8].setNums(card_assign_props[8].getNums()+1);
			}
		}
	}
	
	/*随机分配道具*/
	public void randomAssignProps(Player p){
		
		/*if(getGamePropNums(p.getProps())==1){
			int ran = RandomValue.getRandInt(6);
			p.getProps()[ran].setNums(p.getProps()[ran].getNums()+1);
		}
		if(getGamePropNums(p.getProps())==0){
			int ran = RandomValue.getRandInt(6);
			int ran2 = RandomValue.getRandInt(6);
			p.getProps()[ran].setNums(p.getProps()[ran].getNums()+1);
			p.getProps()[ran2].setNums(p.getProps()[ran2].getNums()+1);
		}*/
		
		initProps(p.getProps());
		int ran = RandomValue.getRandInt(6);
		int ran2 = RandomValue.getRandInt(6);
		p.getProps()[ranProp[ran]].setNums(p.getProps()[ranProp[ran]].getNums()+1);
		p.getProps()[ranProp[ran2]].setNums(p.getProps()[ranProp[ran2]].getNums()+1);
	}

	/*同步道具*/
	public void sysProps(){
		int[] ids = new int[9];
		int[] counts = new int[9];
		for(int i=0;i<props.length;i++){
			ids[i] = props[i].getPropId();
			counts[i] = props[i].getNums();
		}
		ServiceWrapper sw = engine.getServiceWrapper();
		sw.synProps(ids, counts);
		System.out.println("同步道具:"+sw.getServiceMessage());
	}
}
