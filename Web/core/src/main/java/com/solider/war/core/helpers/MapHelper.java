package com.solider.war.core.helpers;

import static com.solider.war.core.Config.FIELD_SIZE;

import com.solider.war.core.path.MapPoint;

public class MapHelper {

	public static double calcPointDistance(MapPoint pointA, MapPoint pointB) {
		return Math.sqrt( ((pointB.getX()-pointA.getX())*(pointB.getX()-pointA.getX())) +
						((pointB.getY()-pointA.getY())*(pointB.getY()-pointA.getY())) );
	}
	
	public static MapPoint getPointOnMap( MapPoint mapPoint) {
		int mapPointX = (int) (mapPoint.getX()/FIELD_SIZE);
		int mapPointY = (int) (mapPoint.getY()/FIELD_SIZE);
		return new MapPoint(mapPointX, mapPointY);
	}
}
