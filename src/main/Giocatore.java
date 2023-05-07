package main;

public class Giocatore {

	private String nome;
	private int tamaGolemRimanenti;

	private TamaGolem tamaGolemInCampo;

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

	public String getNome() {
		return nome;
	}

	public int getTamaGolemRimanenti() {
		return tamaGolemRimanenti;
	}

	public TamaGolem getTamaGolemInCampo() {
		return tamaGolemInCampo;
	}
	
	public int getVitaTamagolem() {
		return tamaGolemInCampo.getVita();
	}
	
	public Pietra[] getPietreMostrateDalTamagolem() {
		return tamaGolemInCampo.getPietreMostrate();
	}
	
	public String getStringaPietreMostrate() {
		return tamaGolemInCampo.getStringaPietreMostrate();
	}
	
	//***
	
	public void removeTamagolem() {
		tamaGolemInCampo = null;
	}
	
	//***
	
	public int danneggiaTamagolem(int danno) {
		return tamaGolemInCampo.danneggia(danno);
	}
	
	public boolean isTamaGolemVivo() {
		return tamaGolemInCampo.isVivo();
	}
	
	public boolean haAncoraTamaGolemVivi() {
		return tamaGolemRimanenti > 0;
	}
	
	public void diminuisciTamagoleRimanenti() {
		if(!isTamaGolemVivo())
			tamaGolemRimanenti--;
	}
	
	//***
	
	public boolean hannoPietreUgualiA(Giocatore altroGiocatore) {
		return tamaGolemInCampo.usaLeStessePietre(altroGiocatore.getTamaGolemInCampo());
	}
	
	public void forzaSconfitta() {
		this.setTamaGolemRimanenti(0);
	}
}