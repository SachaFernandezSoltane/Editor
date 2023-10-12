package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.receiver.EngineImpl;
import fr.istic.aco.editor.receiver.Selection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class ConcreteCommandTest {

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
        EngineImpl engine = new EngineImpl();
        Insert insert = new Insert(engine, "Test");
        insert.execute();
        assertEquals("Test", engine.getBufferContents());
    }

    @Test
    public void testCopy(){
        EngineImpl engine = new EngineImpl();
        engine.insert("Test");
        Selection selection = engine.getSelection();
        selection.setBeginIndex(1);
        selection.setEndIndex(3);
        Copy copy = new Copy(engine);
        copy.execute();
        assertEquals("es", engine.getClipboardContents());
    }

    @Test
    public void testDelete(){
        EngineImpl engine = new EngineImpl();
        engine.insert("Test");
        Selection selection = engine.getSelection();
        selection.setBeginIndex(1);
        selection.setEndIndex(3);
        Delete delete = new Delete(engine);
        delete.execute();
        assertEquals("Tt", engine.getBufferContents());
    }
    @Test
    public void testChangeSelection(){
        EngineImpl engine = new EngineImpl();
        engine.insert("Test");
        Selection selection = engine.getSelection();
        ChangeSelection changeSelection = new ChangeSelection(selection, 0, 2);
        changeSelection.execute();
        assertEquals(0, selection.getBeginIndex());
        assertEquals(2, selection.getEndIndex());
    }
}
