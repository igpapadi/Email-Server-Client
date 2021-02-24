package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

class MailServerThread extends Thread{
    private Socket socket;
    private Account loggedInUser;
    private ArrayList<Account> accounts;
    private BufferedReader input;
    private PrintWriter output;
    private String serverPrompt;



    public MailServerThread(Socket socket, ArrayList<Account> accounts) throws IOException {
        super("MailServerThread");
        this.socket=socket;
        this.accounts=accounts;
        loggedInUser=null;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
        serverPrompt="----- \n MailServer \n ----- \n";
    }

    public void run() {
        try {
            printToClient("Hello, you connected as a guest. \n ================= \n");
            menu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void menu() throws IOException {
        printToClient(">  LogIn \n >  SignIn \n >  Exit \n ========= \n");
        String answer="a";
            answer=input.readLine();
            if(answer.equals("LogIn")) {
                logIn();
                showLoggedInMenu();
            }
            else if (answer.equals("SignIn")) {
                register();
                showLoggedInMenu();
            }
            else if(answer.equals("Exit"))
                exit();
            else output.println("Did you press something wrong? Try again! ");

    }
    private void logIn() throws IOException {

        printToClient(serverPrompt + "Username: \n");
        String user = input.readLine();

        printToClient(serverPrompt + "Password: \n");
        String password = input.readLine();

        boolean flag=false;
        for(Account a:accounts){
            if(a.getUsername().equals(user)){
                if(a.getPassword().equals(password)) {
                    loggedInUser=a;
                    flag=true;
                    printToClient(serverPrompt+ "Welcome back "+a.getUsername()+ "!\n");
                    showLoggedInMenu();
                    break;
                }
            }
        }
        if(!flag)
            printToClient(serverPrompt+ "Invalid username or password! ");


    }

    private void logOut() {
        loggedInUser=null;
    }

    private void register() throws IOException{
        boolean flag=false;
        printToClient(serverPrompt + "Username: \n");
        String user = input.readLine();
        printToClient(serverPrompt + "Password: \n");
        String password = input.readLine();

            for(Account a:accounts){
                if (user.equals(a.getUsername())) {
                    flag = true;
                    loggedInUser=a;
                    showLoggedInMenu();
                    break;
                }
            }
            if(flag)
                System.out.print("There is already a user with this name!");
            else{
                accounts.add(new Account(user,password));
            }
        }

        private void showLoggedInMenu() throws IOException {
            printToClient("====== \n >  NewEmail  \n >  Logout \n >  Exit \n ======= \n");
        String answer="a";
            while ( !answer.equals("NewEmail")&&!answer.equals("Logout")&& !answer.equals("Exit")){
                answer=input.readLine();
                if(answer.equals("NewEmail")) {
                    newEmail();
                    showLoggedInMenu();
                }
                else if (answer.equals("Logout")) {
                    menu();
                    showLoggedInMenu();
                }
                else if(answer.equals("Exit"))
                    exit();
                else
                    printToClient("Did you press something wrong? Try again! ");
            }

        }


    private void showEmails(){
        output.println(serverPrompt);

    }

  /*  private void readEmail() throws IOException {
        writeToClient(serverPrompt +"Enter Id");
        try {
            int id = Integer.parseInt(in.readLine());           // read the id from client
            for (Email email:loggedInUser.getMailbox()){
                if (email.getId()==id){
                    writeToClient("From: " + email.getSender() + "\nSubject: " + email.getSubject() + "\n\n" +
                            email.getMainBody());
                    email.setIsNew(false);
                    return;
                }
            }
            writeToClient("Wrong id.");
        } catch (NumberFormatException e){
            writeToClient("Wrong id.");
        }


    }

    private void deleteEmail() throws IOException {
        writeToClient(serverPrompt +"Enter Id");
        int id = Integer.parseInt(in.readLine());           // read the id from client

        if (loggedInUser.deleteEmail(id)){
            writeToClient("Email deleted!");
        } else {
            writeToClient("Wrong ID!");
        }
    }*/

    private void newEmail() throws IOException {
        printToClient(serverPrompt + "Receiver: \n");
        String receiver = input.readLine();

        printToClient(serverPrompt + "Subject: \n");
        String subject = input.readLine();

        printToClient(serverPrompt + "Main body: \n");
        String mainBody=input.readLine();

        boolean flag=false;
        synchronized (this){
            for (Account account:accounts){
                if (account.getUsername().equals(receiver)){
                    account.addEmail(new Email(true,loggedInUser.getUsername(),receiver, subject, mainBody));
                    printToClient("Mail Sent!");
                    flag=true;
                }
            }
        }
        if(!flag)
            printToClient("Receiver not found. \n");

    }
    private void printToClient(String s){
    output.println(s);
    output.println("BREAK");
    }

    private void exit() throws IOException {
        input.close();
        output.close();
        socket.close();
    }








}
