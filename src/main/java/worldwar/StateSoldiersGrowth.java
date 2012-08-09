package worldwar;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

public class StateSoldiersGrowth {
	
	private WorldWarEngine engine = WorldWarEngine.instance;
	private boolean running;
	private DrawGame showGame = engine.showGame;
	
	
	public void processSoldiersGrowth(Player p, StateMap sm){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			Graphics g = engine.getGraphics();
			while (running) {
				handleSoldiersGrowth(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showSoldiersGrowth(g, p, sm);
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

	private void showSoldiersGrowth(Graphics g, Player p, StateMap sm) {
		
		/*ÖØ»æ*/
		sm.showSelectRegion(g);
		
		Image plus = Resource.loadImage(Resource.id_plus);
		Region[] regions = sm.serachRegionsByInfId(p.getInfluenceId());
		
		for(int i=0;i<regions.length;i++){
			int y = regions[i].getCenterTileY()-10;
			if(regions[i].getGrowthSoldiers()>0){
				g.drawImage(plus, regions[i].getCenterTileX(), y, 0);
				showGame.drawNum(g, regions[i].getGrowthSoldiers(), regions[i].getCenterTileX()+25, y, false);
			}
		}
		if(sm.timePass(1000)){
			running = false;
		}
	}

	private void handleSoldiersGrowth(KeyState keyState) {
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			//running = false;
		}
	}
	
	

	private void clear() {
		//Resource.freeImage(Resource.id_plus);
		//Resource.freeImage(Resource.id_fighting_num);
		//showGame.clearMapBG();
	}
}
