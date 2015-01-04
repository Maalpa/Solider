package com.solider.war.core.path;

import com.solider.war.core.helpers.MapHelper;
import com.solider.war.core.sprites.Animation;
import com.solider.war.core.tools.MarkArea;
import com.solider.war.core.tools.Point;

import java.util.LinkedList;

import static com.solider.war.core.Config.*;



/**
 * @Autor Paweł Kępa
 * 
 * 
 */
public class CalcPath {
	
	private boolean foundDestinationPoint = false;
	private boolean foundPath = false;
	private MapPoint startPosition;
	private MapPoint destinationPosition;
 	private MapPoint[][]  pathMap= new MapPoint[PATH_MAP_SIZE][PATH_MAP_SIZE];
 	private LinkedList<MapPoint> Q = new LinkedList<MapPoint>();
 	private LinkedList<MapPoint> W = new LinkedList<MapPoint>();
 	private LinkedList<MapPoint> nearestPoints = new LinkedList<MapPoint>();

	public CalcPath() {
		// fill table with value equals -1
		for(int i = 0; i< pathMap.length; i++) {
			for(int j=0; j<pathMap[i].length; j++) {
				pathMap[i][j] = new MapPoint(i, j);
				pathMap[i][j].setValue(-1);
				pathMap[i][j].setVisited(false);
			}
		}
	}
	
	public void beforeCalc(Animation animation , MapPoint [][] map) {

		this.Q.clear();
		this.W.clear();
		this.foundPath = false;
		// calc destination point;
		int destX = (int) (Point.getTransformMousePoint().getX()/FIELD_SIZE);
		int destY = (int) (Point.getTransformMousePoint().getY()/FIELD_SIZE);
		
		// object cords
		int animationX = (int) (animation.getX()/FIELD_SIZE);
		int animationY = (int) (animation.getY()/FIELD_SIZE);	
	
		//System.out.println("object position(" + animationX +","+ animationY +")");
		//System.out.println("destination position(" + destX +","+ destY +")");
		
		destinationPosition = map[destX][destY];
		startPosition =  map[animationX][animationY];

	}

