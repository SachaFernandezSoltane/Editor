package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.receiver.Recordable;
import fr.istic.aco.editor.receiver.Selection;

public class ChangeSelection implements Recordable {

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

    @Override
    public Memento getMemento() {
        return null;
    }

    @Override
    public void setMemento(Memento memento) {

    }
}
