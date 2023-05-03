package main;

public class Giocatore {

	public static final int MAX_TAMAGOLEM = 6;

	private String nome;
	private int tamaGolemRimanenti = MAX_TAMAGOLEM;

	private TamaGolem tamaGolemInCampo;

	Giocatore(){
		
	}

	public Giocatore(String nome) {
		this.nome = nome;
	}
	
	//***

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTamaGolemRimanenti(int tamaGolemRimanenti) {
		this.tamaGolemRimanenti = tamaGolemRimanenti;
	}

	public void setTamaGolemInCampo(TamaGolem tamaGolemInCampo) {
		this.tamaGolemInCampo = tamaGolemInCampo;
	}
	
	//***

	public static int getMaxTamagolem() {
		return MAX_TAMAGOLEM;
	}

	public String getNome() {
		return nome;
	}

	public int getTamaGolemRimanenti() {
		return tamaGolemRimanenti;
	}

	public TamaGolem getTamaGolemInCampo() {
		return tamaGolemInCampo;
	}
	
	//***
	
	/**
	 * 
	 * @param danno - il danno subito dal tamagolem
	 * @return vero se il tamagolem è ancora in vita
	 */
	public boolean subisceAttacco(int danno) {
		return danneggiaTamagolem(danno) > 0;
	}
	
	public int danneggiaTamagolem(int danno) {
		return tamaGolemInCampo.danneggia(danno);
	}
	
	public boolean isTamaGolemVivo() {
		return tamaGolemInCampo.isVivo();
	}
	
	public boolean haAncoraTamaGolemVivi() {
		return tamaGolemRimanenti > 0;
	}
}