package worldwar;

/**
 * ����
 * @author Administrator
 *
 */
public class Propety {
	
	/*ID*/
	private int id;
	
	/*��������*/
	private String name;
	
	/*����ID*/
	private int propId;
	
	/*��������*/
	private int nums;
	
	/*����*/
	private String desc;
	
	/*���߼۸�*/
	private int price;
	

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getPropId() {
		return propId;
	}

	public void setPropId(int propId) {
		this.propId = propId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNums() {
		return nums;
	}

	public void setNums(int nums) {
		this.nums = nums;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
