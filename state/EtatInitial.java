package state;

import app.MinuterieApp;

public class EtatInitial implements MinuterieState {

    private final MinuterieApp app;

    public EtatInitial(MinuterieApp app) {
        this.app = app;
    }

    @Override
    public void enter() {
        app.stopTimer();
        app.setBackgroundWhite();

        app.showButtons(true, false, false, false);

        // Reset de l’affichage
        app.setCompteurs(null, null, null);
        app.updateAffichage();
    }

    @Override
    public void tick() {
        // Rien ici
    }
}
