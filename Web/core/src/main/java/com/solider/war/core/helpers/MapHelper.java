package com.solider.war.core.helpers;

import com.solider.war.core.path.MapPoint;

public class MapHelper {
	
	public static double calcPointDistance(MapPoint pointA, MapPoint pointB) { 
		return Math.sqrt( ((pointB.getX()-pointA.getX())*(pointB.getX()-pointA.getX())) +
						((pointB.getY()-pointA.getY())*(pointB.getY()-pointA.getY())) );
	}
	
}
