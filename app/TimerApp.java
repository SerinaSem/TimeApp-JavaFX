package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import model.TempsModel;

public class TimerApp extends Application {

	private BorderPane root;
	private BorderPane zoneAffichage;

	// 🔗 MODELE DE TEMPS PARTAGÉ
	private TempsModel tempsModel;

	// VUES (instanciées UNE SEULE FOIS)
	private HorlogeApp horloge;
	private HorlogeGraphiqueApp horlogeGraphique;
	private ReveilApp reveil;
	private MinuterieApp minuterie;
	private ChronometreApp chronometre;

	@Override
	public void start(Stage stage) {

		// --- Layout principal ---
		root = new BorderPane();
		zoneAffichage = new BorderPane();
		root.setCenter(zoneAffichage);

		// --- Modèle partagé ---
		tempsModel = new TempsModel();

		// --- Création des vues ---
		horloge = new HorlogeApp(tempsModel);
		horlogeGraphique = new HorlogeGraphiqueApp(tempsModel);
		reveil = new ReveilApp(tempsModel);
		minuterie = new MinuterieApp();
		chronometre = new ChronometreApp();

		// --- Menu ---
		MenuBar menuBar = creerMenu();
		root.setTop(menuBar);

		// Vue par défaut
		zoneAffichage.setCenter(horloge.getView());

		Scene scene = new Scene(root, 700, 500);
		stage.setScene(scene);
		stage.setTitle("TimerApp");
		stage.show();
	}

	// -------------------------------------------------------
	// MENU
	// -------------------------------------------------------
	private MenuBar creerMenu() {

		Menu menu = new Menu("Fonctions");

		MenuItem itemHorloge = new MenuItem("Horloge");
		MenuItem itemHorlogeGraphique = new MenuItem("Horloge graphique");
		MenuItem itemReveil = new MenuItem("Réveil");
		MenuItem itemMinuterie = new MenuItem("Minuterie");
		MenuItem itemChronometre = new MenuItem("Chronomètre");

		itemHorloge.setOnAction(e ->
				zoneAffichage.setCenter(horloge.getView()));

		itemHorlogeGraphique.setOnAction(e ->
				zoneAffichage.setCenter(horlogeGraphique.getView()));

		itemReveil.setOnAction(e ->
				zoneAffichage.setCenter(reveil.getView()));

		itemMinuterie.setOnAction(e ->
				zoneAffichage.setCenter(minuterie.getView()));

		itemChronometre.setOnAction(e ->
				zoneAffichage.setCenter(chronometre.getView()));

		menu.getItems().addAll(
				itemHorloge,
				itemHorlogeGraphique,
				itemReveil,
				itemMinuterie,
				itemChronometre
		);

		return new MenuBar(menu);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
