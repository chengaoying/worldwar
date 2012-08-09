package worldwar;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.util.RandomValue;

public class StateFighting implements Common{
	
	private WorldWarEngine engine = WorldWarEngine.instance;
	private boolean running;
	private DrawGame showGame = engine.showGame;

	private WWMap map = StateMap.map;
	private Region region_attack, region_embattled;
	public static long startTime, endTime;
	private int attack_values, embattled_values;
	private int index;  //1,赢   -1,输
	
	public int processFighting(Region r1, Region r2, StateMap sm){
		region_attack = r1;
		region_embattled = r2;
		running = true;
		if(region_attack.getPropId2()==ARMOURED_CARD){	//使用装甲卡
			attack_values = combatValue(region_attack)+6;
			//System.out.println("装甲卡的效果值:"+6);
		}else{
			attack_values = combatValue(region_attack);
		}
		if(region_embattled.getPropId()==DEFENSE_CARD){	//使用防御卡
			embattled_values = combatValue(region_embattled)+6;
			//System.out.println("防御卡效果值:"+6);
		}else{
			embattled_values = combatValue(region_embattled);
		}
		try {
			KeyState keyState = engine.getKeyState();
			Graphics g = engine.getGraphics();
			while (running) {
				handleFighting(keyState, sm);
				if (running) {
					long t1 = System.currentTimeMillis();
					showFighting(g, sm);
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
		//showGame.clearFighting();
		//showGame.clearMapBG();
	}

	
	private void showFighting(Graphics g, StateMap sm) {
		
		/*重绘地图*/
		sm.showSelectRegion(g);
		showGame.drawFighting(g, 
				map.getInfluence(region_attack.getInfluenceId()).getColor(), 
				map.getInfluence(region_embattled.getInfluenceId()).getColor(), 
				region_attack, 
				region_embattled,
				attack_values,
				embattled_values);
		
	}

	
	
	private void handleFighting(KeyState keyState, StateMap sm) {
		
		if(DrawGame.isOver){
			Player p = sm.serachAIByRegion(region_embattled);
			if(attack_values>embattled_values){
				int soliders = region_embattled.getSoldiers();
				region_embattled.setInfluenceId(region_attack.getInfluenceId());
				region_embattled.setSoldiers((short)(region_attack.getSoldiers()-1));
				region_attack.setSoldiers((short)1);
				if(region_embattled.getPropId()==LANDMINE_CARD){ //防守方使用地雷卡
					region_embattled.setSoldiers((short)1);
				}
				if(region_embattled.getPropId()==RETREAT_CARD){	 //防守方使用了撤退卡	
					p.setSoliders(p.getSoliders()+soliders);
				}
				region_embattled.setPropId(0);
				index = 1;
			}else{
				region_attack.setSoldiers((short)1);
				index = -1;
			}
			DrawGame.isOver = false;
			showGame.count=0;
			running = false;
			StateMap.isSelectedAttack = false;
			StateMap.isSelectedEmbattled = false;
			startTime = System.currentTimeMillis();
		}
	}
	
	/*随机值*/
	private int combatValue(Region r){
		int m=0;
		for(int i=0;i<r.getSoldiers();i++){
			m += RandomValue.getRandInt(1, 7);
		}
		return m;
	}
	
}
