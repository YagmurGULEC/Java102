package Model;
import Database.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
public class User {

    private int id;
    private String username;
    private String password;
    private UserRole role;


    private enum LoginStatus {
        SUCCESS,
        FAIL
    }
    private LoginStatus loginStatus=LoginStatus.FAIL;

    private ArrayList<Course> courses = new ArrayList<Course>();

    public static ArrayList<User> users = new ArrayList<User>();
    //public ArrayList<Course> courses = new ArrayList<Course>();
    private ArrayList<Questions> answeredQuestions = new ArrayList<Questions>();
    private void setId(int id) {
        this.id = id;
    }
    private void setUserName(String username) {
        this.username = username;
    }
    private void setPassword(String password) {
        this.password = password;
    }
    public int getId() {
        return id;
    }
    public void Logout() {
        this.loginStatus = LoginStatus.FAIL;
    }

    public static User Login(String username, String password) {
        User user = null;

        try {
            String query = "SELECT * FROM usr " +
                    "WHERE usr.user_username = ? AND usr.user_password=?;";
            Connection connection = DB.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final User newUser = new User();
                newUser.setId(resultSet.getInt("user_id"));
                newUser.setUserName(resultSet.getString("user_username"));
                newUser.setPassword(resultSet.getString("user_password"));
                newUser.setRole(resultSet.getString("user_userType"));
                newUser.loginStatus = LoginStatus.SUCCESS;
                user = newUser;

            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public static User addUser(String username,String password,String userType) {
        User user = null;
        try {
            String query = "INSERT INTO usr (user_username, user_password, user_userType) VALUES (?, ?, ?)";
            Connection connection = DB.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, userType);
            statement.executeUpdate();
            statement.close();
            user = new User();
            user.SetUserName(username);
            user.SetPassword(password);
            user.setRole(userType);
            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    public static ArrayList<User> getAllTeachers()
    {
        ArrayList<User> teachers = new ArrayList<User>();
        try {
            String query = "SELECT * FROM usr WHERE user_userType='instructor' ";
            Connection connection = DB.getInstance();
            Statement statement = connection.createStatement();
            var resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("user_username"));
                user.setPassword(resultSet.getString("user_password"));
                user.setRole(resultSet.getString("user_userType"));
                teachers.add(user);
            }
            resultSet.close();
            statement.close();
            connection.close();

        }
        catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return teachers;
    }
    public ArrayList<Course> getCourses() {
        ArrayList<Course> courses = new ArrayList<Course>();
        try {
            String query = "SELECT * from usr WHERE user_userType=?";
            PreparedStatement statement = DB.getInstance().prepareStatement(query);
            statement.setInt(1, this.id);
            var resultSet = statement.executeQuery();
            Course oldCourse=null;
            Course course = new Course();
            course.setId(resultSet.getInt("course_id"));
            course.setName(resultSet.getString("course_name"));
            course.setDescription(resultSet.getString("course_description"));
            Content content = new Content();
            content.setId(resultSet.getInt("content_id"));
            content.setTitle(resultSet.getString("content_title"));
            content.setDescription(resultSet.getString("content_text"));
            content.setLink(resultSet.getString("content_link"));
            while (resultSet.next()) {
                if (oldCourse==null) {
                    if (course.getTutorName() == null) {
                        course.setTutorName(resultSet.getString("user_username"));
                    }
                    course.addContent(content);
                    AddCourse(course);

                }
                else if (oldCourse!=null && oldCourse.equals(course))
                    courses.getLast().addContent(content);
                else
                {
                    if (course.getTutorName() == null) {
                        course.setTutorName(resultSet.getString("user_username"));
                    }
                    course.addContent(content);
                    courses.add(course);
                }
                oldCourse=course;

            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }
    public static User Register(String username, String password,String role) {
        User user = null;
        try {
            String query = "INSERT INTO usr (user_username, user_password, user_userType) VALUES (?, ?, ?)";
            Connection connection = DB.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);
            statement.executeUpdate();
            statement.close();
            user = new User();
            user.SetUserName(username);
            user.SetPassword(password);
            user.setRole(role);
            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
    private void SetUserName(String username) {
        this.username = username;
    }
    private void SetPassword(String password) {
        this.password = password;
    }
    private void AddCourse(Course course) {
        courses.add(course);
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public LoginStatus getLoginStatus() {
        return loginStatus;
    }
    public void setRole(String role) {

        if (role.equalsIgnoreCase("student"))
            this.role=UserRole.STUDENT;
        else if (role.equalsIgnoreCase("teacher"))
            this.role= UserRole.TEACHER;
        else if (role.equalsIgnoreCase("admin"))
            this.role=UserRole.ADMIN;

    }



    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }


    public String getRole() {
        if (role==UserRole.ADMIN)
            return "admin";
        else if (role==UserRole.STUDENT)
            return "student";
        else if (role==UserRole.TEACHER)
            return "teacher";
        else
            return null;

    }

    public static ArrayList<User> getInstructor() throws SQLException {
        String query = "SELECT * FROM usr WHERE user_userType='teacher' ";
        try {
            Connection connection = DB.getInstance();
            Statement statement = connection.createStatement();
            var resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("user_username"));
                user.setPassword(resultSet.getString("user_password"));
                user.setRole(resultSet.getString("user_userType"));
                users.add(user);
            }
            resultSet.close();
            statement.close();
            connection.close();

        }
        catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return users;
    }

@Override
    public String toString() {
        return username;
    }

}
