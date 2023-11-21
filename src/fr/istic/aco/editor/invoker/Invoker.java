        package fr.istic.aco.editor.invoker;

import fr.istic.aco.editor.command.Command;

import java.util.Map;

public class Invoker {
    private Map<String, Command> m;

    public Invoker(Map<String, Command> m) {
        this.m = m;
    }

    public void addCommand(String nomC,Command command){
        m.put(nomC,command);
    }

    public void playCommand(String nomC){
        Command c = m.get(nomC);
        c.execute();
    }
}
