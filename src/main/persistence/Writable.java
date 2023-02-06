package persistence;

import org.json.JSONObject;

// CITATION: used our JsonSerializationDemo as a template
public interface Writable {
    // EFFECTS: converts this to a JSON object and returns it
    JSONObject toJson();
}
