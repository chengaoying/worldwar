package worldwar;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

public class StateSuccessOrFail {
	private WorldWarEngine engine = WorldWarEngine.instance;
	private boolean running;
	private DrawGame showGame = engine.showGame;
	private int index;

	/*isSuccess: 0游戏成功, 1游戏失败*/
	public int processGameSuccessOrFail(int isSuccess){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			Graphics g = engine.getGraphics();
			while (running) {
				handleGameSuccessOrFail(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showGameSuccessOrFail(g,isSuccess);
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
		return index;
		
	}

	private void clear() {
		showGame.clearSuccessOrFail();
	}

	private void showGameSuccessOrFail(Graphics g, int isSuccess) {
		showGame.drawSuccessOrFail(g, isSuccess, index);
		
	}

	private void handleGameSuccessOrFail(KeyState keyState) {
		if(keyState.containsAndRemove(KeyCode.LEFT)){
			if(index>0){
				index--;
			}
		}
		if(keyState.containsAndRemove(KeyCode.RIGHT)){
			if(index<2){
				index++;
			}
		}
		if(keyState.containsAndRemove(KeyCode.OK)){
			running = false;
		}
	}
}
