package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class MailServer {
    private static ArrayList<Account> accounts;

    public MailServer(){


    }
    public void register(String user,String password){
        boolean flag=false;
        for(Account a:accounts){
            if (user.equals(a.getUsername())) {
                flag = true;
                break;
            }
        }
        if(flag)
            System.out.print("There is already a user with this name!");
        else{
            accounts.add(new Account(user,password));
        }
    }



    public void newEmail( boolean isNew, String sender, String receiver, String subject, String mainBody){
        boolean flag=false;
        for(Account a: accounts){
            if(a.getUsername().equals(receiver)) {
                a.addEmail(new Email(true,sender,receiver,subject,mainBody));
                flag=true;
                break;
            }
        }
        if(!flag){
            System.out.print("Receiver not found! ");
        }
    }

    public void showEmails(){

    }

    public static void main(String[] args) throws IOException {
        accounts=new ArrayList<Account>();
        int port=Integer.parseInt(args[0]);
        ServerSocket serverSocket=new ServerSocket(port);
        while(true) {
            new MailServerThread(serverSocket.accept(), accounts).start();
        }






    }
}
