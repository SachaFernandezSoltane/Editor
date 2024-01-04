package fr.istic.aco.editor.receiver;

import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.concreteMemento.InsertMemento;
import fr.istic.aco.editor.memento.Memento;
import org.hamcrest.core.CombinableMatcher;

import java.util.ArrayList;
import java.util.List;

public class Recorder {

    private boolean isRecording = false;
    private boolean isReplaying = false;

    private List<Pair<Recordable, Memento>> actionsList;

    public Recorder() {
        this.actionsList = new ArrayList<>();
    }

    public void save (Recordable recordable) {
        if (isRecording && !isReplaying){
            actionsList.add(new Pair<>(recordable, recordable.getMemento()));
        }
    }

    public void replay() {
        isReplaying = true;
        if(isReplaying){
            for (Pair<Recordable, Memento> list : actionsList) {
                list.getRecordable().setMemento(list.getMemento());
                list.getRecordable().execute();
            }
        }
        isReplaying = false;
    }

    public void start() {
        isRecording = true;
    }

    public void stop() {
        isRecording = false;
    }
}
