package fr.istic.aco.editor.concreteMemento;

import fr.istic.aco.editor.memento.Memento;

/**
 * Concrete memento for selection operation, allow to store selection state and restore it, working with start/stop/replay command
 * @see Memento
 */
public class SelectionMemento implements Memento {

    private int beginIndex;

    private int endIndex;

    public SelectionMemento(int beginIndex, int endIndex) {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }
    /**
     * @return beginIndex of Selection mememto
     */
    public int getBeginIndex() {
        return beginIndex;
    }
    /**
     * @return endIndex of Selection mememto
     */
    public int getEndIndex() {
        return endIndex;
    }
}
