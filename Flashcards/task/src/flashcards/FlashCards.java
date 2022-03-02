package flashcards;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static flashcards.Constants.FILE_NAME;
import static flashcards.Constants.THE_CARD;

public class FlashCards {
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

        System.out.println(THE_CARD);
        logs.add(THE_CARD);
        term = s.nextLine();
        logs.add(term);

        if (cards.containsKey(term)) {
            System.out.println("The card \"" + term + "\" already exists.\n");
            logs.add("The card \"" + term + "\" already exists.\n");
            return;
        }

        System.out.println("The definition of the card");
        logs.add("The card \"" + term + "\" already exists.");
        definition = s.nextLine();
        logs.add(definition);

        if (cards.containsValue(definition)) {
            System.out.println("The definition \"" + definition + "\" already exists.\n");
            logs.add("The definition \"" + definition + "\" already exists.\n");
            return;
        }

        System.out.println("The pair (\"" + term + "\":\"" + definition + "\") has been added.\n");
        logs.add("The pair (\"" + term + "\":\"" + definition + "\") has been added.\n");
        cards.put(term, definition);
        mistakeCounter.put(term, 0);
    }

    public void removeCard() {
        System.out.println("Which card?");
        logs.add("Which card?");
        String term = s.nextLine();
        logs.add(term);

        if (!cards.containsKey(term)) {
            System.out.println("Can't remove \"" + term + "\": there is no such card.\n");
            logs.add("Can't remove \"" + term + "\": there is no such card.\n");
            return;
        }

        System.out.println("The card has been removed.\n");
        logs.add("The card has been removed.\n");
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
        System.out.println(FILE_NAME);
        logs.add(FILE_NAME);
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
            System.out.println(count + " cards have been loaded.\n");
            logs.add(count + " cards have been loaded.\n");
            sc.close();
        }
        catch(IOException e) {
            System.out.println("File not found.\n");
            logs.add("File not found.\n");
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
            System.out.println(count + " cards have been loaded.\n");
            logs.add(count + " cards have been loaded.\n");
            sc.close();
        }
        catch(IOException e) {
            System.out.println("File not found.\n");
            logs.add("File not found.\n");
        }
    }

    public void exportCard() {
        System.out.println(FILE_NAME);
        logs.add(FILE_NAME);
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
            System.out.println(cards.size() + " cards have been saved.\n");
            logs.add(cards.size() + " cards have been saved.\n");
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
            System.out.println(cards.size() + " cards have been saved.\n");
            logs.add(cards.size() + " cards have been saved.\n");
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

        System.out.println("\n");
        logs.add("\n");

    }

    public void log() {
        System.out.println(FILE_NAME);
        logs.add(FILE_NAME);
        String fileName = s.nextLine();
        logs.add(fileName);
        try {
            File file = new File(fileName);
            FileWriter writer = new FileWriter(file);

            for (String str: logs) {
                writer.write(str + "\n");
            }
            writer.write("The log has been saved.\n");
            System.out.println("The log has been saved.\n");
            logs.add("The log has been saved.\n");
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetStats() {
        mistakeCounter.replaceAll((k, v) -> 0);
        System.out.println("Card statistics have been reset.\n");
        logs.add("Card statistics have been reset.\n");
    }

    public void hardestCard() {
        if (mistakeCounter.size() == 0) {
            System.out.println("There are no cards with errors.\n");
            logs.add("There are no cards with errors.\n");
            return;
        }
        StringBuilder ss = new StringBuilder();
        int max = Collections.max(mistakeCounter.values());
        List<String> m = mistakeCounter.entrySet().stream()
                .filter(x -> x.getValue() == max)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (max == 0) {
            System.out.println("There are no cards with errors.\n");
            logs.add("There are no cards with errors.\n");
        } else if (m.size() == 1) {
            System.out.println("The hardest card is \"" + m.get(0) + "\". You have " + max + " errors answering it.\n");
            logs.add("The hardest card is \"" + m.get(0) + "\". You have " + max + " errors answering it.\n");
        } else {
            for (int i = 0; i < m.size(); i++) {
                ss.append("\"").append(m.get(i)).append("\"");
                ss.append((i == m.size()-1) ? "." : ",");
            }
            System.out.println("The hardest cards are " + ss + "You have " + max + " errors answering them.\n");
            logs.add("The hardest cards are " + ss + "You have " + max + " errors answering them.\n");
        }
    }
}
