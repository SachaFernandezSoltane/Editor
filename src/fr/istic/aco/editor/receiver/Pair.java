package fr.istic.aco.editor.receiver;

public class Pair<Recordable, Memento> {
    private Recordable Recordable;
    private Memento memento;

    // Constructeur
    public Pair(Recordable Recordable, Memento memento) {
        this.Recordable = Recordable;
        this.memento = memento;
    }

    // Getter pour le premier élément
    public Recordable getRecordable() {
        return Recordable;
    }

    // Getter pour le deuxième élément
    public Memento getMemento() {
        return memento;
    }

    // Setter pour le premier élément
    public void setFirst(Recordable Recordable) {
        this.Recordable = Recordable;
    }

    // Setter pour le deuxième élément
    public void setSecond(Memento memento) {
        this.memento = memento;
    }
}


