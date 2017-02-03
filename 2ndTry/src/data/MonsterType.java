package data;

public enum MonsterType {
	Slime("slime", 50, 50, 10, 0);

	String textureName;
	int maxHealth;
	int health;
	int atackDamage;
	int magicDamage;

	MonsterType(String textureName, int maxHealth, int health, int atackDamage, int magicDamage) {
		this.textureName = textureName;
		this.maxHealth = maxHealth;
		this.health = health;
		this.atackDamage = atackDamage;
		this.magicDamage = magicDamage;
	}
}
