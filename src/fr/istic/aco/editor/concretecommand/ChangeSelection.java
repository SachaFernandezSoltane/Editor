package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.caretaker.UndoManager;
import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.concreteMemento.InsertMemento;
import fr.istic.aco.editor.concreteMemento.SelectionMemento;
import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.originator.EngineImpl;
import fr.istic.aco.editor.receiver.Recordable;
import fr.istic.aco.editor.receiver.Recorder;
import fr.istic.aco.editor.receiver.Selection;

/**
 * Concrete command for change selection
 * @see Command
 * @see Recordable
 * @see Memento
 * @see EngineImpl
 * @see Invoker
 * @see Recorder
 * @see UndoManager
 */
public class ChangeSelection implements Recordable, Command {

    private Selection selection;

    private Invoker invoker;

    private Recorder recorder;

    private UndoManager undoManager;


    public ChangeSelection(Selection selection, Invoker invoker,Recorder recorder, UndoManager undoManager) {
        this.selection = selection;
        this.invoker = invoker;
        this.recorder = recorder;
        this.undoManager = undoManager;
    }

    @Override
    public void execute() {
        selection.setEndIndex(invoker.getEndIndex());
        selection.setBeginIndex(invoker.getBeginIndex());
        recorder.save(this);
        undoManager.store();
    }

    @Override
    public Memento getMemento() {
        return new SelectionMemento(invoker.getBeginIndex(), invoker.getEndIndex());
    }

    @Override
    public void setMemento(Memento memento) {
        if (memento instanceof SelectionMemento) {
            SelectionMemento selectionMemento = (SelectionMemento) memento;
            invoker.setEndIndex(selectionMemento.getEndIndex());
            invoker.setBeginIndex(selectionMemento.getBeginIndex());
        }
    }
}
