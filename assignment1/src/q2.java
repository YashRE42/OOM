import  java.util.*;
public class q2_ {
    public static void main(String arg[]) {
        int i = 0;
        int j = 0;
        int t = 0;
        Scanner Scan = new Scanner(System.in);
        t=Scan.nextInt();
        for(i=0;i<t;i++) {
            int n=Scan.nextInt();
            group group = new group(n);
            for(j=0;j<n;j++) {
                group.insertStudent(Scan.next(), Scan.next());
            }
            group.sortStudents();
            group.printStudents();
            group = null;
        }
        Scan.close();
    }
}

class group {
    private student[] students;
    private Integer fillIndex;
    group(Integer numberOfStudents){
        students = new student[numberOfStudents];
        fillIndex=0;
    }

    void insertStudent(String rollNumber, String name){
        students[fillIndex]=new student(rollNumber, name);
        System.out.println(fillIndex + " " + students.size);
        fillIndex++;
    }

    void printStudents(){
        int i = 0;
        for(i=0; i<students.length;i++){
            students[i].printStudent();
        }

    }

    void sortStudents() {
        int i=0;
        int j=0;
        boolean swapped;
        student temp;
        //bubble sort algo
        for (i = 0; i < students.length - 1; i++) {
            for (j = 0; j < students.length-i-1; j++) {
                if (students[j].getRollNo().compareTo(students[j + 1].getRollNo())> 0) {
                    temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
    }
}

class student {
    private String rollNo;
    private String name;
    void printStudent() {
        System.out.println(rollNo + " " + name);
    }
    student(String rollNoIn, String nameIn){
        setData(rollNoIn,nameIn);
    }
    private void setData(String rollSet, String nameSet){
        rollNo=rollSet;
        name=nameSet;
    }
    String getRollNo(){
        return rollNo;
    }
}