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
package com.solider.war.model.sprites.impl;


import static playn.core.PlayN.log;


import com.solider.war.core.model.MousePoint;
import com.solider.war.core.sprites.Sprite;
import com.solider.war.core.sprites.SpriteLoader;
import com.solider.war.model.sprites.Animation;

import playn.core.GroupLayer;
import playn.core.util.Callback;
import org.jbox2d.collision.shapes.EdgeShape;

public class Solider extends Animation {
	
	public static String IMAGE = "sprites/solider_mini.png";
	public static String JSON = "sprites/solider.json";
	
	public Solider(final GroupLayer soliderLayer, final float x, final float y) {
		super(soliderLayer, x, y, IMAGE, JSON);
	}

	public void update(int delta, MousePoint mousePoint) {	
		super.update(delta, mousePoint);
	}
}


