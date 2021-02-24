package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class MailClient {
    private static BufferedReader stdIn;
    private static PrintWriter output;
    private static BufferedReader input;
    private static Socket socket;

    public static void main(String args[]) {
        try{
            int port=Integer.parseInt(args[0]);
            socket = new Socket("localhost",port);

            output = new PrintWriter(socket.getOutputStream(),true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            stdIn = new BufferedReader(new InputStreamReader(System.in));

            readFromServer(); // get greeting
            System.out.print("mpika! \n");
            loggedOutMenu();

        } catch (IndexOutOfBoundsException e){
            System.out.println("Enter the ip address and the port of the server as arguments.");
        } catch (ConnectException e){
            System.out.println("Couldn't connect to the server. Is the server running?");
        } catch (IOException e){
            System.out.println("IO exception occured.");
        }
    }

    private static void logIn() throws IOException {
        readFromServer();

        // get username from standard input and write it to server
        output.println(stdIn.readLine());

        readFromServer();

        // get password from standard input and write it to server
        output.println(stdIn.readLine());
        readFromServer();

    }

    private static void readEmail() throws IOException {
        readFromServer();

        // get email's id from standard input and write it to server
        output.println(stdIn.readLine());

        readFromServer();
    }

    private static void newEmail() throws IOException {
        readFromServer();

        // get the receiver from standard input and write it to server
        output.println(stdIn.readLine());

        readFromServer();

        // get the subject from standard input and write it to server
        output.println(stdIn.readLine());

        readFromServer();

        output.println(stdIn.readLine());

        readFromServer();

    }

    private static void readFromServer() throws IOException {
        String fromServer;
        while (!(fromServer = input.readLine()).equals("BREAK")) {
            System.out.println(fromServer);
        }
    }

    private static void exit() throws IOException {
        output.close();
        input.close();
        stdIn.close();
        socket.close();
    }

    private static void loggedOutMenu() throws IOException {
        readFromServer(); // get the available choices

        // read the answer from the keyboard and write it to the server
        String ans = stdIn.readLine();
        output.println(ans);

       /* String fromServer;
        while ((fromServer=bufferedReader.readLine()).equals("AGAIN")){
            readFromServer(); // get the "Wrong choice." message
            readFromServer(); // get the available choices
            // read the answer from the keyboard and write it to the server
            ans = bufferedReader.readLine();
            printWriter.println(ans);
        }*/


        // act based on user's answer
        if (ans.equals("LogIn")) {
            logIn();
            showLoggedInMenu();
        }
        else if (ans.equals("SignIn")) {
            logIn();
            showLoggedInMenu();
        }
        else if(ans.equals("Exit"))
            exit();

        System.out.println("lpl");
    }

    private static void showLoggedInMenu() throws IOException {
        readFromServer();
        String ans = stdIn.readLine();
        output.println(ans);
        if (ans.equals("NewEmail")) {
            newEmail();
            showLoggedInMenu();
        }
        else if (ans.equals("Logout")) {
            loggedOutMenu();
        }
        else if(ans.equals("Exit"))
            exit();
    }
}




