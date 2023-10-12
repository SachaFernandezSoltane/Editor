package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.receiver.EngineImpl;
import fr.istic.aco.editor.receiver.Selection;

public class ChangeSelection implements Command {

    private Selection selection;

    private int newBeginIndex;

    private int newEndIndex;

    public ChangeSelection(Selection selection, int newBeginIndex, int newEndIndex) {
        this.selection = selection;
        this.newBeginIndex = newBeginIndex;
        this.newEndIndex = newEndIndex;
    }

    @Override
    public void execute() {
        selection.setBeginIndex(newBeginIndex);
        selection.setEndIndex(newEndIndex);
    }
}
