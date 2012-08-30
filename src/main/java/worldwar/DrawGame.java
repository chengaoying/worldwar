package worldwar;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import cn.ohyeah.itvgame.model.GameRanking;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.ui.DrawUtil;
import cn.ohyeah.stb.ui.TextView;

public class DrawGame implements Common{

	
	 private WorldWarEngine engine;
	 private int ran;
	 
	 public DrawGame(WorldWarEngine e){
			this.engine = e;
			ran = e.ran;
		}
	 
	 private byte TopLeft = Graphics.TOP | Graphics.LEFT;
	 private int id_dice, id_dice2, id_solider=0, id_tank=0, id_airplane=0, id_role;
	 private int id_solider_fighting=0, id_tank_fighting=0, id_airplane_fighting=0;
	 private int diceIndex, diceFlag;
	 public int count; 
	 public static boolean diceOver, isOver, begin;
	 private int pixelInterval = 5;
	 private int index, flag, interval=1;
	 private int mapy;
	 public int[][] cardCoord = new int[7][7]; 
	 private short fontSize = 13;
	 
	/*主菜单*/
	public void drawMainMenu(SGraphics g, int index, int favorIndex){
		Image main_bg = Resource.loadImage(Resource.id_main_bg);
		Image main_menu = Resource.loadImage(Resource.id_main_menu);
		g.drawImage(main_bg, 0+Abs_Coords_X, 0+Abs_Coords_Y, TopLeft);
		for (int i = 0; i < Resource.menuAxis.length; ++i) {
			g.drawRegion(main_menu, (index != i) ? Resource.menuW : 0, 
					i*Resource. menuH, Resource.menuW, Resource.menuH, 0, 
					Resource.menuAxis[i][0]+Abs_Coords_X, Resource.menuAxis[i][1]+Abs_Coords_Y, 0);
		}
		/*if(WorldWarEngine.isSupportFavor){
			Image imgFavor = Resource.loadImage(Resource.id_favorites);
			g.drawImage(imgFavor, 537, 442, TopLeft);
			if(favorIndex==1){
				DrawUtil.drawRect(g, 537, 442, 101, 84, 2, 0XFFFF00);
			}
		}*/
	}
	
	/*选择地图*/
	public void drawSelectMapMenu(SGraphics g, int index){
		Image main_bg = Resource.loadImage(Resource.id_main_bg);
		Image map_menu = Resource.loadImage(Resource.id_map_menu);
		g.drawImage(main_bg, 0+Abs_Coords_X, 0+Abs_Coords_Y, TopLeft);
		for (int i = 0; i < Resource.menuAxis.length; ++i) {
			g.drawRegion(map_menu, (index != i) ? Resource.menuW : 0, 
					i*Resource. menuH, Resource.menuW, Resource.menuH, 0, 
					Resource.menuAxis[i][0]+Abs_Coords_X, Resource.menuAxis[i][1]+Abs_Coords_Y, 0);
		}
	}
	
	/*地图背景*/
	public void drawMapBG(SGraphics g, Propety[] game_props, Player currPlayer){
		Image map_bg = Resource.loadImage(Resource.id_map_bg);
		Image card = Resource.loadImage(Resource.id_card);
		g.drawImage(map_bg, 0+Abs_Coords_X, 0+Abs_Coords_Y, 0);
		
		/*随机道具*/
		int xOffset = 99;
		for(int i=0,k=0;i<currPlayer.getProps().length;i++){
			
			for(int j=0;j<currPlayer.getProps()[i].getNums();j++){
				if(cardCoord[k][0]==1){
					cardCoord[k][1] = currPlayer.getProps()[i].getPropId();
					g.drawRegion(card, currPlayer.getProps()[i].getId()*49, 0, 49, 67, 0, xOffset + Abs_Coords_X, 450+Abs_Coords_Y, TopLeft);
					drawNum(g, k+1, xOffset + Abs_Coords_X, 450+Abs_Coords_Y, true);
					int color = g.getColor();
					g.setColor(0Xffffff);
					engine.setFont(fontSize);
					TextView.showMultiLineText(g, Resource.info2[game_props[i].getId()], 5, 520+Abs_Coords_X, 448+Abs_Coords_Y, 110, 69);
				/*	engine.setFont(12);
					TextView.showMultiLineText(g, Resource.info2[game_props[i].getId()], 5, 520, 448, 110, 69);*/
					engine.setDefaultFont();
					g.setColor(color);
				}else{
					g.drawRegion(card, currPlayer.getProps()[i].getId()*49, 0, 49, 67, 0, xOffset + Abs_Coords_X, 460+Abs_Coords_Y, TopLeft);
					drawNum(g, k+1, xOffset + Abs_Coords_X, 460+Abs_Coords_Y, true);
				}
				k++;
				xOffset += 54;
			}
		}
		
		/*购买的道具*/
	    xOffset = 224;
		if(currPlayer.getId()== StateMap.playerIndex){  //玩家自己
			for(int i=0,k=0;i<game_props.length;i++){
				for(int j=0;j<game_props[i].getNums();j++){
					
					if(cardCoord[k+2][0]==1){
						cardCoord[k+2][1] = game_props[i].getPropId();
						g.drawRegion(card, game_props[i].getId()*49, 0, 49, 67, 0, xOffset + Abs_Coords_X, 450+Abs_Coords_Y, TopLeft);
						drawNum(g, k+3, xOffset + Abs_Coords_X, 450+Abs_Coords_Y, true);
						int color = g.getColor();
						g.setColor(0Xffffff);
						engine.setFont(fontSize);
						TextView.showMultiLineText(g, Resource.info2[game_props[i].getId()], 5, 520+Abs_Coords_X, 448+Abs_Coords_Y, 110, 69);
						/*engine.setFont(15);
						TextView.showMultiLineText(g, Resource.info2[game_props[i].getId()], 5, 520, 448, 110, 69);*/
						engine.setDefaultFont();
						g.setColor(color);
					}else{
						g.drawRegion(card, game_props[i].getId()*49, 0, 49, 67, 0, xOffset + Abs_Coords_X, 460+Abs_Coords_Y, TopLeft);
						drawNum(g, k+3, xOffset + Abs_Coords_X, 460+Abs_Coords_Y, true);
					}
					k++;
					xOffset += 54;
				}
			}
		}
	}
	
