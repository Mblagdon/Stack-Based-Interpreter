import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * FileReader class to read and process words from a file argument
 *
 * @author MBlagdon
 */
public class FileReader {
    private final static List<String> wordList = new ArrayList<>();
    private static Map<String, Definition> definitions = new HashMap<>();
    /**
     * Main method for FileReader class
     * Reads file specified by user and processes the words
     *
     * @param args command line arguments are not used in this program
     * @throws IOException if error occurs while reading file
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Please enter a filename for the argument you wish to try:");
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        filename = filename.replaceAll("^\"|\"$", ""); // Remove leading and trailing quotes
        System.out.println();

        try (Scanner scanner1 = new Scanner(new BufferedReader(new java.io.FileReader(filename)))) {
            while (scanner1.hasNext()) {
                wordList.add(scanner1.next().trim());
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
    /**
     * Creates Word objects based on the strings in wordList
     *
     * @param wordList of strings to be converted into Word objects
     * @param words    linkedList of Word objects
     * @return linkedList of Word objects
     * @throws IOException if error occurs while reading file
     */
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
                String definition = iterator.next();
                ArrayList<String> definitionWords = new ArrayList<>();
                while (iterator.hasNext()) {
                    String nextWord = iterator.next();
                    if (nextWord.charAt(0) == ':') {
                        break;
                    } else {
                        definitionWords.add(nextWord);
                    }
                }
                LinkedList<Word> definitionObject = new LinkedList<>();
                createWords(definitionWords, definitionObject);
                String definitionResult = processWords(definitionObject);
                words.addLast(new Definition(definition, definitionResult));
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

    /**
     * Processes the words in the LinkedList
     *
     * @param words of word objects being processed
     * @return result of processing
     * @throws IOException if error occurs while reading file
     */
    public static String processWords(LinkedList<Word> words) throws IOException {
        LinkedList<WordBox<Word>> stack = new LinkedList<>();
        Map<String, Definition> definitions = new HashMap<>();

        for (Word word : words) {
            WordBox<Word> box = new WordBox<>(word);
            if (word instanceof Definition) {
                Definition def = (Definition) box.getWord();
                definitions.put(def.getString(), def);
            } else if (word instanceof StackOperation) {
                stack = ((StackOperation) box.getWord()).runOperation(stack);
            } else if (word instanceof IO) {
                ((IO) word).out(stack.removeLast().getWord().getString());
            } else if (definitions.containsKey(word.getString())) {
                Definition def = definitions.get(word.getString());
                String val = def.getValue(word.getString());
                if (isNumber(val)) {
                    stack.addLast(new WordBox<>(new Number(val)));
                } else {
                    stack.addLast(new WordBox<>(new Word(val)));
                }
            } else {
                stack.addLast(box);
            }
        }

        if (stack.size() > 0) {
            return stack.getLast().getWord().getString();
        }
        return "";
    }

    /**
     * Checks if given string is a number
     *
     * @param word to be checked
     * @return boolean indicating if string is a number
     */
    public static Boolean isNumber(String word) {
        return Pattern.compile("^\\d+$").asPredicate().test(word);
    }
}