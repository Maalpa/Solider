package com.solider.war.core.actors;

import com.solider.war.core.sprites.model.Barrel;
import com.solider.war.core.sprites.model.Shot;

/**
 * User: PKepa1
 * Date: 12.10.14
 * Time: 14:28
 * To change this template use File | Settings | File Templates.
 */
public class ShotThread  {

	private Barrel barrel;
	private Shot shot;

	public ShotThread(Barrel barrel, Shot shot) {
		this.barrel = barrel;
		this.shot = shot;
	}



//	@Override
	public void run() {
		System.out.println("Starting Thread  ...");
		for (int i = 0; i < shot.getSprite().numSprites(); i++) {
			try {
				shot.getSprite().setSprite(i);
			} catch (Exception e) {
				System.out.println("OpenGL Exception with getting shot sprite");
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

//		this.barrel.setFire(false);
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//		}
		this.shot.setPlaying(false);
	}

}
