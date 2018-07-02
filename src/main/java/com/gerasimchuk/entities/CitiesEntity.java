package com.gerasimchuk.entities;

import com.gerasimchuk.enums.CityHasAgency;

import javax.persistence.*;

@Entity
@Table(name = "cities", schema = "logisticon", catalog = "")
public class CitiesEntity {
    private int id;
    private String cityName;
    private CityHasAgency hasAgency;

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
    @Column(name = "city_name")
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Column(name = "has_agency")
    @Enumerated(EnumType.STRING)
    public CityHasAgency getHasAgency() {
        return hasAgency;
    }

    public void setHasAgency(CityHasAgency hasAgency) {
        this.hasAgency = hasAgency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CitiesEntity that = (CitiesEntity) o;

        if (id != that.id) return false;
        if (cityName != null ? !cityName.equals(that.cityName) : that.cityName != null) return false;
        if (hasAgency != that.hasAgency) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (hasAgency != null ? hasAgency.hashCode() : 0);
        return result;
    }
}
