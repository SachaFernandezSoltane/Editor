package fr.istic.aco.editor.receiver;

import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.concreteMemento.InsertMemento;
import fr.istic.aco.editor.memento.Memento;

import java.util.ArrayList;
import java.util.List;

public class Recorder {

    private boolean isRecording = false;
    private boolean isReplaying = false;

    private List<Pair<Recordable, Memento>> actionsList;

    public Recorder() {
        this.actionsList = new ArrayList<>();
    }

    /**
     * save the state of the recordable object
     */
    public void save (Recordable recordable) {
        if (isRecording && !isReplaying){
            actionsList.add(new Pair<>(recordable, recordable.getMemento()));
        }
    }

    /**
     * replay the actions if the recorder is not recording
     */
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

    /**
     * @return true if the recorder is recording
     */
    public boolean isReplaying() {
        return isReplaying;
    }

    /**
     * start recording
     */
    public void start() {
        isRecording = true;
    }

    /**
     * stop recording
     */
    public void stop() {
        isRecording = false;
    }
}
