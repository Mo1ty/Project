import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Teacher extends Human {

    private int salary;
    private List<Integer> studentsIDs;


    public Teacher(int creatingStyle) {
        super(creatingStyle);
        this.salary = 15000;
        this.studentsIDs = new ArrayList<Integer>();
    }

    public void setSalary()
    {
        try
        {
            Scanner moneyPerMonth = new Scanner(System.in);
            System.out.println("Choose students ID. Please set valid ID number or it will not delete anyone.");
            this.salary = moneyPerMonth.nextInt();
        }

        catch(Exception e)
        {
            System.out.println("Oops! Something went wrong! Choose this operation again and use digits, please.");
        }
    }

    public int getSalary()
    {
        return salary;
    }

    public void setStudentsIDs(int studentsID)
    {
        studentsIDs.add(studentsID);
    }

    public void setStudentsIDs(List<Integer> studentsID)
    {
        this.studentsIDs = studentsID;
    }

    public List<Integer> getStudentsIDs()
    {
        return studentsIDs;
    }

    public int getStudensCount()
    {
        return studentsIDs.size();
    }

    public void deleteStudentsIDs(int studentsID)
    {
        studentsIDs.removeIf(id -> id.equals(studentsID));
    }

    public void setSalary(int salary)
    {
        this.salary = salary;
    }
}
