package fr.istic.aco.editor.receiver;

import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.originator.Originator;

public interface Recordable extends Command, Originator {

    /**
     * execute command to replay
     */
    @Override
    void execute();

    /**
     * @return memento
     */
    Memento getMemento();

    /**
     * set memento with memento parameter
     * @param memento
     */
    void setMemento(Memento memento);
}
