/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author Jonathan Ahn
 */
public class CourseQueries {
    private static Connection connection;
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllCourses;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getCourseSeats;
    private static PreparedStatement dropCourse;
    private static ResultSet resultSet1;
    private static ResultSet resultSet2;
    private static ResultSet resultSet3;
    private static ResultSet resultSet4;
    private static StudentEntry newStudent;
    private static int courseSeats;
    
    public static void addCourse(CourseEntry course){
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.course (Semester, CourseCode, Description, Seats) values (?,?,?,?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getDescription());
            addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    public static ArrayList<CourseEntry> getAllCourses(String semester){
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
        try{
            getAllCourses = connection.prepareStatement("select Semester, CourseCode, Description, Seats from app.course where semester = ?");
            getAllCourses.setString(1, semester);
            resultSet1 = getAllCourses.executeQuery();
            while(resultSet1.next()){
                CourseEntry entry = new CourseEntry(resultSet1.getString(1), resultSet1.getString(2), resultSet1.getString(3), resultSet1.getInt(4));
                courses.add(entry);
            }
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            
        }
        return courses;
    }

    public static int getCourseSeats(String semester, String CourseCode){
        connection = DBConnection.getConnection();
        ArrayList<String> seats = new ArrayList<String>();
        try{
            
           getCourseSeats = connection.prepareStatement("select seats from app.course where semester = ? and CourseCode = ?");
           getCourseSeats.setString(1, semester);
           getCourseSeats.setString(2, CourseCode);
           resultSet2 = getCourseSeats.executeQuery();
           while (resultSet2.next()){
               seats.add(resultSet2.getString(1));
           }
           
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return Integer.parseInt(seats.get(0));
    }
    public static ArrayList<String> getAllCourseCodes(String semester){
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<String>();
        try
        {
            getAllCourseCodes = connection.prepareStatement("select * from app.course where semester = ?");
            getAllCourseCodes.setString(1, semester);
            resultSet3 = getAllCourseCodes.executeQuery();
            
            while(resultSet3.next())
            {
                courseCodes.add(resultSet3.getString(2));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseCodes;
    }
    
    public static void dropCourse(String semester, String CourseCode){
        connection = DBConnection.getConnection();
        try
        {
            dropCourse = connection.prepareStatement("delete from app.course where semester = ? and CourseCode = ?");
            dropCourse.setString(1, semester);
            dropCourse.setString(2, CourseCode);
            dropCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    

}
    

