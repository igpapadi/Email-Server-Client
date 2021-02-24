package com.company;

import java.util.ArrayList;

class Account {
    private String username;
    private String password;
    private ArrayList<Email> mailbox;

    public Account(String username, String password){
        this.username=username;
        this.password=password;
        mailbox=new ArrayList<Email>();
    }

    public void addEmail(Email email){
        mailbox.add(email);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
