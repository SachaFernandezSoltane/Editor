package fr.istic.aco.editor.originator;

import fr.istic.aco.editor.memento.Memento;

public interface Originator {

    /**
     * @return memento
     */
    public Memento getMemento();

    /**
     * set memento with memento parameter
     * @param memento
     */
    public void setMemento(Memento memento);
}
