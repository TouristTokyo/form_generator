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
        processTyping(stringBuilder, json, "Result Form", "object", 1);
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

    private static void processTyping(StringBuilder stringBuilder, JsonNode json, String name,
                                      String type, int level) {
        stringBuilder.append("\t".repeat(level));
        stringBuilder.append("\"title\": \"")
                .append(name)
                .append("\",\n");

        stringBuilder.append("\t".repeat(level));
        stringBuilder.append("\"type\": \"")
                .append(type)
                .append("\",\n");

        stringBuilder.append("\t".repeat(level));
        stringBuilder.append("\"properties\": {\n");
        Iterator<Entry<String, JsonNode>> iter = json.fields();

        while (iter.hasNext()) {
            Entry<String, JsonNode> entryJson = iter.next();
            if (entryJson.getValue().isValueNode()) {
                appendSimpleData(stringBuilder, entryJson.getKey(), entryJson.getValue(), level + 1);
            } else if (entryJson.getValue().isObject()) {

                stringBuilder.append("\t".repeat(level + 1));
                stringBuilder.append("\"")
                        .append(entryJson.getKey())
                        .append("\"")
                        .append(": {\n");

                processTyping(stringBuilder, entryJson.getValue(), entryJson.getKey(),
                        entryJson.getValue().getNodeType().toString().toLowerCase(), level + 2);

                stringBuilder.append("\t".repeat(level + 1));
            }
            if (iter.hasNext()) {
                stringBuilder.append("},\n");
            } else {
                stringBuilder.append("}\n");
            }
        }

        stringBuilder.append("\t".repeat(level));
        stringBuilder.append("}\n");
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
