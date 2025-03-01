package org.example.aprendovar4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void UserTestConstructor(){
        User user = new User("Jimmy", "this_isAsTrong12password");
        assertEquals("Jimmy",user.getName());
        assertEquals("this_isAsTrong12password",user.getPassword());
        assertTrue(user.getTopicProgress().isEmpty());
    }

    @Test
    void TestUpdateTotalScore() {
        User user = new User("Jimmy", "this_isAsTrong12password");
        user.addProgress("Spanish Basics", new UserProgress(1,80,2));
        user.addProgress("Spanish Numbers", new UserProgress(2,50,3));

        int exceptedResult = 80+50;
        assertEquals(exceptedResult,user.updateTotalScore(),"Total score should be the sum of all topic scores");
    }


    @Test
    void TestCompareTo() {
        User user1 = new User("Ana","anaPass12");
        User user2 = new User("Maria", "mariaPass34");

        user1.setTotalScore(50);
        user2.setTotalScore(70);

        assertTrue(user1.compareTo(user2) < 0);
        assertTrue(user2.compareTo(user1) > 0);
    }
}