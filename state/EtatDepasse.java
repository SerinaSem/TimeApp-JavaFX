package state;

import app.MinuterieApp;
import model.compteur.Compteur;
import model.compteur.Increment;
import model.compteur.VariationIllimitee;

public class EtatDepasse implements MinuterieState {

    private final MinuterieApp app;

    public EtatDepasse(MinuterieApp app) {
        this.app = app;
    }

    @Override
    public void enter() {

        app.stopTimer();
        app.setBackgroundRed();
        app.showButtons(false, false, false, true);

        Compteur heures = new Compteur(0, new VariationIllimitee(0, new Increment()));
        Compteur minutes = new Compteur(0, new VariationIllimitee(0, new Increment()));
        Compteur secondes = new Compteur(0, new VariationIllimitee(0, new Increment()));

        app.setCompteurs(heures, minutes, secondes);
        app.startTimer();
        app.updateAffichage();
    }

    @Override
    public void tick() {

        Compteur h = app.getHeures();
        Compteur m = app.getMinutes();
        Compteur s = app.getSecondes();

        s.tick();
        if (s.getValeur() >= 60) {
            s.setValeur(0);
            m.tick();
            if (m.getValeur() >= 60) {
                m.setValeur(0);
                h.tick();
            }
        }

        app.updateAffichage();
    }
}
