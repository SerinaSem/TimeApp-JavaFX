package state.chrono;

import app.ChronometreApp;

public class EtatFigeChrono implements ChronoState {

    private final ChronometreApp app;

    public EtatFigeChrono(ChronometreApp app) {
        this.app = app;
    }

    @Override
    public void enter() {
        app.stopTimer();
        app.showButtons(true, false, false, true);
    }

    @Override
    public void tick() {
    }
}
