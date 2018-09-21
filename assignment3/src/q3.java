import java.util.Comparator;
import java.util.Scanner;
import java.util.Vector;

public class q3 {
    public static void main(String[] args) {
        Scanner Scan = new Scanner(System.in);
        int t = Scan.nextInt();
        for(int i=0;i<t;i++){
            int q= Scan.nextInt(), n= Scan.nextInt(), p= Scan.nextInt(), k= Scan.nextInt();
            Vector<question> questions = new Vector<>();
            for(int j=0;j<q;j++){
                questions.add(new question(Scan.nextInt(), Scan.next()));
            }
            Vector<student> students = new Vector<>();
            for(int j=0;j<n;j++){
                students.add(new student(Scan.next(),k));
            }
            Vector<TA> TAs = new Vector<>();
            for(int j=0;j<p;j++){
                String name = Scan.next();
                String questionImplementation = Scan.next();
                String affinityFunction = Scan.next();
                if(affinityFunction.compareTo("CL")==0)
                    TAs.add(new CL(name, questionImplementation));
                if(affinityFunction.compareTo("BR")==0)
                    TAs.add(new BR(name, questionImplementation));
                if(affinityFunction.compareTo("LU")==0)
                    TAs.add(new LU(name, questionImplementation, Scan.nextInt()));
            }
            int r=0;
            for(int j=0;j<n;j++){
                TAs.get(r).addStudent(students.get(j));
                r++;
                if(r%p==0)
                    r=0;
            }
            r=0;
            for(int j=0;j<n*k*TAs.size();j++){
                TAs.get(r).implmentQuestion(questions);
                r++;
                if(r%p==0) {
                    r=0;
                }
            }
            Vector<student> studentsAsEntering = new Vector<>();
            for(int j=0;j<n;j++) {
                studentsAsEntering.add(new student(Scan.next(),k));
            }

            for(student studentAtDoor: studentsAsEntering){
                for(student student :students){
                    if(student.getRollNumber().compareTo(studentAtDoor.getRollNumber())==0) {
                        System.out.println(studentAtDoor.getRollNumber());
                        for (question question : student.getQuestions()) {
                            System.out.println(question.getQuestion());
                        }
                    }
                }
            }
        }
    }
}

class question {
    private String question;
    private Integer ID;
    private boolean assigned;

    public question(Integer ID, String question) {
        this.question = question;
        this.ID = ID;
        this.assigned = false;
    }

    public Integer getID() {
        return ID;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public String getQuestion() {
        return question;
    }
}

class student {
    private String rollNumber;
    private Integer numberOfQuestionsToAssign;
    private Vector<question> questions = new Vector<question>();

    public student(String rollNumber,Integer numberOfQuestionsToAssign) {
        this.rollNumber = rollNumber;
        this.numberOfQuestionsToAssign = numberOfQuestionsToAssign;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public int getRollNumberAsInteger(){
        return Integer.parseInt(rollNumber.substring(3));
    }

    void assignQuestion(question question){
        this.questions.add(question);
        question.setAssigned(true);
    }

    public Vector<question> getQuestions() {
        return questions;
    }

    public boolean isAssigned() {
        if(this.questions.size()==numberOfQuestionsToAssign)
            return true;
        else
            return false;
    }
}

abstract class TA implements assigningTA {
    String name;
    String questionImplementation;
    Vector<student> students= new Vector<student>();

    void addStudent(student student){
        students.add(student);
    }

    TA(String name, String questionImplementation){
        this.name=name;
        this.questionImplementation=questionImplementation;
    }

