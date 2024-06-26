package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.caretaker.UndoManager;
import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.originator.EngineImpl;

/**
 * Concrete command for redo operation
 * @see Command
 * @see EngineImpl
 * @see Invoker
 * @see UndoManager
 */
public class Redo implements Command{
    private UndoManager undoManager;
    private Invoker invoker;
    private EngineImpl engine;

    public Redo(Invoker invoker, UndoManager undoManager, EngineImpl engine) {
        this.undoManager = undoManager;
        this.invoker = invoker;
        this.engine = engine;
    }

    @Override
    public void execute() {
        undoManager.redo();
    }
}

