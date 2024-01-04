package fr.istic.aco.editor.concreteMemento;

import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.concretecommand.*;
import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.receiver.EngineImpl;
import fr.istic.aco.editor.receiver.Recorder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ConcreteMementoTest {
    private EngineImpl engine;
    private Invoker invoker;

    private Recorder recorder;

    private Map<String, Command> mapCmd;

    @BeforeEach
    public void init() {
        engine = new EngineImpl();
        mapCmd = new HashMap<>();
        recorder = new Recorder();

        invoker = new Invoker(mapCmd);

        mapCmd.put("changeSelection", new ChangeSelection(engine.getSelection(), invoker,recorder));
        mapCmd.put("insert", new Insert(engine, invoker,recorder));
        mapCmd.put("copy", new Copy(engine,recorder));
        mapCmd.put("delete", new Delete(engine, invoker,recorder));
        mapCmd.put("cut", new Cut(engine, invoker,recorder));
        mapCmd.put("paste", new Paste(engine, invoker,recorder));
        mapCmd.put("start",new Start(recorder));
        mapCmd.put("stop",new Stop(recorder));
        mapCmd.put("replay",new Replay(recorder));
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
}
