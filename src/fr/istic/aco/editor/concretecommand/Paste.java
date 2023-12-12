package fr.istic.aco.editor.concretecommand;

import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.receiver.EngineImpl;
import fr.istic.aco.editor.receiver.Recordable;
import fr.istic.aco.editor.receiver.Recorder;

public class Paste implements Recordable {

    private Invoker invoker;

    private EngineImpl engine;


    private Recorder recorder;

    public Paste(EngineImpl engine, Invoker invoker,Recorder recorder) {
        this.engine = engine;
        this.invoker = invoker;
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        engine.pasteClipboard();
        invoker.setBeginIndex(engine.getSelection().getBeginIndex());
        invoker.setEndIndex(engine.getSelection().getEndIndex());
        recorder.save(this);
    }

    @Override
    public Memento getMemento() {
        return null;
    }

    @Override
    public void setMemento(Memento memento) {

    }
}