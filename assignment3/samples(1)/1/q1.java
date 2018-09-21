import java.util.*;

public class q1 {
    public static void main(String[] args) {
        Scanner Scan = new Scanner(System.in);
        int t = Scan.nextInt();
        for(int i=0;i<t;i++){
            int q= Scan.nextInt(), n= Scan.nextInt(), k= Scan.nextInt();
            Vector<question> questions = new Vector<>();
            for(int j=0;j<q;j++){
                questions.add(new question(Scan.nextInt(), Scan.next()));
            }
            Vector<student> students = new Vector<>();
            for(int j=0;j<n;j++){
                students.add(new student(Scan.next()));
            }
            students.sort( new SortByRoll());
            for(int j=0;j<n;j++){
                for(int x=0;x<k;x++) {
                    students.get(j).getMinimumAffinityIndexAndAssign(questions);
                }
            }
            Vector<student> studentsAsEntering = new Vector<>();
            for(int j=0;j<n;j++) {
                studentsAsEntering.add(new student(Scan.next()));
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
    private Vector<question> questions = new Vector<question>();

    public student(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public int getRollNumberAsInteger(){
        return Integer.parseInt(rollNumber.substring(3));
    }

    void getMinimumAffinityIndexAndAssign(Vector<question> questions){
        Integer rollNumberAsInteger = getRollNumberAsInteger();
        Integer minimumAffinity = Integer.MAX_VALUE;
        Integer questionIndex=-1;
        for(int i=0;i<questions.size();i++){
            if(!questions.get(i).isAssigned() && minimumAffinity>Math.abs(rollNumberAsInteger-questions.get(i).getID())){
                minimumAffinity=Math.abs(rollNumberAsInteger-questions.get(i).getID());
                questionIndex=i;
            }
        }
        this.assignQuestion(questionIndex,questions);
    }

    void assignQuestion(Integer questionIndex, Vector<question> questions){
        this.questions.add(questions.get(questionIndex));
        questions.get(questionIndex).setAssigned(true);
    }

    public Vector<question> getQuestions() {
        return questions;
    }
}

class SortByRoll implements Comparator<student>{
    public int compare(student a, student b){
     return  a.getRollNumberAsInteger()-b.getRollNumberAsInteger();
    }
}