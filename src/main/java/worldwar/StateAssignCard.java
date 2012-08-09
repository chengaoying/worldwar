package worldwar;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupText;

public class StateAssignCard {
	private WorldWarEngine engine = WorldWarEngine.instance;
	private boolean running;
	//private ShowGameInterface showGame = ShowGameInterface.instance;
	private DrawGame showGame =  engine.showGame;
	private PropManager pm = engine.pm;
	private int indexX, indexY;
	//private short count;
	
	public void processAssignCard(WWMap map, 
			Player[] ais, 
			Player currPlayer, 
			Propety[] card_assign_props, 
			Propety[] game_props,
			Region region,
			StateMap sm)
	{
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			Graphics g = engine.getGraphics();
			while (running) {
				handleAssignCard(keyState, sm);
				if (running) {
					long t1 = System.currentTimeMillis();
					showAssignCard(g, map, ais, currPlayer, card_assign_props, game_props, region, sm);
					engine.flushGraphics();
					System.gc();
					int sleepTime = (int)(125-(System.currentTimeMillis()-t1));
					if (sleepTime <= 0) {
						Thread.sleep(0);
					}
					else {
						Thread.sleep(sleepTime);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			clear();
		}
		
	}

	private void showAssignCard(Graphics g, 
			WWMap map, 
			Player[] ais, 
			Player currPlayer, 
			Propety[] card_assign_props, 
			Propety[] game_props,
			Region region,
			StateMap sm) 
	{
		//map.drawMap(g, ais, currPlayer, game_props, showGame, region, sm);
		sm.showSelectRegion(g);
		showGame.drawCardAssign(g, card_assign_props, game_props, indexX, indexY);
	}

	private void handleAssignCard(KeyState keyState, StateMap sm) {
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			running = false;
			showGame.clearCardAssign();
			sm.startGame = true;
		}
		if (keyState.containsAndRemove(KeyCode.LEFT)) {
			if(indexX>0){
				indexX--;
			}else{
				indexX=0;
			}
		}
		if (keyState.containsAndRemove(KeyCode.RIGHT)) {
			if(indexX<2){
				indexX++;
			}else{
				indexX=3;
				indexY=0;
			}
		}
		if (keyState.containsAndRemove(KeyCode.UP)) {
			if(indexY>0){
				indexY--;
			}else{
				indexY=0;
			}
		}
		if (keyState.containsAndRemove(KeyCode.DOWN)) {
			if(indexX == 3){
				if(indexY<1){
					indexY++;
				}else{
					indexY=1;
				}
			}else{
				if(indexY<2){
					indexY++;
				}else{
					indexY=2;
				}
			}
		}
		
		if(keyState.containsAndRemove(KeyCode.OK)){
			if(indexX==3 && indexY==0){
				sm.startGame = true;
				StateShop ss = new StateShop();
				ss.processShop(pm.props);
			}else if(indexX==3 && indexY==1){
				running = false;
				sm.startGame = true;
			}else{
				if(pm.getGamePropNums(pm.game_props)<5){
					pm.assignProps(indexX, indexY);
				}else{
					PopupText pt = UIResource.getInstance().buildDefaultPopupText();
					pt.setText("¿¨²ÛÒÑ¾­ÂúÁË!");
					pt.popup();
				}
			}
			
		}
	}

	private void clear() {
		showGame.clearCardAssign();
		//showGame.clearMapBG();
	}

}
