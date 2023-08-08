import java.util.HashMap;
import java.util.Map;
/**
 * Definition class extends Word
 * This class is used to define new words
 *
 * @author  MBlagdon
 */
public class Definition extends Word{

    /**
     * Map where each entry is key-value pair representing the definition of a word
     */
    private final Map<String, String> definition;

    /**
     * Constructor for Definition class
     * @param key for word being defined
     * @param value for definition of word
     */
    public Definition(String key, String value) {
        super(key);
        this.definition = new HashMap<>();
        definition.put(key, value);
    }

    /**
     *
     * @return map of word definition
     */
    public Map<String, String> getDefinition() {
        return definition;
    }

    /**
     * Gets definition for specific word
     * @param key word to get definition for
     * @return definition of word, or say if no value of word found
     */
    public String getValue(String key) {
        if(this.definition.containsKey(key)){
            return this.definition.get(key);
        }
        else {return "There is no Value";}
    }
}
