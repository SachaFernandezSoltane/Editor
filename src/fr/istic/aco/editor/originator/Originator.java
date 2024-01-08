package fr.istic.aco.editor.originator;

import fr.istic.aco.editor.memento.Memento;

public interface Originator {

    public Memento getMemento();

    public void setMemento(Memento memento);
}
