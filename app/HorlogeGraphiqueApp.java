package app;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.control.Label;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import model.compteur.Compteur;
import model.compteur.CompteurCompose;

public class HorlogeGraphiqueApp {

    private Compteur heures;
    private CompteurCompose minutes;
    private CompteurCompose secondes;

    private Line aigHeure;
    private Line aigMinute;
    private Line aigSeconde;

    private Rotate rotHeure;
    private Rotate rotMinute;
    private Rotate rotSeconde;

    private Pane root;

    // Centre du cadran
    private final double CX = 200;
    private final double CY = 200;

    // -------------------------------------------------------
    // CONSTRUCTEUR : prépare l'interface et le timer
    // -------------------------------------------------------
    public HorlogeGraphiqueApp() {
        creerCompteurs();
        root = creerInterface();
        bindModelView();
        startTicks(secondes, 1000);
    }

    // -------------------------------------------------------
    // ACCÈS POUR TimeApp
    // -------------------------------------------------------
    public Pane getView() {
        return root;
    }

    // -------------------------------------------------------
    // LOGIQUE
    // -------------------------------------------------------
    private void creerCompteurs() {
        heures = new Compteur(0, 24);
        minutes = new CompteurCompose(0, 60, heures);
        secondes = new CompteurCompose(0, 60, minutes);
    }

    private Pane creerInterface() {

        Pane pane = new Pane();

        Circle cercle = new Circle(CX, CY, 150);
        cercle.setStroke(Color.BLACK);
        cercle.setFill(Color.TRANSPARENT);
        cercle.setStrokeWidth(4);

        // Aiguilles
        aigSeconde = new Line(CX, CY, CX, CY - 100);
        aigSeconde.setStrokeWidth(1);

        aigMinute = new Line(CX, CY, CX, CY - 80);
        aigMinute.setStrokeWidth(2);

        aigHeure = new Line(CX, CY, CX, CY - 60);
        aigHeure.setStrokeWidth(4);

        // Rotations
        rotSeconde = new Rotate(0, CX, CY);
        rotMinute = new Rotate(0, CX, CY);
        rotHeure = new Rotate(0, CX, CY);

        aigSeconde.getTransforms().add(rotSeconde);
        aigMinute.getTransforms().add(rotMinute);
        aigHeure.getTransforms().add(rotHeure);

        // --- Numéros 1 à 12 ---
        double rNum = 120;

        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(i * 30 - 90);
            double x = CX + rNum * Math.cos(angle);
            double y = CY + rNum * Math.sin(angle);

            Label num = new Label(String.valueOf(i));
            num.setLayoutX(x - 7);
            num.setLayoutY(y - 8);
            num.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            pane.getChildren().add(num);
        }

        // ---- Graduations (ticks) ----
        double rTickOuter = 150;
        double rTickInner = 140;
        double rTickHour = 130;

        for (int i = 0; i < 60; i++) {

            double angle = Math.toRadians(i * 6 - 90);

            double x1, y1;

            if (i % 5 == 0) {
                x1 = CX + rTickHour * Math.cos(angle);
                y1 = CY + rTickHour * Math.sin(angle);
            } else {
                x1 = CX + rTickInner * Math.cos(angle);
                y1 = CY + rTickInner * Math.sin(angle);
            }

            double x2 = CX + rTickOuter * Math.cos(angle);
            double y2 = CY + rTickOuter * Math.sin(angle);

            Line tick = new Line(x1, y1, x2, y2);
            tick.setStrokeWidth(i % 5 == 0 ? 3 : 1);

            pane.getChildren().add(tick);
        }

        pane.getChildren().addAll(cercle, aigHeure, aigMinute, aigSeconde);

        return pane;
    }

    private void bindModelView() {

        // SECONDES
        secondes.valeurProperty().addListener((obs, oldV, newV) -> {
            rotSeconde.setAngle(newV.intValue() * 6);
        });

        // MINUTES
        minutes.valeurProperty().addListener((obs, oldV, newV) -> {
            rotMinute.setAngle(newV.intValue() * 6);
        });

        // HEURES
        heures.valeurProperty().addListener((obs, oldV, newV) -> {
            rotHeure.setAngle(newV.intValue() * 30);
        });

        // Valeurs initiales
        rotSeconde.setAngle(secondes.getValeur() * 6);
        rotMinute.setAngle(minutes.getValeur() * 6);
        rotHeure.setAngle(heures.getValeur() * 30);
    }

    private void startTicks(Compteur secondes, int periode) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(periode), e -> secondes.tick())
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
