package fr.istic.aco.editor.concreteMemento;

import fr.istic.aco.editor.memento.Memento;

public class SelectionMemento implements Memento {

    private int beginIndex;

    private int endIndex;

    public SelectionMemento(int beginIndex, int endIndex) {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }
}
