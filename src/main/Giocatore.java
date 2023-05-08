package main;

public class Giocatore {

	//nome del giocatore
	private String nome;
	//quanti tamagolem gli rimangono prima di perdere
	private int tamaGolemRimanenti;

	//il tamagolem che sta combattendo
	private TamaGolem tamaGolemInCampo;

	/**
	 * costruttore del giocatore, chiede solo il nome
	 * @param nome - il nome del giocatore
	 */
	public Giocatore(String nome) {
		this.nome = nome;
	}
	
	//***

	/**
	 * setto il nome del giocatore
	 * @param nome - il nuovo nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * setto quanti tamagolem rimangono al giocatore
	 * @param tamaGolemRimanenti - quanti golem rimangono
	 */
	public void setTamaGolemRimanenti(int tamaGolemRimanenti) {
		this.tamaGolemRimanenti = tamaGolemRimanenti;
	}

	/**
	 * setto il tamagolem in campo
	 * @param tamaGolemInCampo - il nuovo tamagolem
	 */
	public void setTamaGolemInCampo(TamaGolem tamaGolemInCampo) {
		this.tamaGolemInCampo = tamaGolemInCampo;
	}
	
	//***

	/**
	 * @return il nome del giocatore
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return ritorna quanti tamagolem rimangono al giocatore
	 */
	public int getTamaGolemRimanenti() {
		return tamaGolemRimanenti;
	}

	/**
	 * @return il tamagolem che si trova in campo
	 */
	public TamaGolem getTamaGolemInCampo() {
		return tamaGolemInCampo;
	}
	
	/**
	 * @return la vita del tamagolem
	 */
	public int getVitaTamagolem() {
		return tamaGolemInCampo.getVita();
	}
	
	/**
	 * metodo che ritorna una stringa con scritti gli elementi delle pietre del tamagole
	 * con la particolarita di scrivere
	 * l'elemento della pietra solo se e' gia' stata usata.
	 * @return una stringa con gli elementi delle pietre usate fino al momento dell'invocazione
	 */
	public String getStringaPietreMostrate() {
		return tamaGolemInCampo.getStringaPietreMostrate();
	}
	
	//***
	
	/**
	 * rimuove il tamagolem in campo gettandolo tra le fiamme dell'Oblivion, ovvero settandolo a null
	 */
	public void removeTamagolem() {
		tamaGolemInCampo = null;
	}
	
	//***
	
	/**
	 * danneggia il tamagolem di un danno
	 * @param danno - quando diminuire la vita del tamagolem (e' sempre preso positivo)
	 * @return la vita rimanente al tamagolem del tamagolem
	 */
	public int danneggiaTamagolem(int danno) {
		return tamaGolemInCampo.danneggia(danno);
	}
	
	/**
	 * @return vero se il tamagolem in campo e' vivo, falso altrimenti
	 */
	public boolean isTamaGolemVivo() {
		return tamaGolemInCampo.isVivo();
	}
	
	/**
	 * @return vero se il giocatore ha almeno un tamagolem ancora in vita
	 */
	public boolean haAncoraTamaGolemVivi() {
		return tamaGolemRimanenti > 0;
	}
	
	/**
	 * toglie uno dei tamagolem rimanenti se il tamagolem in campo e' vivo
	 */
	public void diminuisciTamagoleRimanenti() {
		if(!isTamaGolemVivo())
			tamaGolemRimanenti--;
	}
	
	//***
	
	/**
	 * controlla se il giocatore e un'altro hanno tamagolem che usano le stesse pietre in sequenza
	 * @param altroGiocatore - l'altro giocatore del cui golem bisogna controllare
	 * @return vero se usano le stesse pietre all'unisono
	 */
	public boolean hannoPietreUgualiA(Giocatore altroGiocatore) {
		return tamaGolemInCampo.usaLeStessePietreDi(altroGiocatore.getTamaGolemInCampo());
	}
	
	/**
	 * metodo per debugging o cheattare, forza il giocatore a perdere la partita
	 */
	public void forzaSconfitta() {
		this.setTamaGolemRimanenti(0);
	}
}