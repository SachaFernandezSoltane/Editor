package fr.istic.aco.editor.concreteMemento;

import fr.istic.aco.editor.memento.Memento;

/**
 * Concrete memento for editor, allow to store editor state and restore it, working with undo/redo command
 * @see Memento
 */
public class EditorMemento implements Memento {
    private String bufferContent;
    private int beginIndex;

    private int endIndex;

    public EditorMemento(String bufferContent, int beginIndex, int endIndex) {
        this.bufferContent = bufferContent;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    /**
     * @return bufferContent of editor mememto
     */
    public String getBufferContent() {
        return bufferContent;
    }

    /**
     * @return beginIndex of editor mememto
     */
    public int getBeginIndex() {
        return beginIndex;
    }
    /**
     * @return endIndex of editor mememto
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * Set beginIndex of editor mememto
     * @param beginIndex
     */
    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    /**
     * Set endIndex of editor mememto
     * @param endIndex
     */
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
}
