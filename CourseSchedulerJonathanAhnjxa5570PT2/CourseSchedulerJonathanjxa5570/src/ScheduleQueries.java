
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jonathan Ahn
 */
public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getScheduledStudentsByCourse;
    private static PreparedStatement getWaitlistedStudentsByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static ResultSet resultSet1;
    private static ResultSet resultSet2;
    private static ResultSet resultSet3;
    private static ResultSet resultSet4;
    private static ResultSet resultSet5;
    private static int studentcount;

    
    
    public static void addScheduleEntry(ScheduleEntry schedule){
        connection = DBConnection.getConnection();
        try
        {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (Semester, StudentID, CourseCode, status, TimeStamp) values (?,?,?,?,?)");
            addScheduleEntry.setString(1, schedule.getSemester());
            addScheduleEntry.setString(2, schedule.getStudentID());
            addScheduleEntry.setString(3, schedule.getCourseCode());
            addScheduleEntry.setString(4, schedule.getStatus());
            addScheduleEntry.setTimestamp(5, schedule.getTimestamp());
            addScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String StudentID){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedules = new ArrayList<ScheduleEntry>();

        try{
            getScheduleByStudent = connection.prepareStatement("select * from app.schedule where semester = ? and StudentID = ?");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, StudentID);
            resultSet1 = getScheduleByStudent.executeQuery();
            while(resultSet1.next()){
                ScheduleEntry entry = new ScheduleEntry(resultSet1.getString(1), resultSet1.getString(3), resultSet1.getString(2), resultSet1.getString(4));
                schedules.add(entry);
            }
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            
        }
        return schedules;
    }
    
    public static int getScheduledStudentCount(String currentsemester, String courseCode){
        connection = DBConnection.getConnection();
        try{
            getScheduledStudentCount = connection.prepareStatement("select count(StudentID) from app.schedule where semester = ? and courseCode = ? and Status = ?");
            getScheduledStudentCount.setString(1, currentsemester);
            getScheduledStudentCount.setString(2, courseCode);
            getScheduledStudentCount.setString(3, "S");
            resultSet2 = getScheduledStudentCount.executeQuery();
            while (resultSet2.next()){
                studentcount = resultSet2.getInt(1);
            }
            
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            
        }
        return studentcount;
    }
    
    
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedules = new ArrayList<ScheduleEntry>();
        try{
            getScheduledStudentsByCourse = connection.prepareStatement("select * from app.schedule where semester = ? and courseCode = ? order by TIMESTAMP asc");
            getScheduledStudentsByCourse.setString(1, semester);
            getScheduledStudentsByCourse.setString(2, courseCode);
            resultSet3 = getScheduledStudentsByCourse.executeQuery();
            while (resultSet3.next()){
                String status = resultSet3.getString(4);
                if (status.equals("S")){
                    ScheduleEntry entry = new ScheduleEntry(resultSet3.getString(1), resultSet3.getString(3), resultSet3.getString(2), resultSet3.getString(4));
                    schedules.add(entry);                    
                }
            }
        }
        
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return schedules;
    }
        public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedules = new ArrayList<ScheduleEntry>();
        try{
            getWaitlistedStudentsByCourse = connection.prepareStatement("select * from app.schedule where semester = ? and courseCode = ? order by TIMESTAMP asc");
            getWaitlistedStudentsByCourse.setString(1, semester);
            getWaitlistedStudentsByCourse.setString(2, courseCode);
            resultSet4 = getWaitlistedStudentsByCourse.executeQuery();
            while (resultSet4.next()){
                String status = resultSet4.getString(4);
                if (status.equals("W")){
                    ScheduleEntry entry = new ScheduleEntry(resultSet4.getString(1), resultSet4.getString(3), resultSet4.getString(2), resultSet4.getString(4));
                    schedules.add(entry);                    
                }
            }
        }
        
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return schedules;
    }
    
    
     
    public static void dropScheduleByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        try{
            dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and courseCode = ?");
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);
            dropScheduleByCourse.executeUpdate();
        }
        
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry){
        connection = DBConnection.getConnection();
        String courseCode = entry.getCourseCode();
        String studentID = entry.getStudentID();
        String status = entry.getStatus();
        try{
            updateScheduleEntry = connection.prepareStatement("update app.schedule set semester = ?, studentID = ?, courseCode = ?, status = ? where semester = ? and studentID = ? and courseCode = ?");
            updateScheduleEntry.setString(1, entry.getSemester());
            updateScheduleEntry.setString(2, studentID);
            updateScheduleEntry.setString(3, courseCode);
            updateScheduleEntry.setString(4, status);
            updateScheduleEntry.setString(5, semester);
            updateScheduleEntry.setString(6, studentID);
            updateScheduleEntry.setString(7, courseCode);
            updateScheduleEntry.executeUpdate();                
        }

        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode){
        connection = DBConnection.getConnection();
        try{
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and studentID = ? and courseCode = ?");
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3, courseCode);
            dropStudentScheduleByCourse.executeUpdate();
        }
        
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
}
