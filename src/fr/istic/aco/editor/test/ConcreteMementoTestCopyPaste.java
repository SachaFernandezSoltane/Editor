package fr.istic.aco.editor.test;

import fr.istic.aco.editor.caretaker.UndoManager;
import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.concreteMemento.EditorMemento;
import fr.istic.aco.editor.concretecommand.*;
import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.receiver.EngineImpl;
import fr.istic.aco.editor.receiver.Recorder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConcreteMementoTestCopyPaste {
    private EngineImpl engine;
    private Invoker invoker;

    private Recorder recorder;

    private UndoManager undoManager;

    private Map<String, Command> mapCmd;

    private List<EditorMemento> pastStates = new ArrayList<>();
    private List<EditorMemento> futureStates = new ArrayList<>();
    @BeforeEach
    public void init() {
        engine = new EngineImpl();
        mapCmd = new HashMap<>();
        recorder = new Recorder();
        undoManager = new UndoManager(pastStates,futureStates,engine);
        invoker = new Invoker(mapCmd);

        mapCmd.put("changeSelection", new ChangeSelection(engine.getSelection(), invoker, recorder,undoManager));
        mapCmd.put("insert", new Insert(engine, invoker, recorder,undoManager));
        mapCmd.put("copy", new Copy(engine, recorder,undoManager));
        mapCmd.put("delete", new Delete(engine, invoker, recorder,undoManager));
        mapCmd.put("cut", new Cut(engine, invoker, recorder,undoManager));
        mapCmd.put("paste", new Paste(engine, invoker, recorder,undoManager));
        mapCmd.put("start", new Start(recorder));
        mapCmd.put("stop", new Stop(recorder));
        mapCmd.put("replay", new Replay(recorder));
        mapCmd.put("undo", new Undo(invoker,undoManager,engine,recorder));
        mapCmd.put("redo", new Redo(invoker,undoManager,engine,recorder));
    }

    @Test
    public void testCopy() {
        invoker.playCommand("start");
        invoker.setText("test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(2);
        invoker.playCommand("changeSelection");
        invoker.playCommand("copy");
        invoker.playCommand("stop");
        invoker.playCommand("replay");
        assertEquals("testst", engine.getBufferContents());
    }

    @Test
    public void testCopyPaste() {
        invoker.playCommand("start");
        invoker.setText("test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(2);
        invoker.playCommand("changeSelection");
        invoker.playCommand("copy");
        invoker.setBeginIndex(2);
        invoker.setEndIndex(4);
        invoker.playCommand("changeSelection");
        invoker.playCommand("paste");
        invoker.playCommand("stop");
        invoker.playCommand("replay");
        assertEquals("tetetest", engine.getBufferContents());
    }

    @Test
    public void testCopyPaste2(){
        invoker.playCommand("start");
        invoker.setText("test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(2);
        invoker.playCommand("changeSelection");
        invoker.playCommand("copy");
        invoker.setBeginIndex(2);
        invoker.setEndIndex(4);
        invoker.playCommand("changeSelection");
        invoker.playCommand("paste");
        invoker.playCommand("stop");
        assertEquals("tete", engine.getBufferContents());
        invoker.playCommand("replay");
        assertEquals("tetetest", engine.getBufferContents());
    }
}
