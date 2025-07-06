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

CREATE TABLE Quiz (
                      quizId          INT PRIMARY KEY AUTO_INCREMENT,
                      title           VARCHAR(200) NOT NULL,
                      creatorUsername VARCHAR(40),
                      timeLimitSec    INT,
                      timesTaken      INT DEFAULT 0,
                    randomQuiz        BOOLEAN
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
                       score INT NOT NULL,
                       attemptTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (quizId) REFERENCES Quiz(quizId)
);
