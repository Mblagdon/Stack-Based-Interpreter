import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {
    private final static List<String> wordList = new ArrayList<>();
    private static Definition definition;

    public static void main(String[] args) throws IOException {
        System.out.println("Please enter a filename, without quotes, for your argument:");
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        System.out.println();

        try (Scanner s = new Scanner(new BufferedReader(new java.io.FileReader(filename)))) {
            while (s.hasNext()) {
                wordList.add(s.next().trim());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Sorry there was no file found, please try again.");
        }

        LinkedList<Word> words = new LinkedList<>();
        createWords(wordList, words);
        try {
            processWords(words);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<Word> createWords(List<String> wordList, LinkedList<Word> words) throws IOException {
        List<String> inputOutputWords = Stream.of("in", "out").collect(Collectors.toList());
        List<String> stackOperationWords = Stream.of("-", "+", "*", "dup", "swap", "pop").collect(Collectors.toList());

        Iterator<String> iterator = wordList.iterator();

        while (iterator.hasNext()) {
            String word = iterator.next();

            if (isNumber(word)) {
                words.addLast(new Number(word));
            } else if ('\'' == word.charAt(0)) {
                StringBuilder quoteString = new StringBuilder();
                quoteString.append(word.replaceAll("'", "") + " ");
                while (iterator.hasNext()) {
                    String nextWord = iterator.next();
                    if (nextWord.charAt(nextWord.length() - 1) == '\'') {
                        quoteString.append(nextWord.replaceAll("'", ""));
                        break;
                    }
                    quoteString.append(nextWord).append(" ");
                }
                words.addLast(new Quote(quoteString.toString().trim()));
            } else if (':' == word.charAt(0) && iterator.hasNext()) {
                String t = iterator.next();
                ArrayList<String> stringArray = new ArrayList<>();
                while (iterator.hasNext()) {
                    String nextWord = iterator.next();
                    if (nextWord.charAt(0) == ':') {
                        break;
                    } else {
                        stringArray.add(nextWord);
                    }
                }
                LinkedList<Word> def = new LinkedList<>();
                String result = processWords(createWords(stringArray, def));
                words.addLast(new Definition(t, result));
            } else if (inputOutputWords.contains(word)) {
                if (word.equalsIgnoreCase("in")) {
                    IO in = new IO(word);
                    words.addLast(new Quote(in.in()));
                } else {
                    words.addLast(new IO(word));
                }
            } else if (stackOperationWords.contains(word)) {
                words.addLast(new StackOperation(word));
            } else {
                words.addLast(new Word(word));
            }
        }
        return words;
    }

    public static String processWords(LinkedList<Word> words) throws IOException {
        LinkedList<WordBox<Word>> stack = new LinkedList<>();
        for(Word word: words){
            WordBox<Word> box = new WordBox<>(word);
            if (word instanceof Definition) {
                definition = (Definition) box.getWord();
            } else if (word instanceof StackOperation) {
                stack = ((StackOperation) box.getWord()).runOperation(stack);
                if (stack.size() == 1 && words.size() == 0) {
                    System.out.println(stack.get(0).getWord().getString());
                }
            } else if (word instanceof IO) {
                ((IO) word).out(stack.removeLast().getWord().getString());
            } else if (definition == null) {
                stack.addLast(box);
            } else if (definition.getDefinition().containsKey((word.getString()))){
                String val = definition.getValue(word.getString());
                if (isNumber(val)){
                    stack.addLast(new WordBox<>(new Number(val)));
                }
                else {
                    stack.addLast(new WordBox<>(new Word(val)));
                }
            } else {
                stack.addLast(box);
            }
        }
        return null;
    }

    public static Boolean isNumber(String word) {
        return Pattern.compile("^\\d+$").asPredicate().test(word);
    }
}

