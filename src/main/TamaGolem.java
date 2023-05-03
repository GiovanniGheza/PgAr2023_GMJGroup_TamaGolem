package main;

public class TamaGolem {
	
	public static final int MAX_VITA = 30;
	public static final int MAX_PIETRE_INSERITE = 3;
	
	private int vita = MAX_VITA;
	private Pietra pietre[] = new Pietra[MAX_PIETRE_INSERITE];
	private int pietraDaUsare = 0;
	

	TamaGolem(){
		
	}
	
	TamaGolem(Pietra pietre[]){
		this.pietre = pietre;
	}
	
	//***
	
	public void setVita(int vita) {
		this.vita = vita;
	}
	
	public void setPietraDaUsare(int pietraDaUsare) {
		this.pietraDaUsare = pietraDaUsare;
	}
	
	public void setPietre(Pietra[] pietre) {
		this.pietre = pietre;
	}
	
	//***

	public int getVita() {
		return vita;
	}

	public static int getMAX_VITA() {
		return MAX_VITA;
	}

	public static int getMAX_PIETRE_INSERITE() {
		return MAX_PIETRE_INSERITE;
	}

	public Pietra[] getPietre() {
		return pietre;
	}
	
	public int getPietraDaUsare() {
		return pietraDaUsare;
	}
	
	//***
	
	public int danneggia(int danno) {
		setVita(getVita() - danno);
		return getVita();
	}
	
	public boolean isVivo() {
		return getVita() > 0;
	}
}