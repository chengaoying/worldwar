package worldwar;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

public class StateHelp {
	
	private WorldWarEngine engine = WorldWarEngine.instance;
	private boolean running;
	private DrawGame showGame = engine.showGame;
	private int index,pageIndex;
	
	public void processHelp(){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			Graphics g = engine.getGraphics();
			while (running) {
				handleHelp(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showHelp(g);
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

	private void showHelp(Graphics g) {
		showGame.drawHelp(g, index, pageIndex);
	}

	private void handleHelp(KeyState keyState) {
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			running = false;
		}
		if(keyState.containsAndRemove(KeyCode.OK)){
			if(pageIndex==0){
				if(index>0){
					index--;
				}
			}
			if(pageIndex==1){
				if(index<3){
					index++;
				}
			}
		}
		if(keyState.containsAndRemove(KeyCode.LEFT)){
			pageIndex=0;
		}
		if(keyState.containsAndRemove(KeyCode.RIGHT)){
			pageIndex=1;
		}
	}
	
	private void clear() {
		showGame.clearHelp();
	}
}
