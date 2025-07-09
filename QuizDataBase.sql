-- Create database
CREATE DATABASE IF NOT EXISTS quiz_system;
USE quiz_system;
DROP TABLE IF EXISTS Score;

DROP TABLE IF EXISTS MultipleChoiceOption;
DROP TABLE IF EXISTS MultipleChoiceQuestion;
DROP TABLE IF EXISTS PictureQuestion;
DROP TABLE IF EXISTS FillInBlankQuestion;
DROP TABLE IF EXISTS QuestionResponse;
DROP TABLE IF EXISTS Question;
DROP TABLE IF EXISTS Quiz;
DROP TABLE IF EXISTS Friends;
DROP TABLE IF EXISTS Messages;
DROP TABLE IF EXISTS Announcements;
DROP TABLE IF EXISTS FriendRequests;
DROP TABLE IF EXISTS Users;




CREATE TABLE Users (
                      userId          INT PRIMARY KEY AUTO_INCREMENT,
                      username VARCHAR(40),
                      passwordHash VARCHAR(40),
                     isAdmin BOOLEAN DEFAULT FALSE


);

CREATE TABLE Quiz (
                      quizId          INT PRIMARY KEY AUTO_INCREMENT,
                      title           VARCHAR(200) NOT NULL,
                      creatorUsername VARCHAR(40),
                        creatorID INT NULL,
                      timeLimitSec    INT,
                      timesTaken      INT DEFAULT 0,
                    randomQuiz        BOOLEAN,
                         FOREIGN KEY (creatorID) REFERENCES Users(userID)ON DELETE SET NULL
);


CREATE TABLE Question (
                          questionId INT PRIMARY KEY AUTO_INCREMENT,
                          quizId INT NOT NULL,
                          type ENUM('response', 'fill', 'mcq', 'picture') NOT NULL,
                          text TEXT NOT NULL,
                          FOREIGN KEY (quizId) REFERENCES Quiz(quizId)
);

CREATE TABLE QuestionResponse (
                                   questionId INT PRIMARY KEY,
                                   Answer VARCHAR(200) NOT NULL,
                                   FOREIGN KEY (questionId) REFERENCES Question(questionId)
);



CREATE TABLE FillInBlankQuestion (
                                      questionId INT PRIMARY KEY,
                                      Answer VARCHAR(200) NOT NULL,
                                      FOREIGN KEY (questionId) REFERENCES Question(questionId)
);


CREATE TABLE PictureQuestion (
                                  questionId INT PRIMARY KEY,
                                  imageUrl VARCHAR(1024) NOT NULL,
                                  Answer VARCHAR(200) NOT NULL,
                                  FOREIGN KEY (questionId) REFERENCES Question(questionId)
);

CREATE TABLE MultipleChoiceQuestion (
                              questionId INT PRIMARY KEY,
                              FOREIGN KEY (questionId) REFERENCES Question(questionId)
);

CREATE TABLE MultipleChoiceOption (
                            optionId INT PRIMARY KEY AUTO_INCREMENT,
                            questionId INT NOT NULL,
                            optionText VARCHAR(200) NOT NULL,
                            isCorrect BOOLEAN NOT NULL,
                            FOREIGN KEY (questionId) REFERENCES MultipleChoiceQuestion(questionId)
);


CREATE TABLE Score (
                       scoreId INT PRIMARY KEY AUTO_INCREMENT,
                       quizId INT NOT NULL,
                       username VARCHAR(50) NOT NULL,
                        userId INT,
                       score INT NOT NULL,
                       attemptTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (quizId) REFERENCES Quiz(quizId),
                       FOREIGN KEY (userId) REFERENCES Users(userID)
);

CREATE TABLE Friends (
                         userId INT NOT NULL,
                         friendId INT NOT NULL,
                         PRIMARY KEY (userId, friendId),
                         FOREIGN KEY (userId) REFERENCES Users(userId),
                         FOREIGN KEY (friendId) REFERENCES Users(userId)
);
CREATE TABLE Messages (
                          messageId INT PRIMARY KEY AUTO_INCREMENT,
                          senderId INT NOT NULL,
                          recipientId INT NOT NULL,
                          message TEXT NOT NULL,
                          friendRequest BOOLEAN DEFAULT FALSE,
                          sentAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (senderId) REFERENCES Users(userId),
                          FOREIGN KEY (recipientId) REFERENCES Users(userId)
);
CREATE TABLE Announcements (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               title VARCHAR(200),
                               message TEXT,
                               postedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE FriendRequests (
                                requestId INT PRIMARY KEY AUTO_INCREMENT,
                                senderId INT NOT NULL,
                                recipientId INT NOT NULL,
                                sentAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                status ENUM('PENDING', 'ACCEPTED', 'REJECTED') DEFAULT 'PENDING',
                                FOREIGN KEY (senderId) REFERENCES Users(userId),
                                FOREIGN KEY (recipientId) REFERENCES Users(userId)
);

INSERT INTO Users (username, passwordHash, isAdmin)
VALUES ('adminUser', '7c4a8d09ca3762af61e59520943dc26494f8941b', TRUE);

