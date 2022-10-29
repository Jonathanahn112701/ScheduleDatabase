
import java.sql.Timestamp;
import java.util.Calendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jonathan Ahn
 */
public class ScheduleEntry {
    private String Semester;
    private String CourseCode;
    private String StudentID;
    private String Status;
    java.sql.Timestamp Timestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());


    public ScheduleEntry(String Semester, String CourseCode, String StudentID, String Status) {
        this.Semester = Semester;
        this.CourseCode = CourseCode;
        this.StudentID = StudentID;
        this.Status = Status;
    }

    public String getSemester() {
        return Semester;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public String getStudentID() {
        return StudentID;
    }

    public String getStatus() {
        return Status;
    }

    public Timestamp getTimestamp() {
        return Timestamp;
    }
    
}
