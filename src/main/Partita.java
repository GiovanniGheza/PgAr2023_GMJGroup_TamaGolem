package main;

public class Partita {
	
	//TODO: guardare la formula su slide
	public static final int MAX_PIETRE_A_DISPOSIZIONE = 10;
	
	private int turno = 0;
	private int fase = 0;
	
	private Giocatore giocatoreA, giocatoreB;
	
	//private Equilibrio equilibrio;
	
	private Pietra pietreADisposizione[] = new Pietra[MAX_PIETRE_A_DISPOSIZIONE];

	public Partita(Giocatore giocatoreA, Giocatore giocatoreB, Pietra[] pietreADisposizione) {
		this.giocatoreA = giocatoreA;
		this.giocatoreB = giocatoreB;
		this.pietreADisposizione = pietreADisposizione;
	}
	
	public void generaEquilibrio() {
		//TODO: da fare
	}
	
	public void generaTamaGolem(char Giocatore, int pietreScelte[]) {
		//TODO: da fare
	}
	
	public void eseguiSetUp() {
		generaEquilibrio();
	}
	
	public String eseguiTurno() {
		//TODO: da fare
		return "Teofrasto";
	}
	
	public boolean eseguiFinePartita() {
		return checkFinePartita();
	}
	
	public String getEquilibrioAsString() {
		//TODO: restituire l'equilibrio come stringa
		return "Gatto";
	}
	
	public boolean checkFinePartita() {
		return !giocatoreA.haAncoraTamaGolemVivi() || !giocatoreA.haAncoraTamaGolemVivi();
	}
}