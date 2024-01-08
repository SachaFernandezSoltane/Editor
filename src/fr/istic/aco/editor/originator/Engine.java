package fr.istic.aco.editor.originator;

import fr.istic.aco.editor.concreteMemento.EditorMemento;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.receiver.Selection;

/**
 * Main API for the text editing engine
 *
 * @author plouzeau
 * @version 1.0
 */

public interface Engine {

    /**
     * Provides access to the selection control object
     * @return the selection object
     */
    Selection getSelection();

    /**
     * Provides whole contents of the buffer, as a string
     * @return a copy of the buffer's contents
     */
    String getBufferContents();

    /**
     * Provides clipboard contents
     * @return a copy of the clipboard's contents
     */
    String getClipboardContents();

    /**
     * Removes text within the interval
     * specified by the selection control object,
     * from the buffer.
     */
    void cutSelectedText();

    /**
     * Copies text within the interval
     * specified by the selection control object
     * into the clipboard.
     */
    void copySelectedText();

    /**
     * Replaces text within the interval specified by the selection object with
     * the contents of the clipboard.
     */
    void pasteClipboard();

    /**
     * Inserts a string in the buffer, which replaces the contents of the selection
     * @param s the text to insert
     */
    void insert(String s);

    /**
     * Removes contents of the selection in the buffer
     */
    void delete();

    /**
     * * Change state of the engine using the memento
     * @param memento
     */

    void setMemento(Memento memento);

    /**
     * * Get memento of the engine
     * @return Memento
     */
    Memento getMemento();
}
