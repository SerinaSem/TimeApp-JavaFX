package state;

import app.MinuterieApp;
import model.compteur.Compteur;

public class EtatDefilement implements MinuterieState {

    private final MinuterieApp app;

    public EtatDefilement(MinuterieApp app) {
        this.app = app;
    }

    @Override
    public void enter() {
        app.setBackgroundWhite();
        app.showButtons(false, true, false, true);
        app.startTimer();
    }

    @Override
    public void tick() {

        Compteur h = app.getHeures();
        Compteur m = app.getMinutes();
        Compteur s = app.getSecondes();

        if (!app.isZero()) {

            s.tick();

            if (s.getValeur() < 0) {
                s.setValeur(59);
                m.tick();

                if (m.getValeur() < 0) {
                    m.setValeur(59);
                    h.tick();

                    if (h.getValeur() < 0) {
                        h.setValeur(0);
                        m.setValeur(0);
                        s.setValeur(0);
                    }
                }
            }
        }

        app.updateAffichage();

        if (app.isZero()) {
            app.changeState(new EtatDepasse(app));
        }
    }
}
