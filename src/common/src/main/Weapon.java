package common.src.main;

public class Weapon {

	private int DAMAGE, ATTACK_SPEED;
	private double COST;
	private String NAME;
	
	public Weapon(String name, int damage, int attackspeed, double cost) {
		this.DAMAGE = damage;
		this.NAME = name;
		this.ATTACK_SPEED = attackspeed;
		this.COST = cost;
	}
	
	
	
}
