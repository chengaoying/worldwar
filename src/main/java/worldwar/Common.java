package worldwar;

/**
 * 公共变量
 * @author Administrator
 *
 */
public interface Common {
	
	public static final byte GAME_STATUS_MAIN_MENU = 0;
	public static final byte GAME_STATUS_PLAYING = 1;
	public static final byte GAME_STATUS_HELP = 2;
	public static final byte GAME_STATUS_RANKING = 3;
	
	public static final byte STATE_SEL_MAP = 11;
	public static final byte STATE_SEL_FIGHTING = 12;
	public static final byte STATE_SEL_REGION = 13;
	
	public static final byte STATUS_ATTACK = 0;
	public static final byte STATUS_WAIT = 1;
	public static final byte GAME_OVER = -1;
	public static final byte GAME_SUCCESS = 2;
	
	public static final int ID_GREEN = 0;
	public static final int ID_YELLOW = 1;
	public static final int ID_RED = 2;
	public static final int ID_BROWN = 3;
	public static final int ID_ASHY = 4;
	public static final int ID_BLUE = 5;
	
	public static final int COLOR_GREEN = 0X677424;
	public static final int COLOR_YELLOW = 0Xab7b33;
	public static final int COLOR_RED = 0X8a432f;
	public static final int COLOR_BROWN = 0X3f281b;
	public static final int COLOR_ASHY = 0X454a43;
	public static final int COLOR_BLUE = 0X578481;
	
	public static final int DEFENSE_CARD = 44;  		/*防御卡*/
	public static final int RETREAT_CARD = 45;			/*撤退卡*/
	public static final int LANDMINE_CARD = 46;			/*地雷卡*/
	public static final int ARMOURED_CARD = 49;			/*装甲卡*/
	public static final int BOMBARD_CARD = 48;			/*炮击卡*/
	public static final int AIRATTACK_CARD = 51;		/*空袭卡*/
	public static final int INVESTIGATE_CARD = 50;		/*侦查卡*/
	public static final int HIDDEN_CARD = 47;			/*隐藏卡*/
	public static final int COVERT_CARD = 52;			/*策反卡*/

	public static final int BASE_VALUE = 44;
	public static final int USE_CARD_TIMES = 3;			/*一个回合最多使用卡片的次数*/
	
	public static final int REGION_SELECTED = 1;		/*选中*/
	public static final int REGION_CHOICE = 0;			/*选择*/
	
	 public static final short Abs_Coords_X = 35, Abs_Coords_Y = 20; //鼎亿同州盒子起点
}
