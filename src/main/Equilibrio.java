package main;

import java.util.*;

public class Equilibrio {
	
	private Map<String, Map<String, Integer>> equilibrio = new HashMap<>();
	
	Equilibrio(String elementi[]){
		//TODO: creazione della mappa
	}
	
	public void generaEquilibrio() {
		//TODO: creazione delle potenze tra elementi
	}

	public Map<String, Map<String, Integer>> getEquilibrio() {
		return equilibrio;
	}
	
	public Map<String, Integer> getPotenza(String elemento) {
		return equilibrio.get(elemento);
	}
	
	public int getPotenza(String primoElemento, String secondoElemento) {
		return equilibrio.get(primoElemento).get(secondoElemento);
	}
}