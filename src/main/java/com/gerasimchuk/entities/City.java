package com.gerasimchuk.entities;

import com.gerasimchuk.enums.CityHasAgency;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Cities")
@Table(name = "cities",schema = "logisticon", catalog = "")
public class City {
    private int id;
    private String cityName;
    private CityHasAgency hasAgency;

    public City(String cityName, CityHasAgency hasAgency) {
        this.cityName = cityName;
        this.hasAgency = hasAgency;
    }

    public City() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

        City cities = (City) o;

        if (id != cities.id) return false;
        if (cityName != null ? !cityName.equals(cities.cityName) : cities.cityName != null) return false;
        if (hasAgency != cities.hasAgency) return false;

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
