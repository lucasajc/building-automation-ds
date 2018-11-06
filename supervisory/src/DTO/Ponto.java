package DTO;

import java.io.Serializable;

public class Ponto implements Serializable{

	private static final long serialVersionUID = 8502952381169633196L;

	private double x;
	private double y;
	
	public Ponto(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	
	
}
