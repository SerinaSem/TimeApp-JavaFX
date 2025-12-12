package app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

import model.compteur.Compteur;
import model.compteur.CompteurCompose;

public class HorlogeApp {

    private Compteur heures;
    private CompteurCompose minutes;
    private CompteurCompose secondes;

    private Label labelH;
    private Label labelM;
    private Label labelS;

    private TextField inputH;
    private TextField inputM;
    private TextField inputS;

    private VBox root;

    private Timeline timeline;

    // -------------------------------------------------------
    // CONSTRUCTEUR : prépare tout
    // -------------------------------------------------------
    public HorlogeApp() {
        creerCompteurs();
        root = creerInterface();
        bindModelView();
        startTicks(secondes, 1000);
    }

    // -------------------------------------------------------
    // ACCÈS POUR TimeApp
    // -------------------------------------------------------
    public VBox getView() {
        return root;
    }

    // -------------------------------------------------------
    // LOGIQUE DE L'HORLOGE
    // -------------------------------------------------------
    private void creerCompteurs() {
        heures = new Compteur(0, 24);
        minutes = new CompteurCompose(0, 60, heures);
        secondes = new CompteurCompose(0, 60, minutes);
    }

    private VBox creerInterface() {

        labelH = new Label();
        labelM = new Label();
        labelS = new Label();

        HBox affichage = new HBox(10, labelH, labelM, labelS);

        inputH = new TextField("0");
        inputM = new TextField("0");
        inputS = new TextField("0");

        Button setButton = new Button("SET");
        setButton.setOnAction(e -> {
            heures.setValeur(Integer.parseInt(inputH.getText()));
            minutes.setValeur(Integer.parseInt(inputM.getText()));
            secondes.setValeur(Integer.parseInt(inputS.getText()));
        });

        HBox reglages = new HBox(10, inputH, inputM, inputS, setButton);

        VBox pane = new VBox(20, affichage, reglages);
        pane.setStyle("-fx-padding: 20;");

        return pane;
    }

    private void bindModelView() {
        labelH.textProperty().bind(heures.valeurProperty().asString("%02d"));
        labelM.textProperty().bind(minutes.valeurProperty().asString(":%02d"));
        labelS.textProperty().bind(secondes.valeurProperty().asString(":%02d"));
    }

    private void startTicks(Compteur c, int periode) {
        timeline = new Timeline(
                new KeyFrame(Duration.millis(periode), e -> c.tick())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
