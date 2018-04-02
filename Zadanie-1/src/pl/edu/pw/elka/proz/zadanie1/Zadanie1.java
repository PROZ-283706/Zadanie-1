package pl.edu.pw.elka.proz.zadanie1;

import java.util.Optional;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * <p>
 * Tresc zadania do wykonania: Zadaniem projektu jest: Zbudowac okienko
 * logowania do systemu. Okienko ma zawierac: liste wyboru z mozliwością wyboru
 * srodowiska: produkcyjnego, testowego, deweloperskiego, listę wyboru
 * identyfikatora uzytkownika z mozliwoscia edycji, pola edycji na wprowadzenia
 * hasla, przyciskow: Logon i Anuluj
 * </p>
 * **********************************************************************
 * 
 * @autor AdrianNadulny /
 * @version 1.1
 * 
 *          Klasa Zadanie1 odpowiada za wyswietlanie okna logowania do systemu
 * 
 **********************************************************************/
public class Zadanie1 extends Application {

	/**
	 * Glowna metoda odpowiedzialna za uruchomienie okienka
	 * 
	 * @param args sa to standardowe argumenty wywolania
	 */
	public static void main(String[] args) {

		launch(args);
	}

	/**
	 * Metoda start tworzy obiekt klasy LogonDialog i wyswietla okienko do
	 * logowania. Jezeli uzytkownik wprowadzi poprawna nazwe uzytkownika i haslo,
	 * obiekt zwraca dwie wartosci, typu Environment oraz typu String
	 * 
	 * @param primaryStage glowne okno dla aplikacji
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		Optional<Pair<Environment, String>> result = (new LogonDialog("Logowanie", "Logowanie do systemu STYLEman")).showAndWait();

		if (result.isPresent()) {
			result.ifPresent(val -> {
				System.out.println("Zalogowano pomyślnie do środowiska: " + val.getKey().getEnviromentName() + " jako: " + val.getValue());
			});
		}
		else 
			System.out.println("Logowanie zakonczone niepowodzeniem!!! Sprawdź poprawną nazwę użytkownika lub hasło.");

	}

}
