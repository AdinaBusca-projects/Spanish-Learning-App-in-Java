package org.example.aprendovar4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TopicStorage {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final File file;

    public TopicStorage(String filename){
        this.file = new File(filename);
    }

    //save topics to a JSON file
    public void saveTopics(Map<String, Topic> topics) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, topics);
            System.out.println("Topics have been saved to " + file.getName());
        } catch (IOException e) {
            System.err.println("Error saving topics: " + e.getMessage());
        }
    }

    // Load topics from a JSON file
    public  Map<String, Topic> loadTopics() {
        Map<String, Topic> topics = new HashMap<>();

        try {
            if (!file.exists()) {
                throw new FileNotFoundException("The file " + file.getName() + " does not exist.");
            }

            topics = objectMapper.readValue(file, new TypeReference<Map<String, Topic>>() {});
            System.out.println("Topics have been loaded from " + file.getName());

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }catch (JsonProcessingException e) {
            System.err.println("Error processing JSON data: " + e.getMessage());
            return new HashMap<>();

        } catch (IOException e) {
            System.err.println("Error loading topics: " + e.getMessage());
        }

        return topics;
    }




}
