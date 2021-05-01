import java.util.Scanner;

public abstract class Human {

    private static int idsUsed = 0;
    private int id;
    private String name;
    private String surname;
    private int birthYear;
    private static double grantMark = 1.5;
    private static int grantMoney = 500;

    public Human(int creatingStyle)
    {
        if(creatingStyle == 1)
        {
            int year = 0;
            int integerWasUsed = 0;

            Scanner names = new Scanner(System.in);
            System.out.println("Print his name:");
            String name = names.nextLine();

            Scanner surNames = new Scanner(System.in);
            System.out.println("Print his surname:");
            String surname = surNames.nextLine();

            while (integerWasUsed == 0)
            {
                try
                {
                    Scanner years = new Scanner(System.in);
                    System.out.println("Print his year of birth:");
                    year = years.nextInt();
                    integerWasUsed = 1;
                }
                catch (Exception e)
                {
                    System.err.println("Error! Use the number!");
                }
            }

            this.id = setID();
            this.name = name;
            this.surname = surname;
            this.birthYear = year;
        }
        else
        {
            this.id = setID();
            this.name = " ";
            this.surname = " ";
            this.birthYear = 0;
        }
    }

    public static double getGrantMark() {
        return grantMark;
    }

    public void setGrantMoney(int grantMoney) {
        Human.grantMoney = grantMoney;
    }

    public static int getGrantMoney()
    {
        return grantMoney;
    }

    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    public int getBirthYear()
    {
        return birthYear;
    }

    public int getID(){
        return id;
    }

    private void setGrantMark(double mark)
    {
        if(mark < 4 && mark > 0)
            grantMark = mark;
        else
            System.out.println("Sorry, that mark is out of range 1.0 - 4.0. Please set another granted average Mark.");
    }

    private int setID()
    {
        idsUsed += 1;
        int idNum = idsUsed;
        return idNum;
    }

    public void setIDNum(int idNum)
    {
        this.id = idNum;
    }

    public void setName(String nameToChange)
    {
        this.name = nameToChange;
    }

    public void setSurName(String surnameToChange)
    {
        this.surname = surnameToChange;
    }

    public void setBirthYear(int yearToChange)
    {
        this.birthYear = yearToChange;
    }

    public void idUpdater(int number)
    {
        if(idsUsed < number)
            idsUsed = number;
    }

    public double getGrankMark()
    {
        return grantMark;
    }

}
