package worldwar;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyState;

public class StateExplosion implements Common{
	
	private WorldWarEngine engine = WorldWarEngine.instance;
	private boolean running;
	//private DrawGame showGame = engine.showGame;
	private int index, flag;
	
	
	public void processSoldiersGrowth(Region r, StateMap sm){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			while (running) {
				handleExplosion(keyState, sm);
				if (running) {
					long t1 = System.currentTimeMillis();
					showExplosion(g, r, sm);
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

	private void showExplosion(SGraphics g, Region r, StateMap sm) {
		
		/*重绘地图*/
		sm.showSelectRegion(g);
		
		Image fail = Resource.loadImage(Resource.id_fight_fail);
		int w = fail.getWidth()/5;
		int h = fail.getHeight();
		int mapx = r.getCenterTileX() - w/2;
		int mapy = r.getCenterTileY() - h/2;
		g.drawRegion(fail, index*fail.getWidth()/5, 0, w, h, 0, mapx, mapy, 0);
		if(flag<1){
			flag++;
		}else{
			index++;
			flag=0;
		}
		if(index==4){
			running = false;
		}
	}

	private void handleExplosion(KeyState keyState, StateMap sm) {
		/*if (keyState.containsAndRemove(KeyCode.NUM0)) {
			PopupConfirm pc = UIResource.getInstance()
					.buildDefaultPopupConfirm();
			pc.setText("确定要退出游戏吗?");
			int index = pc.popup();
			if (index == 0) {
				// 同步道具 
				sm.pm.sysProps();
				sm.player.setUsePropTimes(0);
				engine.status = GAME_STATUS_MAIN_MENU;
				sm.state = STATE_SEL_MAP;
				StateMap.map = null;
				showGame.clearMapBG();
			}
		}*/
	}
	
	

	private void clear() {
		//Resource.freeImage(Resource.id_fight_fail);
		//showGame.clearMapBG();
	}
}
