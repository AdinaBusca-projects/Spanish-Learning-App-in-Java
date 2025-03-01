package org.example.aprendovar4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserProgressTest {

    @Test
    void TestConstructorUsersProgress(){
        int level = 1;
        int score = 10;
        int attempts = 3;

        UserProgress userProgress = new UserProgress(level,score,attempts);

        assertEquals(level,userProgress.getLevel());
        assertEquals(score,userProgress.getScore());
        assertEquals(attempts,userProgress.getAttempts());
        assertTrue(userProgress.isFirstAttempt(), "First attempts should be initialized as true.");

    }
    @Test
    void TestUpdateLevel() {
        UserProgress userProgress = new UserProgress(1,0,0);

        // Test case: score < 30
        userProgress.updateUserProgress(20);
        assertEquals(1, userProgress.getLevel(), "Level should be 1 for scores less than 30.");

        // Test case: 30 <= score < 50
        userProgress.updateUserProgress(35);
        assertEquals(2, userProgress.getLevel(), "Level should be 2 for scores between 30 and 49.");

        // Test case: 50 <= score < 70
        userProgress.updateUserProgress(60);
        assertEquals(3, userProgress.getLevel(), "Level should be 3 for scores between 50 and 69.");

        // Test case: 70 <= score < 90
        userProgress.updateUserProgress(75);
        assertEquals(4, userProgress.getLevel(), "Level should be 4 for scores between 70 and 89.");

        // Test case: score >= 90
        userProgress.updateUserProgress(95);
        assertEquals(5, userProgress.getLevel(), "Level should be 5 for scores 90 and above.");
    }

    @Test
    void updateUserProgress() {
        UserProgress userProgress = new UserProgress(1,0,0);

        // Test updating user progress for the first attempt
        userProgress.updateUserProgress(55);
        assertEquals(55, userProgress.getScore(), "Score should be updated to 55.");
        assertEquals(3, userProgress.getLevel(), "Level should be updated to 3 based on the score.");
        assertEquals(1, userProgress.getAttempts(), "Attempts should be incremented to 1.");
        assertFalse(userProgress.isFirstAttempt(), "First attempt flag should be set to false.");

        // Test updating user progress for subsequent attempts
        userProgress.updateUserProgress(85);
        assertEquals(85, userProgress.getScore(), "Score should be updated to 85.");
        assertEquals(4, userProgress.getLevel(), "Level should be updated to 4 based on the new score.");
        assertEquals(2, userProgress.getAttempts(), "Attempts should be incremented to 2.");
        assertFalse(userProgress.isFirstAttempt(), "First attempt flag should remain false.");
    }


}