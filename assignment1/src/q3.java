import javax.lang.model.type.NullType;
import  java.util.*;
public class q3 {
    public static void main(String arg[]) {
        int i = 0;
        int j = 0;
        int t = 0;
        Scanner Scan = new Scanner(System.in);
        t=Scan.nextInt();
        for(i=0;i<t;i++) {
            int ta=Scan.nextInt();
            int n=Scan.nextInt();
            int k=Scan.nextInt();
            TA[] TAs = new TA[ta];
            group group = new group(n);
            for(j=0;j<ta;j++){
                TAs[j] = new TA(Scan.next(), Scan.next());
            }

            for(j=0;j<n;j++) {
                group.insertStudent(Scan.next(), Scan.next());
            }
            group.sortStudents();
            group.assignTAs(TAs, k);
            group.printStudentsWithTA();
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
        fillIndex++;
    }

    void printStudents(){
        int i = 0;
        for(i=0; i<students.length;i++){
            students[i].printStudent();
        }
    }

    void printStudentsWithTA(){
        int i = 0;
        for(i=0; i<students.length ;i++){
            students[i].printStudentWithTA();
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

    void assignTAs(TA[] TAs,int k){
        int x=0;
        for(int i=0; i< students.length ;i++){
            if(i>0 && i%k==0 && x < TAs.length - 1 ){
                x++;
            }
            students[i].setTA(TAs[x]);
        }
    }
}

class student {
    private String rollNo;
    private String name;
    private TA TA;
    private void setData(String rollSet, String nameSet){
        rollNo=rollSet;
        name=nameSet;
    }
    String getRollNo(){
        return rollNo;
    }
    String getName(){
        return name;
    }
    void printStudent() {
        System.out.println(rollNo + " " + name);
    }
    void printStudentWithTA() {
        System.out.println(rollNo + " " + name + " "+ TA.getRollNo() + " " + TA.getName());
    }
    student(String rollNoIn, String nameIn){
        setData(rollNoIn,nameIn);
    }
    void setTA(TA TASet){
        TA=TASet;
    }
}

class TA {
    private String rollNo;
    private String name;
    private void setData(String rollSet, String nameSet){
        rollNo=rollSet;
        name=nameSet;
    }
    String getRollNo(){
        return rollNo;
    }
    String getName(){
        return name;
    }
    TA(String rollNoIn, String nameIn){
        setData(rollNoIn,nameIn);
    }
}