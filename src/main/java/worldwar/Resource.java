package worldwar;

import java.io.IOException;

import javax.microedition.lcdui.Image;

/**
 * 加载图片资源
 * @author Administrator
 *
 */
public class Resource implements Common{
	
	/*主菜单*/
	public static int menuW = 205, menuH = 56;
	public static int menuAxis[][] = 
	{ { 200, 223 }, 
	  { 200, 279 }, 
	  { 200, 335 },
	  { 200, 391 }, 
	  { 200, 447 },
	};
	
	/*势力颜色值*/
	public static String[] colors ={
		"677424",  	//绿
		"ab7b33",	//黄
		"8a432f",	//红
		"3f281b",	//棕
		"454a43",	//灰
		"578481",	//蓝
	};

	public static String[][] countrys ={
		{"美国","德国","法国","英国","俄罗斯","日本"},
		{"德国","美国","英国","法国","日本","俄罗斯"},
		{"法国","英国","美国","日本","俄罗斯","德国"},
		{"日本","俄罗斯","德国","法国","英国","美国"},
		{"俄罗斯","日本","法国","英国","美国","德国"},
	};
	
	public static String[][] mapPaths = {
			{"/map/2/1.wwm", "/map/2/2.wwm", "/map/2/3.wwm", "/map/2/4.wwm", "/map/2/5.wwm", "/map/2/6.wwm"
				     , "/map/2/7.wwm", "/map/2/8.wwm", "/map/2/9.wwm", "/map/2/10.wwm", "/map/2/11.wwm"},
			 
			{"/map/3/1.wwm", "/map/3/2.wwm", "/map/3/3.wwm", "/map/3/4.wwm", "/map/3/5.wwm"
				     , "/map/3/6.wwm", "/map/3/7.wwm", "/map/3/8.wwm", "/map/3/9.wwm"},
				 
			{"/map/4/1.wwm", "/map/4/2.wwm", "/map/4/3.wwm", "/map/4/4.wwm", "/map/4/5.wwm", "/map/4/6.wwm"
					 , "/map/4/7.wwm", "/map/4/8.wwm", "/map/4/9.wwm", "/map/4/10.wwm"},	
					 
			{"/map/5/1.wwm", "/map/5/2.wwm", "/map/5/3.wwm", "/map/5/4.wwm", "/map/5/5.wwm", "/map/5/6.wwm"
					 , "/map/5/7.wwm", "/map/5/8.wwm", "/map/5/9.wwm"},
						 
			{"/map/6/1.wwm", "/map/6/2.wwm", "/map/6/3.wwm", "/map/6/4.wwm", "/map/6/5.wwm", "/map/6/6.wwm"
					 , "/map/6/7.wwm", "/map/6/8.wwm", "/map/6/9.wwm"}
	}; 
	
	/*兵种坐标*/
	public static int[][] soldierCoord = {
		{155, 211, 12}, //兵种1(蓝色)
		{221, 224, 10}, //兵种2(黄色)
		{216, 227, 10}, //兵种3(绿色)
	};
	
	/*坦克坐标*/
	public static int[][] tankCoord = {
		/*0-坦克x轴坐标, 1-坦克Y轴坐标, 2-兵X轴坐标, 3-兵Y轴坐标, 4-兵X轴间距, 5-兵Y轴间距*/
		{167, 185, 221, 255, 22, 10}, //坦克1(蓝色)
		{170, 168, 155, 268, 17, 0}, //坦克1(黄色)
		{168, 198, 216, 247, 25, 10}, //坦克1(绿色)
	};
	
