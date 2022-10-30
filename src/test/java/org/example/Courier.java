package org.example;

import groovy.transform.builder.Builder;

public class Courier {
    private String login;
    private String password;
    private String firstName;

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
