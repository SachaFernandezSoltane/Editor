package fr.istic.aco.editor.concreteMemento;

import fr.istic.aco.editor.memento.Memento;

public class EditorMemento implements Memento {
    private String bufferContent;
    private int beginIndex;

    private int endIndex;

    public EditorMemento(String bufferContent, int beginIndex, int endIndex) {
        this.bufferContent = bufferContent;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    public String getBufferContent() {
        return bufferContent;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
}
