package fr.istic.aco.editor.receiver;

import fr.istic.aco.editor.command.Command;

public interface Recordable extends Command, Originator {

    @Override
    void execute();
}
