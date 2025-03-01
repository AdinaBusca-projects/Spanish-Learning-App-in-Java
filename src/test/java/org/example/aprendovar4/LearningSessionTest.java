package org.example.aprendovar4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LearningSessionTest {

    @Test
    void TestConstructorLearningSession(){
        String learnName = "Mock LearningSession";
        String content = "I hope the test  will pass!";

        LearningSession newLearningSession = new LearningSession(learnName,content);

        assertEquals(learnName, newLearningSession.getName());
        assertEquals(content, newLearningSession.getContent());
    }
}