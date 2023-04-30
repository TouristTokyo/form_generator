package vsu.cs.server.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vsu.cs.server.utils.JsonUtils;

@RestController
@CrossOrigin
@RequestMapping("/api/generate")
public class JsonSchemaController {

    @PostMapping
    public ResponseEntity<JsonNode> generate(@RequestBody JsonNode data) {
        JsonNode jsonSchema = JsonUtils.createJsonSchema(data);
        if (jsonSchema == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(jsonSchema, HttpStatus.OK);
    }
}
