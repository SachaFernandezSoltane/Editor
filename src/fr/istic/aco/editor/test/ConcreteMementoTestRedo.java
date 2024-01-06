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

public class ConcreteMementoTestRedo {
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
        undoManager = new UndoManager(pastStates, futureStates, engine);
        invoker = new Invoker(mapCmd);

        mapCmd.put("changeSelection", new ChangeSelection(engine.getSelection(), invoker, recorder, undoManager));
        mapCmd.put("insert", new Insert(engine, invoker, recorder, undoManager));
        mapCmd.put("copy", new Copy(engine, recorder, undoManager));
        mapCmd.put("delete", new Delete(engine, invoker, recorder, undoManager));
        mapCmd.put("cut", new Cut(engine, invoker, recorder, undoManager));
        mapCmd.put("paste", new Paste(engine, invoker, recorder, undoManager));
        mapCmd.put("start", new Start(recorder));
        mapCmd.put("stop", new Stop(recorder));
        mapCmd.put("replay", new Replay(recorder));
        mapCmd.put("undo", new Undo(invoker, undoManager, engine, recorder));
        mapCmd.put("redo", new Redo(invoker, undoManager, engine, recorder));
    }

    @Test
    public void testRedoInsert() {
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.playCommand("undo");
        invoker.playCommand("redo");
        assertEquals("Test", engine.getBufferContents());
    }

    @Test
    public void testRedoWithMultipleInserts() {
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.playCommand("undo");
        invoker.playCommand("undo");
        invoker.playCommand("undo");
        invoker.playCommand("redo");
        invoker.playCommand("redo");
        invoker.playCommand("redo");
        assertEquals("TestTestTest", engine.getBufferContents());
    }

    @Test
    public void testRedoChangeSelection() {
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(2);
        invoker.playCommand("changeSelection");
        assertEquals(0, engine.getSelection().getBeginIndex());
        assertEquals(2, engine.getSelection().getEndIndex());
        invoker.playCommand("undo");
        assertEquals(4, engine.getSelection().getBeginIndex());
        assertEquals(4, engine.getSelection().getEndIndex());
        invoker.playCommand("redo");
        assertEquals(0, engine.getSelection().getBeginIndex());
        assertEquals(2, engine.getSelection().getEndIndex());
    }

    @Test
    public void testRedoCopyPaste() {
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(2);
        invoker.playCommand("changeSelection");
        invoker.playCommand("copy");
        invoker.setBeginIndex(2);
        invoker.setEndIndex(4);
        invoker.playCommand("changeSelection");
        invoker.playCommand("paste");
        assertEquals("TeTe", engine.getBufferContents());
        invoker.playCommand("undo");
        invoker.playCommand("undo");
        invoker.playCommand("undo");
        assertEquals("Test", engine.getBufferContents());
        assertEquals(4, engine.getSelection().getEndIndex());
        assertEquals(4, engine.getSelection().getBeginIndex());
        invoker.playCommand("redo");
        invoker.playCommand("redo");
        invoker.playCommand("redo");
        assertEquals("TeTe", engine.getBufferContents());
        assertEquals(4, engine.getSelection().getEndIndex());
        assertEquals(4, engine.getSelection().getBeginIndex());
    }

    @Test
    public void testRedoCutPaste() {
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(2);
        invoker.playCommand("changeSelection");
        invoker.playCommand("cut");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(2);
        invoker.playCommand("changeSelection");
        invoker.playCommand("paste");
        invoker.playCommand("undo");
        invoker.playCommand("undo");
        invoker.playCommand("undo");
        invoker.playCommand("undo");
        assertEquals("Test", engine.getBufferContents());
    }

    @Test
    public void testRedoDelete() {
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(1);
        invoker.playCommand("changeSelection");
        invoker.playCommand("delete");
        invoker.playCommand("undo");
        invoker.playCommand("redo");
        assertEquals("est", engine.getBufferContents());
    }

    @Test
    public void testRedoWithoutInsert() {
        invoker.playCommand("redo");
        assertEquals("", engine.getBufferContents());
    }

    @Test
    public void testRedoWithoutUndo() {
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.playCommand("redo");
        assertEquals("Test", engine.getBufferContents());
    }

    @Test
    public void testRedoReplayDelete() {
        invoker.playCommand("start");
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(1);
        invoker.playCommand("changeSelection");
        invoker.playCommand("delete");
        invoker.playCommand("undo");
        invoker.playCommand("redo");
        assertEquals("est", engine.getBufferContents());
        invoker.playCommand("stop");
        invoker.playCommand("replay");
        assertEquals("estestt", engine.getBufferContents());
    }

    @Test
    public void testRedoReplayInsert(){
        invoker.playCommand("start");
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.playCommand("undo");
        invoker.playCommand("redo");
        assertEquals("Test",engine.getBufferContents());
        invoker.playCommand("stop");
        invoker.playCommand("replay");
        assertEquals("",engine.getBufferContents());
    }
}
