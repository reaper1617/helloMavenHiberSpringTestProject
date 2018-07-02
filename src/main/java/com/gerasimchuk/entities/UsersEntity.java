package com.gerasimchuk.entities;

import com.gerasimchuk.enums.UserRole;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "logisticon", catalog = "")
public class UsersEntity {
    private int id;
    private String userName;
    private String middleName;
    private String lastname;
    private String password;

    UserRole role;


    private ManagersEntity manager;


    private DriversEntity driver;

    @Id
    @Column(name = "id")
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    public UserRole getRole() {
        return role;
    }


    public void setRole(UserRole role) {
        this.role = role;
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


    @OneToOne(targetEntity = ManagersEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "manager")
    public ManagersEntity getManager() {
        return manager;
    }

    public void setManager(ManagersEntity manager) {
        this.manager = manager;
    }

    @OneToOne(targetEntity = DriversEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "driver")
    public DriversEntity getDriver() {
        return driver;
    }


    public void setDriver(DriversEntity driver) {
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (id != that.id) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (manager != null ? !manager.equals(that.manager) : that.manager != null) return false;
        if (driver != null ? !driver.equals(that.driver) : that.driver != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (manager != null ? manager.hashCode() : 0);
        result = 31 * result + (driver != null ? driver.hashCode() : 0);
        return result;
    }
}
