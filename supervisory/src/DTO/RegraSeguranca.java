package DTO;

import java.io.Serializable;
import java.sql.Time;

public class RegraSeguranca implements Serializable{
	private static final long serialVersionUID = 8076506223140173093L;
	
	private int idRegraSeguranca;
	private int idComodo;
	private Time horaInicio;
	private Time horaFim;
	public int getIdRegraSeguranca() {
		return idRegraSeguranca;
	}
	public void setIdRegraSeguranca(int idRegraSeguranca) {
		this.idRegraSeguranca = idRegraSeguranca;
	}
	public int getIdComodo() {
		return idComodo;
	}
	public void setIdComodo(int idComodo) {
		this.idComodo = idComodo;
	}
	public Time getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(Time horaInicio) {
		this.horaInicio = horaInicio;
	}
	public Time getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(Time horaFim) {
		this.horaFim = horaFim;
	}
	
	
}
