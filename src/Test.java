import java.util.*;

public class Test {

    public static int integerGetter()
    {
        boolean continueLoop = true;
        int gotInt = 0;
        while(continueLoop)
        {
            try
            {
                Scanner intToGet = new Scanner(System.in);
                System.out.print("Type here: ");
                gotInt = intToGet.nextInt();
                continueLoop = false;
            }
            catch(Exception e)
            {
                System.err.println("Your input was not a number.");
            }
        }
        return gotInt;

    }


    public static boolean containsStudent(List<Student> students, int id)
    {
        for(Student student : students)
        {
            if (student.getID() == id)
                return true;
        }
        return false;
    }

    public static boolean containsTeacher(List<Teacher> teachers, int id)
    {
        for(Teacher teacher : teachers)
        {
            if (teacher.getID() == id)
                return true;
        }
        return false;
    }

    public static List<Student> teachersStudents(List<Student> students, int id) // returns list of students assigned to the teacher
    {
        List<Student> teacherStudents = new ArrayList<>();

        for(Student student : students)
        {
            if(student.getTeachersIDs().contains(id))
            {
                teacherStudents.add(student);
            }
        }

        return teacherStudents;
    }

    public static int getTotalTeachersSalary(List<Student> students, int salary)
    {
        int totalSalary = salary;

        students.sort(Comparator.comparing(Student::getAvgMark));
        Collections.reverse(students);

        for(Student student : students)
        {
            if(student.getAvgMark() <= Student.getGrantMark())
            {
                totalSalary += Student.getGrantMoney();
            }
            else
                break;
        }
        return totalSalary;
    }

    public static void printTeachers(Teacher teacher, int money)
    {
        System.out.print("It's a teacher. " + "ID: " + teacher.getID() + ", Name: " + teacher.getName() + ", Surname: " + teacher.getSurname());
        System.out.println(", born in: " + teacher.getBirthYear() + " Salary: " + money);
    }

