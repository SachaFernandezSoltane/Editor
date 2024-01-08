package fr.istic.aco.editor.receiver;

/**
 * Provides access to selection control operations
 *
 * @author plouzeau
 * @version 1.0
 */
public interface Selection {

    /**
     * Provides index of the first character designated
     * by the selection.
     *
     * @return
     */
    int getBeginIndex();

    /**
     * Provides index of the first character
     * after the last character designated
     * by the selection.
     *
     * @return end index
     */
    int getEndIndex();

    /**
     * Provides index of the first character in the buffer
     *
     * @return the buffer's begin index
     */
    int getBufferBeginIndex();

    /**
     * Provides index of the first "virtual" character
     * after the end of the buffer
     *
     * @return the post end buffer index
     */
    int getBufferEndIndex();

    /**
     * Changes value of the begin index of the selection
     *
     * @param beginIndex, must be within the buffer index range
     * @throws IndexOutOfBoundsException if the beginIndex is out of bounds
     */
    void setBeginIndex(int beginIndex);

    /**
     * Changes value of the end index of the selection
     *
     * @param endIndex, must be within the buffer index range
     * @throws IndexOutOfBoundsException if the beginIndex is out of bounds
     */
    void setEndIndex(int endIndex);


}
