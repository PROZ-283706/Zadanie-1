package pl.edu.pw.elka.proz.zadanie1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;


/**
 * <p>
 * Klasa uzupelniajaca okienko logowania
 * W sklad okienka wchodza dwa przyciski "Login" i "Anuluj"
 * Klasa zwraca pare wartosci typu Environment i typu String 
 * Korzysta z ukladu zarzadcy panelu GridPane i z klasy Environment  
 * </p>
 * 
 * @author AdrianNadulny
 *
 */

public class LogonDialog {
	
	private Dialog<ButtonType> dialog = new Dialog<ButtonType>();

	private ChoiceBox<Environment> choiceBox = new ChoiceBox<Environment>();
	private ComboBox<String> comboBox = new ComboBox<String>();
	private PasswordField passwordField = new PasswordField();

	private ButtonType logonButtonType = new ButtonType("Logon", ButtonData.OK_DONE);

	ObservableList<String> productionUserList = FXCollections.observableArrayList();
	ObservableList<String> testerUserList = FXCollections.observableArrayList();
	ObservableList<String> developerUserList = FXCollections.observableArrayList();

	List<String> productionUserPasswords = new ArrayList<String>();
	List<String> testerUserPasswords = new ArrayList<String>();
	List<String> developerUserPasswords = new ArrayList<String>();
	
