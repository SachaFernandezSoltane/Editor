package fr.istic.aco.editor;

import fr.istic.aco.editor.caretaker.UndoManager;
import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.concretecommand.*;
import fr.istic.aco.editor.invoker.Invoker;
import fr.istic.aco.editor.receiver.EngineImpl;
import fr.istic.aco.editor.receiver.Recorder;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Ihm {

  private int beginIndex = 0;
  private int endIndex = 0;
  private Scanner scanner;
  private EngineImpl engine;
  private Invoker invoker;

  private Recorder recorder;

  private UndoManager undoManager;
  private Map<String, Command> mapCmd;

  public Ihm() {
    this.scanner = new Scanner(System.in);
    engine = new EngineImpl();
    recorder = new Recorder();
    mapCmd = new HashMap<>();
    invoker = new Invoker(mapCmd);

    mapCmd.put("changeSelection", new ChangeSelection(engine.getSelection(), invoker,recorder,undoManager));
    mapCmd.put("insert", new Insert(engine, invoker,recorder,undoManager));
    mapCmd.put("copy", new Copy(engine,recorder,undoManager));
    mapCmd.put("delete", new Delete(engine, invoker,recorder));
    mapCmd.put("cut", new Cut(engine, invoker,recorder));
    mapCmd.put("paste", new Paste(engine, invoker,recorder, undoManager));

  }

  public void start() { //TODO fix le souci d'insert avec plusieurs char déja sélectionnés.
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
    System.out.println("i : insérer texte\ns : sélectionner texte\nc : copier sélection\nv : coller\nx : couper sélection\nd : supprimer sélection\nexit : sortie de l'éditeur");
    System.out.println();

    while (choice) {
      String commande = scanner.nextLine();
      switch (commande) {
        case "s" -> {
          String content;
          boolean inputOk = false;
          System.out.println();
          System.out.println("Quelle est la première borne de la sélection (minimum 0, maximum " + engine.getBufferContents().length() + ") :");
          content = scanner.nextLine();
          while(!inputOk) {
            try {
              int contentAsInt = Integer.parseInt(content);
              if (contentAsInt < 0 || contentAsInt > engine.getBufferContents().length()) {
                System.out.println("Veuillez entrer un nombre valide !");
                content = scanner.nextLine();
              } else {
                inputOk = true;
                beginIndex = contentAsInt;
              }
            } catch (NumberFormatException e) {
              System.out.println("Veuillez entrer un nombre valide !");
              content = scanner.nextLine();
            }
          }
          System.out.println();
          System.out.println("Quelle est la deuxième borne de la sélection (minimum " + beginIndex + ", maximum " + engine.getBufferContents().length() + ") :");
          inputOk = false;
          content = scanner.nextLine();
          while(!inputOk) {
            try {
              int contentAsInt = Integer.parseInt(content);
              if (contentAsInt < beginIndex || contentAsInt > engine.getBufferContents().length()) {
                System.out.println("Veuillez entrer un nombre valide !");
                content = scanner.nextLine();
              } else {
                inputOk = true;
                endIndex = contentAsInt;
              }
            } catch (NumberFormatException e) {
              System.out.println("Veuillez entrer un nombre valide !");
              content = scanner.nextLine();
            }
          }
          invoker.setBeginIndex(beginIndex);
          invoker.setEndIndex(endIndex);
          invoker.playCommand("changeSelection");
          System.out.println("Bornes du contenu sélectionné : [" + invoker.getBeginIndex() + ";" + invoker.getEndIndex() + "]");
          System.out.println("Contenu sélectionné : " + engine.getBufferContents().substring(beginIndex, endIndex));
        }
        case "v" -> {
          invoker.playCommand("paste");
          System.out.println("Contenu collé : " + engine.getBufferContents());
          System.out.println("Position des bornes de sélection : [" + invoker.getBeginIndex() + ";" + invoker.getEndIndex() + "]");
        }
        case "c" -> {
          invoker.playCommand("copy");
          System.out.println("Contenu copié : " + engine.getClipboardContents());
          System.out.println("Position des bornes de sélection : [" + invoker.getBeginIndex() + ";" + invoker.getEndIndex() + "]");
        }
        case "x" -> {
          invoker.playCommand("cut");
          System.out.println("Contenu restant : " + engine.getBufferContents());
          System.out.println("Contenu dans le presse papier : " + engine.getClipboardContents());
          System.out.println("Position des bornes de sélection : [" + invoker.getBeginIndex() + ";" + invoker.getEndIndex() + "]");
        }
        case "d" -> {
          invoker.playCommand("delete");
          System.out.println("Nouveau contenu : " + engine.getBufferContents());
          System.out.println("Position des bornes de sélection : [" + invoker.getBeginIndex() + ";" + invoker.getEndIndex() + "]");
        }
        case "i" -> {
          System.out.println();
          System.out.println("Quel est le texte à insérer :");
          String txt = scanner.nextLine();
          invoker.setText(txt);
          invoker.playCommand("insert");
          System.out.println("Nouveau contenu : " + engine.getBufferContents());
          System.out.println("Position des bornes de sélection : [" + invoker.getBeginIndex() + ";" + invoker.getEndIndex() + "]");
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
