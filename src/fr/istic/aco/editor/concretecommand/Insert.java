package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.caretaker.UndoManager;
import fr.istic.aco.editor.concreteMemento.InsertMemento;
import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.originator.EngineImpl;
import fr.istic.aco.editor.receiver.Recordable;
import fr.istic.aco.editor.receiver.Recorder;

public class Insert implements Recordable {

    private Invoker invoker;

    private EngineImpl engine;

    private Recorder recorder;

    private UndoManager undoManager;

    public Insert(EngineImpl engine, Invoker invoker, Recorder recorder,UndoManager undoManager){
        this.engine = engine;
        this.invoker = invoker;
        this.recorder = recorder;
        this.undoManager = undoManager;
    }

    @Override
    public void execute() {
        engine.insert(invoker.getText());
        invoker.setEndIndex(engine.getSelection().getEndIndex());
        invoker.setBeginIndex(engine.getSelection().getBeginIndex());
        recorder.save(this);
        undoManager.store();
    }

    @Override
    public Memento getMemento() {
        return new InsertMemento(invoker.getText());
    }

    @Override
    public void setMemento(Memento memento) {
        if (memento instanceof InsertMemento) {
            InsertMemento insertMemento = (InsertMemento) memento;
            invoker.setText(insertMemento.getText());
        }
    }
}
