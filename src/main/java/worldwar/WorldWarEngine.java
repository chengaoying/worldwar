package worldwar;

import javax.microedition.midlet.MIDlet;

import cn.ohyeah.itvgame.model.GameRanking;
import cn.ohyeah.stb.game.Configurations;
import cn.ohyeah.stb.game.GameCanvasEngine;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupText;
import cn.ohyeah.stb.util.DateUtil;
import cn.ohyeah.stb.util.RandomValue;

public class WorldWarEngine extends GameCanvasEngine implements Common{

	private StateMap tm;
	public Player player;
	public Player[] aiNums;
	public PropManager pm;
	public int status, mainIndex, favorIndex;
	public static int ScrW = 0;
	public static int ScrH = 0;
	public DrawGame showGame;
	public SaveGameAttainment saveAttainment;
	private GameRanking[] gameRanking;
	public Propety[] props = new Propety[9];
	public Propety[] card_assign_props = new Propety[9];
	public Propety[] game_props = new Propety[9];
	public int attainmentId;
	public int ran;
	public static boolean isSupportFavor;
	public boolean isFreshMan = true; //是否是新手
	
	public static WorldWarEngine instance = buildGameEngine();
	
	private static WorldWarEngine buildGameEngine() {
		return new WorldWarEngine(WorldWarMIDlet.getInstance());
	}
	
	private WorldWarEngine(MIDlet midlet) {
		super(midlet);
		setRelease(false);
		ScrW = screenWidth;
		ScrH = screenHeight;
		player = new Player();
		pm = new PropManager(this);
		showGame = new DrawGame(this);
		saveAttainment = new SaveGameAttainment(this);
		tm = new StateMap(this);
		status = GAME_STATUS_MAIN_MENU;
	}
	
	protected void loop() {
		switch (status) {
		case GAME_STATUS_MAIN_MENU:
			processGameMenu();
			break;
		case GAME_STATUS_PLAYING:
			processPlaying();
			break;
		case GAME_STATUS_HELP:
			processHelp();
			break;
		case GAME_STATUS_RANKING:
			processRanking();
			break;
		}
		
		switch (status) {
		case GAME_STATUS_MAIN_MENU:
			showGameMenu(g);
			break;
		case GAME_STATUS_PLAYING:
			showPlaying(g);
			break;
		case GAME_STATUS_HELP:
			showHelp(g);
			break;
		case GAME_STATUS_RANKING:
			showRanking(g);
			break;
		}
		
	}

	private void showPlaying(SGraphics g) {
		tm.show(g);
	}

	private void processPlaying() {
		ran = RandomValue.getRandInt(5);
		tm.handle(keyState);
	}

	private void showRanking(SGraphics g) {
		// TODO Auto-generated method stub
		
	}

	private void processRanking() {
		// TODO Auto-generated method stub
		
	}

	private void processHelp() {
		// TODO Auto-generated method stub
		
	}

	private void showHelp(SGraphics g) {
		// TODO Auto-generated method stub
		
	}

	private void processGameMenu() {
		
		isSupportFavor = Configurations.getInstance().isFavorWayTelcomgd();
		//isSupportFavor = true;
		java.util.Date gst = new java.util.Date(engineService.getCurrentTime().getTime());
		int year = DateUtil.getYear(gst);
		int month = DateUtil.getMonth(gst);
		attainmentId = year*100+(month+1);
		
		if (keyState.contains(KeyCode.UP) && favorIndex==0) {
			keyState.remove(KeyCode.UP);
			mainIndex = (mainIndex + 5 - 1) % 5;
		} else if (keyState.contains(KeyCode.DOWN) && favorIndex==0) {
			keyState.remove(KeyCode.DOWN);
			mainIndex = (mainIndex + 1) % 5;
		} else if (keyState.contains(KeyCode.OK)) {
			keyState.remove(KeyCode.OK);
			pm.queryAllProps();
			process();
			showGame.clearMainMenu();
			mainIndex=0;
	    } /*else if (keyState.contains(KeyCode.RIGHT) && isSupportFavor) {
	    	keyState.remove(KeyCode.RIGHT);
	    	favorIndex = 1;
	    	
	    } else if (keyState.contains(KeyCode.LEFT) && isSupportFavor) {
	    	keyState.remove(KeyCode.LEFT);
	    	favorIndex = 0;
	    }*/
		if (keyState.contains(KeyCode.BACK)){ //返回键直接退出
			keyState.contains(KeyCode.BACK);
			exit = true;
		}
	}
	
	private void process(){
		if(favorIndex==0){
			if(mainIndex == 0){
				status = GAME_STATUS_PLAYING;
			}else if(mainIndex==1){
				ServiceWrapper sw = getServiceWrapper();
				gameRanking = sw.queryRankingList(0, 10);
				StateRanking sr = new StateRanking();
				sr.processRanking(gameRanking);
			}else if(mainIndex==2){
				StateShop ss = new StateShop();
				ss.processShop(card_assign_props);
			}else if(mainIndex==3){
				StateHelp sh = new StateHelp();
				sh.processHelp();
			}else if(mainIndex==4){
				exit = true;
			}
		}else{ //收藏
			ServiceWrapper sw = getServiceWrapper();
			sw.addFavoritegd();
			PopupText pop = UIResource.getInstance().buildDefaultPopupText();
			if(sw.isServiceSuccessful()){
				pop.setText("收藏成功!");
				pop.popup();
			}else{
				pop.setText("收藏失败,原因: "+sw.getServiceMessage());
				pop.popup();
			}
		}
	}
	
	private void showGameMenu(SGraphics g) {
		showGame.drawMainMenu(g, mainIndex, favorIndex);
	}
	
}
