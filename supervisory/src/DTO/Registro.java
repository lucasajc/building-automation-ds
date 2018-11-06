package DTO;

import java.io.Serializable;

public class Registro implements Serializable{
	
	private static final long serialVersionUID = 1447687467697276113L;
	
	private String id;
	private String tipo;
	private String descricao;
	private int area;
	private int seq;
	private int estado;
	private double valor;
	private char variavel;
	private int pino;
	private String url;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public char getVariavel() {
		return variavel;
	}
	public void setVariavel(char variavel) {
		this.variavel = variavel;
	}
	public int getPino() {
		return pino;
	}
	public void setPino(int pino) {
		this.pino = pino;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
