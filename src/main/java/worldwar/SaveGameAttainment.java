package worldwar;

import cn.ohyeah.itvgame.model.GameAttainment;
import cn.ohyeah.itvgame.model.GameRecord;
import cn.ohyeah.stb.game.ServiceWrapper;


public class SaveGameAttainment {
	
	private WorldWarEngine engine;
	public SaveGameAttainment(WorldWarEngine engine) {
		this.engine = engine;
	}

	public int saveGameAttainment(int scores){
		ServiceWrapper sw = engine.getServiceWrapper();
		GameAttainment attainment = new GameAttainment();
		attainment.setAttainmentId(engine.attainmentId);
		attainment.setScores(scores);
		attainment.setRemark("游戏成就");
		sw.saveAttainment(attainment);
			
		return sw.getServiceResult();
	}
	
	public int saveGameRecord(){
		ServiceWrapper sw = engine.getServiceWrapper();
		GameRecord gr = new GameRecord();
		gr.setRecordId(engine.attainmentId);
		gr.setRemark("游戏记录");
		sw.saveRecord(gr);
		return sw.getServiceResult();
	}
	
	public int readGameRecord(int recordId){
		ServiceWrapper sw = engine.getServiceWrapper();
		sw.readRecord(recordId);
		return sw.getServiceResult();
	}
}
