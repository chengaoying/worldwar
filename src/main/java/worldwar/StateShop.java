package worldwar;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.game.StateRecharge;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

public class StateShop {
	
	private WorldWarEngine engine = WorldWarEngine.instance;
	private boolean running;
	private DrawGame showGame = engine.showGame;
	private int shopX, shopY;
	private PropManager pm = new PropManager(engine);
	
	
	public void processShop(Propety[] props){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			Graphics g = engine.getGraphics();
			while (running) {
				handleShop(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showShop(g, props);
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

	private void showShop(Graphics g, Propety[] props) {
		showGame.drawShop(g, shopX, shopY, props);
	}

	private void handleShop(KeyState keyState) {
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			running = false;
			showGame.clearShop();
		}
		if (keyState.containsAndRemove(KeyCode.LEFT)) {
			if(shopX>0){
				shopX--;
			}else{
				shopX=0;
			}
		}
		if (keyState.containsAndRemove(KeyCode.RIGHT)) {
			if(shopY<3){
				if(shopX<2){
					shopX++;
				}else{
					shopX=2;
				}
			}else{
				if(shopX<1){
					shopX++;
				}else{
					shopX=1;
				}
			}
		}
		if (keyState.containsAndRemove(KeyCode.UP)) {
			if(shopY>0){
				shopY--;
			}else{
				shopY=0;
			}
		}
		if (keyState.containsAndRemove(KeyCode.DOWN)) {
			if(shopY<2){
				shopY++;
			}else{
				shopY=3;
				shopX=0;
			}
		}
		
		if(keyState.containsAndRemove(KeyCode.OK)){
			if(shopX==1 && shopY==3){ //返回
				running = false;
				showGame.clearShop();
			} else	if(shopY==3 && shopX==0){ //进入充值
				showGame.clearShop();
				StateRecharge recharge = new StateRecharge(engine);
				recharge.recharge();
			}else{
				pm.purchaseProp(shopX, shopY); //购买道具
			}
			
		}
	}
	
	

	private void clear() {
		showGame.clearShop();
	}
}
