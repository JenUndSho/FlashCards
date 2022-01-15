package flashcards;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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
                    System.out.println("\n");
                    fc.logs.add("\n");
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

class FlashCards {
    private Map<String, String> cards;
    private Scanner s;
    List<String> logs;
    private Map<String, Integer> mistakeCounter;

    public Map<String, String> getCards(){
        return this.cards;
    }

    public FlashCards() {
        cards = new LinkedHashMap<String, String>();
        s = new Scanner(System.in);
        logs = new ArrayList<>();
        mistakeCounter = new LinkedHashMap<String, Integer>();
    }

    public void addCard() {
        String term = null;
        String definition = null;

        System.out.println("The card:");
        logs.add("The card:");
        term = s.nextLine();
        logs.add(term);

        if (cards.containsKey(term)) {
            System.out.println("The card \"" + term + "\" already exists.");
            logs.add("The card \"" + term + "\" already exists.");
            return;
        }

        System.out.println("The definition of the card");
        logs.add("The card \"" + term + "\" already exists.");
        definition = s.nextLine();
        logs.add(definition);

        if (cards.containsValue(definition)) {
            System.out.println("The definition \"" + definition + "\" already exists.");
            logs.add("The definition \"" + definition + "\" already exists.");
            return;
        }

        System.out.println("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
        logs.add("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
        cards.put(term, definition);
        mistakeCounter.put(term, 0);
    }

    public void removeCard() {
        System.out.println("Which card?");
        logs.add("Which card?");
        String term = s.nextLine();
        logs.add(term);

        if (!cards.containsKey(term)) {
            System.out.println("Can't remove \"" + term + "\": there is no such card.");
            logs.add("Can't remove \"" + term + "\": there is no such card.");
            return;
        }

        System.out.println("The card has been removed.");
        logs.add("The card has been removed.");
        cards.remove(term);
        mistakeCounter.remove(term);
    }

    public void printAllCards() {
        System.out.println("______________");
        for (String name: cards.keySet()) {
            String key = name;
            String value = cards.get(name);
            System.out.println(key + " " + value);
        }
        System.out.println("______________");
    }

    public void printAllMistakes() {
        System.out.println("______________");
        for (String name: mistakeCounter.keySet()) {
            String key = name;
            Integer value = mistakeCounter.get(name);
            System.out.println(key + " " + value);
        }
        System.out.println("______________");
    }

    public void importCards() {
        System.out.println("File name:");
        logs.add("File name:");
        String fileName = s.nextLine();
        logs.add(fileName);
        String term = null;
        String definition = null;
        int mistakes = 0;
        int count = 0;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            Scanner sc = new Scanner(fis);
            while(sc.hasNextLine())
            {

                term = sc.nextLine();
                definition = sc.nextLine();
                mistakes = Integer.parseInt(sc.nextLine());
                cards.put(term, definition);
                mistakeCounter.put(term, mistakes);
                count++;
            }
            System.out.println(count + " cards have been loaded.");
            logs.add(count + " cards have been loaded.");
            sc.close();
        }
        catch(IOException e) {
            System.out.println("File not found.");
            logs.add("File not found.");
        }
    }

    public void importCards(String fileName) {
        String term = null;
        String definition = null;
        int mistakes = 0;
        int count = 0;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            Scanner sc = new Scanner(fis);
            while(sc.hasNextLine())
            {
                term = sc.nextLine();
                definition = sc.nextLine();
                mistakes = Integer.parseInt(sc.nextLine());
                cards.put(term, definition);
                mistakeCounter.put(term, mistakes);
                count++;
            }
            System.out.println(count + " cards have been loaded.");
            logs.add(count + " cards have been loaded.");
            sc.close();
        }
        catch(IOException e) {
            System.out.println("File not found.");
            logs.add("File not found.");
        }
    }

