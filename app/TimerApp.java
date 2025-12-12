package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TimerApp extends Application {

	private BorderPane root;
	private BorderPane zoneAffichage;

	@Override
	public void start(Stage stage) {

		root = new BorderPane();
		zoneAffichage = new BorderPane();

		// --- MENU ---
		Button bHorloge      = new Button("Horloge");
		Button bHorlogeGraph = new Button("Horloge Graphique");
		Button bReveil       = new Button("Réveil");
		Button bMinuterie    = new Button("Minuterie");
		Button bChrono       = new Button("Chronomètre");

		HBox menu = new HBox(10, bHorloge, bHorlogeGraph, bReveil, bMinuterie, bChrono);
		menu.setStyle("-fx-padding: 10;");
		root.setTop(menu);
		root.setCenter(zoneAffichage);

		// --- ACTIONS DU MENU ---
		bHorloge.setOnAction(e -> afficherHorloge());
		bHorlogeGraph.setOnAction(e -> afficherHorlogeGraph());
		bReveil.setOnAction(e -> afficherReveil());
		bMinuterie.setOnAction(e -> afficherMinuterie());
		bChrono.setOnAction(e -> afficherChronometre());

		Scene scene = new Scene(root, 700, 500);
		stage.setScene(scene);
		stage.setTitle("TimeApp");
		stage.show();
	}

	// -------------------------------------------------------------
	//  AFFICHAGES
	// -------------------------------------------------------------

	private void afficherHorloge() {
		HorlogeApp app = new HorlogeApp();
		zoneAffichage.setCenter(app.getView());
	}

	private void afficherHorlogeGraph() {
		HorlogeGraphiqueApp app = new HorlogeGraphiqueApp();
		zoneAffichage.setCenter(app.getView());
	}

	private void afficherReveil() {
		ReveilApp app = new ReveilApp();
		zoneAffichage.setCenter(app.getView());
	}

	private void afficherMinuterie() {
		MinuterieApp app = new MinuterieApp();
		zoneAffichage.setCenter(app.getView());
	}

	private void afficherChronometre() {
		ChronometreApp app = new ChronometreApp();
		zoneAffichage.setCenter(app.getView());
	}

	public static void main(String[] args) {
		launch();
	}
}
