/**
 * @author PKepa1
 * 
 */
package com.solider.war.core.sprites.model;
	
	
import static playn.core.PlayN.log;
import java.util.List;
import com.solider.war.core.model.GPoint;
import com.solider.war.core.sprites.Animation;
import playn.core.GroupLayer;
import static com.solider.war.core.Config.DEFAULT_HEALTH;
import static com.solider.war.core.Config.DEFAULT_SHIELD;

	
public class Tank extends Animation {
	
	private static final String IMAGE = "sprites/tank.png";
	private static final String JSON  = "json_config/tank.json";
	private final float TANK_WIDTH =  71.0f;
	private final float TANK_HEIGHT =  83.0f;
	
	private Barrel barrel;
	
	private float attackDistance;
	private float attackRange = 200;
	private int health;
	private int shield;
	
	public Tank( final float x, final float y, final GroupLayer... layer) {
		super(layer[0], x, y, IMAGE, JSON);
		this.width = TANK_WIDTH;
		this.height = TANK_HEIGHT;
		this.barrel = new Barrel(x, y, layer[1]);
		this.health = DEFAULT_HEALTH;
		this.shield = DEFAULT_SHIELD;
	}
	
//****************************************************************
//*********** Getters and Setters

	public void update(int delta, List<Animation> animations) {	
//		updateBarrel(delta, animations);
		super.update(delta, animations);
		this.barrel.setRotation(this.rotation);
	}
	public void updateBarrel(int delta, List<Animation> animations) {
//		this.barrel.setDestinationPoint(this.destinationPoint);
		this.barrel.setPosition(this.x, this.y);
		this.barrel.setSelected(this.isSelected());
		for(Animation animation : animations) {
			if(isInRange(animation)) {
				this.barrel.setRotationToMouse(new GPoint(animation.getX(), animation.getY()));
				this.barrel.setFire(true);
				break;
			} else {
				this.barrel.setFire(false);
			}
		}
	}

	public Barrel getBarrel() {
		return barrel;
	}

	public void setBarrel(Barrel barrel) {
		this.barrel = barrel;
	}

	@Override
	public void fire() {
		this.barrel.fire();
	}

	@Override
	public boolean isInRange(Animation enemy) {
		double enemyDistance = calcEnemyDistance(enemy);
		if(enemy != this && enemyDistance < attackRange) {
			return true;
		}
		return false;
	}

//****************************************************************
//*********** Getters and Setters
	
	public float getAttackDistance() {
		return attackDistance;
	}

	public void setAttackDistance(float attackDistance) {
		this.attackDistance = attackDistance;
	}

	public float getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(float attackRange) {
		this.attackRange = attackRange;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getShield() {
		return shield;
	}

	public void setShield(int shield) {
		this.shield = shield;
	}
}