    void implmentQuestion(Vector<question>questions){
        if(this.questionImplementation.compareTo("G")==0)
            this.getMinimumAffinityAndAssign(students,questions);
        if(this.questionImplementation.compareTo("QW")==0){
            questions.sort( new SortByID());
            int i=0;
            Vector<question> question =new Vector<question>();
            question.add(questions.get(i));
            while(!this.getMinimumAffinityAndAssign(students,question) && i<questions.size()){
                i++;
                if(i<questions.size() && !questions.get(i).isAssigned())
                    question.add(questions.get(i));
            };
        }
        if(this.questionImplementation.compareTo("SW")==0){
            students.sort( new SortByRoll());
            int i=0;
            Vector<student> student =new Vector<student>();
            if(!this.students.isEmpty())
            student.add(this.students.get(i));
            while(!this.getMinimumAffinityAndAssign(student,questions) && i<this.students.size()){
                i++;
                if(i<this.students.size()&&!this.students.get(i).isAssigned()) {
                    student = new Vector<student>();
                    student.add(students.get(i));
                }
            };
        }
    }
}

interface assigningTA{
    boolean getMinimumAffinityAndAssign(Vector<student> students, Vector<question> questions);
}

class CL extends TA{
    public boolean getMinimumAffinityAndAssign(Vector<student> students, Vector<question> questions){ //closeness loving
        Integer minimumAffinity = Integer.MAX_VALUE;
        question selectedQuestion=null;
        student selectedStudent=null;
        for(student student:students) {
            Integer rollNumberAsInteger = student.getRollNumberAsInteger();
            for (question question:questions) {
                if (!question.isAssigned() && !student.isAssigned() && minimumAffinity > Math.abs(rollNumberAsInteger - question.getID())) {
                    minimumAffinity = Math.abs(rollNumberAsInteger - question.getID());
                    selectedQuestion=question;
                    selectedStudent=student;
                }
            }
        }
        if(selectedQuestion!=null && selectedStudent!=null) {
            selectedStudent.assignQuestion(selectedQuestion);
            return true;
        }
        return false;
    }
    CL(String name, String questionImplementation){
        super(name, questionImplementation);
    }
}

class BR extends TA{
    public boolean getMinimumAffinityAndAssign(Vector<student> students, Vector<question> questions){ //best roll number
        Integer minimumStudentRollNumber = Integer.MAX_VALUE;
        Integer minimumQuestionID = Integer.MAX_VALUE;
        question selectedQuestion=null;
        student selectedStudent=null;
        for(student student:students) {
            if(!student.isAssigned() && student.getRollNumberAsInteger()< minimumStudentRollNumber){
                minimumStudentRollNumber=student.getRollNumberAsInteger();
                selectedStudent=student;
            }
        }
        for(question question:questions) {
            if(!question.isAssigned() && question.getID()<  minimumQuestionID){
                minimumQuestionID=question.getID();
                selectedQuestion=question;
            }
        }
        if(selectedQuestion!=null && selectedStudent!=null) {
            selectedStudent.assignQuestion(selectedQuestion);
            return true;
        }
        return false;
    }
    BR(String name, String questionImplementation){
        super(name, questionImplementation);
    }
}

class LU extends TA{
    private Integer luckyNumber;
    public boolean getMinimumAffinityAndAssign(Vector<student> students, Vector<question> questions){ //lucky type
        Integer minimumAffinity = Integer.MAX_VALUE;
        question selectedQuestion=null;
        student selectedStudent=null;
        for(student student:students) {
            Integer rollNumberAsInteger = student.getRollNumberAsInteger();
            for (question question:questions) {
                if (!question.isAssigned() && !student.isAssigned() && minimumAffinity > (rollNumberAsInteger + question.getID())%luckyNumber) {
                    minimumAffinity = (rollNumberAsInteger + question.getID())%luckyNumber;
                    selectedQuestion=question;
                    selectedStudent=student;
                } else if(!question.isAssigned() && !student.isAssigned() && minimumAffinity==(rollNumberAsInteger + question.getID())%luckyNumber){
                    if(question.getID()<selectedQuestion.getID()) {
                        selectedQuestion = question;
                        selectedStudent = student;

                    } else if (question.getID() == selectedQuestion.getID()) {
                        if (student.getRollNumber().compareTo(selectedStudent.getRollNumber())<0) {
                            selectedStudent = student;
                        }
                    }
                }
            }
        }

        if(selectedQuestion!=null && selectedStudent!=null) {
            selectedStudent.assignQuestion(selectedQuestion);
            return true;
        }
        return false;
    }
    LU(String name, String questionImplementation,Integer luckyNumber){
        super(name, questionImplementation);
        this.luckyNumber=luckyNumber;
    }
}

class SortByRoll implements Comparator<student>{
    public int compare(student a, student b){
        return  a.getRollNumber().compareTo(b.getRollNumber());
    }
}

class SortByID implements Comparator<question>{
    public int compare(question a, question b){
        return  a.getID()-b.getID();
    }
}