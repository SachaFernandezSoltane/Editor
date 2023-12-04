package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.receiver.EngineImpl;
import fr.istic.aco.editor.receiver.Recordable;

public class Delete implements Recordable {

    private Invoker invoker;

    private EngineImpl engine;

    public Delete(EngineImpl engine, Invoker invoker) {
        this.engine = engine;
        this.invoker = invoker;
    }

    @Override
    public void execute() {
        engine.delete();
        invoker.setBeginIndex(engine.getSelection().getBeginIndex());
        invoker.setEndIndex(engine.getSelection().getEndIndex());
    }

    @Override
    public Memento getMemento() {
        return null;
    }

    @Override
    public void setMemento(Memento memento) {

    }
}
