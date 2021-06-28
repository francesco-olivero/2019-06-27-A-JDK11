package it.polito.tdp.crimes.model;

public class Arco {
	
	String tipoReato1;
	String tipoReato2;
	int peso;
	
	public Arco(String tipoReato1, String tipoReato2, int peso) {
		super();
		this.tipoReato1 = tipoReato1;
		this.tipoReato2 = tipoReato2;
		this.peso = peso;
	}

	public String getTipoReato1() {
		return tipoReato1;
	}

	public void setTipoReato1(String tipoReato1) {
		this.tipoReato1 = tipoReato1;
	}

	public String getTipoReato2() {
		return tipoReato2;
	}

	public void setTipoReato2(String tipoReato2) {
		this.tipoReato2 = tipoReato2;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "Arco [tipoReato1=" + tipoReato1 + ", tipoReato2=" + tipoReato2 + ", peso=" + peso + "]";
	}

	
}
