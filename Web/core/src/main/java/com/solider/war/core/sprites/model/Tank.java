/**
 * @author PKepa1
 * 
 */
package com.solider.war.core.sprites.model;


import com.solider.war.core.model.GPoint;
import com.solider.war.core.path.MapPoint;
import com.solider.war.core.sprites.Animation;
import com.solider.war.core.tools.MarkArea;
import playn.core.GroupLayer;

import java.util.List;

import static com.solider.war.core.Config.DEFAULT_HEALTH;
import static com.solider.war.core.Config.DEFAULT_SHIELD;

public class Tank extends Animation {
	
	private static final String IMAGE = "sprites/tank.png";
	private static final String JSON  = "json_config/tank.json";
	private final double ATTACK_RANGE = 200.0f;
	private final float TANK_WIDTH =  40.0f;
	private final float TANK_HEIGHT =  49.0f;
	private Barrel barrel;
	private Animation enemyToShot;
	private double enemyMinDistance = ATTACK_RANGE;
	private float attackDistance;
	private double attackRange = ATTACK_RANGE;
	private int health = 100;
	private int shield = 100;
	
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

	public void update(int delta, List<Animation> animations, Animation animation, MarkArea markArea, MapPoint[][] map) {	
		super.update(delta, animations, animation, markArea, map );
	}
	
	public void updateBarrel(int delta, List<Animation> animations) {
		this.barrel.setSelected(this.isSelected());
		for(Animation animation : animations) {
			if(isInRange(animation)) {
				this.barrel.setRotationToMouse(new GPoint(enemyToShot.getX(), enemyToShot.getY()));
				this.barrel.setFire(true);
			}
		}
		
		if(this.barrel.isFire()) {
			this.barrel.pointRotation(this.x, this.y, this.barrel.getAngle());
		} else {
			this.barrel.setRotation(this.rotation);
			this.barrel.pointRotation(this.x, this.y, this.angle);
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
			// get closer stands enemy to shot
			if(enemyMinDistance >=  enemyDistance ) {
				enemyToShot =  enemy;	
				enemyMinDistance = enemyDistance; 
			} 
			if(enemy == enemyToShot) {
				enemyMinDistance = enemyDistance;
			}
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
	
	@Override
	public String toString(){
		return "Tank";
	}
}
