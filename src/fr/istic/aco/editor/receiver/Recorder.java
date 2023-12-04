package fr.istic.aco.editor.receiver;

import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.memento.Memento;
import org.hamcrest.core.CombinableMatcher;

import java.util.List;

public class Recorder {

    private List<Pair<Recordable, Memento>> actionsList;

    public void save (Command c) {
        //this.actionsList.add(Pair<, >
    }

    public void replay() {

    }

    public void start() {

    }

    public void stop() {

    }
}