    public void exportCard() {
        System.out.println("File name:");
        logs.add("File name:");
        String fileName = s.nextLine();
        logs.add(fileName);

        try {
            File file = new File(fileName);
            FileWriter writer = new FileWriter(file);

            for (String name: cards.keySet()) {
                writer.write(name + "\n");
                writer.write(cards.get(name) + "\n");
                writer.write(mistakeCounter.get(name) + "\n");
            }
            System.out.println(cards.size() + " cards have been saved.");
            logs.add(cards.size() + " cards have been saved.");
            writer.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void exportCard(String fileName) {
        try {
            File file = new File(fileName);
            FileWriter writer = new FileWriter(file);

            for (String name: cards.keySet()) {
                writer.write(name + "\n");
                writer.write(cards.get(name) + "\n");
                writer.write(mistakeCounter.get(name) + "\n");
            }
            System.out.println(cards.size() + " cards have been saved.");
            logs.add(cards.size() + " cards have been saved.");
            writer.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void askCard() {
        String answer = null;

        System.out.println("How many times to ask?");
        logs.add("How many times to ask?");
        int n = Integer.parseInt(s.nextLine());
        logs.add(String.valueOf(n));
        int i = 0;
        while (true) {

            for (Map.Entry<String, String> e : cards.entrySet()) {
                i++;
                System.out.println("Print the definition of \"" + e.getKey() + "\":");
                logs.add("Print the definition of \"" + e.getKey() + "\":");
                answer = s.nextLine();
                logs.add(answer);
                if (answer.equals(e.getValue())) {
                    System.out.println("Correct!");
                    logs.add("Correct!");
                }
                else if (cards.containsValue(answer)) {
                    for (Map.Entry<String, String> localEntry : cards.entrySet()) {
                        if (answer.equals(localEntry.getValue())) {
                            System.out.println("Wrong. The right answer is \"" + e.getValue() + "\", but your definition is correct for \"" + localEntry.getKey() + "\".");
                            logs.add("Wrong. The right answer is \"" + e.getValue() + "\", but your definition is correct for \"" + localEntry.getKey() + "\".");
                            mistakeCounter.put(e.getKey(), mistakeCounter.get(e.getKey()) + 1);
                        }
                    }
                } else {
                    System.out.println("Wrong. The right answer is \"" + e.getValue() + "\".");
                    logs.add("Wrong. The right answer is \"" + e.getValue() + "\".");
                    mistakeCounter.put(e.getKey(), mistakeCounter.get(e.getKey()) + 1);
                }

                if (i == n) break;
            }

            if (i == n) break;
        }
    }

    public void log() {
        System.out.println("File name:");
        logs.add("File name:");
        String fileName = s.nextLine();
        logs.add(fileName);
        try {
            File file = new File(fileName);
            FileWriter writer = new FileWriter(file);

            for (String str: logs) {
                writer.write(str + "\n");
            }
            writer.write("The log has been saved.\n");
            System.out.println("The log has been saved.");
            logs.add("The log has been saved.");
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetStats() {
        mistakeCounter.replaceAll((k, v) -> 0);
        System.out.println("Card statistics have been reset.");
        logs.add("Card statistics have been reset.");
    }

    public void hardestCard() {
        if (mistakeCounter.size() == 0) {
            System.out.println("There are no cards with errors.");
            logs.add("There are no cards with errors.");
            return;
        }
        StringBuilder ss = new StringBuilder();
        int max = Collections.max(mistakeCounter.values());
        List<String> m = mistakeCounter.entrySet().stream()
                .filter(x -> x.getValue() == max)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (max == 0) {
            System.out.println("There are no cards with errors.");
            logs.add("There are no cards with errors.");
        } else if (m.size() == 1) {
            System.out.println("The hardest card is \"" + m.get(0) + "\". You have " + max + " errors answering it.");
            logs.add("The hardest card is \"" + m.get(0) + "\". You have " + max + " errors answering it.");
        } else {
            for (int i = 0; i < m.size(); i++) {
                ss.append("\"").append(m.get(i)).append("\"");
                ss.append((i == m.size()-1) ? "." : ",");
            }
            System.out.println("The hardest cards are " + ss + "You have " + max + " errors answering them.");
            logs.add("The hardest cards are " + ss + "You have " + max + " errors answering them.");
        }
    }
}

class Constants {
    public final String MENU = "Input the action (add, remove, import, export, ask, exit):";
    public final String BYE = "Bye bye!";
    public final String THE_CARD = "The card:";
    public final String FILE_NAME = "File name:";

}