	/*使用卡片在地图上的图标(防御卡, 撤退卡, 地雷卡,隐藏卡, 装甲卡)*/
	public void drawCardIcon(SGraphics g, int propId, int x, int y){
		Image icon = Resource.loadImage(Resource.id_card_icon);
		if(propId<=46){
			g.drawRegion(icon, (propId-44)*icon.getWidth()/5, 0, icon.getWidth()/5, icon.getHeight(), 0, x+13+Abs_Coords_X, y+Abs_Coords_Y, TopLeft);
		}else if(propId==HIDDEN_CARD){	//隐藏卡片图标
			g.drawRegion(icon, 3*icon.getWidth()/5, 0, icon.getWidth()/5, icon.getHeight(), 0, x+13+Abs_Coords_X, y+Abs_Coords_Y, TopLeft);
		}else if(propId==ARMOURED_CARD){
			g.drawRegion(icon, 4*icon.getWidth()/5, 0, icon.getWidth()/5, icon.getHeight(), 0, x+13+Abs_Coords_X, y+Abs_Coords_Y, TopLeft);

		}
	}
	
	/*游戏成功或失败      isSuccess: 0成功, 1失败*/
	public void drawSuccessOrFail(SGraphics g, int isSuccess, int index){
		Image card_bg = Resource.loadImage(Resource.id_card_bg);
		Image over = Resource.loadImage(Resource.id_over);
		Image over_menu = Resource.loadImage(Resource.id_over_menu);
		
		int mapx = 110+Abs_Coords_X, mapy=105+Abs_Coords_Y;
		g.drawImage(card_bg, mapx, mapy, TopLeft);
		g.drawRegion(over, isSuccess*over.getWidth()/2, 0, over.getWidth()/2, over.getHeight(), 0, mapx+90, mapy+30, TopLeft);
		
		if(index==0){
			g.drawRegion(over_menu, 0, 0, over_menu.getWidth()/2, over_menu.getHeight()/3, 0, mapx+33, mapy+255, TopLeft);
			g.drawRegion(over_menu, over_menu.getWidth()/2, over_menu.getHeight()/3, over_menu.getWidth()/2, over_menu.getHeight()/3, 0, mapx+160, mapy+255, TopLeft);
			g.drawRegion(over_menu, over_menu.getWidth()/2, 2*over_menu.getHeight()/3, over_menu.getWidth()/2, over_menu.getHeight()/3, 0, mapx+280, mapy+255, TopLeft);
		}else if(index==1){
			g.drawRegion(over_menu, over_menu.getWidth()/2, 0, over_menu.getWidth()/2, over_menu.getHeight()/3, 0, mapx+33, mapy+255, TopLeft);
			g.drawRegion(over_menu, 0, over_menu.getHeight()/3, over_menu.getWidth()/2, over_menu.getHeight()/3, 0, mapx+160, mapy+255, TopLeft);
			g.drawRegion(over_menu, over_menu.getWidth()/2, 2*over_menu.getHeight()/3, over_menu.getWidth()/2, over_menu.getHeight()/3, 0, mapx+280, mapy+255, TopLeft);
		}else{
			g.drawRegion(over_menu, over_menu.getWidth()/2, 0, over_menu.getWidth()/2, over_menu.getHeight()/3, 0, mapx+33, mapy+255, TopLeft);
			g.drawRegion(over_menu, over_menu.getWidth()/2, over_menu.getHeight()/3, over_menu.getWidth()/2, over_menu.getHeight()/3, 0, mapx+160, mapy+255, TopLeft);
			g.drawRegion(over_menu, 0, 2*over_menu.getHeight()/3, over_menu.getWidth()/2, over_menu.getHeight()/3, 0, mapx+280, mapy+255, TopLeft);
		}
	}
	
