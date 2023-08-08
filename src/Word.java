/**
 * Word class
 * This class is used to represent a string or word
 *
 * @author  MBlagdon
 */
public class Word {

    private final String string;
    /**
     * Constructor for Word class
     *
     * @param string representation of word
     */
    public Word(String string) {
        this.string = string;
    }
    /**
     * Getter for word's string     *
     * @return value of word
     */
    public String getString() {
        return string;
    }
    /**
     * Method to concatenate word's string with additional string
     *
     * @param additionalString to concatenate with
     * @return concatenated string
     */
    public String putEmTogether(String additionalString) {
        return additionalString + " " + this.string;
    }
    /**
     * Method to reverse word's string
     *
     * @return reversed string
     */
    public String negate() {
        char[] chars = this.string.toCharArray();
        StringBuilder reverse = new StringBuilder();
        for (int i = chars.length-1; i >=+ 0 ; i--) {
            reverse.append(chars[i]);
        }
        return reverse.toString();
    }
    /**
     * Method to find word's string in target string and return substring from
     * target string starting at index of word's string
     *
     * @param targetString to find the word's string
     * @return substring of target string from the index of the word's string
     */
    public String multiply(String targetString) {
        int index = targetString.indexOf(this.string);
        return targetString.substring(index);
    }
}