	/**
	 * Konstruktor tworzacy dialog z wykorzystaniem zarzadcy panelu GridPane 
	 * 
	 * @param title tekst wyswietlenia tytulu okna, znajduje sie na samej gorze okienka
	 * @param header tekst wyswietlany u gory okienka
	 */
	public LogonDialog(String title, String header) {

		dialog.setTitle(title);
		dialog.setHeaderText(header);
		
		initialLists();

		// Dodanie zdjecia do dialogu
		dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

		//Utworzenie GridPanel
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 40, 20, 10));

		// Pole Srodowisko
		choiceBox.setPrefSize(170, 20);
		choiceBox.setItems(FXCollections.observableArrayList(new Environment("Produkcyjne"), new Environment("Testowe"),
				new Environment("Deweloperskie")));
		choiceBox.getSelectionModel().selectFirst();
		choiceBox.getSelectionModel().selectedItemProperty().addListener((val, oldVal, newVal) -> {
			environmentChanged(choiceBox.getValue());
		});
		choiceBox.valueProperty().addListener((observable, oldVal, newVal) -> logonButtonChanged());
		grid.add(new Label("Œrodowisko:"), 2, 0);
		grid.add(choiceBox, 4, 0);

		// Pole Uzytkownik
		comboBox.setEditable(true);
		comboBox.getEditor().textProperty().addListener((observable, oldVal, newVal) -> logonButtonChanged());
		comboBox.setItems(productionUserList);
		grid.add(new Label("U¿ytkownik:"), 2, 1);
		grid.add(comboBox, 4, 1);

		// Pole Haslo
		passwordField.setPromptText("Has³o");
		passwordField.textProperty().addListener((observable, oldVal, newVal) -> logonButtonChanged());
		grid.add(new Label("Has³o:"), 2, 2);
		grid.add(passwordField, 4, 2);

		// dodanie zarzadcy do okienka
		dialog.getDialogPane().setContent(grid);

		// Ustawienie typu przycisku Anuluj
		ButtonType anulujButtonType = new ButtonType("Anuluj", ButtonData.CANCEL_CLOSE);

		dialog.getDialogPane().getButtonTypes().addAll(logonButtonType, anulujButtonType);

		Node logonButton = dialog.getDialogPane().lookupButton(logonButtonType);
		logonButton.setDisable(true);
	}

	/**
	 * Metoda wypelniajaca listy uzytkownikow i listy hasel
	 */
	private void initialLists() {

		productionUserList.addAll("Adam.Nowak", "Ewa.Cudna", "Jan.Kowalski", "Marek.Piorun");
		testerUserList.addAll("Jan.Kubik", "Kowal.Kowal", "Olgierd.Kalmar", "Marta.Madonna");
		developerUserList.addAll("Witek.Luzak", "Sonia.Sonik", "Stefan.Batory", "Olga.Liroy");

		productionUserPasswords.addAll(Arrays.asList("kot", "pies", "fraNek", "Wuuulkan"));
		testerUserPasswords.addAll(Arrays.asList("DOM", "choChlik", "Stolik", "123"));
		developerUserPasswords.addAll(Arrays.asList("kaT23", "1do2id", "mop5", "Olcia"));
	}
	
	/**
	 * Metoda odpowiedzialna za wlaczanie i wylaczanie przycisku logon. Jest ona
	 * wywolywana podczas zmiany dowolnej wartosci z pol Srodowisko, Uzytkownik, Haslo
	 */
	private void logonButtonChanged() {

		if (choiceBox.getValue() == null || comboBox.getEditor().getText().trim().isEmpty()
				|| passwordField.getText().isEmpty())
			dialog.getDialogPane().lookupButton(logonButtonType).setDisable(true);
		else
			dialog.getDialogPane().lookupButton(logonButtonType).setDisable(false);
	}

	/**
	 * Metoda odpowiedzialna za zmiane listy uzytkownikow dla kontrolki comboBox
	 * Dodatkowo czyszczone jest pole dla hasla i poczatkowa wartosc kontrolki comboBox 
	 * @param newVal nowe srodowisko
	 */
	private void environmentChanged(Environment newVal) {

		switch (newVal.getEnviromentName()) {
		case "Produkcyjne":
			comboBox.setItems(productionUserList);
			break;

		case "Testowe":
			comboBox.setItems(testerUserList);
			break;

		case "Deweloperskie":
			comboBox.setItems(developerUserList);
		}

		passwordField.clear();
		comboBox.setValue("");
	}
	
	
	/**
	 * Metoda odpowiedzialna za konwersje z Optional<ButtonType> na Optional<Pair<Environment, String>>
	 * @param buttonType obiekt zwracany przez dialog przy jego zamknieciu
	 * @return zwraca srodowisko i uzytkownika, ktory sie zalogowal 
	 */
	private Optional<Pair<Environment, String>> resultConverter(Optional<ButtonType> buttonType) {

		if (buttonType.get() == logonButtonType) {
			if (isPassCorrect(choiceBox.getValue().getEnviromentName(), comboBox.getValue(), passwordField.getText()))
				return Optional.of(new Pair<Environment, String>(choiceBox.getValue(), comboBox.getValue()));
		}

		return Optional.empty();
	}

	/**
	 * Metoda czekajaca na zmiany wprowadzane przez uzytkownika
	 * @return skonwertowana wartosc z dialog.showAndWait()
	 */
	public Optional<Pair<Environment, String>> showAndWait() {
		return resultConverter(dialog.showAndWait());
	}
	
	/**
	 * Metoda sprawdzajaca czy podany uzytkownik z przypisanym mu haslem znajduje sie w danym srodowisku
	 * @param environmentName aktualna wartosc dla pola Srodowisko
	 * @param userName aktualna wartosc dla pola Uzytkownik
	 * @param userPassword aktualna wartosc dla pola Haslo
	 * @return <b>true</b> jesli znaleziono podanego uzytkownika z odpowiadajacym mu haslem w wybranym srodowisku lub <b>false</b> w przeciwnym przypadku
	 */
	public Boolean isPassCorrect(String environmentName, String userName, String userPassword) {

		int counter = 0;
		switch (environmentName) {

		case "Produkcyjne":
			for (String listElem : productionUserList) {
				if (listElem.equals(userName) && productionUserPasswords.get(counter).equals(userPassword))
					return true;
				counter++;
			}
			break;

		case "Testowe":
			for (String listElem : testerUserList) {
				if (listElem.equals(userName) && testerUserPasswords.get(counter).equals(userPassword))
					return true;
				counter++;
			}
			break;

		case "Deweloperskie":
			for (String listElem : developerUserList) {
				if (listElem.equals(userName) && developerUserPasswords.get(counter).equals(userPassword))
					return true;
				counter++;
			}
			break;
		}
		return false;
	}

}
