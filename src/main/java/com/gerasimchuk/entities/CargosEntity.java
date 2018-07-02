package com.gerasimchuk.entities;

import javax.persistence.*;

@Entity
@Table(name = "cargos", schema = "logisticon", catalog = "")
public class CargosEntity {
    private int id;
    private String cargoName;

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
    @Column(name = "cargo_name")
    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CargosEntity that = (CargosEntity) o;

        if (id != that.id) return false;
        if (cargoName != null ? !cargoName.equals(that.cargoName) : that.cargoName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (cargoName != null ? cargoName.hashCode() : 0);
        return result;
    }
}
