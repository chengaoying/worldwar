package worldwar;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

public class StateFreshMan {
	
	private WorldWarEngine engine = WorldWarEngine.instance;
	private boolean running;
	private DrawGame showGame = engine.showGame;
	private int index;
	
	public void processFreshMan(){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			while (running) {
				handleFreshMan(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showFreshMan(g);
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

	private void showFreshMan(SGraphics g) {
		showGame.drawFreshMan(g, index);
	}

	private void handleFreshMan(KeyState keyState) {
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			running = false;
		}
		if(keyState.containsAndRemove(KeyCode.OK)){
			if(index<1){
				index++;
			}else{
				running = false;
			}
		}
	}
	
	private void clear() {
		showGame.clearFreshMan();
	}
}
