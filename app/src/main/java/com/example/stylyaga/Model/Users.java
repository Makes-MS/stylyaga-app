package com.example.stylyaga.Model;

public class Users
{
    private String Name, Login, Password;

    public Users()
    {

    }

    public Users(String Name, String Login, String Password) {
        this.Name = Name;
        this.Login = Login;
        this.Password = Password;
    }

    public String getName(){
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String Login) {
        this.Login = Login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
}