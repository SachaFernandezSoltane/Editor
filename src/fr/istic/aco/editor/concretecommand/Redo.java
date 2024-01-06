package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.caretaker.UndoManager;
import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.receiver.EngineImpl;
import fr.istic.aco.editor.receiver.Recordable;
import fr.istic.aco.editor.receiver.Recorder;

public class Redo implements Recordable {
    private UndoManager undoManager;
    private Invoker invoker;
    private EngineImpl engine;

    private Recorder recorder;


    public Redo(Invoker invoker, UndoManager undoManager, EngineImpl engine, Recorder recorder) {
        this.undoManager = undoManager;
        this.invoker = invoker;
        this.engine = engine;
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        undoManager.redo();
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

