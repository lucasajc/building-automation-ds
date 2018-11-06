package DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class CamadaSupervisorio implements Serializable{
	
	private static final long serialVersionUID = -236638870892281308L;
	
	private ArrayList<Supervisorio> supervisorios;

	public CamadaSupervisorio() {
		this.supervisorios = new ArrayList<Supervisorio>();
	}

	/**
	 * @return the supervisorios
	 */
	public ArrayList<Supervisorio> getSupervisorios() {
		return supervisorios;
	}

	/**
	 * @param supervisorios the supervisorios to set
	 */
	public void setSupervisorios(ArrayList<Supervisorio> supervisorios) {
		this.supervisorios = supervisorios;
	}
	
	
}
