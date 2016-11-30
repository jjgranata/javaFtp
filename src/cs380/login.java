package cs380;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class login {

        public static void run() throws IOException {
            Scanner s1,s2;
            s1=new Scanner(new FileInputStream("E:\\Programming\\Intellij IDEA workspace\\Java\\General\\cs380java\\javaFtp\\src\\cs380\\user.txt")); //change filepath to get user.txt
            s2=new Scanner(System.in);
            boolean flag=false;
            String name,pword,n,p;
            System.out.println("Enter name:");
            n=s2.next();
            System.out.println("Enter password:");
            p=s2.next();
            while(s1.hasNext()) {
                name=s1.next();
                pword=s1.next();
                if(n.equals(name) && p.equals(pword)) {
                    System.out.println("You are logged in."); //we can execute our code here
                    flag=true;
                    break;
                }
            }
            if (!flag)
                System.out.println("Incorrect user and/or password.");
                run();
        }

        public static void main(String[] args) throws IOException {
            run();
        } //when run() is implemented, you will need IOException

}