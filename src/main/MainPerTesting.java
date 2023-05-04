package main;

public class MainPerTesting {

	public static void main(String[] args) {
		String elementi[] = {"casa", "pane", "gatto", "topo", "joe"};

		Equilibrio myEquilibrio = new Equilibrio(elementi);

		for(int i = 0; i < 5; i++) {
			myEquilibrio.generaEquilibrio();
			String tabella = myEquilibrio.toString();
			System.out.print("\n\nProva " + i + " \n" + tabella);
		}
	}
}