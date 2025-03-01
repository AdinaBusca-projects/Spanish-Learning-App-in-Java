Overview:
-Aprendo is a Spanish learning app that allows its users to create account, login and start their learning journey.​

-Once the users log in, they can check their overall progress or choose to go to a specific topic.​

-In the topic section, users can choose to learn, take a quiz or see their progress for the specific topic.​

Using a database management system (SQLite), there are two tables:
Users(name PRIMARY KEY,  password TEXT NOT NULL, totalScore INTEGER NOT NULL DEFAULT 0);​

TopicProgress( userName TEXT NOT NULL,  topicName TEXT NOT NULL, level INTEGER NOT NULL, score NOT NULL, attempts INTEGER NOT NULL, firstAttempt BOOLEAN NOT NULL, FOREIGN KEY (userName) REFERENCES Users (name), UNIQUE (userName, topicName))​

GUI:
As of now, there are 10 scenes, each of those providing a behavior specific to the actions a user can make. Thus, some scenes have the sole purpose of letting the user log in or create the account, while others provide ways to get the overall progress, 
total progress, or get to a topic that they want to learn and eventually take a quiz.​

Future Improvement:
1.Handling of multiple users at the same time​

2.A functionality that ranks the users based on their total score or scores for specific topics​

3.A generating system for questions that chooses 10 random questions from the given set (instead of the same 10 questions every time)​
