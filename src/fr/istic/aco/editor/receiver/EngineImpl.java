package fr.istic.aco.editor.receiver;

import fr.istic.aco.editor.concreteMemento.EditorMemento;
import fr.istic.aco.editor.memento.Memento;

public class EngineImpl implements Engine {

    private StringBuilder buffer = new StringBuilder();
    private String clipboard = "";
    private final Selection selection = new SelectionImpl(buffer);

    /**
     * Provides access to the selection control object
     *
     * @return the selection object
     */
    @Override
    public Selection getSelection() {
        return selection;
    }

    /**
     * Provides the whole contents of the buffer, as a string
     *
     * @return a copy of the buffer's contents
     */
    @Override
    public String getBufferContents() {
        return buffer.toString();
    }

    /**
     * Provides the clipboard contents
     *
     * @return a copy of the clipboard's contents
     */
    @Override
    public String getClipboardContents() {
        return clipboard;
    }

    /**
     * Removes the text within the interval
     * specified by the selection control object,
     * from the buffer.
     */
    @Override
    public void cutSelectedText() {
        int begin = selection.getBeginIndex();
        int end = selection.getEndIndex();
        clipboard = buffer.substring(begin, end);
        buffer.delete(begin, end);
        selection.setEndIndex(begin);
    }

    /**
     * Copies the text within the interval
     * specified by the selection control object
     * into the clipboard.
     */
    @Override
    public void copySelectedText() {
        int begin = selection.getBeginIndex();
        int end = selection.getEndIndex();
        clipboard = buffer.substring(begin, end);
    }

    /**
     * Replaces the text within the interval specified by the selection object with
     * the contents of the clipboard.
     */
    @Override
    public void pasteClipboard() {
        int begin = selection.getBeginIndex();
        int end = selection.getEndIndex();
        buffer.replace(begin, end, clipboard);
        selection.setEndIndex(begin + clipboard.length());
        selection.setBeginIndex(selection.getEndIndex());
    }

    /**
     * Inserts a string in the buffer, which replaces the contents of the selection
     *
     * @param s the text to insert
     */
    @Override
    public void insert(String s) {
        int begin = selection.getBeginIndex();
        int end = selection.getEndIndex();
        buffer.replace(begin, end, s);
        selection.setEndIndex(selection.getBeginIndex() + s.length());
        selection.setBeginIndex(selection.getEndIndex());
    }

    /**
     * Removes the contents of the selection in the buffer
     */
    @Override
    public void delete() {
        int begin = selection.getBeginIndex();
        int end = selection.getEndIndex();
        buffer.delete(begin, end);
        selection.setEndIndex(begin);
    }

    public Memento getMemento() {
        return new EditorMemento(buffer.toString(), selection.getBeginIndex(), selection.getEndIndex());
    }

    public void setMemento(Memento memento) {
        if (memento instanceof EditorMemento editorMemento) {
            if(!editorMemento.getBufferContent().contentEquals(buffer)){
                if(editorMemento.getBufferContent().isEmpty())
                    buffer.delete(0, buffer.length());
                else if(editorMemento.getBufferContent().length() > buffer.length())
                    buffer.append(editorMemento.getBufferContent().substring(buffer.length()));
                else if(editorMemento.getBufferContent().length() < buffer.length())
                    buffer.delete(editorMemento.getBufferContent().length(), buffer.length());
                else if(editorMemento.getBufferContent().length() == buffer.length())
                    buffer.replace(0, buffer.length(), editorMemento.getBufferContent());
            }
            selection.setEndIndex(editorMemento.getEndIndex());
            selection.setBeginIndex(editorMemento.getBeginIndex());
        }
    }
}
