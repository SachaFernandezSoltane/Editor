package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.caretaker.UndoManager;
import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.originator.EngineImpl;
import fr.istic.aco.editor.receiver.Recordable;
import fr.istic.aco.editor.receiver.Recorder;
import fr.istic.aco.editor.receiver.Selection;

/**
 * Concrete command for copy operation
 * @see Command
 * @see Recordable
 * @see Memento
 * @see EngineImpl
 * @see Invoker
 * @see Recorder
 * @see UndoManager
 */
public class Copy implements Command, Recordable {

    private EngineImpl engine;

    private Recorder recorder;

    private UndoManager undoManager;

    public Copy(EngineImpl engine, Recorder recorder, UndoManager undoManager) {
        this.engine = engine;
        this.recorder = recorder;
        this.undoManager = undoManager;
    }

    @Override
    public void execute() {
        engine.copySelectedText();
        recorder.save(this);
    }

    @Override
    public Memento getMemento() {
        return null;
    }

    @Override
    public void setMemento(Memento memento) {

    }
}
