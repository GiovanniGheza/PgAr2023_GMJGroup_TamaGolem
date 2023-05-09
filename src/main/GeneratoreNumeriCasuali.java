package main;

import java.util.Random;

/**
 * semplice generatore di numeri casuali, se non specificato il seme del random si usa l'orario
 */
public class GeneratoreNumeriCasuali {

	private static Random estrattore = new Random(System.currentTimeMillis());
	
	/**
	 * setto il seed del generatore di random
	 * @param seed
	 */
	public static void setSeed(int seed) {
		estrattore.setSeed(seed);
	}
	
	/**
	 * estrae un intero casuale
	 * @param min - valore minimo
	 * @param max - valore massimo
	 * @return il valore estratto
	 */
	public static int estraiIntero(int min, int max){
		int range = max + 1 - min;
		int casuale = estrattore.nextInt(range);
		return casuale + min;
	}

	/**
	 * estrae un numero con virgola casuale
	 * @param min - valore minimo
	 * @param max - valore massimo
	 * @return il valore estratto
	 */
	public static double estraiDouble(double min, double max){
		double range = max - min;
		double casuale = estrattore.nextDouble();

		double posEstratto = range*casuale;

		return posEstratto + min;
	}
}