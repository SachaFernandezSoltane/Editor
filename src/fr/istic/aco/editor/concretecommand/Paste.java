package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.receiver.EngineImpl;

public class Paste implements Command {

    private Invoker invoker;

    private EngineImpl engine;

    public Paste(EngineImpl engine, Invoker invoker) {
        this.engine = engine;
        this.invoker = invoker;
    }

    @Override
    public void execute() {
        engine.pasteClipboard();
        invoker.setBeginIndex(engine.getSelection().getBeginIndex());
        invoker.setEndIndex(engine.getSelection().getEndIndex());
    }
}