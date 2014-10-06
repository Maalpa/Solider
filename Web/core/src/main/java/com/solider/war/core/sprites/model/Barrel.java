package com.solider.war.core.sprites.model;

import static playn.core.PlayN.graphics;

import java.io.Serializable;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.solider.war.core.actors.ShotActor;
import playn.core.GroupLayer;
import com.solider.war.core.sprites.Animation;

public class Barrel extends Animation implements Serializable {
	
	public static String IMAGE = "sprites/barrel.png";
	public static String JSON  = "json_config/barrel.json";
	private Shot shot;
	private GroupLayer shotLayer;
	private GroupLayer parentLayer;
	private ActorRef shotRef = null;
	ActorSystem system = ActorSystem.create();



	public Barrel(float x, float y, GroupLayer... layer) {

		super(layer[0], x, y, IMAGE, JSON);
		this.width = 22.0f;
		this.height = 39.0f;
		this.parentLayer = layer[0];
		this.shotLayer = graphics().createGroupLayer();
		this.parentLayer.add(shotLayer);
		this.shotRef = system.actorOf(Props.create(ShotActor.class, this));

	}

	public void fire() {
		if(this.fire) {

			if( this.shot == null) {
				this.shot = new Shot(x, y, this.shotLayer );
			}
			this.shotLayer.setVisible(true);

			if(this.shot.isHasLoaded() && !this.shot.isPlaying()) {
				System.out.println("start !!! ");
				this.shot.setPlaying(true);
				shotRef.tell(this.shot, ActorRef.noSender());
			}

			this.shot.pointRotation( this.x , this.y, this.angle);
			this.shot.setRotation(this.rotation);
		} else {
			this.shotLayer.setVisible(false);
			this.shot.setPlaying(false);
			shotRef.tell("FINISH", ActorRef.noSender() );
		}
	}



	public void pointRotation(float bx, float by,  float angle ) {
		float x, y;
		if(Math.cos(angle) != 1.0 ) {
			x = (float) (bx + ( -(Math.cos(angle) * (5.1f))));
			y = (float) (by + ( Math.sin(angle) * (5.1f)));
		} 
		else {
			y = by-5;
			x = bx;
		}
        this.setPosition(x, y);
	}

	@Override
	public boolean isInRange(Animation enemy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString(){
		return "Barrel";
	}

}
