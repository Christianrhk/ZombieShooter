package common.src.main;

import java.util.concurrent.atomic.AtomicInteger;

class item  {
	private String name, type;
	//<Add more variables depending on item type >
	private static final AtomicInteger count = new AtomicInteger(0);
	private int cost, ID;
	
	public item(String name, String type, int cost) {
		this.ID=count.incrementAndGet();
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
