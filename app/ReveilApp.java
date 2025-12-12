package app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

import model.TempsModel;

public class ReveilApp {

    private final TempsModel temps;

    private Label labelH, labelM, labelS;
    private TextField inputHReveil, inputMReveil;
    private Button stopButton;

    private int hReveil = -1;
    private int mReveil = -1;
    private boolean reveilActif = false;

    private VBox root;
    private Timeline timeline;

    // -------------------------------------------------------
    // CONSTRUCTEUR
    // -------------------------------------------------------
    public ReveilApp(TempsModel temps) {
        this.temps = temps;

        root = creerInterface();
        bindModelView();
        startDetection();
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

        inputHReveil = new TextField("0");
        inputMReveil = new TextField("0");

        inputHReveil.setPrefWidth(50);
        inputMReveil.setPrefWidth(50);

        Button setReveil = new Button("Activer réveil");
        setReveil.setOnAction(e -> {
            hReveil = Integer.parseInt(inputHReveil.getText());
            mReveil = Integer.parseInt(inputMReveil.getText());
            reveilActif = true;
        });

        HBox reglages = new HBox(10,
                new Label("Réveil :"),
                inputHReveil,
                inputMReveil,
                setReveil
        );

        stopButton = new Button("STOP");
        stopButton.setVisible(false);
        stopButton.setOnAction(e -> {
            reveilActif = false;
            stopButton.setVisible(false);
            root.setStyle("-fx-background-color: white;");
        });

        VBox pane = new VBox(20, affichage, reglages, stopButton);
        pane.setStyle("-fx-padding: 20;");

        return pane;
    }

    // -------------------------------------------------------
    // BINDINGS
    // -------------------------------------------------------
    private void bindModelView() {
        labelH.textProperty().bind(temps.heures.valeurProperty().asString("%02d :"));
        labelM.textProperty().bind(temps.minutes.valeurProperty().asString("%02d :"));
        labelS.textProperty().bind(temps.secondes.valeurProperty().asString("%02d"));
    }

    // -------------------------------------------------------
    // DÉTECTION DU RÉVEIL
    // -------------------------------------------------------
    private void startDetection() {

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {

                    if (reveilActif &&
                            temps.heures.getValeur() == hReveil &&
                            temps.minutes.getValeur() == mReveil) {

                        root.setStyle("-fx-background-color: red;");
                        stopButton.setVisible(true);
                    }
                })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
