package worldwar;

import cn.ohyeah.itvgame.model.GameRanking;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

public class StateRanking {
	
	private WorldWarEngine engine = WorldWarEngine.instance;
	private boolean running;
	private DrawGame showGame = engine.showGame;
	
	
	public void processRanking(GameRanking[] gameRanking){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			while (running) {
				handleRanking(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showRanking(g,gameRanking);
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

	private void showRanking(SGraphics g, GameRanking[] gameRanking) {
		showGame.drawRanking(g, gameRanking);
	}

	private void handleRanking(KeyState keyState) {
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			running = false;
		}
		if(keyState.containsAndRemove(KeyCode.OK)){
		}
	}
	
	

	private void clear() {
		showGame.clearRanking();
	}
}
