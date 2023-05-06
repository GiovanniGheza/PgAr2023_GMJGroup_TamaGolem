package main;

public class Pietra {

	private String elemento;

	Pietra(String elemento){
		this.elemento = elemento;
	}

	public void setElemento(String elemento) {
		this.elemento = elemento;
	}

	public String getElemento() {
		return elemento;
	}
	
	public boolean equals(Pietra pietra) {
		return this.elemento == pietra.getElemento();
	}
}