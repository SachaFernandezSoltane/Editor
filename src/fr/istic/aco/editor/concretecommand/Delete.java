package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.receiver.EngineImpl;

public class Delete implements Command {

    private EngineImpl engine;

    public Delete(EngineImpl engine) {
        this.engine = engine;
    }

    @Override
    public void execute() {
        engine.delete();
    }
}
