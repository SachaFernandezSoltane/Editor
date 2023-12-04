package fr.istic.aco.editor.receiver;

import fr.istic.aco.editor.memento.Memento;

public interface Originator {

    public Memento getMemento();

    public void setMemento(Memento memento);
}
