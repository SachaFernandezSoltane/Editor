package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.receiver.Recorder;

/**
 * Concrete command for stop operation
 * @see Command
 * @see Recorder
 */
public class Stop implements Command {

    private Recorder recorder;

    public Stop(Recorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        recorder.stop();
    }
}
