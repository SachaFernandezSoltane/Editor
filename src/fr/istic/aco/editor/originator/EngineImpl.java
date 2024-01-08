package fr.istic.aco.editor.originator;

import fr.istic.aco.editor.concreteMemento.EditorMemento;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.receiver.Selection;
import fr.istic.aco.editor.receiver.SelectionImpl;

public class EngineImpl implements Engine {

    private StringBuilder buffer = new StringBuilder();
    private String clipboard = "";
    private final Selection selection = new SelectionImpl(buffer);

    @Override
    public Selection getSelection() {
        return selection;
    }

    @Override
    public String getBufferContents() {
        return buffer.toString();
    }

    @Override
    public String getClipboardContents() {
        return clipboard;
    }

    @Override
    public void cutSelectedText() {
        int begin = selection.getBeginIndex();
        int end = selection.getEndIndex();
        clipboard = buffer.substring(begin, end);
        buffer.delete(begin, end);
        selection.setEndIndex(begin);
    }

    @Override
    public void copySelectedText() {
        int begin = selection.getBeginIndex();
        int end = selection.getEndIndex();
        clipboard = buffer.substring(begin, end);
    }

    @Override
    public void pasteClipboard() {
        int begin = selection.getBeginIndex();
        int end = selection.getEndIndex();
        buffer.replace(begin, end, clipboard);
        selection.setEndIndex(begin + clipboard.length());
        selection.setBeginIndex(selection.getEndIndex());
    }

    @Override
    public void insert(String s) {
        int begin = selection.getBeginIndex();
        int end = selection.getEndIndex();
        buffer.replace(begin, end, s);
        selection.setEndIndex(selection.getBeginIndex() + s.length());
        selection.setBeginIndex(selection.getEndIndex());
    }

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
