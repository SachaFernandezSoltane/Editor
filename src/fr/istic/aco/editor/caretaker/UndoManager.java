package fr.istic.aco.editor.caretaker;

import fr.istic.aco.editor.concreteMemento.EditorMemento;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.receiver.Engine;

import java.util.List;

public class UndoManager {
    private List<EditorMemento> pastStates;

    private List<EditorMemento> futureStates;

    private Engine engine;

    public UndoManager(List<EditorMemento> pastStates, List<EditorMemento> futureStates, Engine engine) {
        this.pastStates = pastStates;
        this.futureStates = futureStates;
        this.engine = engine;
    }

    //TODO: implement this method
    public void store() {
        pastStates.add(new EditorMemento(engine.getBufferContents(), engine.getSelection().getBeginIndex(), engine.getSelection().getEndIndex()));
    }

    //TODO: implement this method
    public void undo() {
        if (pastStates.size() > 1) {
            EditorMemento previousState = pastStates.get(pastStates.size() - 2);
            pastStates.remove(pastStates.size() - 1);
            futureStates.add(new EditorMemento(engine.getBufferContents(), engine.getSelection().getBeginIndex(), engine.getSelection().getEndIndex()));
            engine.setMemento((Memento) previousState);
        } else if (pastStates.size() == 1) {
            EditorMemento newState = new EditorMemento("", 0, 0);
            pastStates.remove(0);
            futureStates.add(new EditorMemento(engine.getBufferContents(), engine.getSelection().getBeginIndex(), engine.getSelection().getEndIndex()));
            engine.setMemento((Memento) newState);
            engine.getSelection().setBeginIndex(0);
            engine.getSelection().setEndIndex(0);
        } else {
            engine.getSelection().setBeginIndex(0);
            engine.getSelection().setEndIndex(0);
        }
    }

    //TODO: implement this method
    public void redo() {
        if (!futureStates.isEmpty()) {
            EditorMemento nextState = futureStates.get(futureStates.size() - 1);
            futureStates.remove(futureStates.size() - 1);
            pastStates.add(new EditorMemento(engine.getBufferContents(), engine.getSelection().getBeginIndex(), engine.getSelection().getEndIndex()));
            //engine.setMemento((Memento)nextState);
        }
    }
}