	/*道具描述*/
	public static String[] info = {
		"防御卡: 在常规的战斗中为防守方增加6点骰子点数。如果进攻方使用了装甲卡或者炮击卡，两张卡效果抵消",
		"撤退卡: 防守方在战斗失败后，该领地兵数会转移到到下一次的援军增加数量",
		"地雷卡: 使占领该领地的敌方部队的数量变为1",
		"隐藏卡: 使用后保护己方的某一领地下回合不受敌方进攻",
		"炮击卡: 投掷一粒六面骰来计算伤害。只对与玩家相邻的领地起作用，如果被攻击的领地使用了防御工事卡，则两张卡效果抵消",
		"装甲卡: 在进攻一块领地时为进攻方增加6点骰子点数。如果对方使用了防御工事卡，则两张卡效果抵消",
		"侦查卡: 敌对玩家所有领地上已使用的防御卡片都会显示出来",
		"空袭卡: 对敌方任意一块领地上的部队造成毁灭性打击，使其部队变成1",
		"策反卡: 使任意一块敌方领地变成自己的领地",
	};
	
	/*道具描述2*/
	public static String[] info2 = {
		"防御卡: 在战斗中为防守方增加6点骰子点数",
		"撤退卡: 防守失败后 该领地兵数不会消失",
		"地雷卡: 占领该领地的敌方部队的数量变为1",
		"隐藏卡: 使用后该领地下一回合不受敌方进攻",
		"炮击卡: 对相邻领地造成一定伤害",
		"装甲卡: 增加进攻方6点骰子点数",
		"侦查卡: 敌方已使用的防御卡片都会显示出来",
		"空袭卡: 被攻击方部队变成1",
		"策反卡: 使任意一块敌方领地变成自己的领地",
	};
	
	public static String[] helpInfo = {
		"【游戏目标】占领所有的领地获得游戏的胜利。#r【如何进攻】方向键选择一块己方部队数量大于1的领地,点击确定按钮锁定该领地,方向键选择一块与锁定领地相连的敌方领地,点击确定按钮对这块领地发起进攻。",
		"【如何使用卡片】防御卡，撤退卡，地雷卡，装甲卡，隐藏卡：数字键选中卡片，方向键选择一块属于自己的领地，点击确定按钮，即可部署卡片。 #r炮击卡：数字键选中炮击卡，选择一块与玩家领地相连的敌方领地，点击确定按钮，即可完成炮击卡的使用。 #r空袭卡，策反卡：数字键选中卡片，选择任意一块敌方领地，点击确定按钮，即可完成卡片的使用。 #r侦查卡：在游戏中，数字键选择侦查卡，点击确定按钮，即可完成侦查卡的使用。",
		"【操作说明】上下左右方向键：控制选择框的移动。#r确定键：锁定进攻方和被进攻方，使用卡片。#r数字键1至7：选择卡片。#r数字键0：退出游戏。#r数字键8:游戏帮助。#r数字键9：回合结束。",
		"【剧情简介】攻击，保护，支配世界！保卫你的领土，不断扩大版图，所有的一切只须投掷骰子即可做到！在这款经典的棋盘游戏中你将成为将军！不断的省事和调整战略战术，配合道具商城中的卡片，建立强大的军队，征服所有的领土。你可以最多同5位电脑将军逐鹿中原，计谋与卡片的结合，扣人心弦的较量，即使最温和的玩家也会激起雄心和斗志！"
	};
	
