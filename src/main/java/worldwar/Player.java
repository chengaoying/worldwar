package worldwar;

/**
 * ��Һ�AI
 * @author Administrator
 *
 */
public class Player {
	
	/*ID  0Ϊ���, 1-5ΪAI*/
	private int id;
	
	/*ӵ�е�����id*/
	private int influenceId;
	
	/*��ǰ״̬: 0,�ȴ�   1,����  , -1��, 2Ӯ*/
	private int state;
	
	/*�غ���*/
	private int rounds;
	
	/*һ���غ�ʹ�õĵ�����*/
	private int usePropTimes;
	
	/*����͵ĵ���*/
	private Propety[] props;
	
	/*����*/
	private int scores;
	
	/*����������ɫ*/
	private int color;
	
	/*��鿨 */
	private boolean investigateCard;
	
	/*�߷���*/
	private boolean covertCard;
	
	/*���˱���*/
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
