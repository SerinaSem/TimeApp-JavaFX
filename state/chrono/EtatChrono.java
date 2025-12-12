package state.chrono;

import app.ChronometreApp;

public class EtatChrono implements ChronoState {

    private final ChronometreApp app;

    public EtatChrono(ChronometreApp app) {
        this.app = app;
    }

    @Override
    public void enter() {
        app.showButtons(false, true, true, false);
        app.startTimer();
    }

    @Override
    public void tick() {
        app.getCentiemes().tick();
        app.updateAffichage();
    }
}
