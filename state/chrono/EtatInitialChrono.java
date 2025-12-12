package state.chrono;

import app.ChronometreApp;

public class EtatInitialChrono implements ChronoState {

    private final ChronometreApp app;

    public EtatInitialChrono(ChronometreApp app) {
        this.app = app;
    }

    @Override
    public void enter() {
        app.stopTimer();
        app.resetCompteurs();
        app.clearIntervals();
        app.updateAffichage();

        app.showButtons(true, false, false, false);
    }

    @Override
    public void tick() {
    }
}
