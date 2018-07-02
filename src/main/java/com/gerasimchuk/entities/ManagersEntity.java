package com.gerasimchuk.entities;

import com.gerasimchuk.enums.ManagerPosition;

import javax.persistence.*;

@Entity
@Table(name = "managers", schema = "logisticon", catalog = "")
public class ManagersEntity {
    private int id;
    ManagerPosition managerPosition;

    @Id
    @Column(name = "id")
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Column(name = "manager_position" )
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

        ManagersEntity that = (ManagersEntity) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
