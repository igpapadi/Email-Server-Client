package com.company;

class Email {
    private boolean isNew;
    private String sender;
    private String receiver;
    private String subject;
    private String mainBody;

    Email(boolean isNew, String sender, String receiver, String subject, String mainBody){
        this.isNew=isNew;
        this.sender=sender;
        this.receiver=receiver;
        this.subject=subject;
        this.mainBody=mainBody;
    }


}