	private static short NUMS = 0;
	/*游戏菜单界面*/
	public static short id_dice_green = NUMS++;
	public static short id_dice_yellow = NUMS++;
	public static short id_dice_red = NUMS++;
	public static short id_dice_brown = NUMS++;
	public static short id_dice_ashy = NUMS++;
	public static short id_dice_blue = NUMS++;
	public static short id_main_bg = NUMS++;
	public static short id_main_menu = NUMS++;
	public static short id_map_bg = NUMS++;
	public static short id_battle_bg = NUMS++;
	public static short id_battle_bg2 = NUMS++;
	public static short id_soldier_1 = NUMS++;
	public static short id_soldier_2 = NUMS++;
	public static short id_soldier_3 = NUMS++;
	public static short id_tank_1 = NUMS++;
	public static short id_tank_2 = NUMS++;
	public static short id_tank_3 = NUMS++;
	public static short id_airplane_1 = NUMS++;
	public static short id_airplane_2 = NUMS++;
	public static short id_airplane_3 = NUMS++;
	public static short id_soldier_1_small = NUMS++;
	public static short id_soldier_2_small = NUMS++;
	public static short id_soldier_3_small = NUMS++;
	public static short id_tank_1_small = NUMS++;
	public static short id_tank_2_small = NUMS++;
	public static short id_tank_3_small = NUMS++;
	public static short id_airplane_1_small = NUMS++;
	public static short id_airplane_2_small = NUMS++;
	public static short id_airplane_3_small = NUMS++;
	public static short id_card = NUMS++;
	public static short id_roleFlame = NUMS++;
	public static short id_playerName = NUMS++;
	public static short id_fight_fail = NUMS++;
	public static short id_fighting_num = NUMS++;
	public static short id_shop_num = NUMS++;
	public static short id_role_yellow = NUMS++;
	public static short id_map_menu = NUMS++;
	public static short id_help_menu = NUMS++;
	public static short id_card_bg = NUMS++;
	public static short id_card_name = NUMS++;
	public static short id_card_info = NUMS++;
	public static short id_card_menu = NUMS++;
	public static short id_card_tag = NUMS++;
	public static short id_interface_bg = NUMS++;
	public static short id_info_bg = NUMS++;
	public static short id_shop_title = NUMS++;
	public static short id_shop_balance = NUMS++;
	public static short id_shop_price = NUMS++;
	public static short id_shop_card_bg = NUMS++;
	public static short id_shop_recharge = NUMS++;
	public static short id_card_icon = NUMS++;
	public static short id_over = NUMS++;
	public static short id_over_menu = NUMS++;
	public static short id_plus = NUMS++;
	public static short id_rank_info = NUMS++;
	public static short id_ranking = NUMS++;
	public static short id_rank_tag = NUMS++;
	public static short id_return_button = NUMS++;
	public static short id_open_bate= NUMS++;
	public static short id_favorites = NUMS++;
	public static short id_help = NUMS++;
	public static short id_freshman = NUMS++;
	public static short id_freshman2 = NUMS++;
	public static short id_updown = NUMS++;
	
	public static String[] imagesrcs = {
		"/dice_green.png",
		"/dice_yellow.png",
		"/dice_red.png",
		"/dice_brown.png",
		"/dice_ashy.png",
		"/dice_blue.png",
		"/main_bg.jpg",
		"/main_menu.png",
		"/map_bg.jpg",
		"/battle_bg.png",
		"/battle_bg2.png",
		"/soldier_1.png",
		"/soldier_2.png",
		"/soldier_3.png",
		"/tank_1.png",
		"/tank_2.png",
		"/tank_3.png",
		"/airplane_1.png",
		"/airplane_2.png",
		"/airplane_3.png",
		"/soldier_1_small.png",
		"/soldier_2_small.png",
		"/soldier_3_small.png",
		"/tank_1_small.png",
		"/tank_2_small.png",
		"/tank_3_small.png",
		"/airplane_1_small.png",
		"/airplane_2_small.png",
		"/airplane_3_small.png",
		"/card.png",
		"/roleFlame.png",
		"/playername.png",
		"/fight_fail.png",
		"/fighting_num.png",
		"/shop_num.png",
		"/role_yellow.png",
		"/map_menu.png",
		"/help_menu.png",
		"/card_bg.png",
		"/card_name.png",
		"/card_info.png",
		"/card_menu.png",
		"/card_tag.png",
		"/interface_bg.jpg",
		"/info_bg.jpg",
		"/shop_title.jpg",
		"/shop_balance.png",
		"/shop_price.png",
		"/shop_card_bg.png",
		"/shop_recharge.png",
		"/card_icon.png",
		"/over.png",
		"/over_menu.png",
		"/plus.png",
		"/rank_info.png",
		"/ranking.png",
		"/rank_tag.jpg",
		"/return_button.png",
		"/bate.png",
		"/favorites.png",
		"/help.jpg",
		"/freshman.jpg",
		"/freshman2.jpg",
		"/updown.png",
	};
	private static final Image[] images = new Image[NUMS];

