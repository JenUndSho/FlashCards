package flashcards;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        FlashCards fc = new FlashCards();
        Scanner scan = new Scanner(System.in);
        String fileOutput = null;

        for (int i = 0; i< args.length; i++) {
            if ("-import".equals(args[i])) {
                fc.importCards(args[i+1]);
            }

            if ("-export".equals(args[i])) {
                fileOutput = args[i+1];
            }

        }

        do {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            fc.logs.add("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            String command = scan.nextLine();
            fc.logs.add(command);
            switch (command) {
                case "add":
                    fc.addCard();
                    System.out.println("\n");
                    fc.logs.add("\n");
                    break;
                case "remove":
                    fc.removeCard();
                    System.out.println("\n");
                    fc.logs.add("\n");
                    break;
                case "import":
                    fc.importCards();
                    System.out.println("\n");
                    fc.logs.add("\n");
                    break;
                case "export":
                    fc.exportCard();
                    System.out.println("\n");
                    fc.logs.add("\n");
                    break;
                case "ask":
                    fc.askCard();
                    break;
                case "log":
                    fc.log();
                    System.out.println("\n");
                    fc.logs.add("\n");
                    break;
                case "reset stats":
                    fc.resetStats();
                    System.out.println("\n");
                    fc.logs.add("\n");
                    break;
                case "hardest card":
                    fc.hardestCard();
                    System.out.println("\n");
                    fc.logs.add("\n");
                    break;
                case "exit":
                    if (fileOutput != null) {
                        fc.exportCard(fileOutput);
                    }
                    System.out.println("Bye bye!");
                    fc.logs.add("Bye bye!");
                    System.out.println("\n");
                    fc.logs.add("\n");
                    return;
            }
        } while (true);
    }
}



