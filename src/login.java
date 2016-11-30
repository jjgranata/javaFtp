import java.util.Scanner;

public class login {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String username;
        String password;

        System.out.println("Log in:");
        System.out.println("username: ");
        username = input.next();

        System.out.println("password: ");
        password = input.next();

        if(username.equals(username) && password.equals(password))
            System.out.println("logged in");

    }

}