	/*卡片分配*/
	public void drawCardAssign(SGraphics g, Propety[] props, Propety[] game_props,  int indexX, int indexY){
		Image card = Resource.loadImage(Resource.id_card);
		Image card_bg = Resource.loadImage(Resource.id_card_bg);
		Image card_name = Resource.loadImage(Resource.id_card_name);
		Image card_menu = Resource.loadImage(Resource.id_card_menu);
		Image card_info = Resource.loadImage(Resource.id_card_info);
		Image card_tag = Resource.loadImage(Resource.id_card_tag);
		int mapx=110, mapy=105;
		
		g.drawImage(card_bg, mapx+Abs_Coords_X, mapy+Abs_Coords_Y, TopLeft);
		g.drawImage(card_tag, mapx+270+Abs_Coords_X, mapy+20+Abs_Coords_Y, TopLeft);
		g.drawImage(card_info, mapx+265+Abs_Coords_X, mapy+53+Abs_Coords_Y, TopLeft);
		
		if(indexX<3){
			engine.setFont(fontSize);
			g.setColor(0Xffffff);
			TextView.showMultiLineText(g, Resource.info2[getIndex(indexX, indexY)], 5, 387+Abs_Coords_X, 172+Abs_Coords_Y, 95, 125);
			engine.setDefaultFont();
		}
		
		if(indexY==0 && indexX==3){
			g.drawRegion(card_menu, 0, 0, 100, 40, 0, mapx+275+Abs_Coords_X, mapy+210+Abs_Coords_Y, TopLeft);
		}else{
			g.drawRegion(card_menu, 100, 0, 100, 40, 0, mapx+275+Abs_Coords_X, mapy+210+Abs_Coords_Y, TopLeft);
		}
		if(indexY==1 && indexX==3){
			g.drawRegion(card_menu, 0, 40, 100, 40, 0, mapx+275+Abs_Coords_X, mapy+250+Abs_Coords_Y, TopLeft);
		}else{
			g.drawRegion(card_menu, 100, 40, 100, 40, 0, mapx+275+Abs_Coords_X, mapy+250+Abs_Coords_Y, TopLeft);
		}
		
		int card_spaceX = 81, card_spaceY = 95, name_spaceX = 82, name_spaceY = 95;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(indexX==j && indexY==i){
					DrawUtil.drawRect(g, mapx+(name_spaceX)*j+20+Abs_Coords_X, mapy+(card_spaceY)*i+20+Abs_Coords_Y, 63, 89, 3, 0Xffff00);
				}
				g.drawRegion(card, getIndex(j, i)*49, 0, 49, 67, 0, mapx+(card_spaceX)*j+28+Abs_Coords_X, mapy+(card_spaceY)*i+20+Abs_Coords_Y, TopLeft);
				g.drawImage(card_name, mapx+(name_spaceX)*j+20+Abs_Coords_X, mapy+(name_spaceY)*i+88+Abs_Coords_Y, TopLeft);
				drawNum(g, props[getIndex(j, i)].getNums(), mapx+(name_spaceX)*j+40+Abs_Coords_X, mapy+(name_spaceY)*i+88+Abs_Coords_Y, true);
			}
		}
		
		for(int i=0,k=0;i<game_props.length;i++){
			for(int j=0;j<game_props[i].getNums();j++){
				if(cardCoord[k+2][0]==1){
					cardCoord[k+2][1] = game_props[i].getPropId();
					g.drawRegion(card, game_props[i].getId()*49, 0, 49, 67, 0, 224+(k*54)+Abs_Coords_X, 450+Abs_Coords_Y, TopLeft);
					drawNum(g, k+3, 224+(k*54)+Abs_Coords_X, 450+Abs_Coords_Y, true);
					int color = g.getColor();
					g.setColor(0Xffffff);
					engine.setFont(fontSize);
					TextView.showMultiLineText(g, Resource.info2[game_props[i].getId()], 5, 520+Abs_Coords_X, 448+Abs_Coords_Y, 110, 69);
					engine.setDefaultFont();
					g.setColor(color);
				}else{
					g.drawRegion(card, game_props[i].getId()*49, 0, 49, 67, 0, 224+(k*54)+Abs_Coords_X, 460+Abs_Coords_Y, TopLeft);
					drawNum(g, k+3, 224+(k*54)+Abs_Coords_X, 460+Abs_Coords_Y, true);
				}
				k++;
			}
		}
	}
	
	/*商城*/
	public void drawShop(SGraphics g, int shopX, int shopY, Propety[] props){
		Image card = Resource.loadImage(Resource.id_card);
		Image shop_bg = Resource.loadImage(Resource.id_interface_bg);
		Image info_bg = Resource.loadImage(Resource.id_info_bg);
		Image shop_title = Resource.loadImage(Resource.id_shop_title);
		Image shop_balance = Resource.loadImage(Resource.id_shop_balance);
		Image shop_price = Resource.loadImage(Resource.id_shop_price);
		Image shop_card_bg = Resource.loadImage(Resource.id_shop_card_bg);
		Image shop_recharge = Resource.loadImage(Resource.id_shop_recharge);
		
		g.drawImage(shop_bg, 0+Abs_Coords_X, 0+Abs_Coords_Y, TopLeft);
		g.drawImage(info_bg, 35+Abs_Coords_X, 350+Abs_Coords_Y, TopLeft);
		g.drawImage(shop_title, 250+Abs_Coords_X, 20+Abs_Coords_Y, TopLeft);
		g.drawImage(shop_balance, 31+Abs_Coords_X, 470+Abs_Coords_Y, TopLeft);
		drawNum(g, engine.getEngineService().getBalance(), 101+Abs_Coords_X, 470+Abs_Coords_Y, true);
		
		if(shopY==3 && shopX==1){
			g.drawRegion(shop_recharge, 0, 40, 100, 40, 0, 495+Abs_Coords_X, 470+Abs_Coords_Y, TopLeft);
		}else{
			g.drawRegion(shop_recharge, 100, 40, 100, 40, 0, 495+Abs_Coords_X, 470+Abs_Coords_Y, TopLeft);
		}
		if(shopY==3 && shopX==0){
			g.drawRegion(shop_recharge, 0, 0, 100, 40, 0, 350+Abs_Coords_X, 470+Abs_Coords_Y, TopLeft);
		}else{
			g.drawRegion(shop_recharge, 100, 0, 100, 40, 0, 350+Abs_Coords_X, 470+Abs_Coords_Y, TopLeft);
		}
		
		int x = 31, y = 69, spaceX = 25, spaceY = 8;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				g.drawRegion(shop_card_bg, 179, 0, 179, 89, 0, x+(spaceX+179)*j+Abs_Coords_X, y+(spaceY+89)*i+Abs_Coords_Y, TopLeft);
			}
		}
		int mapx = 25, mapy = 63;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(shopX==j && shopY==i){
					g.drawRegion(shop_card_bg, 0, 0, 179, 89, 0, mapx+(spaceX+179)*j+Abs_Coords_X, mapy+(spaceY+89)*i+Abs_Coords_Y, TopLeft);
					g.drawRegion(card, getIndex(j, i)*49, 0, 49, 67, 0, mapx+(spaceX+179)*j+10+Abs_Coords_X, mapy+(spaceY+89)*i+9+Abs_Coords_Y, TopLeft);
					g.drawImage(shop_price, mapx+(spaceX+179)*j+65+Abs_Coords_X, mapy+(spaceY+89)*i+12+Abs_Coords_Y, TopLeft);
					drawNum(g, props[getIndex(j, i)].getPrice(), mapx+(spaceX+179)*j+130+Abs_Coords_X, mapy+(spaceY+89)*i+12+Abs_Coords_Y, true);
					drawNum(g, props[getIndex(j, i)].getNums(), mapx+(spaceX+179)*j+130+Abs_Coords_X, mapy+(spaceY+89)*i+44+Abs_Coords_Y, true);
				}else{
					g.drawRegion(shop_card_bg, 0, 0, 179, 89, 0, x+(spaceX+179)*j+Abs_Coords_X, y+(spaceY+89)*i+Abs_Coords_Y, TopLeft);
					g.drawRegion(card, getIndex(j, i)*49, 0, 49, 67, 0, x+(spaceX+179)*j+10+Abs_Coords_X, y+(spaceY+89)*i+9+Abs_Coords_Y, TopLeft);
					g.drawImage(shop_price, x+(spaceX+179)*j+65+Abs_Coords_X, y+(spaceY+89)*i+12+Abs_Coords_Y, TopLeft);
					drawNum(g, props[getIndex(j, i)].getPrice(), x+(spaceX+179)*j+130+Abs_Coords_X, y+(spaceY+89)*i+12+Abs_Coords_Y, true);
					drawNum(g, props[getIndex(j, i)].getNums(), x+(spaceX+179)*j+130+Abs_Coords_X, y+(spaceY+89)*i+44+Abs_Coords_Y, true);
				}
			}
		}
		if(shopY<3){
			engine.setFont(19);
			g.setColor(0Xffffff);
			TextView.showMultiLineText(g, Resource.info[getIndex(shopX, shopY)], 5, 56+Abs_Coords_X, 385+Abs_Coords_Y, 522, 55);
			engine.setDefaultFont();
		}
	}
	
	private int getIndex(int x, int y){
		if(x==0 && y==0)return 0;
		if(x==1 && y==0)return 1;
		if(x==2 && y==0)return 2;
		if(x==0 && y==1)return 3;
		if(x==1 && y==1)return 4;
		if(x==2 && y==1)return 5;
		if(x==0 && y==2)return 6;
		if(x==1 && y==2)return 7;
		if(x==2 && y==2)return 8;
		return -1;
	}
	
	/*玩家信息*/
	public void showPlayerInfo(SGraphics g, Player[] ais, StateMap sm, Player currPlayer){
		
		Image playerImg = Resource.loadImage(Resource.id_roleFlame);
		final int palyerWidth = playerImg.getWidth()/6;
		int mapx = 12;
		for(int i=0;i<ais.length;i++,mapx += 3+palyerWidth){
			
			Player p = ais[i];
			if(p!=null && p.getState() != GAME_OVER){
				int id_AI = Resource.getColorLocation(p.getColor());
				//int mapx = 12+i*(3+palyerWidth);
				g.drawRegion(playerImg, id_AI*palyerWidth, 0, palyerWidth, playerImg.getHeight(), 0, mapx+Abs_Coords_X, 0+Abs_Coords_Y, TopLeft);
				int num = sm.serachRegionsByInfId(ais[i].getInfluenceId()).length;
				if(num<10){
					drawNum(g, num, mapx+(84-13)+Abs_Coords_X, 68-25+Abs_Coords_Y, false);
				}else{
					drawNum(g, num, mapx+(84-26)+Abs_Coords_X, 68-25+Abs_Coords_Y, false);
				}
				id_role = Resource.getSoliderId_fighting(p.getColor());
				if(id_role == Resource.id_soldier_2){
					id_role = Resource.id_role_yellow;
				}
				Image solider = Resource.loadImage(id_role);
				Image playerName = Resource.loadImage(Resource.id_playerName);
				int x = mapx+palyerWidth/2-solider.getWidth()/2;
				g.drawImage(solider, x+Abs_Coords_X, 5+Abs_Coords_Y, TopLeft);
				g.drawImage(playerName, mapx+Abs_Coords_X, 68+Abs_Coords_Y, TopLeft);
				
				g.setColor(0XFFFFFF);
				if(p.getId() == StateMap.playerIndex){
					g.drawString("玩家", 28+mapx+Abs_Coords_X, 68+Abs_Coords_Y, TopLeft);
				}else{
					g.drawString(Resource.countrys[ran][i], 28+mapx+Abs_Coords_X, 68+Abs_Coords_Y, TopLeft);
				}
				
				if(p == currPlayer){
					DrawUtil.drawRect(g, mapx-1+Abs_Coords_X, 3+Abs_Coords_Y, 85, 83, 3, 0Xffff00);
				}
			}
		}
	}
	
	/**
	 * 战斗界面
	 * @param g
	 * @param color_attack 攻击方势力颜色
	 * @param color_embattled 被攻击放势力颜色
	 * @param nums_attack 攻击方兵数
	 * @param nums_embattled 被攻击方兵数
	 */
	public void drawFighting(SGraphics g, int color_attack, 
							int color_embattled, 
							Region region_attack, 
							Region region_embattled,
							int attack_num, 
							int embattled_num){
		
		Image battle_bg = Resource.loadImage(Resource.id_battle_bg);
		Image battle_bg2 = Resource.loadImage(Resource.id_battle_bg2);
		id_dice = Resource.getColorLocation(color_attack);
		id_dice2 = Resource.getColorLocation(color_embattled);
		Image dice = Resource.loadImage(id_dice);
		Image dice2 = Resource.loadImage(id_dice2);
		Image fight_fail = Resource.loadImage(Resource.id_fight_fail);
		g.drawImage(battle_bg, 120+Abs_Coords_X, 125+Abs_Coords_Y, TopLeft);
		g.drawRegion(battle_bg2, Resource.getColorLocation(color_attack)*129, 0, 129, 255, 0, 134+Abs_Coords_X, 138+Abs_Coords_Y, TopLeft);
		g.drawRegion(battle_bg2, Resource.getColorLocation(color_embattled)*129, 0, 129, 255, 0, 345+Abs_Coords_X, 138+Abs_Coords_Y, TopLeft);
		Image card = null;
		Image card_2 = null;
		Image plus = null;
		if(region_attack.getPropId2()==ARMOURED_CARD){
			card = Resource.loadImage(Resource.id_card);
			plus = Resource.loadImage(Resource.id_plus);
			g.drawRegion(card, (region_attack.getPropId2()-44)*card.getWidth()/9, 0, card.getWidth()/9, card.getHeight(), 0, 134+Abs_Coords_X, 138+Abs_Coords_Y, TopLeft);
			g.drawImage(plus, card.getWidth()/9+140+Abs_Coords_X, 155+Abs_Coords_Y, TopLeft);
			drawNum(g, 6, card.getWidth()/9+174+Abs_Coords_X, 155+Abs_Coords_Y, false);
		}
		if(region_embattled.getPropId()==DEFENSE_CARD || region_embattled.getPropId()==RETREAT_CARD || region_embattled.getPropId()==LANDMINE_CARD){
			card_2 = Resource.loadImage(Resource.id_card);
			g.drawRegion(card_2, (region_embattled.getPropId()-44)*card_2.getWidth()/9, 0, card_2.getWidth()/9, card_2.getHeight(), 0, 345+Abs_Coords_X, 138+Abs_Coords_Y, TopLeft);
			if(region_embattled.getPropId()==DEFENSE_CARD){
				plus = Resource.loadImage(Resource.id_plus);
				g.drawImage(plus, card_2.getWidth()/9+355+Abs_Coords_X, 155+Abs_Coords_Y, TopLeft);
				drawNum(g, 6, card_2.getWidth()/9+385+Abs_Coords_X, 155+Abs_Coords_Y, false);
			}
		}
		
		if(count<1){
			if(diceFlag<1){
				diceFlag++;
			}else{
				diceIndex=(diceIndex+1)%6;
				diceFlag=0;
				if(diceIndex==0){
					count++;
				}
			}
			mapy = 260-fight_fail.getHeight()/2; //失败动画纵坐标初始值
		}else{
			diceIndex=0;
			begin = true; //战斗动画播放结束
			//System.out.println("动画播放结束");
		}
		for(int i=0;i<region_attack.getSoldiers();i++){
			g.drawRegion(dice, 23*diceIndex, 0, 23, 26, 0, 135+135+Abs_Coords_X, 138+i*32+Abs_Coords_Y, TopLeft);
		}
		for(int j=0;j<region_embattled.getSoldiers();j++){
			g.drawRegion(dice2, 23*diceIndex, 0, 23, 26, 0, 135+180+Abs_Coords_X, 138+j*32+Abs_Coords_Y, TopLeft);
		}
		
		/*士兵*/
		if(attack_num>embattled_num){
			drawSolidersFighting(g, region_attack, color_attack, true);
			if(!begin){
				drawSolidersFighting(g, region_embattled, color_embattled, false);
			}
		}else{
			drawSolidersFighting(g, region_embattled, color_embattled, false);
			if(!begin){
				drawSolidersFighting(g, region_attack, color_attack, true);
			}
		}
		
		if(begin){ //190, 410, 260
			if(index<4){
				if(flag<interval){
					flag++;
				}else{
					index++;
					flag=0;
				}
			}else{
				index = 4;
			}
			drawNum(g, attack_num, 190+Abs_Coords_X, 350+Abs_Coords_Y, false);
			drawNum(g, embattled_num, 410+Abs_Coords_X, 350+Abs_Coords_Y, false);
			if(index==4){
				mapy -= 10;
			}
			if(attack_num <= embattled_num){
				g.drawRegion(fight_fail, 
						index*fight_fail.getWidth()/5, 
						0, 
						fight_fail.getWidth()/5, 
						fight_fail.getHeight(), 
						0, 
						190-(fight_fail.getWidth()/10)+Abs_Coords_X, 
						mapy+Abs_Coords_Y, 
						TopLeft);
			}else{
				g.drawRegion(fight_fail, 
						index*fight_fail.getWidth()/5, 
						0, 
						fight_fail.getWidth()/5, 
						fight_fail.getHeight(), 
						0, 
						410-(fight_fail.getWidth()/10)+Abs_Coords_X,
						mapy+Abs_Coords_Y, 
						TopLeft);
			}
		}
		if(mapy<155){
			isOver = true;
			begin = false;
			index = 0;
			flag = 0;
		}
	}
	
	/*数字转换成图片*/
	public void drawNum(SGraphics g, int num, int x, int y, boolean isShop) {
		Image fighting_num;
		if(!isShop){
			fighting_num = Resource.loadImage(Resource.id_fighting_num);
		}else{
			fighting_num = Resource.loadImage(Resource.id_shop_num);
		}
		String number = String.valueOf(num);
		for (byte i = 0; i < number.length(); i++) {
			g.drawRegion(fighting_num, (number.charAt(i) - '0') * 13, 0, 13, 25,
					0, x + i * (13 + 1), y, 0);
		}
	}
	
	/*战斗界面上的士兵*/
	private void drawSolidersFighting(SGraphics g, Region region, int color, boolean isAttackRegion){
		int count = region.getSoldiers();
		int x = 0;
		if(!isAttackRegion){
			x = 210;
		}
		id_solider_fighting = Resource.getSoliderId_fighting(color);
		id_tank_fighting = Resource.getTankId_fighting(color);
		id_airplane_fighting = Resource.getAirplaneId_fighting(color);
		Image solider = Resource.loadImage(id_solider_fighting);
		Image tank = Resource.loadImage(id_tank_fighting);
		Image airplane = Resource.loadImage(id_airplane_fighting);
		
		if(count == 1){
			if(id_solider_fighting == Resource.id_soldier_2){
				g.drawRegion(solider, 0, 0, solider.getWidth(), solider.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+135+Abs_Coords_X, 223+Abs_Coords_Y, TopLeft);
			}else if(id_solider_fighting == Resource.id_soldier_1){
				g.drawRegion(solider, 0, 0, solider.getWidth(), solider.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+180+Abs_Coords_X, 214+Abs_Coords_Y, TopLeft);
			}else{
				g.drawRegion(solider, 0, 0, solider.getWidth(), solider.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+171+Abs_Coords_X, 217+Abs_Coords_Y, TopLeft);
			}
		} else if(count == 2){
			for(int i=0;i<count;i++){
				if(id_solider_fighting == Resource.id_soldier_2){
					g.drawRegion(solider, 0, 0, solider.getWidth(), solider.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+135+Abs_Coords_X, 223+i*12+Abs_Coords_Y, TopLeft);
				}else if(id_solider_fighting == Resource.id_soldier_1){
					g.drawRegion(solider, 0, 0, solider.getWidth(), solider.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+169+i*12+Abs_Coords_X, 210+i*10+Abs_Coords_Y, TopLeft);
				}else {
					g.drawRegion(solider, 0, 0, solider.getWidth(), solider.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+159+i*15+Abs_Coords_X, 213+i*10+Abs_Coords_Y, TopLeft);
				}
			}
		} else if(count == 3){
			for(int i=0;i<count;i++){
				if(id_solider_fighting == Resource.id_soldier_2){
					g.drawRegion(solider, 0, 0, solider.getWidth(), solider.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+135+Abs_Coords_X, 211+i*12+Abs_Coords_Y, TopLeft);
				}else if(id_solider_fighting == Resource.id_soldier_1){
					g.drawRegion(solider, 0, 0, solider.getWidth(), solider.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+157+i*12+Abs_Coords_X, 204+i*10+Abs_Coords_Y, TopLeft);
				}else{
					g.drawRegion(solider, 0, 0, solider.getWidth(), solider.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+146+i*12+Abs_Coords_X, 207+i*10+Abs_Coords_Y, TopLeft);
				}
			}
		} else if(count == 4){
			if(id_tank_fighting == Resource.id_tank_2){
				g.drawRegion(tank, 0, 0, tank.getWidth(), tank.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+150+Abs_Coords_X, 209+Abs_Coords_Y, TopLeft);
			}else if(id_tank_fighting == Resource.id_tank_1){
				g.drawRegion(tank, 0, 0, tank.getWidth(), tank.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+147+Abs_Coords_X, 211+Abs_Coords_Y, TopLeft);
			}else{
				g.drawRegion(tank, 0, 0, tank.getWidth(), tank.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+148+Abs_Coords_X, 218+Abs_Coords_Y, TopLeft);
			}
		} else if(count == 5){
			if(id_tank_fighting == Resource.id_tank_2){
				g.drawRegion(tank, 0, 0, tank.getWidth(), tank.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+150+Abs_Coords_X, 190+Abs_Coords_Y, TopLeft);
			}else if(id_tank_fighting == Resource.id_tank_1){
				g.drawRegion(tank, 0, 0, tank.getWidth(), tank.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+147+Abs_Coords_X, 185+Abs_Coords_Y, TopLeft);
			}else{
				g.drawRegion(tank, 0, 0, tank.getWidth(), tank.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+148+Abs_Coords_X, 198+Abs_Coords_Y, TopLeft);
			}
			
			if(id_solider_fighting == Resource.id_soldier_2){
				g.drawRegion(solider, 0, 0, solider.getWidth(), solider.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+135+Abs_Coords_X, 258+Abs_Coords_Y, TopLeft);
			}else if(id_solider_fighting == Resource.id_soldier_1){
				g.drawRegion(solider, 0, 0, solider.getWidth(), solider.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+180+Abs_Coords_X, 241+Abs_Coords_Y, TopLeft);
			}else{
				g.drawRegion(solider, 0, 0, solider.getWidth(), solider.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+171+Abs_Coords_X, 237+Abs_Coords_Y, TopLeft);
			}
			
		} else if(count == 6){
			if(id_tank_fighting == Resource.id_tank_2){
				g.drawRegion(tank, 0, 0, tank.getWidth(), tank.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+150+Abs_Coords_X, 180+Abs_Coords_Y, TopLeft);
			}else if(id_tank_fighting == Resource.id_tank_1){
				g.drawRegion(tank, 0, 0, tank.getWidth(), tank.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+147+Abs_Coords_X, 185+Abs_Coords_Y, TopLeft);
			}else{
				g.drawRegion(tank, 0, 0, tank.getWidth(), tank.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+148+Abs_Coords_X, 198+Abs_Coords_Y, TopLeft);
			}
			
			for(int i=0;i<count-4;i++){
				if(id_solider_fighting == Resource.id_soldier_2){
					g.drawRegion(solider, 0, 0, solider.getWidth(), solider.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+135+Abs_Coords_X, 248+i*17+Abs_Coords_Y, TopLeft);
				}else if(id_solider_fighting == Resource.id_soldier_1){
					g.drawRegion(solider, 0, 0, solider.getWidth(), solider.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+159+i*12+Abs_Coords_X, 241+i*10+Abs_Coords_Y, TopLeft);
				}else{
					g.drawRegion(solider, 0, 0, solider.getWidth(), solider.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+159+i*12+Abs_Coords_X, 237+i*10+Abs_Coords_Y, TopLeft);
				}
			}
		} else if(count == 7){
			if(id_tank_fighting == Resource.id_tank_2){
				g.drawRegion(tank, 0, 0, tank.getWidth(), tank.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+150+Abs_Coords_X, 168+Abs_Coords_Y, TopLeft);
			}else if(id_tank_fighting == Resource.id_tank_1){
				g.drawRegion(tank, 0, 0, tank.getWidth(), tank.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+147+Abs_Coords_X, 185+Abs_Coords_Y, TopLeft);
			}else{
				g.drawRegion(tank, 0, 0, tank.getWidth(), tank.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+148+Abs_Coords_X, 198+Abs_Coords_Y, TopLeft);
			}
			
			for(int i=0;i<count-4;i++){
				if(id_solider_fighting == Resource.id_soldier_2){
					g.drawRegion(solider, 0, 0, solider.getWidth(), solider.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+135+Abs_Coords_X, 234+i*17+Abs_Coords_Y, TopLeft);
				}else if(id_solider_fighting == Resource.id_soldier_1){
					g.drawRegion(solider, 0, 0, solider.getWidth(), solider.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+157+i*12+Abs_Coords_X, 235+i*10+Abs_Coords_Y, TopLeft);
				}else{
					g.drawRegion(solider, 0, 0, solider.getWidth(), solider.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+146+i*15+Abs_Coords_X, 227+i*10+Abs_Coords_Y, TopLeft);
				}
			}
		} else if(count == 8){
			if(id_airplane_fighting == Resource.id_airplane_1){
				g.drawRegion(airplane, 0, 0, airplane.getWidth(), airplane.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+137+Abs_Coords_X, 227+Abs_Coords_Y, TopLeft);
			} else if(id_airplane_fighting == Resource.id_airplane_2){
				g.drawRegion(airplane, 0, 0, airplane.getWidth(), airplane.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+142+Abs_Coords_X, 213+Abs_Coords_Y, TopLeft);
			} else{
				g.drawRegion(airplane, 0, 0, airplane.getWidth(), airplane.getHeight(), isAttackRegion==true?0:Sprite.TRANS_MIRROR, x+137+Abs_Coords_X, 216+Abs_Coords_Y, TopLeft);
			}
		}
		
	}
	
	/*地图上的士兵*/
	public void drawSoldiers(SGraphics g, Region r, Influence inf, int coor_x, int coor_y){
		int count = r.getSoldiers();
		if(count<=3){
			id_solider = Resource.getSoliderId(inf.getColor());
			Image solider = Resource.loadImage(id_solider);
			for(int i=0;i<count;i++){
				g.drawImage(solider, (coor_x-solider.getWidth()/2)+i*pixelInterval+Abs_Coords_X, coor_y-solider.getHeight()/2+Abs_Coords_Y, TopLeft);
			}
		}
		else if(count<8 && count>3){
			id_tank = Resource.getTankId(inf.getColor());
			Image tank = Resource.loadImage(id_tank);
			g.drawImage(tank, coor_x-tank.getWidth()/2+Abs_Coords_X, coor_y-tank.getHeight()/2-5+Abs_Coords_Y, TopLeft);
			if(count>4){
				id_solider = Resource.getSoliderId(inf.getColor());
				Image solider = Resource.loadImage(id_solider);
				for(int i=0;i<count-4;i++){
					g.drawImage(solider, coor_x-solider.getWidth()/2+i*pixelInterval+Abs_Coords_X, coor_y-solider.getHeight()/2+5+Abs_Coords_Y, TopLeft);
				}
			}
		}
		else {
			id_airplane = Resource.getAirplaneId(inf.getColor());
			Image airplane = Resource.loadImage(id_airplane);
			g.drawImage(airplane, coor_x-airplane.getWidth()/2+Abs_Coords_X, coor_y-airplane.getHeight()/2+Abs_Coords_Y, TopLeft);
		}
	}

	/*游戏排行*/
	public void drawRanking(SGraphics g, GameRanking[] gameRanking){
		Image bg = Resource.loadImage(Resource.id_interface_bg);
		Image rank_info = Resource.loadImage(Resource.id_rank_info);
		Image ranking = Resource.loadImage(Resource.id_ranking);
		Image rank_tag = Resource.loadImage(Resource.id_rank_tag);
		Image return_button = Resource.loadImage(Resource.id_return_button);
	
		g.drawImage(bg, 0+Abs_Coords_X, 0+Abs_Coords_Y, TopLeft);
		g.drawImage(rank_info, 50+Abs_Coords_X, 88+Abs_Coords_Y, TopLeft);
		g.drawImage(ranking, 78+Abs_Coords_X, 475+Abs_Coords_Y, TopLeft);
		g.drawImage(rank_tag, 263+Abs_Coords_X, 27+Abs_Coords_Y, TopLeft);
		g.drawImage(return_button, 435+Abs_Coords_X, 470+Abs_Coords_Y, TopLeft);
		
		engine.setFont(19);
		String ownRank="榜上无名!";
		int color = g.getColor();
		g.setColor(0Xffffff);
		if(gameRanking!=null){
			for(int i=0;i<gameRanking.length;i++){
				int rank = gameRanking[i].getRanking();
				String userId = gameRanking[i].getUserId();
				int scores = gameRanking[i].getScores();
				if(userId.equals(engine.getEngineService().getUserId())){
					ownRank = String.valueOf(gameRanking[i].getRanking());
				}
				TextView.showSingleLineText(g, String.valueOf(rank), 80+Abs_Coords_X, 152+(i*30)+Abs_Coords_Y, 50, 30, 1);
				TextView.showSingleLineText(g, userId, 192+Abs_Coords_X, 152+(i*30)+Abs_Coords_Y, 200, 30, 1);
				TextView.showSingleLineText(g, String.valueOf(scores), 475+Abs_Coords_X, 152+(i*30)+Abs_Coords_Y, 90, 30, 1);
			}
		}
		g.drawString(ownRank, 105+98+Abs_Coords_X, 477+Abs_Coords_Y, TopLeft);
		g.setColor(color);
	}
	
	/*新手指导*/
	public void drawFreshMan(SGraphics g, int index){
		Image freshman = Resource.loadImage(Resource.id_freshman);
		Image freshman2 = Resource.loadImage(Resource.id_freshman2);
		if(index==0){
			g.drawImage(freshman, 0+Abs_Coords_X, 0+Abs_Coords_Y, TopLeft);
		}else{
			g.drawImage(freshman2, 0+Abs_Coords_X, 0+Abs_Coords_Y, TopLeft);
		}
	}
	
	/*游戏帮助             80,137 500,315*/
	public void drawHelp(SGraphics g, int index, int pageIndex){
		Image help = Resource.loadImage(Resource.id_help);
		Image updown = Resource.loadImage(Resource.id_updown);
		g.drawImage(help, 0+Abs_Coords_X, 0+Abs_Coords_Y, TopLeft);
		g.drawRegion(updown, pageIndex==0?0:updown.getWidth()/2, 0, updown.getWidth()/2, updown.getHeight()/2, 0, 110+Abs_Coords_X, 470+Abs_Coords_Y, TopLeft);
		g.drawRegion(updown, pageIndex==1?0:updown.getWidth()/2, updown.getHeight()/2, updown.getWidth()/2, updown.getHeight()/2, 0, 255+Abs_Coords_X, 470+Abs_Coords_Y, TopLeft);
		engine.setFont(19);
		TextView.showMultiLineText(g, Resource.helpInfo[index], 10+Abs_Coords_X, 80+Abs_Coords_Y, 137, 480, 315);
		int color =  g.getColor();
		g.setColor(0XFFFFFF);
		TextView.showMultiLineText(g, Resource.helpInfo[index], 10, 80, 137, 480, 315);
		engine.setDefaultFont();
		g.setColor(color);
	}
	
	/*清游新手指导*/
	public void clearFreshMan(){
		Resource.freeImage(Resource.id_freshman);
		Resource.freeImage(Resource.id_freshman2);
	}
	
	/*清游戏帮助*/
	public void clearHelp(){
		Resource.freeImage(Resource.id_help);
		Resource.freeImage(Resource.id_updown);
	}
	
	/*清游戏排行*/
	public void clearRanking(){
		Resource.freeImage(Resource.id_interface_bg);
		Resource.freeImage(Resource.id_rank_info);
		Resource.freeImage(Resource.id_ranking);
		Resource.freeImage(Resource.id_rank_tag);
		Resource.freeImage(Resource.id_return_button);
	}
	
	/*清选择地图界面*/
	public void clearSelectMapMenu(){
		Resource.freeImage(Resource.id_main_bg);
		Resource.freeImage(Resource.id_map_menu);
	}
	
	/*清游戏成功或失败图片*/
	public void clearSuccessOrFail(){
		Resource.freeImage(Resource.id_card_bg);
		Resource.freeImage(Resource.id_over);
		Resource.freeImage(Resource.id_over_menu);
	}
	
	/*清商店图片*/
	public void clearShop(){
		Resource.freeImage(Resource.id_card);
		Resource.freeImage(Resource.id_interface_bg);
		Resource.freeImage(Resource.id_info_bg);
		Resource.freeImage(Resource.id_shop_title);
		Resource.freeImage(Resource.id_shop_balance);
		Resource.freeImage(Resource.id_shop_price);
		Resource.freeImage(Resource.id_shop_card_bg);
		Resource.freeImage(Resource.id_shop_recharge);
		Resource.freeImage(Resource.id_shop_num);
	}
	
	/*清卡片飞分配图片*/
	public void clearCardAssign(){
		Resource.freeImage(Resource.id_card);
		Resource.freeImage(Resource.id_card_bg);
		Resource.freeImage(Resource.id_card_info);
		Resource.freeImage(Resource.id_card_menu);
		Resource.freeImage(Resource.id_card_name);
		Resource.freeImage(Resource.id_card_tag);
		Resource.freeImage(Resource.id_shop_num);
	}
	
	/*清战斗图片*/
	public void clearFighting(){
		Resource.freeImage(Resource.id_battle_bg);
		Resource.freeImage(Resource.id_battle_bg2);
		Resource.freeImage(id_dice);
		Resource.freeImage(id_dice2);
		Resource.freeImage(id_solider_fighting);
		Resource.freeImage(id_tank_fighting);
		Resource.freeImage(id_airplane_fighting);
		Resource.freeImage(Resource.id_fight_fail);
		Resource.freeImage(Resource.id_fighting_num);
		Resource.freeImage(Resource.id_shop_num);
		Resource.freeImage(Resource.id_card);
		Resource.freeImage(Resource.id_plus);
	}
	
	/*士兵分配*/
	public void clearSolidersGrowth(){
		Resource.freeImage(Resource.id_plus);
		Resource.freeImage(Resource.id_fighting_num);
	}
	
	/*清地图背景图片*/
	public void clearMapBG(){
		Resource.freeImage(Resource.id_map_bg);
		Resource.freeImage(Resource.id_card);
		Resource.freeImage(Resource.id_shop_num);
		Resource.freeImage(Resource.id_card_icon);
		Resource.freeImage(id_solider);
		Resource.freeImage(id_tank);
		Resource.freeImage(id_airplane);
		Resource.freeImage(Resource.id_roleFlame);
		Resource.freeImage(id_role);
		Resource.freeImage(Resource.id_playerName);
	}
	
	/*清空主菜单图片*/
	public void clearMainMenu(){
		Resource.freeImage(Resource.id_main_bg);
		Resource.freeImage(Resource.id_main_menu);
		Resource.freeImage(Resource.id_favorites);
	}
	
}
	