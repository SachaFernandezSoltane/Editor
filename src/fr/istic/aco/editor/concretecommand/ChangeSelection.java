package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.concreteMemento.InsertMemento;
import fr.istic.aco.editor.concreteMemento.SelectionMemento;
import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.receiver.Recordable;
import fr.istic.aco.editor.receiver.Recorder;
import fr.istic.aco.editor.receiver.Selection;

public class ChangeSelection implements Recordable {

    private Selection selection;

    private Invoker invoker;

    private Recorder recorder;

    public ChangeSelection(Selection selection, Invoker invoker,Recorder recorder) {
        this.selection = selection;
        this.invoker = invoker;
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        selection.setEndIndex(invoker.getEndIndex());
        selection.setBeginIndex(invoker.getBeginIndex());
        recorder.save(this);
    }

    @Override
    public Memento getMemento() {
        return new SelectionMemento(invoker.getBeginIndex(), invoker.getEndIndex());
    }

    @Override
    public void setMemento(Memento memento) {
        if (memento instanceof SelectionMemento) {
            SelectionMemento selectionMemento = (SelectionMemento) memento;
            invoker.setBeginIndex(selectionMemento.getBeginIndex());
            invoker.setEndIndex(selectionMemento.getEndIndex());
        }
    }
}
