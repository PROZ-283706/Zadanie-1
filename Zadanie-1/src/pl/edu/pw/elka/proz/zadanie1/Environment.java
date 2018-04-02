package pl.edu.pw.elka.proz.zadanie1;

/**
 * Obiekty klasy Environment przechowuja nazwe srodowiska, 
 * do ktorego mozna sie zalogowac 
 * 
 * @author AdrianNadulny
 */

public class Environment {
	private String environmentName;
	
	/**
	 * Konstruktor uzywajacy jako parametru wejsciowego nazwe srodowiska
	 * @param environment - nazwa srodowiska
	 */
	public Environment(String environment) {
		this.environmentName = environment;
	}
	
	/**
	 * Metoda ktora zwraca nazwe danego srodowiska 
	 * @return string - nazwa srodowiska
	 */
	public String getEnviromentName()	{
		return this.environmentName;
	}
	
	
	public String toString() {
		return this.environmentName;
	}
}