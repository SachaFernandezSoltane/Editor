package fr.istic.aco.editor.concreteMemento;

import fr.istic.aco.editor.memento.Memento;

public class InsertMemento implements Memento {

    private String text;

    public InsertMemento(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
