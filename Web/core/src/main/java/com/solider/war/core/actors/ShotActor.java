package com.solider.war.core.actors;

import com.solider.war.core.sprites.model.Barrel;
import com.solider.war.core.sprites.model.Shot;

/**
 * User: PKepa1
 * Date: 06.10.14
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */
public class ShotActor {


	private Barrel barrel;

	public ShotActor(Barrel barrel) {
		this.barrel = barrel;
	}

	public void onReceive(Object o) throws Exception {
		if(o instanceof  Shot) {
			Shot shot = (Shot) o;
			for(int i = 0 ; i<shot.getSprite().numSprites(); i++) {
				try {
					shot.getSprite().setSprite(i);
				} catch (Exception e) {
					System.out.println("OpenGL Exception with getting shot  sprite");
				}
				Thread.sleep(50);
			}
			this.barrel.setFire(false);
			Thread.sleep(500);
			shot.setPlaying(false);
		}
	}
}
