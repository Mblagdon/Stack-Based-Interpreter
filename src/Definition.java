import java.util.HashMap;
import java.util.Map;

public class Definition extends Word{

    private final Map<String, String> definition;

    public Definition(String key, String value) {
        super(key);
        this.definition = new HashMap<>();
        definition.put(key, value);
    }

    public Map<String, String> getDefinition() {
        return definition;
    }

    public String getValue(String key) {
        if(this.definition.containsKey(key)){
            return this.definition.get(key);
        }
        else {return "No Value";}
    }
}
