package com.solider.war.core.actors;

import akka.actor.UntypedActor;
import com.solider.war.core.sprites.model.Barrel;
import com.solider.war.core.sprites.model.Shot;
import playn.core.GroupLayer;

/**
 * Created with IntelliJ IDEA.
 * User: PKepa1
 * Date: 06.10.14
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */
public class ShotActor extends UntypedActor {

	private Barrel barrel;

	public ShotActor(Barrel barrel) {
		this.barrel = barrel;
	}


	@Override
	public void onReceive(Object o) throws Exception {

		Thread.sleep(2000);
		if(o instanceof  Shot) {
			Shot shot = (Shot) o;
			for(int i = 0 ; i<3; i++) {
				System.out.println("LOOP");
//				shot.getSprite().s  etSprite(i);
				Thread.sleep(200);
			}
			this.barrel.setFire(false);
			Thread.sleep(1000);
			System.out.println("Test message from Shot Actor !!! " + Thread.currentThread().getId());
		} else {
			System.out.println(" Sends from not ");
		}

	}
}
