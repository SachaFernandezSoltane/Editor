package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.receiver.Recorder;

/**
 * Concrete command for replay operation
 * @see Command
 * @see Recorder
 */
public class Replay implements Command {

    private Recorder recorder;

    public Replay(Recorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        recorder.replay();
    }
}
