import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student extends Human{

    private List<Integer> marks;
    private double avgMark;
    private List<Integer> teachersIDs;

    public Student(int creatingStyle) {
        super(creatingStyle);
        this.marks = new ArrayList<Integer>();
        this.avgMark = 4;
        this.teachersIDs = new ArrayList<Integer>();
    }

    public void addMark(int mark)
    {
        marks.add(mark);
        setAvgMark(marks);
        System.out.println(avgMark);
    }

    private void setAvgMark(List<Integer> marks)
    {
        double marksSum = 0;
        double length = 0;

        for(int mark : marks)
        {
            marksSum += mark;
            length += 1;
        }
        avgMark = marksSum / length;
    }

    public List<Integer> getMarks()
    {
        return marks;
    }

    public double getAvgMark()
    {
        return avgMark;
    }

    public void setTeachersIDs(int teachersID)
    {
        teachersIDs.add(teachersID);
    }

    public void deleteTeachersIDs(int teachersID)
    {
        teachersIDs.removeIf(id -> id.equals(teachersID));
    }

    public List<Integer> getTeachersIDs()
    {
        return teachersIDs;
    }

    public void setMarks(List<Integer> marks) {
        this.marks = marks;
    }

    public void setAvgMark(double avgMark) {
        this.avgMark = avgMark;
    }

    public void setTeachersIDs(List<Integer> teachersIDs)
    {
        this.teachersIDs = teachersIDs;
    }
}