	public LinkedList<MapPoint> calcPath( Animation animation, MarkArea markArea, MapPoint[][] map) {

		beforeCalc(animation, map);

		this.Q.clear();
		this.W.clear();
		this.nearestPoints.clear();
		this.foundDestinationPoint= false;
			
		for(int i = 0; i< pathMap.length; i++) {
			for(int j=0; j<pathMap[i].length; j++) {
				pathMap[i][j].setValue(-1);
				pathMap[i][j].setVisited(false);
				pathMap[i][j].setX(i);
				pathMap[i][j].setY(j);
			}
		}
		
		// set start point in value 0
		pathMap[startPosition.getX()][startPosition.getY()].setValue(0);
		Q.add(pathMap[startPosition.getX()][startPosition.getY()]);
		
		MapPoint w = null;
		boolean foundFiled;
		
		while(!Q.isEmpty()) {
			w  = Q.poll();
			for(int i=0; i<8; i++) {
				if(i == 0) {
					if( (w.getX()+1) < pathMap.length && !pathMap[w.getX()+1][w.getY()].isVisited() && !map[w.getX()+1][w.getY()].isOccupied() ) {
						pathMap[w.getX()+1][w.getY()].setVisited(true);
						pathMap[w.getX()+1][w.getY()].setValue(w.getValue()+1);
						Q.add(pathMap[w.getX()+1][w.getY()]);
						addNearestDestinationPoint(pathMap[w.getX()+1][w.getY()]);
					}
				}

				if(i == 1) {
					if((w.getY()+1) < pathMap.length && !pathMap[w.getX()][w.getY()+1].isVisited() && !map[w.getX()][w.getY()+1].isOccupied() ) {
						pathMap[w.getX()][w.getY()+1].setVisited(true);
						pathMap[w.getX()][w.getY()+1].setValue(w.getValue()+1);
						Q.add(pathMap[w.getX()][w.getY()+1]);
						addNearestDestinationPoint(pathMap[w.getX()][w.getY()+1]);
					}
				}
				
				if(i == 2) {
					if((w.getX()-1) >= 0 && !pathMap[w.getX()-1][w.getY()].isVisited() &&  !map[w.getX()-1][w.getY()].isOccupied() ) {
						pathMap[w.getX()-1][w.getY()].setVisited(true);
						pathMap[w.getX()-1][w.getY()].setValue(w.getValue()+1);
						Q.add(pathMap[w.getX()-1][w.getY()]);
						addNearestDestinationPoint(pathMap[w.getX()-1][w.getY()]);
					}
				}

				if(i == 3) {
					if((w.getY()-1) >= 0 && !pathMap[w.getX()][w.getY()-1].isVisited() &&  !map[w.getX()][w.getY()-1].isOccupied()) {
						pathMap[w.getX()][w.getY()-1].setVisited(true);
						pathMap[w.getX()][w.getY()-1].setValue(w.getValue()+1);
						Q.add(pathMap[w.getX()][w.getY()-1]);
						addNearestDestinationPoint(pathMap[w.getX()][w.getY()-1]);
					}
				}
				
				if(i == 4) {
					if( (w.getX()-1) >= 0 && (w.getY()+1) < pathMap.length && !pathMap[w.getX()-1][w.getY()+1].isVisited() && !map[w.getX()-1][w.getY()+1].isOccupied() ) {
						pathMap[w.getX()-1][w.getY()+1].setVisited(true);
						pathMap[w.getX()-1][w.getY()+1].setValue(w.getValue()+1);
						Q.add(pathMap[w.getX()-1][w.getY()+1]);
						addNearestDestinationPoint(pathMap[w.getX()-1][w.getY()+1]);
					}
				}
				
				if(i == 5) {
					if( (w.getX()+1) < pathMap.length && (w.getY()+1) < pathMap.length && !pathMap[w.getX()+1][w.getY()+1].isVisited() && !map[w.getX()+1][w.getY()+1].isOccupied() ) {
						pathMap[w.getX()+1][w.getY()+1].setVisited(true);
						pathMap[w.getX()+1][w.getY()+1].setValue(w.getValue()+1);
						Q.add(pathMap[w.getX()+1][w.getY()+1]);
						addNearestDestinationPoint(pathMap[w.getX()+1][w.getY()+1]);
					}
				}
				
				if(i == 6) {
					if((w.getX()+1) < pathMap.length && (w.getY()-1) >= 0 && !pathMap[w.getX()+1][w.getY()-1].isVisited() && !map[w.getX()+1][w.getY()-1].isOccupied() ) {
						pathMap[w.getX()+1][w.getY()-1].setVisited(true);
						pathMap[w.getX()+1][w.getY()-1].setValue(w.getValue()+1);
						Q.add(pathMap[w.getX()+1][w.getY()-1]);
						addNearestDestinationPoint(pathMap[w.getX()+1][w.getY()-1]);
					}
				}
				
				if(i == 7) {
					if((w.getX()-1) >= 0 && (w.getY()-1) > 0 && !pathMap[w.getX()-1][w.getY()-1].isVisited() && !map[w.getX()-1][w.getY()-1].isOccupied()  ) {
						pathMap[w.getX()-1][w.getY()-1].setVisited(true);
						pathMap[w.getX()-1][w.getY()-1].setValue(w.getValue()+1);
						Q.add(pathMap[w.getX()-1][w.getY()-1]);
						addNearestDestinationPoint(pathMap[w.getX()-1][w.getY()-1]);
					}
				}
			}
			
			if((w.getX() == destinationPosition.getX() && w.getY() == destinationPosition.getY()) && !destinationPosition.isOccupied() ) {
				//System.out.println("Found Path"  + w.getValue() + "");
				foundDestinationPoint = true;
				break;
			}
		}
		
		if(foundDestinationPoint) {
			//System.out.println(" Destination has been found ");
		} else {
			//System.out.println(" DestinationPoint has not been found  !!!! ");
			if(nearestPoints != null && !nearestPoints.isEmpty()) w=nearestPoints.getFirst();
		}
		
		pathMap[startPosition.getX()][startPosition.getY()].setValue(0);
		W.add(w);
		while(!foundPath) {
			foundFiled = false;
			
			for(int i=0; i < 8; i++) {
				if(i == 0) {
					if(  (w.getX()+1)<pathMap.length && pathMap[w.getX()+1][w.getY()].getValue() == (w.getValue()-1) ) {
						w=pathMap[w.getX()+1][w.getY()];
						foundFiled = true;
						break;
					}
				}
				
				if(i == 1) {
					if( (w.getY()+1) < pathMap.length && pathMap[w.getX()][w.getY()+1].getValue() == (w.getValue()-1) ) {
						w=pathMap[w.getX()][w.getY()+1];
						foundFiled = true;
						break;
					}
				}
				
				if(i == 2) {
					if( (w.getX()-1) >= 0 && pathMap[w.getX()-1][w.getY()].getValue() == (w.getValue()-1) ) {
						w=pathMap[w.getX()-1][w.getY()];
						foundFiled = true;
						break;
					}
				}
	
				if(i == 3) {
					if( (w.getY()-1) >= 0  && pathMap[w.getX()][w.getY()-1].getValue() == (w.getValue()-1) ) {
						w=pathMap[w.getX()][w.getY()-1];
						foundFiled = true;
						break;
					}
				}
				
				if(i == 4) {
					if( (w.getY()-1) >= 0 && (w.getX()+1)<pathMap.length && pathMap[w.getX()+1][w.getY()-1].getValue() == (w.getValue()-1)) {
						w=pathMap[w.getX()+1][w.getY()-1];
						foundFiled = true;
						break;
					}
				}
				
				if(i == 5) {
					if( (w.getX()-1) >= 0 && (w.getY()+1)<pathMap.length && pathMap[w.getX()-1][w.getY()+1].getValue() == (w.getValue()-1) ) {
						w=pathMap[w.getX()-1][w.getY()+1];
						foundFiled = true;
						break;
					}
				}
				
				if(i == 6) {
					if( (w.getX()+1) < pathMap.length&& (w.getY()+1)<pathMap.length && pathMap[w.getX()+1][w.getY()+1].getValue() == (w.getValue()-1) ) {
						w=pathMap[w.getX()+1][w.getY()+1];
						foundFiled = true;
						break;
					}
				}
				
				if(i == 7) {
					if( (w.getX()-1) >= 0 && (w.getY()-1)>=0 && pathMap[w.getX()-1][w.getY()-1].getValue() == (w.getValue()-1) ) {
						w=pathMap[w.getX()-1][w.getY()-1];
						foundFiled = true;
						break;
					}
				}
			}
			
			if(!foundFiled) {
				//System.out.println("Path has not been found !!!");
				W.clear();
				break;
			}
			
			W.add(w);
	
			if(w.getX() == startPosition.getX() && w.getY() == startPosition.getY()) {
				foundPath = true;
				//System.out.println("Found Position");
				break;
			}
		}
		
//		drawing path 
		if (DRAW_PATH) {
			for(int i=0;i<W.size();i++) {
				markArea.markPath((W.get(i).getX()*FIELD_SIZE), (W.get(i).getY()*FIELD_SIZE), FIELD_SIZE, FIELD_SIZE);
			}
		}

		return W;
	}


