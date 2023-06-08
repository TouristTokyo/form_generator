package vsu.cs.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ServerApplicationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    @Autowired
    ServerApplicationTests(MockMvc mockMvc, ObjectMapper mapper) {
        this.mockMvc = mockMvc;
        this.mapper = mapper;
    }

    @Test
    void testRequest() throws Exception {
        String json = "{ \"f1\":\"Hello\", \"f2\":null }";

        JsonNode jsonNode = mapper.readTree(json);

        mockMvc.perform(post("/api/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jsonNode)))
                .andExpect(status().isOk());
    }

    @Test
    void testIncorrectRequest() throws Exception {
        mockMvc.perform(post("/api/generate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGenerateMethodSimpleData() throws Exception {
        String json = "{ \"data\":\"Hello\", \"data2\": 1 }";
        JsonNode jsonNode = mapper.readTree(json);

        mockMvc.perform(post("/api/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jsonNode)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Result Form")))
                .andExpect(jsonPath("$.type", is("object")))
                .andExpect(jsonPath("$.properties.data.title", is("data")))
                .andExpect(jsonPath("$.properties.data.type", is("string")))
                .andExpect(jsonPath("$.properties.data.default", is("Hello")))
                .andExpect(jsonPath("$.properties.data2.title", is("data2")))
                .andExpect(jsonPath("$.properties.data2.type", is("number")))
                .andExpect(jsonPath("$.properties.data2.default", is("1")));
    }

    @Test
    void testGenerateMethodArrayData() throws Exception {
        String json = "{ \"data\":[\"Hello\", \"test\"]}";
        JsonNode jsonNode = mapper.readTree(json);

        mockMvc.perform(post("/api/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jsonNode)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Result Form")))
                .andExpect(jsonPath("$.type", is("object")))
                .andExpect(jsonPath("$.properties.data.title", is("data")))
                .andExpect(jsonPath("$.properties.data.type", is("array")))
                .andExpect(jsonPath("$.properties.data.items.type", is("string")));
    }

    @Test
    void testGenerateMethodBooleanData() throws Exception {
        String json = "{ \"data\": true}";
        JsonNode jsonNode = mapper.readTree(json);

        mockMvc.perform(post("/api/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jsonNode)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Result Form")))
                .andExpect(jsonPath("$.type", is("object")))
                .andExpect(jsonPath("$.properties.data.title", is("data")))
                .andExpect(jsonPath("$.properties.data.type", is("boolean")))
                .andExpect(jsonPath("$.properties.data.default", is("true")));
    }
    @Test
    void testGenerateMethodCombinationData() throws Exception {
        String json = "{ \"key\": [{\"key2\": \"test\"}]}";
        JsonNode jsonNode = mapper.readTree(json);

        mockMvc.perform(post("/api/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jsonNode)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Result Form")))
                .andExpect(jsonPath("$.type", is("object")))
                .andExpect(jsonPath("$.properties.key.title", is("key")))
                .andExpect(jsonPath("$.properties.key.type", is("array")))
                .andExpect(jsonPath("$.properties.key.items.type", is("object")));
    }
}
