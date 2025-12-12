package app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import model.compteur.Compteur;
import model.compteur.CompteurCompose;

public class ReveilApp {

    private Compteur heures;
    private CompteurCompose minutes;
    private CompteurCompose secondes;

    private Label labelH, labelM, labelS;
    private TextField inputHReveil, inputMReveil;
    private Button stopButton;

    private int hReveil = -1;
    private int mReveil = -1;
    private boolean reveilActive = false;

    private VBox root;
    private Timeline timeline;

    // ------------------------------------------------------
    // CONSTRUCTEUR : prépare tout
    // ------------------------------------------------------
    public ReveilApp() {
        creeCompteurs();
        root = creeInterface();
        bindModelView();
        startTicks(secondes, 1000);
    }

    // ------------------------------------------------------
    // ACCÈS POUR TIMEAPP
    // ------------------------------------------------------
    public VBox getView() {
        return root;
    }

    // ------------------------------------------------------
    // COMPTEURS
    // ------------------------------------------------------
    private void creeCompteurs() {
        heures   = new Compteur(0, 24);
        minutes  = new CompteurCompose(0, 60, heures);
        secondes = new CompteurCompose(0, 60, minutes);
    }

    // ------------------------------------------------------
    // INTERFACE
    // ------------------------------------------------------
    private VBox creeInterface() {

        labelH = new Label();
        labelM = new Label();
        labelS = new Label();

        HBox affichage = new HBox(10, labelH, labelM, labelS);

        // Entrée de l'heure du réveil
        inputHReveil = new TextField("0");
        inputMReveil = new TextField("0");

        inputHReveil.setPrefWidth(50);
        inputMReveil.setPrefWidth(50);

        Button setReveil = new Button("Activer Réveil");
        setReveil.setOnAction(e -> {
            hReveil = Integer.parseInt(inputHReveil.getText());
            mReveil = Integer.parseInt(inputMReveil.getText());
            reveilActive = true;
        });

        HBox reglageReveil = new HBox(10, new Label("Réveil :"), inputHReveil, inputMReveil, setReveil);

        // Bouton STOP
        stopButton = new Button("STOP");
        stopButton.setVisible(false);
        stopButton.setOnAction(e -> {
            reveilActive = false;
            stopButton.setVisible(false);
            root.setStyle("-fx-background-color: white;");
        });

        VBox pane = new VBox(20, affichage, reglageReveil, stopButton);
        pane.setPadding(new Insets(20));

        return pane;
    }

    // ------------------------------------------------------
    // BINDINGS
    // ------------------------------------------------------
    private void bindModelView() {
        labelH.textProperty().bind(heures.valeurProperty().asString("%02d :"));
        labelM.textProperty().bind(minutes.valeurProperty().asString("%02d :"));
        labelS.textProperty().bind(secondes.valeurProperty().asString("%02d"));
    }

    // ------------------------------------------------------
    // TIMER
    // ------------------------------------------------------
    private void startTicks(Compteur secondes, int periode) {

        timeline = new Timeline(
                new KeyFrame(Duration.millis(periode), e -> {

                    secondes.tick();

                    if (reveilActive &&
                            heures.getValeur() == hReveil &&
                            minutes.getValeur() == mReveil) {

                        root.setStyle("-fx-background-color: red;");
                        stopButton.setVisible(true);
                    }

                })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void stopTimer() {
        if (timeline != null)
            timeline.stop();
    }
}
