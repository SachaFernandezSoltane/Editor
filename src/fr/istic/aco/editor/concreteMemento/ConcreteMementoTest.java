package fr.istic.aco.editor.concreteMemento;

import fr.istic.aco.editor.caretaker.UndoManager;
import fr.istic.aco.editor.command.Command;
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

import static org.junit.Assert.assertEquals;

public class ConcreteMementoTest {
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
        mapCmd.put("delete", new Delete(engine, invoker, recorder));
        mapCmd.put("cut", new Cut(engine, invoker, recorder));
        mapCmd.put("paste", new Paste(engine, invoker, recorder,undoManager));
        mapCmd.put("start", new Start(recorder));
        mapCmd.put("stop", new Stop(recorder));
        mapCmd.put("replay", new Replay(recorder));
        mapCmd.put("undo", new Undo(invoker,undoManager,engine));
    }

    @Test
    public void testInsert() {
        invoker.playCommand("start");
        invoker.setText("les chaussures monsieur");
        invoker.playCommand("insert");
        invoker.setText("les chaussures monsieur");
        invoker.playCommand("insert");
        invoker.playCommand("stop");
        invoker.playCommand("replay");
        assertEquals("les chaussures monsieurles chaussures monsieurles chaussures monsieurles chaussures monsieur", engine.getBufferContents());
    }

    @Test
    public void testInsert2() {
        invoker.playCommand("start");
        invoker.setText("les chaussures monsieur");
        invoker.playCommand("insert");
        invoker.setText("les chaussures monsieur");
        invoker.playCommand("insert");
        invoker.playCommand("stop");
        invoker.playCommand("replay");
        invoker.setText("les chaussures monsieur");
        invoker.playCommand("insert");
        assertEquals("les chaussures monsieurles chaussures monsieurles chaussures monsieurles chaussures monsieurles chaussures monsieur", engine.getBufferContents());
    }

    @Test
    public void testDelete() {
        invoker.playCommand("start");
        invoker.setText("test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(2);
        invoker.playCommand("changeSelection");
        invoker.playCommand("delete");
        invoker.playCommand("stop");
        invoker.playCommand("replay");
        assertEquals("stst", engine.getBufferContents());
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
    public void testCut() {
        invoker.playCommand("start");
        invoker.setText("test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(2);
        invoker.playCommand("changeSelection");
        invoker.playCommand("cut");
        invoker.playCommand("stop");
        invoker.playCommand("replay");
        assertEquals("stst", engine.getBufferContents());
    }

    @Test
    public void testPaste() {
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
    public void testCopyPaste(){
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

    @Test
    public void testReplayWithoutStart() {
        invoker.setText("test");
        invoker.playCommand("insert");
        invoker.playCommand("replay");
        assertEquals("test", engine.getBufferContents());
    }

    @Test
    public void testReplayWithoutStop() {
        invoker.playCommand("start");
        invoker.setText("test");
        invoker.playCommand("insert");
        invoker.playCommand("replay");
        assertEquals("testtest", engine.getBufferContents());
        invoker.setText("test");
        invoker.playCommand("insert");
        invoker.playCommand("stop");
        invoker.playCommand("replay");
        assertEquals("testtesttesttesttest", engine.getBufferContents());
    }

    @Test
    public void testStopWithoutStart() {
        invoker.setText("test");
        invoker.playCommand("insert");
        invoker.playCommand("stop");
        invoker.playCommand("replay");
        assertEquals("test", engine.getBufferContents());
    }

    @Test
    public void testUndo() {
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.playCommand("undo");
        assertEquals("", engine.getBufferContents());
    }

    @Test
    public void testUndo2() {
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.setText("Test2");
        invoker.playCommand("insert");
        invoker.playCommand("undo");
        assertEquals("Test", engine.getBufferContents());
    }

    @Test
    public void testUndo3() {
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(2);
        invoker.playCommand("changeSelection");
        assertEquals(0, invoker.getBeginIndex());
        assertEquals(2, invoker.getEndIndex());
        invoker.playCommand("undo");
        assertEquals(4, engine.getSelection().getEndIndex());
        assertEquals(4, engine.getSelection().getBeginIndex());
    }

    @Test
    public void testUndo4() {
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(2);
        invoker.playCommand("changeSelection");
        assertEquals(0, engine.getSelection().getBeginIndex());
        assertEquals(2, engine.getSelection().getEndIndex());
        invoker.playCommand("undo");
        assertEquals(4, engine.getSelection().getEndIndex());
        assertEquals(4, engine.getSelection().getBeginIndex());
        invoker.setText("Test2");
        invoker.playCommand("insert");
        assertEquals("TestTest2", engine.getBufferContents());
    }

    @Test
    public void testUndoCopyPasteUntilBlank() {
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
        assertEquals("Test", engine.getBufferContents());
        assertEquals(4, engine.getSelection().getEndIndex());
        assertEquals(2, engine.getSelection().getBeginIndex());
        invoker.playCommand("undo");
        assertEquals("Test", engine.getBufferContents());
        assertEquals(2, engine.getSelection().getEndIndex());
        assertEquals(0, engine.getSelection().getBeginIndex());
        invoker.playCommand("undo");
        assertEquals(4, engine.getSelection().getEndIndex());
        assertEquals(4, engine.getSelection().getBeginIndex());
        invoker.playCommand("undo");
        assertEquals("", engine.getBufferContents());
    }
}
