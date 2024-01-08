package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.receiver.Recorder;

/**
 * Concrete command for start operation
 * @see Command
 * @see Recorder
 */
public class Start implements Command {

    private Recorder recorder;

    public Start(Recorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        recorder.start();
    }
}
