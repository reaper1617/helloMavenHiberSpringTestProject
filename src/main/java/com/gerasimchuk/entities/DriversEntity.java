package com.gerasimchuk.entities;

import javax.persistence.*;

@Entity
@Table(name = "drivers", schema = "logisticon", catalog = "")
public class DriversEntity {

    private int id;
    private double hoursWorked;

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
    @Column(name = "hours_worked")
    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriversEntity that = (DriversEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.hoursWorked, hoursWorked) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(hoursWorked);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
