package fr.istic.aco.editor;

import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.concretecommand.*;
import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.receiver.EngineImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Ihm {

  private int beginIndex = 0;

  private int endIndex = 0;
  private Scanner scanner;
  private EngineImpl engine;
  private Invoker invoker;
  private Map<String, Command> mapCmd;

  public Ihm() {
    this.scanner = new Scanner(System.in);
    engine = new EngineImpl();
    mapCmd = new HashMap<>();
    invoker = new Invoker(mapCmd);

    mapCmd.put("changeSelection", new ChangeSelection(engine.getSelection(), invoker));
    mapCmd.put("insert", new Insert(engine, invoker));
    mapCmd.put("copy", new Copy(engine));
    mapCmd.put("delete", new Delete(engine));
    mapCmd.put("cut", new Cut(engine));
    mapCmd.put("paste", new Paste(engine));

  }

  public void start() { //TODO rajouter les états du clipboard, du buffer et des index.
    boolean choice = true;

    System.out.println("Bienvenue dans l'éditeur de texte !");
    System.out.println();
    System.out.println(
            "  ______     _  _  _              ___    ___  ___   ____  \n" +
            " |  ____|   | |(_)| |            |__ \\  / _ \\|__ \\ |___ \\ \n" +
            " | |__    __| | _ | |_  ___   _ __  ) || | | |  ) |  __) |\n" +
            " |  __|  / _` || || __|/ _ \\ | '__|/ / | | | | / /  |__ < \n" +
            " | |____| (_| || || |_| (_) || |  / /_ | |_| |/ /_  ___) |\n" +
            " |______|\\__,_||_| \\__|\\___/ |_| |____| \\___/|____||____/");
    System.out.println();
    System.out.println("Les commandes disponibles sont :");
    System.out.println();
    System.out.println("i : insérer texte\ns : sélectionner texte\nc : copier sélection\nx : couper sélection\nd : supprimer sélection\nexit : sortie de l'éditeur");
    System.out.println();

    while (choice) {
      String commande = scanner.nextLine();
      switch (commande) {
        case "s" -> {
          System.out.println();
          System.out.println("Quelle est la première borne de la sélection  :");
          beginIndex = Integer.parseInt(scanner.nextLine());
          System.out.println();
          System.out.println("Quelle est la deuxième borne de la sélection  :");
          endIndex = Integer.parseInt(scanner.nextLine());
          invoker.setBeginIndex(beginIndex);
          invoker.setEndIndex(endIndex);
          invoker.playCommand("changeSelection");
        }
        case "v" -> {
          invoker.playCommand("paste");
          System.out.println("Contenu collé : " + engine.getBufferContents());
        }
        case "c" -> {
          invoker.playCommand("copy");
          System.out.println("Contenu copié : " + engine.getClipboardContents());
        }
        case "x" -> {
          invoker.playCommand("cut");
          System.out.println("Contenu restant : " + engine.getBufferContents());
          System.out.println("Contenu dans le presse papier : " + engine.getClipboardContents());

        }
        case "d" -> {
          invoker.playCommand("delete");
          System.out.println("Nouveau contenu : " + engine.getBufferContents());
        }
        case "i" -> {
          System.out.println();
          System.out.println("Quel est le texte à insérer :");
          String txt = scanner.nextLine();
          invoker.setText(txt);
          invoker.playCommand("insert");
          System.out.println("Contenu inséré : " + engine.getBufferContents());
        }
        case "exit" -> {
          System.out.println("Sortie de l'éditeur...");
          choice = false;
        }
        default -> System.out.println("Choix incorrect, réessayez");
      }
    }
  }
  public static void main(String[] args) {
    Ihm ihm = new Ihm();
    ihm.start();
  }
}
