package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.receiver.EngineImpl;
import fr.istic.aco.editor.receiver.Selection;

public class ChangeSelection implements Command {

    private Selection selection;

    private Invoker invoker;

    public ChangeSelection(Selection selection, Invoker invoker) {
        this.selection = selection;
        this.invoker = invoker;
    }

    @Override
    public void execute() {
        selection.setEndIndex(invoker.getEndIndex());
        selection.setBeginIndex(invoker.getBeginIndex());
    }
}
