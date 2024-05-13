package View;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.stream.events.StartDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import Model.*;
import org.w3c.dom.Text;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class NimbusSwingApp implements ActionListener {
    private User user;
    private JFrame frame;

    private JPanel mainPanel;


    private TextField userInfo;

    private ArrayList<Object> items;

    private   JComboBox<Course> courses;
    private JComboBox<User>  teachers;
    //common buttons
    private String[] buttonNames={"Login","Register","Logout","Search"};
    private String[] textFields={"Username","Password","Confirm Password","Search"};

    private String[] adminButtons={"Add Course","Delete Course","Add an instructor",""};
    private String[] adminTextFields={"Course Name","Course Description"};
    public NimbusSwingApp(){

        try {
            // Set Nimbus look and feel
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to the default look and feel
            e.printStackTrace();
        }

        // Create frame
        initialize();

    }

    private void initialize() {

        frame = new JFrame("Nimbus Swing App");
        frame.setTitle("Course App");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel = new JPanel((new GridLayout(0, 2, 10, 10)));
        frame.setLocationRelativeTo(null);

        frame.setLocationRelativeTo(null); // Center the window
        frame.setSize(700, 700); // Increased frame size
        //frame.setLayout(new GridLayout(1,1));
        frame.setLocationRelativeTo(null); // Center the window

        userInfo = new TextField();
        // instantiate panels
        items=new ArrayList<>();
       mainPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));



        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton(buttonNames[i]);
            mainPanel.add(button);
            button.addActionListener(this);
            items.add(button);
        }
        for (int i = 0; i < textFields.length; i++) {

            if (textFields[i].equalsIgnoreCase("Password") || textFields[i].equalsIgnoreCase("Confirm Password")) {
                JPasswordField passwordField = new JPasswordField();
                passwordField.getDocument().putProperty("name", textFields[i]);
                mainPanel.add(new JLabel(textFields[i]));
                mainPanel.add(passwordField);
                items.add(passwordField);

            } else {
                JTextField textField = new JTextField();
                textField.getDocument().putProperty("name", textFields[i]);
                mainPanel.add(new JLabel(textFields[i]));
                mainPanel.add(textField);
                items.add(textField);
            }


        }
        mainPanel.add(userInfo);
        mainPanel.add(new TextField(""));
        frame.add(mainPanel);
        frame.setVisible(true);



    }
    public String getUserName()
    {
        for (Object o : items) {
            if (o instanceof JTextField) {
                JTextField usernameField = (JTextField) o;
                if (usernameField.getDocument().getProperty("name").equals("Username")) {
                    return usernameField.getText();
                }

                }
            }
        return null;
    }

    public String[] getPassword()
    {
        String [] passwords=new String[2];
        for (Object o : items) {
            if (o instanceof JPasswordField) {
                JPasswordField passwordField = (JPasswordField) o;
                if (passwordField.getDocument().getProperty("name").equals("Password")) {
                    passwords[0]=passwordField.getText();
                }
                if (passwordField.getDocument().getProperty("name").equals("Confirm Password"))
                {
                    passwords[1]=passwordField.getText();
                }
            }

        }
        return passwords;
    }

    public void resetPassword() {
        for (Object o : items) {
            if (o instanceof JPasswordField) {
                JPasswordField passwordField = (JPasswordField) o;
                if (passwordField.getDocument().getProperty("name").equals("Password")) {
                    passwordField.setText("");
                }
            }
        }
    }

    public void resetUserName()
    {
        for (Object o : items) {
            if (o instanceof JTextField) {
                JTextField usernameField = (JTextField) o;
                if (usernameField.getDocument().getProperty("name").equals("Username")) {
                    usernameField.setText("");
                }
            }
        }
    }
    public void createAdminPage()
    {

        for (int i=0;i<adminTextFields.length;i++)
        {

                JTextField textField = new JTextField();
                textField.getDocument().putProperty("name", adminTextFields[i]);
                mainPanel.add(new JLabel(adminTextFields[i]));
                mainPanel.add(textField);
                items.add(textField);

        }


        for (int i = 0; i < adminButtons.length; i++) {
            JButton button = new JButton(adminButtons[i]);
            mainPanel.add(button);
            button.addActionListener(this);
            items.add(button);
        }

        courses=new JComboBox();
        teachers=new JComboBox();


        ArrayList<Course> listCourses = Course.getAllCourses();
        ArrayList<User> allInstructor=User.getAllTeachers();
        for (Course c : listCourses) {
                courses.addItem(c);
            }
        for (User c : allInstructor) {
            teachers.addItem(c);
        }
        courses.addActionListener(this);
        teachers.addActionListener(this);
        mainPanel.add(new JLabel("Courses"));
        mainPanel.add(courses);
        mainPanel.add(new JLabel("Instructors"));
        mainPanel.add(teachers);
        frame.revalidate();
        frame.repaint();
    }
    public String[] courseInfo()
    {

        String[] info=new String[2];

        for (Object o : items) {
            if (o instanceof JTextField) {
                JTextField textField = (JTextField) o;
                if (textField.getDocument().getProperty("name").equals("Course Name")) {
                    info[0]=textField.getText();
                }
                if (textField.getDocument().getProperty("name").equals("Course Description"))
                {
                    info[1]=textField.getText();
                }
            }

        }
        return info;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

//        if (e.getSource().equals(courses))
//        {
//            Course course=(Course) courses.getSelectedItem();
//            System.out.println(course);
//        }
//        if(e.getSource().equals(teachers))
//        {
//            User teacher=(User) teachers.getSelectedItem();
//            System.out.println(teacher.getId());
//        }
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            String username = getUserName();
            String password = getPassword()[0];
            String confirmedPassword = getPassword()[1];

            //if login button is clicked
            if (button.getText().equalsIgnoreCase("Login")) {


                user = User.Login(username, password);
                if (user != null) {

                    userInfo.setText("Welcome " + user.getUsername() + " as a " + user.getRole());
                    if (user.getRole().equals("admin"))
                    {
                        createAdminPage();

                    }
                }
                else
                {
                    userInfo.setText("Login failed");
                }
            }
            if (button.getText().equalsIgnoreCase("Logout"))
            {

                user=null;
                userInfo.setText("");
                resetPassword();
                resetUserName();
            }

            if (button.getText().equalsIgnoreCase("Register"))
            {
                if (password.equals(confirmedPassword))
                {
                    user=User.Register(username,password,"student");
                    if (user!=null)
                    {
                        userInfo.setText("Welcome " + user.getUsername() + " as a " + user.getRole());
                    }
                    else
                    {
                        userInfo.setText("Registration failed");
                    }
                }
                else
                {
                    userInfo.setText("Passwords do not match");
                }


            }
            //add a course to the database and update the panel
            if (button.getText().equalsIgnoreCase("Add Course"))
            {
                String[] courseInfo=courseInfo();
                User teacher=(User) teachers.getSelectedItem();

                Course addedCourse=Course.addCourse(courseInfo[0],courseInfo[1],teacher);

                if (addedCourse!=null)
                {
                    userInfo.setText("Course added successfully");
                    courses.addItem(addedCourse);
                }
                else
                {
                    userInfo.setText("Course addition failed");
                }


            }
            if (button.getText().equalsIgnoreCase("Delete Course"))
            {
                Course deletedCourse=(Course) courses.getSelectedItem();
                boolean isDeleted=Course.deleteCourse(deletedCourse);
                if (isDeleted)
                {
                    userInfo.setText("Course deleted successfully");
                    courses.removeItem(deletedCourse);
                }
                else
                {
                    userInfo.setText("Course deletion failed");
                }
            }
            //add an instructor
            if (button.getText().equalsIgnoreCase("Add an instructor"))
            {
                System.out.println("Add an instructor");
                System.out.println(username);
                System.out.println(password);
                System.out.println(confirmedPassword);
                if (password.equals(confirmedPassword))
                {
                    user=User.Register(username,password,"instructor");
                    if (user!=null)
                    {
                        userInfo.setText("Welcome " + user.getUsername() + " as a " + user.getRole());
                        teachers.addItem(user);
                    }
                    else
                    {
                        userInfo.setText("Registration failed");
                    }
                }
                else
                {
                    userInfo.setText("Passwords do not match");
                }

            }





        }

