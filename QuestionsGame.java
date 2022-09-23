// Jasarjan Singh
// 05/26/2022
// CSE 143
// TA: Himani Nijhawan
// Assesment: 20 Questions 
// This program represents a tree of yes/no questions 
// and answers for playing games of 20 Questions

import java.util.*;
import java.io.*;

public class QuestionsGame {
    private QuestionNode rootTree;
    private Scanner console;

    // post: Initialize a new QuestionsGame object with a single 
    //       leaf node representing the object "computer"
    public QuestionsGame() {
        rootTree = new QuestionNode("computer");
        console = new Scanner(System.in);
    }

    // pre: files must be in standard format. The first line contains either "Q:" or "A:" 
    //      The second line of the pair should contain the text for that node.
    // post: Replace the current tree by reading another tree from a file.
    //       Passes a Scanner that is linked to the file and should replace
    //       the current tree with a new tree using the information in the file
    public void read(Scanner input) {
        rootTree = readHelper(input);
    }

    // pre: files must be in standard format. The first line contains either "Q:" or "A:" 
    //      The second line of the pair should contain the text for that node.
    // post: Helper method for read which reads the lines of input 
    //       and constructs a tree using the information in the file
    private QuestionNode readHelper(Scanner input) {
        String type = input.nextLine();
        String data = input.nextLine();
        //System.out.println(data);
        QuestionNode current = new QuestionNode(data);
        if(type.equals("Q:")) {
            current.leftNode = readHelper(input);
            current.rightNode = readHelper(input);
        }
        return current;
    }
    
    // pre: files must be in standard format. The first line contains either "Q:" or "A:" 
    //      The second line of the pair should contain the text for that node.
    // post: Store the current questions tree to an output
    //       file represented by the given PrintStream
    public void write(PrintStream output) {
        write(output, rootTree);
    }

    // pre: files must be in standard format. The first line contains either "Q:" or "A:" 
    //      The second line of the pair should contain the text for that node.
    // post: PrintStream output and QuestionNode current as parameters. Helper
    //       method to write that stores content of current tree to an input file
    private void write(PrintStream output, QuestionNode current) {
        if(current.leftNode != null || current.rightNode != null){
            output.println("Q:");
            output.println(current.data);
            write(output, current.leftNode);
            write(output, current.rightNode);  
        } else {
            output.println("A:");
            output.println(current.data);
        } 
         
    }

    // post: Uses the current question tree to play one complete guessing game with 
    //       the player, asking yes/no questions until reaching an answer object to guess.
    //       If the computer cannot guess the object, it expands the tree to include the object
    public void askQuestions() {
        rootTree = askQuestions(rootTree);
    }

    // post: Private helper method to askQuestions which asks 
    //       yes/no questions. If the computer cannot guess the 
    //       object, it expands the tree to include the object 
    private QuestionNode askQuestions(QuestionNode current) {
        if (current.leftNode != null || current.rightNode != null) {
            if (yesTo(current.data)) {
                current.leftNode = askQuestions(current.leftNode);
            } else {
                current.rightNode = askQuestions(current.rightNode); 
            }   
        } else {
            if (yesTo("Would your object happen to be " + current.data +"?")) {
                System.out.println("Great, I got it right!");
            } else {
                System.out.print("What is the name of your object? ");
                QuestionNode answer = new QuestionNode(console.nextLine());
                System.out.println("Please give me a yes/no question that");
                System.out.println("distinguishes between your object");
                System.out.print("and mine--> "); 
                String question = console.nextLine(); 
                if (yesTo("And what is the answer for your object?")) {
                    current = new QuestionNode(question, answer, current); 
                } else {
                    current = new QuestionNode(question, current, answer); 
                }   
            }
        }
        return current;
    }

    // QuestionNode class constructs the QuestionNode of the tree
    private static class QuestionNode {
        public String data;
        public QuestionNode leftNode;
        public QuestionNode rightNode;

        // post: Constructs a question node with given data 
        public QuestionNode(String data) {
            this(data, null, null); 
        }

        // post: Constructs a branch node with the given data, 
        //       left subtree, and right subtree
        public QuestionNode(String data, QuestionNode leftNode, QuestionNode rightNode) {
            this.data = data;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        } 
    } 

    // Do not modify this method in any way
    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    private boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
}
