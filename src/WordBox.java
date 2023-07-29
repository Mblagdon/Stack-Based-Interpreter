/**
 * WordBox class
 * This class is a generic container for words
 *
 * @param <T> type of word object this box can hold
 * @author  MBlagdon
 */
public class WordBox<T extends Word> {
    private T contents;
    /**
     * Constructor for WordBox class
     *
     * @param contents of WordBox
     */
    public WordBox(T contents) {
        this.contents = contents;
    }
    /**
     * Getter for WordBox's content
     *
     * @return content of WordBox
     */
    public T getWord() {
        return contents;
    }
}
