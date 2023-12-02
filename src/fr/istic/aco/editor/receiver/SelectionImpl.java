package fr.istic.aco.editor.receiver;

public class SelectionImpl implements Selection {
    private StringBuilder buffer;
    private int beginIndex;
    private int endIndex;
    private final int bufferBeginIndex;

    public SelectionImpl(StringBuilder buffer) {
        this.buffer = buffer;
        this.bufferBeginIndex = 0;
    }

    @Override
    public int getBeginIndex() {
        return beginIndex;
    }

    @Override
    public int getEndIndex() {
        return endIndex;
    }

    @Override
    public int getBufferBeginIndex() {
        return bufferBeginIndex;
    }

    @Override
    public int getBufferEndIndex() {
        return buffer.length();
    }

    @Override
    public void setBeginIndex(int beginIndex) {
        if (beginIndex >= bufferBeginIndex && beginIndex <= endIndex) {
            this.beginIndex = beginIndex;
        } else {
            throw new RuntimeException("L'index de départ du buffer est inférieur à l'index minimum");
        }
    }

    @Override
    public void setEndIndex(int endIndex) {
        if (endIndex <= buffer.length()) {
            this.endIndex = endIndex;
        } else {
            throw new RuntimeException("L'index de fin du buffer est supérieur à l'index maximum");
        }
    }
}
