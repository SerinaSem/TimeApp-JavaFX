package state;

import app.MinuterieApp;

public class EtatPause implements MinuterieState {

    private final MinuterieApp app;

    public EtatPause(MinuterieApp app) {
        this.app = app;
    }

    @Override
    public void enter() {
        app.stopTimer();
        app.setBackgroundWhite();
        app.showButtons(false, false, true, true);
    }

    @Override
    public void tick() {
    }
}
