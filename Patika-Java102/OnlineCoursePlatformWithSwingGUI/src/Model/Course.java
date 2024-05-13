package Model;
import Database.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
public class Course {
    private int id;
    private  String name;
    private String description;
    private String link;
    private String tutorName;
    private ArrayList<Content> contents;
    private ArrayList<Quiz> quizzes;

    public static Course addCourse(String s, String s1, User tutor) {
        Course course = null;
        try {
            String query = "INSERT INTO courses(course_name,course_tutor_id,course_description) VALUES (?,?,?);";
            Connection connection = DB.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, s);

            statement.setInt(2, tutor.getId());
            statement.setString(3, s1);

            statement.executeUpdate();
            statement.close();
            connection.close();
            course=new Course();
            course.setName(s);
            course.setDescription(s1);
            course.setTutorName(tutor.getUsername());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return course;
    }
    public static boolean deleteCourse(Course course) {
        boolean isDeleted=false;
        try {
            String query = "DELETE FROM courses WHERE course_id=?";
            Connection connection = DB.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, course.getId());
            statement.executeUpdate();
            statement.close();
            connection.close();
            isDeleted=true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isDeleted;
    }

    public int getId() {
        return id;
    }
  
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getLink() {
        return link;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void addContent(Content content) {
        if (contents == null) {
            contents = new ArrayList<>();
        }
        contents.add(content);
    }
    public static void addCourse(Course course, User tutor) {
        try {
            String query = "INSERT INTO courses(course_name,course_tutor_id,course_description) VALUES (?,?,?);";
            Connection connection = DB.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, course.getName());

            statement.setInt(2, tutor.getId());
            statement.setString(3, course.getDescription());

            statement.executeUpdate();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Content> getContents() {
            return contents;
        }
    public void setLink(String link) {
        this.link = link;
    }
    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }
    public String getTutorName() {
        return tutorName;
    }
    public void addQuiz(Quiz quiz) {
        if (quizzes == null) {
            quizzes = new ArrayList<>();
        }
        quizzes.add(quiz);
    }

   public static ArrayList<Course> getAllCourses() {
        ArrayList<Course> courses = new ArrayList<Course>();
        try {
            String query = "SELECT * from courses";
            Connection connection = DB.getInstance();
            var statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Course newCourse = new Course();
                newCourse.setId(resultSet.getInt("course_id"));
                newCourse.setName(resultSet.getString("course_name"));
                newCourse.setDescription(resultSet.getString("course_description"));
                newCourse.setTutorName(resultSet.getString("course_tutor_id"));
                courses.add(newCourse);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }
    public static ArrayList<Course> getSearchResults(String keyword) {
        ArrayList<Course> searchResults = new ArrayList<Course>();
        try {
            String query = "SELECT * from courses LEFT JOIN usr ON courses.course_tutor_id = usr.user_id LEFT JOIN enrolments ON  " +
                    "enrolments.enrolment_student_id = usr.user_id\n" +
                    "LEFT JOIN contents ON courses.course_id = contents.content_c_id " +
                    "LEFT JOIN quizzes ON courses.course_id = quizzes.quiz_course_id " +
                    "LEFT JOIN questions ON quizzes.quiz_id = questions.question_test_id" +
                    " WHERE LOWER(courses.course_name)  LIKE ? OR LOWER(courses.course_description) LIKE  ? ORDER BY courses.course_id";
            var statement = DB.getInstance().prepareStatement(query);
            statement.setString(1, "%" + keyword.toLowerCase() + "%");
            statement.setString(2, "%" + keyword.toLowerCase() + "%");
            ResultSet resultSet = statement.executeQuery();
//            ResultSetMetaData rsmd=resultSet.getMetaData();
//            int colCount = rsmd.getColumnCount();

            Course oldCourse=null;
            while(resultSet.next()) {
                Course newCourse = new Course();
                newCourse.setId(resultSet.getInt("course_id"));
                newCourse.setName(resultSet.getString("course_name"));
                newCourse.setDescription(resultSet.getString("course_description"));
                newCourse.setTutorName(resultSet.getString("user_username"));
                Content content = new Content();
                content.setId(resultSet.getInt("content_id"));
                content.setTitle(resultSet.getString("content_title"));
                content.setDescription(resultSet.getString("content_text"));
                content.setLink(resultSet.getString("content_link"));


                if (oldCourse!=null && oldCourse.equals(newCourse))
                    searchResults.getLast().addContent(content);
                else
                {
                    newCourse.addContent(content);
                    searchResults.add(newCourse);
                }
                oldCourse=newCourse;


        }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return searchResults;
    }
    @Override
    public String toString() {
        return name;
    }
    //to avoid from having duplicate objects in the list
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Course) {
            return ((Course) obj).getId() == this.getId();
        }
        return false;
    }
    @Override
    public int hashCode() {
        return this.getId();
    }

}
