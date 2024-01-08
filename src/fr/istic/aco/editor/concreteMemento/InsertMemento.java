package fr.istic.aco.editor.concreteMemento;

import fr.istic.aco.editor.memento.Memento;

/**
 * Concrete memento for insert operation, allow to store insert state and restore it, working with start/stop/replay command
 * @see Memento
 */
public class InsertMemento implements Memento {

    private String text;

    public InsertMemento(String text) {
        this.text = text;
    }

    /**
     * @return text of memento
     */
    public String getText() {
        return text;
    }
}
