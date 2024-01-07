package fr.istic.aco.editor.caretaker;

import fr.istic.aco.editor.concreteMemento.EditorMemento;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.receiver.Engine;
import fr.istic.aco.editor.receiver.Recorder;

import java.util.List;

public class UndoManager {

    private Recorder recorder;
    private List<EditorMemento> pastStates;

    private List<EditorMemento> futureStates;

    private Engine engine;

    public UndoManager(List<EditorMemento> pastStates, List<EditorMemento> futureStates, Engine engine,Recorder recorder){
        this.recorder = recorder;
        this.pastStates = pastStates;
        this.futureStates = futureStates;
        this.engine = engine;
    }

    public void store() {
        boolean isReplayingValue = recorder.isReplaying();
        if(!isReplayingValue){
            pastStates.add(new EditorMemento(engine.getBufferContents(), engine.getSelection().getBeginIndex(), engine.getSelection().getEndIndex()));
        }
    }

    public void undo() {
        if (pastStates.size() > 1) {
            EditorMemento previousState = pastStates.get(pastStates.size() - 2);
            pastStates.removeLast();
            futureStates.add(new EditorMemento(engine.getBufferContents(), engine.getSelection().getBeginIndex(), engine.getSelection().getEndIndex()));
            engine.setMemento((Memento) previousState);
        } else if (pastStates.size() == 1) {
            EditorMemento newState = new EditorMemento("", 0, 0);
            pastStates.removeFirst();
            futureStates.add(new EditorMemento(engine.getBufferContents(), engine.getSelection().getBeginIndex(), engine.getSelection().getEndIndex()));
            engine.setMemento((Memento) newState);
            engine.getSelection().setBeginIndex(0);
            engine.getSelection().setEndIndex(0);
        } else {
            engine.getSelection().setBeginIndex(0);
            engine.getSelection().setEndIndex(0);
        }
    }

    public void redo() {
        if (!futureStates.isEmpty()) {
            EditorMemento nextState = futureStates.getLast();
            futureStates.removeLast();
            pastStates.add(new EditorMemento(engine.getBufferContents(), engine.getSelection().getBeginIndex(), engine.getSelection().getEndIndex()));
            engine.setMemento((Memento) nextState);
        }
    }
}

