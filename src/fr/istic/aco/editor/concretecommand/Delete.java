package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.caretaker.UndoManager;
import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.originator.EngineImpl;
import fr.istic.aco.editor.receiver.Recordable;
import fr.istic.aco.editor.receiver.Recorder;

/**
 * Concrete command for delete operation
 * @see Command
 * @see Recordable
 * @see Memento
 * @see EngineImpl
 * @see Invoker
 * @see Recorder
 * @see UndoManager
 */
public class Delete implements Recordable, Command {

    private Invoker invoker;

    private EngineImpl engine;

    private Recorder recorder;

    private UndoManager undoManager;


    public Delete(EngineImpl engine, Invoker invoker,Recorder recorder,UndoManager undoManager) {
        this.engine = engine;
        this.invoker = invoker;
        this.recorder = recorder;
        this.undoManager = undoManager;
    }

    @Override
    public void execute() {
        engine.delete();
        invoker.setBeginIndex(engine.getSelection().getBeginIndex());
        invoker.setEndIndex(engine.getSelection().getEndIndex());
        recorder.save(this);
        undoManager.store();
    }

    @Override
    public Memento getMemento() {
        return null;
    }

    @Override
    public void setMemento(Memento memento) {

    }
}
