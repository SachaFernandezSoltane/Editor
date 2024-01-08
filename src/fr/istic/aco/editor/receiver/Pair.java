package fr.istic.aco.editor.receiver;

public class Pair<Recordable, Memento> {
    private Recordable Recordable;
    private Memento memento;

    public Pair(Recordable Recordable, Memento memento) {
        this.Recordable = Recordable;
        this.memento = memento;
    }

    /**
     * @return Recordable which is first element of the pair
     */
    public Recordable getRecordable() {
        return Recordable;
    }

    /**
     * @return memento which is second element of the pair
     */
    public Memento getMemento() {
        return memento;
    }

    /**
     * set Recordable with Recordable parameter
     * @param Recordable the Recordable to set
     */
    public void setFirst(Recordable Recordable) {
        this.Recordable = Recordable;
    }

    /**
     * set mememto with memento parameter
     * @param memento the memento to set
     */
    public void setSecond(Memento memento) {
        this.memento = memento;
    }
}