	public static Image loadImage(int id){
		if(images[id]==null){
			try {
				images[id] = Image.createImage(imagesrcs[id]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return images[id];
	}
	
	public static void freeImage(int id){
		images[id] = null;
	}
	
	/*设置势力颜色*/
	public static int setColorValue(int color){
		switch (color){
		case ID_GREEN:
			return COLOR_GREEN;
		case ID_YELLOW:
			return COLOR_YELLOW;
		case ID_RED:
			return COLOR_RED;
		case ID_BROWN:
			return COLOR_BROWN;
		case ID_ASHY:
			return COLOR_ASHY;
		case ID_BLUE:
			return COLOR_BLUE;
		}
		return -1;
	}
	
	public static int getColorLocation(int color){
		switch (color){
		case COLOR_GREEN:
			return ID_GREEN;
		case COLOR_YELLOW:
			return ID_YELLOW;
		case COLOR_RED:
			return ID_RED;
		case COLOR_BROWN:
			return ID_BROWN;
		case COLOR_ASHY:
			return ID_ASHY;
		case COLOR_BLUE:
			return ID_BLUE;
		}
		return -1;
	}
	
	public static int getSoliderId(int color){
		switch (color){
		case COLOR_GREEN:
			return id_soldier_1_small;
		case COLOR_YELLOW:
			return id_soldier_2_small;
		case COLOR_RED:
			return id_soldier_3_small;
		case COLOR_BROWN:
			return id_soldier_1_small;
		case COLOR_ASHY:
			return id_soldier_2_small;
		case COLOR_BLUE:
			return id_soldier_3_small;
		}
		return -1;
	}
	
	public static int getTankId(int color){
		switch (color){
		case COLOR_GREEN:
			return id_tank_1_small;
		case COLOR_YELLOW:
			return id_tank_2_small;
		case COLOR_RED:
			return id_tank_3_small;
		case COLOR_BROWN:
			return id_tank_1_small;
		case COLOR_ASHY:
			return id_tank_2_small;
		case COLOR_BLUE:
			return id_tank_3_small;
		}
		return -1;
	}
	
	public static int getAirplaneId(int color){
		switch (color){
		case COLOR_GREEN:
			return id_airplane_1_small;
		case COLOR_YELLOW:
			return id_airplane_2_small;
		case COLOR_RED:
			return id_airplane_2_small;
		case COLOR_BROWN:
			return id_airplane_1_small;
		case COLOR_ASHY:
			return id_airplane_2_small;
		case COLOR_BLUE:
			return id_airplane_3_small;
		}
		return -1;
	}
	
	public static int getSoliderId_fighting(int color){
		switch (color){
		case COLOR_GREEN:
			return id_soldier_1;
		case COLOR_YELLOW:
			return id_soldier_2;
		case COLOR_RED:
			return id_soldier_3;
		case COLOR_BROWN:
			return id_soldier_1;
		case COLOR_ASHY:
			return id_soldier_2;
		case COLOR_BLUE:
			return id_soldier_3;
		}
		return -1;
	}
	
	public static int getTankId_fighting(int color){
		switch (color){
		case COLOR_GREEN:
			return id_tank_1;
		case COLOR_YELLOW:
			return id_tank_2;
		case COLOR_RED:
			return id_tank_3;
		case COLOR_BROWN:
			return id_tank_1;
		case COLOR_ASHY:
			return id_tank_2;
		case COLOR_BLUE:
			return id_tank_3;
		}
		return -1;
	}
	
	public static int getAirplaneId_fighting(int color){
		switch (color){
		case COLOR_GREEN:
			return id_airplane_1;
		case COLOR_YELLOW:
			return id_airplane_2;
		case COLOR_RED:
			return id_airplane_3;
		case COLOR_BROWN:
			return id_airplane_1;
		case COLOR_ASHY:
			return id_airplane_2;
		case COLOR_BLUE:
			return id_airplane_3;
		}
		return -1;
	}
}
