package fr.istic.aco.editor.receiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EngineTest {

    private Engine engine;

    @BeforeEach
    void setUp() {
        engine = new EngineImpl();
    }

    private void todo() {
        fail("Unimplemented test");
    }

    @Test
    @DisplayName("Buffer must be empty after initialisation")
    void getSelection() {
        Selection selection = engine.getSelection();
        assertEquals(selection.getBufferBeginIndex(), selection.getBeginIndex());
        assertEquals(selection.getBufferEndIndex(), selection.getEndIndex());
        assertEquals("", engine.getBufferContents());
    }

    @Test
    void getBufferContents() {
        Selection selection = engine.getSelection();
        engine.insert("abcd");
        assertEquals("abcd", engine.getBufferContents());
    }

    @Test
    void getClipboardContents() {
        Selection selection = engine.getSelection();
        engine.insert("abcd");
        selection.setBeginIndex(1);
        selection.setEndIndex(3);
        engine.copySelectedText();
        assertEquals("bc", engine.getClipboardContents());
    }

    @Test
    void cutSelectedText() {
        Selection selection = engine.getSelection();
        engine.insert("abcd");
        selection.setBeginIndex(1);
        selection.setEndIndex(3);
        engine.cutSelectedText();
        assertEquals("ad", engine.getBufferContents());
        assertEquals(selection.getBeginIndex(), selection.getEndIndex());
    }

    @Test
    void copySelectedText() {
        Selection selection = engine.getSelection();
        engine.insert("abcd");
        selection.setBeginIndex(1);
        selection.setEndIndex(3);
        engine.copySelectedText();
        assertEquals("bc", engine.getClipboardContents());
    }

    @Test
    void pasteClipboard() {
        Selection selection = engine.getSelection();
        engine.insert("abcd");
        selection.setBeginIndex(1);
        selection.setEndIndex(3);
        engine.copySelectedText();
        selection.setBeginIndex(3);
        selection.setEndIndex(3);
        engine.pasteClipboard();
        assertEquals("abcbcd", engine.getBufferContents());
    }

    @Test
    void deleteSelectedText() {
        Selection selection = engine.getSelection();
        engine.insert("abcd");
        selection.setBeginIndex(1);
        selection.setEndIndex(3);
        engine.delete();
        assertEquals("ad", engine.getBufferContents());
        assertEquals(selection.getBeginIndex(), selection.getEndIndex());
    }

    @Test
    void wrongBeginIndexTest() {
        Selection selection = engine.getSelection();
        engine.insert("123456");
        assertThrows(RuntimeException.class, () -> selection.setBeginIndex(-1));
    }

    @Test
    void wrongEndIndexTest() {
        Selection selection = engine.getSelection();
        engine.insert("123456");
        assertThrows(RuntimeException.class, () -> selection.setEndIndex(8));
    }

    @Test
    void wrongIndexOrderTest() {
        Selection selection = engine.getSelection();
        engine.insert("123456");
        selection.setEndIndex(3);
        assertThrows(RuntimeException.class, () -> selection.setBeginIndex(4));
    }
}
