DROP TABLE IF EXISTS usr CASCADE;
DROP TABLE IF EXISTS enrolments CASCADE;
DROP TABLE IF EXISTS quizzes CASCADE;
DROP TABLE IF EXISTS questions CASCADE;
DROP TABLE IF EXISTS courses CASCADE;
DROP TABLE IF EXISTS student_answer CASCADE;
DROP TABLE IF EXISTS contents CASCADE;

CREATE TABLE usr (
  user_id SERIAL PRIMARY KEY NOT NULL,
  user_username VARCHAR(255) UNIQUE NOT NULL,
  user_password VARCHAR(255)  NOT NULL,
  user_userType VARCHAR(255) NOT NULL
);



CREATE TABLE courses (
  course_id SERIAL PRIMARY KEY NOT NULL,
  course_name VARCHAR(255) UNIQUE NOT NULL,
  course_tutor_id INTEGER REFERENCES usr(user_id) ON DELETE CASCADE,
  course_description TEXT NOT NULL,
  UNIQUE(course_name)
);

CREATE TABLE enrolments(
  enrolment_student_id INTEGER REFERENCES usr(user_id) ON DELETE CASCADE,
  enrolment_c_id INTEGER REFERENCES courses(course_id) ON DELETE CASCADE,
  UNIQUE(enrolment_student_id,enrolment_c_id)
);


CREATE TABLE quizzes(
  quiz_id SERIAL PRIMARY KEY NOT NULL,
  quiz_name TEXT NOT NULL,
  quiz_course_id INTEGER REFERENCES courses(course_id) ON DELETE CASCADE
);

CREATE TABLE questions(
  question_id SERIAL PRIMARY KEY NOT NULL,
  question_text TEXT NOT NULL,
  question_test_id INTEGER REFERENCES quizzes(quiz_id) ON DELETE CASCADE
);

CREATE TABLE student_answer(
  student_answer_s_id INTEGER REFERENCES usr(user_id) ON DELETE CASCADE,
  student_answer_answer TEXT NOT NULL,
  student_answer_question_id INTEGER REFERENCES questions(question_id) ON DELETE CASCADE,
  student_answer_score INTEGER DEFAULT NULL,
  UNIQUE(student_answer_s_id,student_answer_question_id)
);

CREATE TABLE contents (
  content_id SERIAL PRIMARY KEY NOT NULL,
  content_title TEXT NOT NULL,
  content_c_id INTEGER REFERENCES courses(course_id) ON DELETE CASCADE,
  content_text TEXT NOT NULL,
  content_link TEXT DEFAULT NULL
  );


