package app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import model.compteur.Compteur;
import model.compteur.Decrement;
import model.compteur.VariationIllimitee;
import state.*;

public class MinuterieApp {

    private VBox root;
    private Label affichage;

    private TextField inputH;
    private TextField inputM;
    private TextField inputS;

    private Button boutonStart;
    private Button boutonPause;
    private Button boutonResume;
    private Button boutonReset;

    private Compteur heures;
    private Compteur minutes;
    private Compteur secondes;

    private Timeline timeline;

    private MinuterieState currentState;

    public MinuterieApp() {
        creerInterface();
        creerEvenements();
        changeState(new EtatInitial(this));
    }

    public VBox getView() {
        return root;
    }

    public void changeState(MinuterieState newState) {
        currentState = newState;
        newState.enter();
    }

    private void creerInterface() {

        affichage = new Label("00:00:00");
        affichage.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        inputH = new TextField("0");
        inputM = new TextField("0");
        inputS = new TextField("0");

        inputH.setPrefWidth(50);
        inputM.setPrefWidth(50);
        inputS.setPrefWidth(50);

        HBox entreeTemps = new HBox(10, inputH, inputM, inputS);

        boutonStart  = new Button("Départ");
        boutonPause  = new Button("Pause");
        boutonResume = new Button("Reprendre");
        boutonReset  = new Button("Réinitialiser");

        HBox boutons = new HBox(10, boutonStart, boutonPause, boutonResume, boutonReset);

        root = new VBox(20, affichage, entreeTemps, boutons);
        root.setStyle("-fx-padding: 20;");
    }

    private void creerEvenements() {

        boutonStart.setOnAction(e -> {

            int h = getInputHeures();
            int m = getInputMinutes();
            int s = getInputSecondes();

            // On utilise une variation illimitée avec décrément
            heures   = new Compteur(h, new VariationIllimitee(h, new Decrement()));
            minutes  = new Compteur(m, new VariationIllimitee(m, new Decrement()));
            secondes = new Compteur(s, new VariationIllimitee(s, new Decrement()));

            updateAffichage();
            changeState(new EtatDefilement(this));
        });

        boutonPause.setOnAction(e -> changeState(new EtatPause(this)));

        boutonResume.setOnAction(e -> changeState(new EtatDefilement(this)));

        boutonReset.setOnAction(e -> changeState(new EtatInitial(this)));
    }

    public void startTimer() {
        stopTimer();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> currentState.tick()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void stopTimer() {
        if (timeline != null)
            timeline.stop();
    }

    public void setBackgroundWhite() {
        root.setStyle("-fx-background-color: white; -fx-padding: 20;");
    }

    public void setBackgroundRed() {
        root.setStyle("-fx-background-color: red; -fx-padding: 20;");
    }

    public void updateAffichage() {
        if (heures == null || minutes == null || secondes == null) {
            affichage.setText("00:00:00");
        } else {
            affichage.setText(formatHeure());
        }
    }

    public String formatHeure() {
        return String.format("%02d:%02d:%02d",
                heures.getValeur(),
                minutes.getValeur(),
                secondes.getValeur());
    }

    public void showButtons(boolean start, boolean pause, boolean resume, boolean reset) {
        boutonStart.setVisible(start);
        boutonPause.setVisible(pause);
        boutonResume.setVisible(resume);
        boutonReset.setVisible(reset);
    }

    public boolean isZero() {
        return heures.getValeur() == 0 &&
                minutes.getValeur() == 0 &&
                secondes.getValeur() == 0;
    }

    public void setCompteurs(Compteur h, Compteur m, Compteur s) {
        heures   = h;
        minutes  = m;
        secondes = s;
    }

    public Compteur getHeures()   { return heures; }
    public Compteur getMinutes()  { return minutes; }
    public Compteur getSecondes() { return secondes; }

    public int getInputHeures()  { return Integer.parseInt(inputH.getText()); }
    public int getInputMinutes() { return Integer.parseInt(inputM.getText()); }
    public int getInputSecondes(){ return Integer.parseInt(inputS.getText()); }
}
