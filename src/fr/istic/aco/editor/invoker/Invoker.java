package fr.istic.aco.editor.invoker;

import fr.istic.aco.editor.command.Command;

import java.util.Map;

public class Invoker {
    private Map<String, Command> m;

    private String text;

    private int beginIndex;

    private int endIndex;

    public Invoker(Map<String, Command> m) {
        this.m = m;
    }

    public void addCommand(String nomC, Command command) {
        m.put(nomC, command);
    }

    public void playCommand(String nomC) {
        Command c = m.get(nomC);
        c.execute();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
}
