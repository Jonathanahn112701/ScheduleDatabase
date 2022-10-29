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
public class StudentQueries {
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    private static ResultSet resultSet2;
    private static StudentEntry newStudent;
    
    public static void addStudent(StudentEntry student){
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (StudentID, FirstName, LastName) values (?,?,?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    public static ArrayList<StudentEntry> getAllStudents(){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();

        try{
            getAllStudents = connection.prepareStatement("select studentid,firstname,lastname from app.student");
            resultSet = getAllStudents.executeQuery();
            while(resultSet.next()){
                StudentEntry entry = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                students.add(entry);
            }
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            
        }
        return students;
    }
    public static StudentEntry getStudent(String studentID){
        connection = DBConnection.getConnection();
        ArrayList<String> studentlist = new ArrayList<String>();
        try{
            getStudent = connection.prepareStatement("select firstname,lastname from app.student where StudentID = ?");
            getStudent.setString(1, studentID);
            resultSet2 = getStudent.executeQuery();
            while (resultSet2.next()){
                studentlist.add(resultSet2.getString(1));
                studentlist.add(resultSet2.getString(2));
            }
        }
        
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        StudentEntry student = new StudentEntry(studentID, studentlist.get(0),studentlist.get(1));
        return student;
    }
    
    public static void dropStudent(String studentID){
        connection = DBConnection.getConnection();
        try
        {
            dropStudent = connection.prepareStatement("delete from app.student where studentID = ?");
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    

    
}
