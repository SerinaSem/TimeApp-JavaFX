package app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

import model.TempsModel;
import model.compteur.Compteur;

public class HorlogeApp {

    // 🔗 MODELE PARTAGÉ
    private final TempsModel temps;

    // UI
    private Label labelH;
    private Label labelM;
    private Label labelS;

    private TextField inputH;
    private TextField inputM;
    private TextField inputS;

    private VBox root;
    private Timeline timeline;

    // -------------------------------------------------------
    // CONSTRUCTEUR : reçoit le modèle partagé
    // -------------------------------------------------------
    public HorlogeApp(TempsModel temps) {
        this.temps = temps;

        root = creerInterface();
        bindModelView();
        startTicks(temps.secondes, 1000);
    }

    // -------------------------------------------------------
    // ACCÈS POUR TimerApp
    // -------------------------------------------------------
    public VBox getView() {
        return root;
    }

    // -------------------------------------------------------
    // INTERFACE
    // -------------------------------------------------------
    private VBox creerInterface() {

        labelH = new Label();
        labelM = new Label();
        labelS = new Label();

        HBox affichage = new HBox(10, labelH, labelM, labelS);

        inputH = new TextField("0");
        inputM = new TextField("0");
        inputS = new TextField("0");

        inputH.setPrefWidth(50);
        inputM.setPrefWidth(50);
        inputS.setPrefWidth(50);

        Button setButton = new Button("SET");
        setButton.setOnAction(e -> {
            temps.heures.setValeur(Integer.parseInt(inputH.getText()));
            temps.minutes.setValeur(Integer.parseInt(inputM.getText()));
            temps.secondes.setValeur(Integer.parseInt(inputS.getText()));
        });

        HBox reglages = new HBox(10, inputH, inputM, inputS, setButton);

        VBox pane = new VBox(20, affichage, reglages);
        pane.setStyle("-fx-padding: 20;");

        return pane;
    }

    // -------------------------------------------------------
    // BINDINGS
    // -------------------------------------------------------
    private void bindModelView() {
        labelH.textProperty().bind(temps.heures.valeurProperty().asString("%02d"));
        labelM.textProperty().bind(temps.minutes.valeurProperty().asString(":%02d"));
        labelS.textProperty().bind(temps.secondes.valeurProperty().asString(":%02d"));
    }

    // -------------------------------------------------------
    // TIMER
    // -------------------------------------------------------
    private void startTicks(Compteur secondes, int periode) {
        timeline = new Timeline(
                new KeyFrame(Duration.millis(periode), e -> secondes.tick())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
