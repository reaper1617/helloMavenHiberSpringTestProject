package com.gerasimchuk.entities;

import com.gerasimchuk.enums.UserRole;

import javax.persistence.*;

@Entity(name = "Users")
@Table(name = "users",schema = "logisticon", catalog = "")
public class User {
    private int id;
    private String userName;
    private String middleName;
    private String lastname;
    private String password;
    private UserRole role;
    private Manager manager;
    private Driver driver;

    public User() {
    }

    public User(String userName,
                String middleName,
                String lastname,
                String password,
                UserRole role,
                Manager manager) {

        this.userName = userName;
        this.middleName = middleName;
        this.lastname = lastname;
        this.password = password;
        this.role = role;
        this.manager = manager;
        this.driver = null;
    }

    public User(String userName,
                String middleName,
                String lastname,
                String password,
                UserRole role,
                Driver driver) {

        this.userName = userName;
        this.middleName = middleName;
        this.lastname = lastname;
        this.password = password;
        this.role = role;
        this.manager = null;
        this.driver = driver;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "middle_name")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Basic
    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @OneToOne
    @JoinColumn(name = "id")
    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @OneToOne
    @JoinColumn(name = "driver")
    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User users = (User) o;

        if (id != users.id) return false;
        if (userName != null ? !userName.equals(users.userName) : users.userName != null) return false;
        if (middleName != null ? !middleName.equals(users.middleName) : users.middleName != null) return false;
        if (lastname != null ? !lastname.equals(users.lastname) : users.lastname != null) return false;
        if (password != null ? !password.equals(users.password) : users.password != null) return false;
        if (role != users.role) return false;
        if (manager != null ? !manager.equals(users.manager) : users.manager != null) return false;
        if (driver != null ? !driver.equals(users.driver) : users.driver != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (manager != null ? manager.hashCode() : 0);
        result = 31 * result + (driver != null ? driver.hashCode() : 0);
        return result;
    }
}
