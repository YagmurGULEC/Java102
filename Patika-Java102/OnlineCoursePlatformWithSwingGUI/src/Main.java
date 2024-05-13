import java.security.*;
import Database.DB;
import java.sql.*;
import Model.User;
import Model.Course;
import View.NimbusSwingApp;

import javax.swing.*;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NimbusSwingApp();
            }
        });


    }
}
