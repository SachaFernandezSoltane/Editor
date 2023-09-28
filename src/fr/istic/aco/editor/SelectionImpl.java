package fr.istic.aco.editor;

public class SelectionImpl implements Selection{
    private StringBuilder buffer;
    private int beginIndex;
    private int endIndex;
    private int bufferBeginIndex;
    private int bufferEndIndex;

    public SelectionImpl(StringBuilder buffer) {
        this.buffer = buffer;
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
        return bufferEndIndex;
    }

    @Override
    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    @Override
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
}
