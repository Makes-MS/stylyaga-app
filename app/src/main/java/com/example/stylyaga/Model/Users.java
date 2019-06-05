package com.example.stylyaga.Model;

public class Users
{
    private String Login, Password;

    public Users()
    {

    }

    public Users(String Login, String Password) {
        this.Login = Login;
        this.Password = Password;
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