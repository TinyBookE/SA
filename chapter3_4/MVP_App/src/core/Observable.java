package core;

import java.util.ArrayList;

public abstract class Observable {
	ArrayList<Observer> observers = new ArrayList<Observer>();
	public void addObserver(Observer observer) {
		observers.add(observer);
	}
	
	public void NotifyObservers() {
		for (Observer observer : observers) {
			observer.update();
		}
	}
}
