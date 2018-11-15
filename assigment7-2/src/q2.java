/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author DELL
 */
public class q2 {
    //create interface and pass data to it
    public static void main(String[] args) {
        new Menu().setVisible(true);
        students students = new students();
        System.out.print("hello world!");
    }

    //driver code for q2, modified from code for q1
    Boolean run(File file){
        Integer n;
        try {
            Scanner s = new Scanner(file);
            n = s.nextInt();
            students students = new students();

            for( Integer i=0; i<n; i++){
                students.add(new student(s.next(),s.next(),s.nextInt()));
            }

            for(Integer i=0; i<n; i++){
                student student = students.findByRoll(s.next());
                Integer k=s.nextInt();
                students friends = new students();
                for(Integer j=0; j<k; j++) {
                    friends.add(students.findByRoll(s.next()));
                }
                student.setFriends(friends);
            }
            allocateSweets(students);
        } catch (java.io.FileNotFoundException e){
            System.out.print("File not found error");
            return false;
        }
        return true;
    }

    Boolean allocateSweets(students students){
        students.sort();
        students.giveSweets();
        return true;
    }
}
