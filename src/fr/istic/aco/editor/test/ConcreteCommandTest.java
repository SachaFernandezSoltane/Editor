package fr.istic.aco.editor.test;

import fr.istic.aco.editor.caretaker.UndoManager;
import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.concreteMemento.EditorMemento;
import fr.istic.aco.editor.concretecommand.*;
import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.originator.EngineImpl;
import fr.istic.aco.editor.receiver.Recorder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConcreteCommandTest {

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
        undoManager = new UndoManager(pastStates,futureStates,engine,recorder);
        invoker = new Invoker(mapCmd);

        mapCmd.put("changeSelection", new ChangeSelection(engine.getSelection(), invoker,recorder,undoManager));
        mapCmd.put("insert", new Insert(engine, invoker,recorder,undoManager));
        mapCmd.put("copy", new Copy(engine,recorder,undoManager));
        mapCmd.put("delete", new Delete(engine, invoker,recorder,undoManager));
        mapCmd.put("cut", new Cut(engine, invoker,recorder,undoManager));
        mapCmd.put("paste", new Paste(engine, invoker,recorder, undoManager));
    }

    @Test
    public void testCut() {
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(1);
        invoker.setEndIndex(3);
        invoker.playCommand("changeSelection");
        invoker.playCommand("cut");
        assertEquals(1, invoker.getBeginIndex());
        assertEquals(invoker.getBeginIndex(), invoker.getEndIndex());
        assertEquals("Tt", engine.getBufferContents());
        assertEquals("es", engine.getClipboardContents());
    }

    @Test
    public void testPaste() {
        invoker.setText("Oui");
        invoker.playCommand("insert");
        invoker.setBeginIndex(1);
        invoker.setEndIndex(3);
        invoker.playCommand("changeSelection");
        invoker.playCommand("copy");
        assertEquals("ui", engine.getClipboardContents());
        invoker.setBeginIndex(0);
        invoker.setEndIndex(0);
        invoker.playCommand("changeSelection");
        invoker.playCommand("paste");
        assertEquals("uiOui", engine.getBufferContents());
    }

    @Test
    public void testPaste_2() {
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(1);
        invoker.playCommand("changeSelection");
        invoker.playCommand("cut");
        assertEquals("T", engine.getBufferContents());
        assertEquals("est", engine.getClipboardContents());
        invoker.setBeginIndex(0);
        invoker.setEndIndex(0);
        invoker.playCommand("changeSelection");
        invoker.playCommand("paste");
        assertEquals("estT", engine.getBufferContents());
    }

    @Test
    public void testPaste_3() {
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(1);
        invoker.setEndIndex(3);
        invoker.playCommand("changeSelection");
        invoker.playCommand("cut");
        assertEquals("Tt", engine.getBufferContents());
        assertEquals("es", engine.getClipboardContents());
        invoker.setBeginIndex(0);
        invoker.setEndIndex(1);
        invoker.playCommand("changeSelection");
        invoker.playCommand("paste");
        assertEquals("est", engine.getBufferContents());
    }

    @Test
    public void testInsert() {
        invoker.setText("les chaussures monsieur");
        invoker.playCommand("insert");
        assertEquals(23, invoker.getBeginIndex());
        assertEquals(invoker.getBeginIndex(), invoker.getEndIndex());
        assertEquals("les chaussures monsieur", engine.getBufferContents());
    }

    @Test
    public void testCopy() {
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(1);
        invoker.setEndIndex(3);
        invoker.playCommand("changeSelection");
        invoker.playCommand("copy");
        assertEquals("es", engine.getClipboardContents());
    }

    @Test
    public void testCopyPaste(){
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(1);
        invoker.setEndIndex(3);
        invoker.playCommand("changeSelection");
        invoker.playCommand("copy");
        invoker.setBeginIndex(2);
        invoker.setEndIndex(4);
        invoker.playCommand("changeSelection");
        invoker.playCommand("paste");
        assertEquals("Tees", engine.getBufferContents());
    }

    @Test
    public void testDelete() {
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(1);
        invoker.setEndIndex(3);
        invoker.playCommand("changeSelection");
        invoker.playCommand("delete");
        assertEquals(1, invoker.getBeginIndex());
        assertEquals(invoker.getBeginIndex(), invoker.getEndIndex());
        assertEquals("Tt", engine.getBufferContents());
    }

    @Test
    public void testChangeSelection() {
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(1);
        invoker.setEndIndex(2);
        invoker.playCommand("changeSelection");
        assertEquals(1, engine.getSelection().getBeginIndex());
        assertEquals(2, engine.getSelection().getEndIndex());
    }

    @Test
    public void testInsertInSelection() {
        invoker.setText("Test");
        invoker.playCommand("insert");
        invoker.setBeginIndex(1);
        invoker.setEndIndex(2);
        invoker.playCommand("changeSelection");
        invoker.setText("TEST");
        invoker.playCommand("insert");
    }
}