/**
 * Copyright 2011 The PlayN Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.solider.war.core.sprites.model;


import static playn.core.PlayN.log;


import com.solider.war.core.model.MousePoint;
import com.solider.war.core.sprites.Animation;

import playn.core.GroupLayer;
import playn.core.util.Callback;
import org.jbox2d.collision.shapes.EdgeShape;

public class Tank extends Animation {
	
	private static String IMAGE = "sprites/tank.png";
	private static String JSON = "sprites/tank.json";
	
	private Barrel barrel;
	
	public Tank( final float x, final float y, final GroupLayer... layer) {
		
		super(layer[0], x, y, IMAGE, JSON);
		
		this.width = 71.0f;
		this.height = 83.0f;

		barrel = new Barrel(x, y, layer[1]);
	}	

	public void update(int delta, MousePoint mousePoint) {	
		updateBarrel(delta, mousePoint);
		super.update(delta, mousePoint);
	}
	
	private void updateBarrel(int delta, MousePoint mousePoint) {
		barrel.setDestinationPoint(this.destinationPoint);
		barrel.setAngle(this.getAngle());
		barrel.setObjectRotation(this.rotation);
		
		
		
		barrel.update(delta, mousePoint);
	}
}


