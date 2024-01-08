package fr.istic.aco.editor.caretaker;

import fr.istic.aco.editor.concreteMemento.EditorMemento;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.originator.Engine;
import fr.istic.aco.editor.receiver.Recorder;

import java.util.List;

/**
 * Caretaker for undo and redo operations
 * @see Recorder
 * @see EditorMemento
 * @see Engine
 */
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

    /**
     * Stores current state of the editor
     */
    public void store() {
        boolean isReplayingValue = recorder.isReplaying();
        if(!isReplayingValue){
            pastStates.add(new EditorMemento(engine.getBufferContents(), engine.getSelection().getBeginIndex(), engine.getSelection().getEndIndex()));
        }
    }

    /**
     * Restores the most recent state of the editor
     * and stores current state in the redo stack
     */
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

    /**
     * Restores the most recent undone state of the editor
     * and stores current state in the undo stack
     */
    public void redo() {
        if (!futureStates.isEmpty()) {
            EditorMemento nextState = futureStates.getLast();
            futureStates.removeLast();
            pastStates.add(new EditorMemento(engine.getBufferContents(), engine.getSelection().getBeginIndex(), engine.getSelection().getEndIndex()));
            engine.setMemento((Memento) nextState);
        }
    }
}

