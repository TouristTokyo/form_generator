package vsu.cs.server.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map.Entry;

@Slf4j
public class JsonUtils {
    public static JsonNode createJsonSchema(JsonNode json) {
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{\n");
        stringBuilder.append("\"title\": \"Result form\",\n");
        stringBuilder.append("\"type\": \"object\",\n");
        stringBuilder.append("\"properties\": {\n");

        Iterator<Entry<String, JsonNode>> iter = json.fields();
        int level = 1;

        while (iter.hasNext()) {
            Entry<String, JsonNode> entryJson = iter.next();
            if (entryJson.getValue().isValueNode()) {
                appendSimpleData(stringBuilder, entryJson.getKey(), entryJson.getValue(), level);
            }
            if (iter.hasNext()) {
                stringBuilder.append("},\n");
            } else {
                stringBuilder.append("}\n");
            }
        }

        stringBuilder.append("}\n");
        stringBuilder.append("}");

        try {
            JsonNode result = objectMapper.readTree(stringBuilder.toString());
            log.info("JsonSchema successfully created\n" + stringBuilder);
            return result;
        } catch (JsonProcessingException ex) {
            log.error("Error JSON: " + ex.getMessage());
            return null;
        }
    }

    private static void appendSimpleData(StringBuilder stringBuilder, String field, JsonNode value, int level) {
        stringBuilder.append("\t".repeat(Math.max(0, level)));
        stringBuilder.append("\"")
                .append(field)
                .append("\"")
                .append(": {\n");

        level++;
        stringBuilder.append("\t".repeat(Math.max(0, level)));
        stringBuilder.append("\"title\": \"")
                .append(field)
                .append("\",\n");

        stringBuilder.append("\t".repeat(Math.max(0, level)));
        stringBuilder.append("\"type\": \"")
                .append(value.getNodeType().toString().toLowerCase())
                .append("\",\n");

        stringBuilder.append("\t".repeat(Math.max(0, level)));
        stringBuilder.append("\"default\": \"")
                .append(value.asText())
                .append("\"\n");

        level--;
        stringBuilder.append("\t".repeat(Math.max(0, level)));
    }
}
