package fr.istic.aco.editor.receiver;

import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.originator.Originator;

public interface Recordable extends Command, Originator {

    @Override
    void execute();

    Memento getMemento();

    void setMemento(Memento memento);
}
