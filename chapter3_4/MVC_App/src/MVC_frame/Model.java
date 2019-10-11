package MVC_frame;

import core.Observable;

public class Model extends Observable {
	final int BALL_SIZE =20;
	int xPosition = 0;
	int yPosition = 0;
	int xLimit, yLimit;
	int xDelta = 6, yDelta = 4;
	
	public Model() {
		
	}
	
	public Model(int xLimit, int yLimit) {
		this.xLimit = xLimit;
		this.yLimit = yLimit;
	}
	
	public void makeOneStep() {
		xPosition += xDelta;
		if(xPosition < 0 || xPosition >= xLimit) {
			xDelta = -xDelta;
			xPosition += xDelta;
		}
		
		yPosition += yDelta;
		if(yPosition <0 || yPosition >= yLimit) {
			yDelta = -yDelta;
			yPosition += yDelta;
		}
		
		NotifyObservers();
	}
}
