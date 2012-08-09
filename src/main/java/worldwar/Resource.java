package worldwar;

import java.io.IOException;

import javax.microedition.lcdui.Image;

/**
 * ����ͼƬ��Դ
 * @author Administrator
 *
 */
public class Resource implements Common{
	
	/*���˵�*/
	public static int menuW = 205, menuH = 56;
	public static int menuAxis[][] = 
	{ { 200, 223 }, 
	  { 200, 279 }, 
	  { 200, 335 },
	  { 200, 391 }, 
	  { 200, 447 },
	};
	
	/*������ɫֵ*/
	public static String[] colors ={
		"677424",  	//��
		"ab7b33",	//��
		"8a432f",	//��
		"3f281b",	//��
		"454a43",	//��
		"578481",	//��
	};

	public static String[][] countrys ={
		{"����","�¹�","����","Ӣ��","����˹","�ձ�"},
		{"�¹�","����","Ӣ��","����","�ձ�","����˹"},
		{"����","Ӣ��","����","�ձ�","����˹","�¹�"},
		{"�ձ�","����˹","�¹�","����","Ӣ��","����"},
		{"����˹","�ձ�","����","Ӣ��","����","�¹�"},
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
	
	/*��������*/
	public static int[][] soldierCoord = {
		{155, 211, 12}, //����1(��ɫ)
		{221, 224, 10}, //����2(��ɫ)
		{216, 227, 10}, //����3(��ɫ)
	};
	
	/*̹������*/
	public static int[][] tankCoord = {
		/*0-̹��x������, 1-̹��Y������, 2-��X������, 3-��Y������, 4-��X����, 5-��Y����*/
		{167, 185, 221, 255, 22, 10}, //̹��1(��ɫ)
		{170, 168, 155, 268, 17, 0}, //̹��1(��ɫ)
		{168, 198, 216, 247, 25, 10}, //̹��1(��ɫ)
	};
	
	/*��������*/
	public static String[] info = {
		"������: �ڳ����ս����Ϊ���ط�����6�����ӵ��������������ʹ����װ�׿������ڻ��������ſ�Ч������",
		"���˿�: ���ط���ս��ʧ�ܺ󣬸���ر�����ת�Ƶ�����һ�ε�Ԯ����������",
		"���׿�: ʹռ�����صĵз����ӵ�������Ϊ1",
		"���ؿ�: ʹ�ú󱣻�������ĳһ����»غϲ��ܵз�����",
		"�ڻ���: Ͷ��һ���������������˺���ֻ����������ڵ���������ã���������������ʹ���˷������¿��������ſ�Ч������",
		"װ�׿�: �ڽ���һ�����ʱΪ����������6�����ӵ���������Է�ʹ���˷������¿��������ſ�Ч������",
		"��鿨: �ж���������������ʹ�õķ�����Ƭ������ʾ����",
		"��Ϯ��: �Եз�����һ������ϵĲ�����ɻ����Դ����ʹ�䲿�ӱ��1",
		"�߷���: ʹ����һ��з���ر���Լ������",
	};
	
	/*��������2*/
	public static String[] info2 = {
		"������: ��ս����Ϊ���ط�����6�����ӵ���",
		"���˿�: ����ʧ�ܺ� ����ر���������ʧ",
		"���׿�: ռ�����صĵз����ӵ�������Ϊ1",
		"���ؿ�: ʹ�ú�������һ�غϲ��ܵз�����",
		"�ڻ���: ������������һ���˺�",
		"װ�׿�: ���ӽ�����6�����ӵ���",
		"��鿨: �з���ʹ�õķ�����Ƭ������ʾ����",
		"��Ϯ��: �����������ӱ��1",
		"�߷���: ʹ����һ��з���ر���Լ������",
	};
	
	public static String[] helpInfo = {
		"����ϷĿ�꡿ռ�����е���ػ����Ϸ��ʤ����#r����ν����������ѡ��һ�鼺��������������1�����,���ȷ����ť���������,�����ѡ��һ����������������ĵз����,���ȷ����ť�������ط��������",
		"�����ʹ�ÿ�Ƭ�������������˿������׿���װ�׿������ؿ������ּ�ѡ�п�Ƭ�������ѡ��һ�������Լ�����أ����ȷ����ť�����ɲ���Ƭ�� #r�ڻ��������ּ�ѡ���ڻ�����ѡ��һ���������������ĵз���أ����ȷ����ť����������ڻ�����ʹ�á� #r��Ϯ�����߷��������ּ�ѡ�п�Ƭ��ѡ������һ��з���أ����ȷ����ť��������ɿ�Ƭ��ʹ�á� #r��鿨������Ϸ�У����ּ�ѡ����鿨�����ȷ����ť�����������鿨��ʹ�á�",
		"������˵�����������ҷ����������ѡ�����ƶ���#rȷ�����������������ͱ���������ʹ�ÿ�Ƭ��#r���ּ�1��7��ѡ��Ƭ��#r���ּ�0���˳���Ϸ��#r���ּ�8:��Ϸ������#r���ּ�9���غϽ�����",
		"�������顿������������֧�����磡����������������������ͼ�����е�һ��ֻ��Ͷ�����Ӽ����������������������Ϸ���㽫��Ϊ���������ϵ�ʡ�º͵���ս��ս������ϵ����̳��еĿ�Ƭ������ǿ��ľ��ӣ��������е���������������ͬ5λ���Խ�����¹��ԭ����ı�뿨Ƭ�Ľ�ϣ��������ҵĽ�������ʹ���º͵����Ҳ�ἤ�����ĺͶ�־��"
	};
	
	private static short NUMS = 0;
	/*��Ϸ�˵�����*/
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
	
	/*����������ɫ*/
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
