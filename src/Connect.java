import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Connect {

    private static Connection connStud;
    private static Connection connTeach;

    public static boolean connectStud() {

        connStud = null;

        try {
            connStud = DriverManager.getConnection("jdbc:sqlite:students.db");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public static boolean connectTeach() {

        connTeach = null;

        try {
            connTeach = DriverManager.getConnection("jdbc:sqlite:teachers.db");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public void disconnectStud() throws SQLException {

        if (connStud != null) {
            try {
                connStud.close();
            }
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void disconnectTeach() throws SQLException {

        if (connTeach != null) {
            try {
                connTeach.close();
            }
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static boolean createTableStud()
    {
        if (connStud == null)
            return false;
        String sql = "CREATE TABLE IF NOT EXISTS students(" + "id integer PRIMARY KEY," + "name varchar(255) NOT NULL,"+ "surname varchar(255) NOT NULL,"
                + "birthYear integer, " + "marks varchar(255), " + "avgmark real," +  "teachersids varchar(255)" + ");";
        try{
            Statement stmt = connStud.createStatement();
            stmt.execute(sql);
            return true;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean createTableTeach()
    {
        if (connTeach ==null)
            return false;
        String sql = "CREATE TABLE IF NOT EXISTS teachers(" + "id integer PRIMARY KEY," + "name varchar(255) NOT NULL,"+ "surname varchar(255) NOT NULL,"
                + "birthYear integer, " + "salary integer," +  "studentsids varchar(255)" + ");";
        try{
            Statement stmt = connTeach.createStatement();
            stmt.execute(sql);
            return true;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void insertRecordStud(int id, String name, String surname, int birthYear, List<Integer> marks, double avgMark, List<Integer> teachersids) {

        String sql = "INSERT INTO Students(id,name,surname,birthYear,marks,avgmark,teachersids) VALUES(?,?,?,?,?,?,?)";
        String stringedMarks = "";
        String stringedIDs = "";

        for(int mark : marks)
        {
            stringedMarks = stringedMarks + mark + " ";
        }

        for(int teacherID : teachersids)
        {
            stringedIDs = stringedIDs + teacherID + " ";
        }

        try {
            PreparedStatement pstmt = connStud.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, surname);
            pstmt.setInt(4, birthYear);
            pstmt.setString(5, stringedMarks);
            pstmt.setDouble(6, avgMark);
            pstmt.setString(7, stringedIDs);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertRecordTeach(int id, String name, String surname, int birthYear, int salary, List<Integer> studentsids) {

        String sql = "INSERT INTO Teachers(id,name,surname,birthYear,salary,studentsids) VALUES(?,?,?,?,?,?)";
        String stringedIDs = "";

        for(int studentID : studentsids)
        {
            stringedIDs = stringedIDs + studentID + " ";
        }

        try {
            PreparedStatement pstmt = connTeach.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, surname);
            pstmt.setInt(4, birthYear);
            pstmt.setInt(5, salary);
            pstmt.setString(6, stringedIDs);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Student> selectAllStud(){
        String sql = "SELECT id,name,surname,birthYear,marks,avgmark,teachersids FROM Students";
        List<Student> studentList = new ArrayList<>();

        try {
            Statement stmt  = connStud.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                Student studento = new Student(0);
                int idNum = rs.getInt("id");
                studento.setIDNum(idNum);
                studento.setName(rs.getString("name"));
                studento.setSurName(rs.getString("surname"));
                studento.setBirthYear(rs.getInt("birthYear"));

                List<Integer> marks1 = new ArrayList<>();
                String[] marko;
                marko = rs.getString("marks").split(" ");
                for(String mark : marko)
                {
                    if(!(mark.equals("")))
                        marks1.add(Integer.valueOf(mark));
                }
                studento.setMarks(marks1);

                studento.setAvgMark(rs.getDouble("avgmark"));

                List<Integer> ids1 = new ArrayList<>();
                String[] ids;
                ids = rs.getString("teachersids").split(" ");
                for(String id : ids)
                {
                    if(!(id.equals("")))
                        ids1.add(Integer.valueOf(id));
                }
                studento.setTeachersIDs(ids1);

                studentList.add(studento);
                studento.idUpdater(idNum);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return studentList;
    }

    public static List<Teacher> selectAllTeach(){
        String sql = "SELECT id,name,surname,birthYear,salary,studentsids FROM Teachers";
        List<Teacher> teacherList = new ArrayList<>();

        try {
            Statement stmt  = connTeach.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                Teacher teachero = new Teacher(0);
                int idNum = rs.getInt("id");
                teachero.setIDNum(idNum);
                teachero.setName(rs.getString("name"));
                teachero.setSurName(rs.getString("surname"));
                teachero.setBirthYear(rs.getInt("birthYear"));

                teachero.setSalary(rs.getInt("salary"));

                List<Integer> ids1 = new ArrayList<>();
                String[] ids;
                ids = rs.getString("studentsids").split(" ");
                for(String id : ids)
                {
                    if(!(id.equals("")))
                        ids1.add(Integer.valueOf(id));
                }
                teachero.setStudentsIDs(ids1);

                teacherList.add(teachero);
                teachero.idUpdater(idNum);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return teacherList;
    }

    public static void removeStud()
    {
        String sql = "DELETE FROM students";

        try {
            PreparedStatement pstmt = connStud.prepareStatement(sql);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeTeach()
    {
        String sql = "DELETE FROM teachers";

        try {
            PreparedStatement pstmt = connTeach.prepareStatement(sql);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
