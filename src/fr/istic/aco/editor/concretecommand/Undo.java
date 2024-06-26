package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.caretaker.UndoManager;
import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.originator.EngineImpl;

/**
 * Concrete command for undo operation
 * @see Command
 * @see EngineImpl
 * @see Invoker
 * @see UndoManager
 */
public class Undo implements Command {

    private UndoManager undoManager;
    private Invoker invoker;
    private EngineImpl engine;


    public Undo(Invoker invoker,UndoManager undoManager,EngineImpl engine){
        this.undoManager = undoManager;
        this.invoker = invoker;
        this.engine = engine;
    }

    @Override
    public void execute() {
        undoManager.undo();
    }
}
