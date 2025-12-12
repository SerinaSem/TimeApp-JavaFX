package model;

import model.compteur.Compteur;
import model.compteur.CompteurCompose;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class TempsModel {
    public final Compteur heures;
    public final CompteurCompose minutes;
    public final CompteurCompose secondes;

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy");

    public TempsModel() {
        heures = new Compteur(0, 24);
        minutes = new CompteurCompose(0, 60, heures);
        secondes = new CompteurCompose(0, 60, minutes);
    }

    public String getDateFormatee() {
        return LocalDate.now().format(formatter);
    }
}
