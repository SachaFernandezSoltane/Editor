package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.receiver.EngineImpl;
import fr.istic.aco.editor.receiver.Selection;
import fr.istic.aco.editor.receiver.SelectionImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ConcreteCommandTest {

    private EngineImpl engine;
    private Invoker invoker;

    private Selection selection;


    private Map<String,Command> mapCmd;
    @BeforeEach
    public void init(){
        engine = new EngineImpl();
        mapCmd = new HashMap<>();
        invoker = new Invoker(mapCmd);

        mapCmd.put("changeSelection", new ChangeSelection(engine.getSelection(),invoker));
        mapCmd.put("insert", new Insert(engine, invoker));
        mapCmd.put("copy", new Copy(engine));
        mapCmd.put("delete", new Delete(engine));
    }

    @Test
    public void testCut() {
        EngineImpl engine = new EngineImpl();
        engine.insert("Test");
        Selection selection = engine.getSelection();
        selection.setBeginIndex(1);
        selection.setEndIndex(3);
        Cut cut = new Cut(engine);
        cut.execute();
        assertEquals("Tt", engine.getBufferContents());
    }

    @Test
    public void testInsert(){
        invoker.setText("les chaussures monsieur");
        invoker.playCommand("insert");
        assertEquals("les chaussures monsieur", engine.getBufferContents());
    }

    @Test
    public void testCopy(){
        invoker.setText("Test");
        invoker.playCommand("insert");

        invoker.setBeginIndex(1);
        invoker.setEndIndex(3);
        invoker.playCommand("changeSelection");
        invoker.playCommand("copy");
        assertEquals("es", engine.getClipboardContents());
    }

    @Test
    public void testDelete() {
        invoker.setText("Test");
        invoker.playCommand("insert");

        invoker.setBeginIndex(1);
        invoker.setEndIndex(3);
        invoker.playCommand("changeSelection");
        invoker.playCommand("delete");
        assertEquals("Tt", engine.getBufferContents());
    }

    @Test
    public void testChangeSelection(){
        invoker.setText("Test");
        invoker.playCommand("insert");

        invoker.setBeginIndex(1);
        invoker.setEndIndex(2);
        invoker.playCommand("changeSelection");

        assertEquals(1,engine.getSelection().getBeginIndex());
        assertEquals(2,engine.getSelection().getEndIndex());

    }
}
