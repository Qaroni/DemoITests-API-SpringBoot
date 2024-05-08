package com.qaroni.demoitests.support;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@AllArgsConstructor
public class JsonAssertion {
    private ObjectMapper objectMapper;
    private Path parentPath;

    public void assertEquals(String expectedResponsePath, String response) throws IOException {
        JsonNode expectedTree = objectMapper.readTree(readFromFile(expectedResponsePath));
        String expectedJSONPretty = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(expectedTree);

        JsonNode currentTree = objectMapper.readTree(response);
        String currentJSONPretty = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(currentTree);

        Assertions.assertEquals(expectedJSONPretty, currentJSONPretty);
    }

    public String readFromFile(String filePath) throws IOException {
        return Files.readString(parentPath.resolve(filePath));
    }
}