    public static void main(String[] args)
    {
        List<Teacher> teachers = new ArrayList<>();
        List<Student> students = new ArrayList<>();


        System.out.println("Welcome to the SUS university database project! \n This is a console mode ver. 0.0.1.");

        boolean playProg = true;

        while(playProg)
        {
            Scanner number = new Scanner(System.in);
            System.out.println("Choose what do you want to do: \n 1 - Add student. \n 2 - Add teacher. \n 3 - Give student a mark.");
            System.out.println(" 4 - Delete student. \n 5 - Delete teacher. \n 6 - Print all teachers assigned to the student. \n 7 - Assign a teacher to the student.");
            System.out.println(" 8 - Get person using ID. \n 9 - Sort teachers by their students count. \n 10 - Find every student by their teacher's ID.");
            System.out.println(" 11 - Print every student and teacher \n 12 - Find out how much does university spend right now.");
            System.out.println(" 13 - Create SQL databases with teachers and students. \n 14 - Save databases. \n 15 - Load databases. \n 16 - Quit.");
            String decision = number.nextLine();

            switch(decision) {

                case "1": // Adds student.
                {
                    if(teachers.size() == 0)
                    {
                        System.err.println("You can't add student before adding 1st teacher.");
                        break;
                    }

                    Student alex = new Student(1);

                    boolean allAdded = false;
                    while(!allAdded)
                    {
                        try
                        {
                            Scanner teacherID = new Scanner(System.in);
                            System.out.println("Tap teachers ID ");
                            int teacherPotentialID = teacherID.nextInt();
                            for(Teacher teacher : teachers)
                            {
                                if (teacher.getID() == teacherPotentialID) {
                                    alex.setTeachersIDs(teacherPotentialID);
                                    teacher.setStudentsIDs(alex.getID());
                                    System.out.println("Success!");
                                }
                            }

                            Scanner digit = new Scanner(System.in);
                            System.out.println("If you don't want to add teachers anymore, write 0. Any other digit/letter will continue the program.");

                            if(digit.nextLine().equals("0"))
                                allAdded = true;
                        }

                        catch(Exception e)
                        {
                            System.out.println("Oops! Something went wrong! Choose this operation again and use digits, please.");
                        }
                    }

                    students.add(alex);
                    break;
                }

                case "2":
                {
                    teachers.add(new Teacher(1));
                    break;
                }

                case "3": // add mark to the student
                {
                    try {
                        Scanner idNeeded = new Scanner(System.in);
                        System.out.println("Choose students ID:");
                        int id = idNeeded.nextInt();

                        Scanner markToSet = new Scanner(System.in);
                        System.out.println("Write mark:");
                        int mark = markToSet.nextInt();
                        boolean markWasSet = false;

                        for (Student student : students) {
                            if (student.getID() == id) {
                                student.addMark(mark);
                                markWasSet = true;
                                break;
                            }
                        }

                        if (!markWasSet) {
                            System.out.println("Sorry, ID was not found. Try again!");
                        }

                    } catch (Exception e) {
                        System.err.println("Oops! Something went wrong! Try again and use digits, please.");
                    }
                    break;
                }

                case "4": // delete student
                {
                    try
                    {
                        Scanner idNeeded = new Scanner(System.in);
                        System.out.println("Choose students ID. Please set valid ID number or it will not delete anyone.");
                        int id = idNeeded.nextInt();

                        students.removeIf(student -> student.getID() == id);

                        for(Teacher teacher : teachers)
                        {
                            teacher.deleteStudentsIDs(id);
                        }
                    }

                    catch(Exception e)
                    {
                        System.out.println("Oops! Something went wrong! Try again and use digits, please.");
                    }
                    break;
                }

                case "5": // delete teacher
                {
                    try
                    {
                        Scanner idNeeded = new Scanner(System.in);
                        System.out.println("Choose teachers ID. Please set valid ID number or it will not delete anyone.");
                        int id = idNeeded.nextInt();

                        teachers.removeIf(teacher -> teacher.getID() == id);

                        for(Student student : students)
                        {
                            student.deleteTeachersIDs(id);
                        }
                    }

                    catch(Exception e)
                    {
                        System.out.println("Oops! Something went wrong! Try again and use digits, please.");
                    }
                    break;
                }

                case "6":
                {
                    Scanner idNeeded = new Scanner(System.in);
                    System.out.println("Choose student's ID.");
                    int studentId = idNeeded.nextInt();
                    List<Integer> teachersList = new ArrayList<>();

                    for(Student student : students)
                    {
                        if(student.getID() == studentId)
                        {
                            teachersList = student.getTeachersIDs();
                            break;
                        }
                    }

                    for(int id : teachersList)
                    {
                        for(Teacher teacher : teachers)
                        {
                            if(teacher.getID() == id)
                                System.out.println("ID: " + teacher.getID() + ", Name: " + teacher.getName() + ", Surname: " + teacher.getSurname() + ", born in: " + teacher.getBirthYear());
                        }
                    }

                    break;
                }

                case "7":
                {
                    System.out.println("Type teacher's ID.");
                    int teacherId = integerGetter();
                    System.out.println("Now type student's ID.");
                    int studentId = integerGetter();

                    if (containsStudent(students, studentId) && containsTeacher(teachers, teacherId))
                    {
                        for(Teacher teacher : teachers)
                        {
                            if (teacher.getID() == teacherId)
                                teacher.setStudentsIDs(studentId);
                            break;
                        }
                        for(Student student : students)
                        {
                            if (student.getID() == studentId)
                                student.setTeachersIDs(teacherId);
                            break;
                        }
                    }
                    break;
                }

                case "8":
                {
                    System.out.println("Type person's ID.");
                    int neededId = integerGetter();
                    for(Teacher teacher : teachers)
                    {
                        if (teacher.getID() == neededId) {

                            List<Student> teacherStudents = teachersStudents(students, neededId);
                            int money = getTotalTeachersSalary(teacherStudents, teacher.getSalary());

                            printTeachers(teacher, money);
                            break;
                        }
                    }
                    for(Student student : students)
                    {
                        if (student.getID() == neededId) {
                            System.out.print("It's a student." + "ID: " + student.getID() + ", Name: " + student.getName() + ", Surname: " + student.getSurname());
                            System.out.println(", born in: " + student.getBirthYear());
                            break;
                        }
                    }
                    break;
                }

                case "9":
                {
                    List<Teacher> sortedTeachers = new ArrayList<>(teachers);
                    sortedTeachers.sort(Comparator.comparing(Teacher::getStudensCount));
                    for(Teacher teacher : sortedTeachers)
                    {
                        List<Student> teacherStudents = teachersStudents(students, teacher.getID());
                        int money = getTotalTeachersSalary(teacherStudents, teacher.getSalary());

                        printTeachers(teacher, money);
                    }
                    break;
                }

                case "10":
                {
                    System.out.println("Type teacher's ID.");
                    int neededId = integerGetter();

                    for(Student student : students)
                    {
                        if(student.getTeachersIDs().contains(neededId))
                        {
                            System.out.print("It's a student." + "ID: " + student.getID() + ", Name: " + student.getName() + ", Surname: " + student.getSurname());
                            System.out.println(", born in: " + student.getBirthYear());
                        }
                    }
                    break;
                }

                case "11":
                {
                    System.out.println("STUDENTS:");
                    for(Student student : students)
                    {
                        System.out.print("It's a student." + "ID: " + student.getID() + ", Name: " + student.getName() + ", Surname: " + student.getSurname());
                        System.out.println(", born in: " + student.getBirthYear());
                    }
                    System.out.println("\nTEACHERS:");
                    for(Teacher teacher : teachers)
                    {
                        List<Student> teacherStudents = teachersStudents(students, teacher.getID());
                        int money = getTotalTeachersSalary(teacherStudents, teacher.getSalary());

                        printTeachers(teacher, money);
                    }
                    break;
                }

                case "12":
                {
                    int money = 0;
                    for(Teacher teacher : teachers)
                    {
                        List<Student> teacherStudents = teachersStudents(students, teacher.getID());
                        money += getTotalTeachersSalary(teacherStudents, teacher.getSalary());

                    }
                    for (Student student : students)
                    {
                        if (student.getAvgMark() < student.getGrankMark())
                            money += Human.getGrantMoney();
                    }

                    System.out.println("Total spent per month: " + money);
                    break;
                }

                case "13":
                {
                    System.out.println("Connecting students base:" + Connect.connectStud());
                    System.out.println("Connecting teachers base:" + Connect.connectTeach());
                    System.out.println("Creating students base:" + Connect.createTableStud());
                    System.out.println("Creating teachers base:" + Connect.createTableTeach());

                    break;
                }

                case "14":
                {
                    Connect.removeTeach();
                    for(Teacher teacher : teachers)
                    {
                        Connect.insertRecordTeach(teacher.getID(), teacher.getName(), teacher.getSurname(), teacher.getBirthYear(),
                                teacher.getSalary(), teacher.getStudentsIDs());
                    }

                    Connect.removeStud();
                    for(Student student : students)
                    {
                        Connect.insertRecordStud(student.getID(), student.getName(), student.getSurname(), student.getBirthYear(),
                               student.getMarks(), student.getAvgMark(), student.getTeachersIDs());
                    }
                    break;
                }

                case "15":
                {
                    List<Student> studentovyList = Connect.selectAllStud();
                    List<Teacher> teacherovyList = Connect.selectAllTeach();
                    if(studentovyList.size() != 0 && teacherovyList.size() != 0)
                    {
                        if(teachers.size() != 0)
                        {
                            System.out.println("Your old lists were deleted. Lists from the database were uploaded.");
                        }
                        teachers = teacherovyList;
                        students = studentovyList;
                    }
                    break;
                }

                case "16": {
                    playProg = false;
                    System.out.println("Thank you for using our program. \nMolty.");
                    break;
                }

                default: {
                    System.out.println("Command not found. Please use digits from 1 to 12.");
                }
            }
        }
    }
}
