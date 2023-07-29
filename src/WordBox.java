public class WordBox<T extends Word> {
    private T contents;

    public WordBox(T contents) {
        this.contents = contents;
    }

    public T getWord() {
        return contents;
    }
}
