package fr.istic.aco.editor.invoker;

import fr.istic.aco.editor.command.Command;

import java.util.Map;

/**
 * Invoker class
 * @see Command
 */
public class Invoker {
    private Map<String, Command> m;

    private String text;

    private int beginIndex;

    private int endIndex;

    public Invoker(Map<String, Command> m) {
        this.m = m;
    }

    /**
     * Execute command
     * @param nomC name of command
     */
    public void playCommand(String nomC) {
        Command c = m.get(nomC);
        c.execute();
    }

    /**
     * @return text of memento
     */
    public String getText() {
        return text;
    }

    /**
     * Set text of memento
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return beginIndex of memento
     */
    public int getBeginIndex() {
        return beginIndex;
    }

    /**
     * Set beginIndex of memento
     * @param beginIndex
     */
    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    /**
     * @return endIndex of memento
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * Set endIndex of memento
     * @param endIndex
     */
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
}
