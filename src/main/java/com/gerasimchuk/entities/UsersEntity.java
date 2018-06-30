package com.gerasimchuk.entities;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "testdb", catalog = "")
public class UsersEntity {
    private int userId;
    private String userName;
    private String userMiddleName;
    private String userLastname;
    private String userPassword;
    private Integer userManager;
    private Integer userDriver;

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
    @Column(name = "user_middle_name")
    public String getUserMiddleName() {
        return userMiddleName;
    }

    public void setUserMiddleName(String userMiddleName) {
        this.userMiddleName = userMiddleName;
    }

    @Basic
    @Column(name = "user_lastname")
    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    @Basic
    @Column(name = "user_password")
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Basic
    @Column(name = "user_manager", nullable = false)
    public Integer getUserManager() {
        return userManager;
    }

    public void setUserManager(Integer userManager) {
        this.userManager = userManager;
    }

    @Basic
    @Column(name = "user_driver", nullable = false)
    public Integer getUserDriver() {
        return userDriver;
    }

    public void setUserDriver(Integer userDriver) {
        this.userDriver = userDriver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (userId != that.userId) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (userMiddleName != null ? !userMiddleName.equals(that.userMiddleName) : that.userMiddleName != null)
            return false;
        if (userLastname != null ? !userLastname.equals(that.userLastname) : that.userLastname != null) return false;
        if (userPassword != null ? !userPassword.equals(that.userPassword) : that.userPassword != null) return false;
        if (userManager != null ? !userManager.equals(that.userManager) : that.userManager != null) return false;
        if (userDriver != null ? !userDriver.equals(that.userDriver) : that.userDriver != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userMiddleName != null ? userMiddleName.hashCode() : 0);
        result = 31 * result + (userLastname != null ? userLastname.hashCode() : 0);
        result = 31 * result + (userPassword != null ? userPassword.hashCode() : 0);
        result = 31 * result + (userManager != null ? userManager.hashCode() : 0);
        result = 31 * result + (userDriver != null ? userDriver.hashCode() : 0);
        return result;
    }
}
