package common.src.main;

class item  {
	private String name, type;
	//<Add more variables depending on item type >
	private int cost, ID;
	
	public item(int ID, String name, String type, int cost) {
		this.ID=ID;
		this.name=name;
		this.type=type;
		this.cost=cost;
	}
	
	public int getCost() {
		return cost; 
	}
	
	public int getID() {
		return ID; 
	}
	
	public String getName() {
		return name; 
	}
	
	public String getType() {
		return type; 
	}
	
}