	private void addNearestDestinationPoint( MapPoint point ) {

		point.setDestinationValue(MapHelper.calcPointDistance(point, destinationPosition));

		if((nearestPoints.isEmpty()) &&  !point.isOccupied()) {
			nearestPoints.add(point);
		} else {
			if((nearestPoints.getLast().getDestinationValue() > point.getDestinationValue())  &&  !point.isOccupied()) {
				nearestPoints.clear();
				nearestPoints.add(point);
			} else if((nearestPoints.getLast().getDestinationValue() == point.getDestinationValue()) &&  !point.isOccupied()) {
				nearestPoints.add(point);
			}
		}
	}

//*****************************************************************
// GETTERS AND SETTERS
//*****************************************************************

	public boolean isFoundDestinationPoint() {
		return foundDestinationPoint;
	}

	public void setFoundDestinationPoint(boolean foundDestinationPoint) {
		this.foundDestinationPoint = foundDestinationPoint;
	}

	public MapPoint getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(MapPoint startPosition) {
		this.startPosition = startPosition;
	}

	public MapPoint getDestinationPosition() {
		return destinationPosition;
	}

	public void setDestinationPosition(MapPoint destinationPosition) {
		this.destinationPosition = destinationPosition;
	}

	public MapPoint[][] getPathMap() {
		return pathMap;
	}

	public void setPathMap(MapPoint[][] pathMap) {
		this.pathMap = pathMap;
	}

	public LinkedList<MapPoint> getQ() {
		return Q;
	}

	public void setQ(LinkedList<MapPoint> q) {
		Q = q;
	}
	
}

