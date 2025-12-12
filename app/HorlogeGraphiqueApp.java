package app;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

import model.TempsModel;

public class HorlogeGraphiqueApp {

    private final TempsModel temps;

    private Pane root;

    private Line aigHeure;
    private Line aigMinute;
    private Line aigSeconde;

    private Rotate rotHeure;
    private Rotate rotMinute;
    private Rotate rotSeconde;

    private static final double CX = 200;
    private static final double CY = 200;

    public HorlogeGraphiqueApp(TempsModel temps) {
        this.temps = temps;

        root = creerInterface();
        bindModelView();

        // ❌ PAS de timer ici : l'horloge graphique observe seulement le modèle
    }

    public Pane getView() {
        return root;
    }

    private Pane creerInterface() {

        Pane pane = new Pane();

        Circle cercle = new Circle(CX, CY, 150);
        cercle.setStroke(Color.BLACK);
        cercle.setFill(Color.TRANSPARENT);
        cercle.setStrokeWidth(4);

        aigSeconde = new Line(CX, CY, CX, CY - 100);
        aigSeconde.setStrokeWidth(1);

        aigMinute = new Line(CX, CY, CX, CY - 80);
        aigMinute.setStrokeWidth(2);

        aigHeure = new Line(CX, CY, CX, CY - 60);
        aigHeure.setStrokeWidth(4);

        rotSeconde = new Rotate(0, CX, CY);
        rotMinute = new Rotate(0, CX, CY);
        rotHeure = new Rotate(0, CX, CY);

        aigSeconde.getTransforms().add(rotSeconde);
        aigMinute.getTransforms().add(rotMinute);
        aigHeure.getTransforms().add(rotHeure);

        // Numéros
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

        // Graduations
        double rOuter = 150;
        double rInner = 140;
        double rHour  = 130;

        for (int i = 0; i < 60; i++) {
            double angle = Math.toRadians(i * 6 - 90);

            double x1 = CX + (i % 5 == 0 ? rHour : rInner) * Math.cos(angle);
            double y1 = CY + (i % 5 == 0 ? rHour : rInner) * Math.sin(angle);
            double x2 = CX + rOuter * Math.cos(angle);
            double y2 = CY + rOuter * Math.sin(angle);

            Line tick = new Line(x1, y1, x2, y2);
            tick.setStrokeWidth(i % 5 == 0 ? 3 : 1);

            pane.getChildren().add(tick);
        }

        pane.getChildren().addAll(cercle, aigHeure, aigMinute, aigSeconde);
        return pane;
    }

    private void bindModelView() {

        temps.secondes.valeurProperty().addListener((obs, o, n) ->
                rotSeconde.setAngle(n.intValue() * 6));

        temps.minutes.valeurProperty().addListener((obs, o, n) ->
                rotMinute.setAngle(n.intValue() * 6));

        temps.heures.valeurProperty().addListener((obs, o, n) ->
                rotHeure.setAngle((n.intValue() % 12) * 30));

        // Initialisation
        rotSeconde.setAngle(temps.secondes.getValeur() * 6);
        rotMinute.setAngle(temps.minutes.getValeur() * 6);
        rotHeure.setAngle((temps.heures.getValeur() % 12) * 30);
    }
}
