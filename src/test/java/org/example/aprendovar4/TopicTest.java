package org.example.aprendovar4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TopicTest {

     @Test
    void TestConstructorTopic(){
         LearningSession mockLearn = new LearningSession("Mock Topic", "Mock Content");
         QuizSession mockQuiz = new QuizSession(0,false,"Mock Quiz", new User("Jimmy", "new_years!3v3"));

         Topic topic = new Topic("Test Topic",mockLearn, mockQuiz);

         assertEquals("Test Topic", topic.getName());
         assertEquals(mockLearn, topic.getLearn());
         assertEquals(mockQuiz,topic.getQuiz());
     }
}