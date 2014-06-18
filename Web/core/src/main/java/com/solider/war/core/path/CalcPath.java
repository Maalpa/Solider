package com.solider.war.core.path;

import java.util.ArrayList;
import java.util.LinkedList;
import com.solider.war.core.sprites.Animation;
import com.solider.war.core.tools.Point;

public class CalcPath {
	
	private final int  MAP_SIZE = 1021;
	private boolean foundDestinationPoint = false;
	
	private PathPoint startPosition;
	private PathPoint destinationPosition;
 	private PathPoint[][]  pathMap= new PathPoint[MAP_SIZE][MAP_SIZE];
 
 	private LinkedList<PathPoint> Q = new LinkedList<PathPoint>();
 	private ArrayList<PathPoint> W = new ArrayList<PathPoint>();
 	
	
	public CalcPath(Animation animation , int mapSize) {
		
		// calc destination point;
		int destX = (int) (Point.getTransformMousePoint().getX()/30);
		int destY = (int) (Point.getTransformMousePoint().getY()/30);
				
		// object cords
		int animationX = (int) (animation.getX()/30);
		int animationY = (int) (animation.getY()/30); 		
				
		System.out.println("object position(" + animationX +","+ animationY +")");
		System.out.println("destination position(" + destX +","+ destY +")");

		startPosition = new PathPoint(destX, destY);
		destinationPosition = new PathPoint(animationX, animationY);
	
//		pathMap= new int[mapSize][mapSize];
	}
	
	public void calcPath() {
		// uzupełniamy tablice wartosciami -1
		for(int i = 0; i< pathMap.length; i++) {
			for(int j=0; j<pathMap[i].length; j++) {
				pathMap[i][j] = new PathPoint(i, j);
				pathMap[i][j].setValue(-1);
				pathMap[i][j].setVisited(false);
			}
		}
		
		// ustawiamy punkt startowy na 0
		pathMap[destinationPosition.getX()][destinationPosition.getY()].setValue(0);
		Q.add(pathMap[destinationPosition.getX()][destinationPosition.getY()]);
		
		PathPoint v;
		PathPoint w;
		while(!Q.isEmpty()) {
			
			v  = Q.poll();
			W.add(v);
			w = v;
			
			for(int i=0; i<8; i++) {
				if(i == 0) {
					if(!pathMap[w.getX()+1][w.getY()].isVisited()) {
						pathMap[w.getX()+1][w.getY()].setVisited(true);
						pathMap[w.getX()+1][w.getY()].setValue(w.getValue()+1);
						Q.add(pathMap[w.getX()+1][w.getY()]);
					}
				}
				if(i == 1) {
					if(!pathMap[w.getX()+1][w.getY()+1].isVisited()) {
						pathMap[w.getX()+1][w.getY()+1].setVisited(true);
						pathMap[w.getX()+1][w.getY()+1].setValue(w.getValue()+1);
						Q.add(pathMap[w.getX()+1][w.getY()+1]);
					}
				}
				if(i == 2) {
					if(!pathMap[w.getX()][w.getY()+1].isVisited()) {
						pathMap[w.getX()][w.getY()+1].setVisited(true);
						pathMap[w.getX()][w.getY()+1].setValue(w.getValue()+1);
						Q.add(pathMap[w.getX()+1][w.getY()+1]);
					}
				}
				if(i == 3) {
					if(!pathMap[w.getX()-1][w.getY()].isVisited()) {
						pathMap[w.getX()-1][w.getY()].setVisited(true);
						pathMap[w.getX()-1][w.getY()].setValue(w.getValue()+1);
						Q.add(pathMap[w.getX()-1][w.getY()]);
					}
				}
				if(i == 4) {
					if(!pathMap[w.getX()-1][w.getY()-1].isVisited()) {
						pathMap[w.getX()-1][w.getY()-1].setVisited(true);
						pathMap[w.getX()-1][w.getY()-1].setValue(w.getValue()+1);
						Q.add(pathMap[w.getX()-1][w.getY()-1]);
					}
				}
				if(i == 5) {
					if(!pathMap[w.getX()][w.getY()-1].isVisited()) {
						pathMap[w.getX()][w.getY()-1].setVisited(true);
						pathMap[w.getX()][w.getY()-1].setValue(w.getValue()+1);
						Q.add(pathMap[w.getX()][w.getY()-1]);
					}
				}
				if(i == 5) {
					if(!pathMap[w.getX()+1][w.getY()-1].isVisited()) {
						pathMap[w.getX()+1][w.getY()-1].setVisited(true);
						pathMap[w.getX()+1][w.getY()-1].setValue(w.getValue()+1);
						Q.add(pathMap[w.getX()+1][w.getY()-1]);
					}
				}
				if(i == 5) {
					if(!pathMap[w.getX()-1][w.getY()+1].isVisited()) {
						pathMap[w.getX()-1][w.getY()+1].setVisited(true);
						pathMap[w.getX()-1][w.getY()+1].setValue(w.getValue()+1);
						Q.add(pathMap[w.getX()-1][w.getY()+1]);
					}
				}
			}
			
			System.out.println("SIZEEEEE!!!!" +  W.size());
			
			if(w.getX() == destinationPosition.getX() && w.getY() == destinationPosition.getY()) {
				System.out.println("Found Path");
				System.out.println("+++++++++++++++++++PATH++++++++++++++++++++++");
				
				System.out.println(W.size());
				
				for(int i=0; i<W.size(); i++) {
					System.out.println("("+W.get(i).getX()+","+W.get(i).getY()+")");
				}
				
				System.out.println("+++++++++++++++++++PATH++++++++++++++++++++++");
				break;
			}
		}
	}
	
	public boolean isFoundDestinationPoint() {
		return foundDestinationPoint;
	}

	public void setFoundDestinationPoint(boolean foundDestinationPoint) {
		this.foundDestinationPoint = foundDestinationPoint;
	}

	public PathPoint getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(PathPoint startPosition) {
		this.startPosition = startPosition;
	}

	public PathPoint getDestinationPosition() {
		return destinationPosition;
	}

	public void setDestinationPosition(PathPoint destinationPosition) {
		this.destinationPosition = destinationPosition;
	}

	public PathPoint[][] getPathMap() {
		return pathMap;
	}

	public void setPathMap(PathPoint[][] pathMap) {
		this.pathMap = pathMap;
	}

	public LinkedList<PathPoint> getQ() {
		return Q;
	}

	public void setQ(LinkedList<PathPoint> q) {
		Q = q;
	}

	public int getMAP_SIZE() {
		return MAP_SIZE;
	}
	
}

