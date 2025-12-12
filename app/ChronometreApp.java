package app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

import model.compteur.*;
import state.chrono.*;

public class ChronometreApp {

    private VBox root;
    private Label affichage;
    private VBox intervalsBox;

    private Button startBtn;
    private Button stopBtn;
    private Button intervalBtn;
    private Button resetBtn;

    private Compteur minutes;
    private CompteurCompose secondes;
    private CompteurCompose centiemes;

    private Timeline timeline;

    private ChronoState currentState;

    // ---------------------------------------------------------
    // CONSTRUCTEUR : crée l'interface + met l'état initial
    // ---------------------------------------------------------
    public ChronometreApp() {
        creerInterface();
        creerEvenements();
        changeState(new EtatInitialChrono(this));
    }

    // ---------------------------------------------------------
    // Accès pour TimeApp
    // ---------------------------------------------------------
    public Pane getView() {
        return root;
    }

    // ---------------------------------------------------------
    // STATE PATTERN
    // ---------------------------------------------------------
    public void changeState(ChronoState newState) {
        currentState = newState;
        newState.enter();
    }

    // ---------------------------------------------------------
    // INTERFACE
    // ---------------------------------------------------------
    private void creerInterface() {

        affichage = new Label("00:00:00");
        affichage.setStyle("-fx-font-size: 40; -fx-font-weight: bold;");

        intervalsBox = new VBox(5);

        startBtn = new Button("Démarrer");
        stopBtn = new Button("Arrêter");
        intervalBtn = new Button("Intervalle");
        resetBtn = new Button("Réinitialiser");

        HBox boutons = new HBox(10, startBtn, stopBtn, intervalBtn, resetBtn);

        root = new VBox(20, affichage, boutons, intervalsBox);
        root.setStyle("-fx-padding: 20;");
    }

    // ---------------------------------------------------------
    // ÉVÉNEMENTS BOUTONS
    // ---------------------------------------------------------
    private void creerEvenements() {

        startBtn.setOnAction(e -> changeState(new EtatChrono(this)));

        stopBtn.setOnAction(e -> changeState(new EtatFigeChrono(this)));

        resetBtn.setOnAction(e -> changeState(new EtatInitialChrono(this)));

        intervalBtn.setOnAction(e -> ajouterIntervalle());
    }

    // ---------------------------------------------------------
    // LOGIQUE DU CHRONOMÈTRE
    // ---------------------------------------------------------
    public void resetCompteurs() {
        minutes  = new Compteur(0, new VariationIllimitee(0, new Increment()));
        secondes = new CompteurCompose(0, 59, minutes);
        centiemes = new CompteurCompose(0, 99, secondes);
    }

    public void startTimer() {
        stopTimer();
        timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> currentState.tick()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void stopTimer() {
        if (timeline != null)
            timeline.stop();
    }

    public void updateAffichage() {
        affichage.setText(String.format("%02d:%02d:%02d",
                minutes.getValeur(),
                secondes.getValeur(),
                centiemes.getValeur()));
    }

    public void clearIntervals() {
        intervalsBox.getChildren().clear();
    }

    public void ajouterIntervalle() {
        Label lbl = new Label(affichage.getText());
        lbl.setStyle("-fx-font-size: 18;");
        intervalsBox.getChildren().add(lbl);
    }

    public void showButtons(boolean start, boolean stop, boolean interval, boolean reset) {
        startBtn.setVisible(start);
        stopBtn.setVisible(stop);
        intervalBtn.setVisible(interval);
        resetBtn.setVisible(reset);
    }

    // ---------------------------------------------------------
    // GETTERS
    // ---------------------------------------------------------
    public Compteur getMinutes()   { return minutes; }
    public CompteurCompose getSecondes() { return secondes; }
    public CompteurCompose getCentiemes() { return centiemes; }

}
