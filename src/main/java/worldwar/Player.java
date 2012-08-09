package worldwar;

/**
 * 玩家和AI
 * @author Administrator
 *
 */
public class Player {
	
	/*ID  0为玩家, 1-5为AI*/
	private int id;
	
	/*拥有的势力id*/
	private int influenceId;
	
	/*当前状态: 0,等待   1,进攻  , -1输, 2赢*/
	private int state;
	
	/*回合数*/
	private int rounds;
	
	/*一个回合使用的道具数*/
	private int usePropTimes;
	
	/*随机送的道具*/
	private Propety[] props;
	
	/*积分*/
	private int scores;
	
	/*所属势力颜色*/
	private int color;
	
	/*侦查卡 */
	private boolean investigateCard;
	
	/*策反卡*/
	private boolean covertCard;
	
	/*撤退兵数*/
	private int soliders;
	
	public int getSoliders() {
		return soliders;
	}

	public void setSoliders(int soliders) {
		this.soliders = soliders;
	}

	public boolean isInvestigateCard() {
		return investigateCard;
	}

	public void setInvestigateCard(boolean investigateCard) {
		this.investigateCard = investigateCard;
	}

	public boolean isCovertCard() {
		return covertCard;
	}

	public void setCovertCard(boolean covertCard) {
		this.covertCard = covertCard;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getInfluenceId() {
		return influenceId;
	}

	public void setInfluenceId(int influenceId) {
		this.influenceId = influenceId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getRounds() {
		return rounds;
	}

	public void setRounds(int rounds) {
		this.rounds = rounds;
	}

	public Propety[] getProps() {
		return props;
	}

	public void setProps(Propety[] props) {
		this.props = props;
	}
	
	public int getScores() {
		return scores;
	}

	public void setScores(int scores) {
		this.scores = scores;
	}

	public void setUsePropTimes(int usePropTimes) {
		this.usePropTimes = usePropTimes;
	}

	public int getUsePropTimes() {
		return usePropTimes;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
