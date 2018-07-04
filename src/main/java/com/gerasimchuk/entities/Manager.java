package com.gerasimchuk.entities;

import com.gerasimchuk.enums.ManagerPosition;

import javax.persistence.*;

@Entity(name = "Managers")
@Table(name = "managers",schema = "logisticon", catalog = "")
public class Manager {
    private int id;
    private ManagerPosition managerPosition;

    public Manager() {
    }

    public Manager(ManagerPosition managerPosition) {
        this.managerPosition = managerPosition;
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

    @Column(name = "manager_position")
    @Enumerated(EnumType.STRING)
    public ManagerPosition getManagerPosition() {
        return managerPosition;
    }

    public void setManagerPosition(ManagerPosition managerPosition) {
        this.managerPosition = managerPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Manager managers = (Manager) o;

        if (id != managers.id) return false;
        if (managerPosition != managers.managerPosition) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (managerPosition != null ? managerPosition.hashCode() : 0);
        return result;
    }
}
