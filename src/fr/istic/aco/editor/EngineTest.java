package fr.istic.aco.editor;

import static org.junit.jupiter.api.Assertions.*;

class EngineTest {

    private Engine engine;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        engine = new EngineImpl();
    }

    @org.junit.jupiter.api.Test
    void getSelection() {
        Selection selection = engine.getSelection();
        assertEquals(selection.getBufferBeginIndex(),selection.getBeginIndex());
    }

    @org.junit.jupiter.api.Test
    void getBufferContents() {
    }

    @org.junit.jupiter.api.Test
    void getClipboardContents() {
    }

    @org.junit.jupiter.api.Test
    void cutSelectedText() {
    }

    @org.junit.jupiter.api.Test
    void copySelectedText() {
    }

    @org.junit.jupiter.api.Test
    void pasteClipboard() {
    }
}
