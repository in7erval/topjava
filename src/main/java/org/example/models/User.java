package org.example.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class User extends AbstractBaseEntity {
    private String name;
    private String email;
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registerDate;

    private boolean isAdmin = false;

    public User() {
    }

    public User(Integer id, String name, String email, String password, Date registerDate, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.registerDate = registerDate;
        this.isAdmin = isAdmin;
    }

    public User(Integer id, String name, String email, String password, boolean isAdmin) {
        this(id, name, email, password, new Date(), isAdmin);
    }

    public User(String name, String email, String password, boolean isAdmin) {
        this(null, name, email, password, new Date(), isAdmin);
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registerDate=" + registerDate +
                '}';
    }
}
