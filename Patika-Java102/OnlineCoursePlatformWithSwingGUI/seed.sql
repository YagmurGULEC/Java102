INSERT INTO usr (user_username, user_password, user_userType) VALUES ('admin', '1234', 'admin');
INSERT INTO usr (user_username, user_password, user_userType) VALUES ('instructor1', '1234', 'instructor');
INSERT INTO usr (user_username, user_password, user_userType) VALUES ('student1', '1234', 'student');

INSERT INTO courses(course_name,course_tutor_id,course_description) VALUES ('Java101', 2,'Beginner');
INSERT INTO courses(course_name,course_tutor_id,course_description) VALUES ('Java102', 2,'Intermediate');
INSERT INTO courses(course_name,course_tutor_id,course_description) VALUES ('Python', 2,'Advanced');
INSERT INTO enrolments(enrolment_student_id,enrolment_c_id) VALUES (3, 1);
INSERT INTO quizzes(quiz_quiz_name,quiz_course_id) VALUES ('Java101 Test1', 1);
INSERT INTO questions(question_question, question_test_id) VALUES ('What is Java?', 1);
INSERT INTO questions(question_question, question_test_id) VALUES ('Which data structure?', 1);
INSERT INTO contents (content_title, content_c_id, content_text, content_link) VALUES ('Java101 Lesson 1', 1, 'Java101 Lesson 1 content', 'https://www.youtube.com/watch?v=Hl-zzrqQoSE');
INSERT INTO contents (content_title, content_c_id, content_text, content_link) VALUES ('Java101 Lesson 2', 1, 'Java101 Lesson 2 content', 'https://www.youtube.com/watch?v=Hl-zzrqQoSE');
INSERT INTO student_answer(student_answer_s_id,student_answer_answer,student_answer_question_id) VALUES (3, 'programming language', 1);

SELECT * from usr WHERE user_password='1234' AND user_username='admin';

--User login query
SELECT * from usr
LEFT JOIN enrolments ON enrolments.enrolment_student_id = usr.user_id
LEFT JOIN courses ON  courses.course_tutor_id = usr.user_id AND enrolments.enrolment_c_id = courses.course_id
LEFT JOIN contents ON contents.content_c_id= courses.course_id WHERE user_username = 'student1' AND user_password = '1234'
;

