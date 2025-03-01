package org.example.aprendovar4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuizSessionTest {

     @Test
    void TestConstructorQuizSession(){
         User mockUser = new User("Jimmy", "password!23");

         QuizSession quizSession = new QuizSession(85,true, "Mock Quiz", mockUser);

         assertEquals(85, quizSession.getScore());
         assertTrue(quizSession.isCompleted());
         assertEquals("Mock Quiz", quizSession.getName());
         assertEquals(mockUser, quizSession.getUser());
     }
}