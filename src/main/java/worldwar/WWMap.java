package worldwar;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class WWMap extends Map implements Common{
	
	private DrawGame showGame = null;

	 public static WWMap loadWWMap(String path)
	 {
	    	InputStream is = null;
	    	try {
	    		is = WWMap.class.getResourceAsStream(path);
	    		WWMap map = new WWMap();
				map.deserialize(is);
				return map;
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
			finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	    }
	 
	 public void drawMap(Graphics g, 
			 Player[] ais, 
			 Player currPlayer, 
			 Propety[] game_props, 
			 DrawGame show, 
			 Region region,
			 StateMap sm){
		 
		 showGame = show;
		 showGame.drawMapBG(g, game_props, currPlayer);
		 drawMap(g);
		 /*if(sm.regionSelectedOrChoice==REGION_CHOICE){
			 drawRegion(g, region, 0Xff0000);
		 }else if(sm.regionSelectedOrChoice==REGION_SELECTED){
			 drawRegion(g, region, 0Xffff00);
		 }*/
		 drawRegion(g, region, 0Xff0000);
		 Region r;
		 for(int i=0;i<getRegions().length;i++){
			 r = getRegions()[i];
			 if(r.isSelected()){
				 drawRegion(g, r, 0Xffff00);
			 }
			 Influence inf = getInfluence(r.getInfluenceId());
			 showGame.drawSoldiers(g, r, inf, r.getCenterTileX(), r.getCenterTileY());
			 Font font = g.getFont();
			 String nums = String.valueOf(r.getSoldiers());
			 int w = font.stringWidth(nums)/2;
			 int h = font.getHeight()/2;
			 showGame.drawNum(g, r.getSoldiers(), r.getCenterTileX()-w+Abs_Coords_X, r.getCenterTileY()-h+Abs_Coords_Y, true);
			 if(r.getPropId()==DEFENSE_CARD || r.getPropId()==RETREAT_CARD || r.getPropId()==LANDMINE_CARD || r.getPropId()==HIDDEN_CARD){
				 if(sm.player.getInfluenceId()==r.getInfluenceId()
						 || sm.player.isInvestigateCard()){
					 showGame.drawCardIcon(g, r.getPropId(), r.getCenterTileX()-w, r.getCenterTileY()-h);
				 }
			 }
			 if(r.getPropId2()==ARMOURED_CARD){
				 if(sm.player.getInfluenceId()==r.getInfluenceId()
						 || sm.player.isInvestigateCard()){
					 showGame.drawCardIcon(g, r.getPropId2(), r.getCenterTileX()-w+5, r.getCenterTileY()-h+5);
				 }
			 }
		 }
		 showGame.showPlayerInfo(g, ais, sm, currPlayer);
	 }
	 
}