//        if (e.getSource() == loginButton) {
//            String username = "admin";
//            String password = "1234";
//            //String username=usernameField.getText();
//            //String password=new String(passwordField.getPassword());
//
//            user = User.Login(username, password);
//
//            if (user != null) {
//                userInfo.setText("Welcome " + user.getUsername() + "as a " + user.getRole());
//                loginPanel.add(userInfo);
//                //admin can  see all the courses
//                if (user.getRole().equalsIgnoreCase("admin")) {
//
//                    ArrayList<Course> courses = Course.getAllCourses();
//                    courseListModel = new DefaultListModel<>();
//                    for (Course c : courses) {
//                        courseListModel.addElement(c);
//                        System.out.println(c);
//                    }
//
//                    courseList = new JList<>(courseListModel);
//                    courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//                    courseList.setLayoutOrientation(JList.VERTICAL);
//                    courseList.setVisibleRowCount(-1);
//                    courseList.setFixedCellHeight(30);
//                    courseList.add(coursePanel);
//
//
//                    frame.revalidate();
//                    frame.repaint();
//                }
//
//            } else if (e.getSource() == searchButton) {
//                coursePanel.removeAll();
//                String courseToSearch = searchField.getText();
//                ArrayList<Course> results = Course.getSearchResults(courseToSearch);
//                courseSearchListModel = new DefaultListModel<>();
//                for (Course c : results) {
//                    courseSearchListModel.addElement(c);
//                }
//                courseSearchList = new JList<>(courseSearchListModel);
//                courseSearchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//                coursePanel.add(courseSearchList);
//
//
//                //if user logging as
//                if (user != null) {
//                    if (user.getRole().equalsIgnoreCase("student")) {
//                        coursePanel.add(enrollCourseButton);
//                        coursePanel.add(dropCourseButton);
//                    }
//
//                }
//                courseSearchList.addListSelectionListener(new ListSelectionListener() {
//                    @Override
//                    public void valueChanged(ListSelectionEvent e) {
//
//                        if (!e.getValueIsAdjusting()) { // Ensure event is not fired twice
//                            Course selectedCourse = courseSearchList.getSelectedValue();
//                            if (selectedCourse != null) {
//                                String text = "Instructor:" + selectedCourse.getTutorName() + "\n";
//                                for (Content c : selectedCourse.getContents()) {
//                                    text += c.getTitle() + "\n";
//
//
//                                }
//
//                            }
//                        }
//                    }
//                });
//                frame.revalidate();
//                frame.repaint();
//
//            } else if (e.getSource() == registerButton) {
//                username = usernameField.getText();
//                password = new String(passwordField.getPassword());
//                String confirmedPassword = new String(confirmPasswordField.getPassword());
//                user = User.Register(username, password);
//
//                if (user != null) {
//                    userInfo.setText("Welcome " + user.getUsername() + "as a " + user.getRole());
//
//                } else {
//                    TextField info = new TextField("Registration failed");
//                    coursePanel.add(info);
//                }
//
//                frame.revalidate();
//                frame.repaint();
//                System.out.println("register button clicked");
//            } else if (e.getSource() == logoutButton) {
//
//                usernameField.setText("");
//                passwordField.setText("");
//                userInfo.setText("");
//                user = null;
//                frame.revalidate();
//                frame.repaint();
//            } else if (e.getSource() == courseList) {
//                Course selectedCourse = courseList.getSelectedValue();
//                System.out.println(selectedCourse);
//            }
//        }
    }



}