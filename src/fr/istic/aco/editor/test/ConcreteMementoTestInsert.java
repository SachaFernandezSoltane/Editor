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

public class ConcreteMementoTestInsert {
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
    public void testMultipleInsert() {
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
}
