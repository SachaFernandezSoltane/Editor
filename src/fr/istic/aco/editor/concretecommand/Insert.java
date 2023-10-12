package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.receiver.EngineImpl;

public class Insert implements Command {

    private EngineImpl engine;

    private String text;

    public Insert(EngineImpl engine, String text) {
        this.engine = engine;
        this.text = text;
    }

    @Override
    public void execute() {
        engine.insert(text);
    }
}
