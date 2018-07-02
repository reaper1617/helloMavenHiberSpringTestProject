package com.gerasimchuk.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class MapEntityPK implements Serializable {
    private CitiesEntity departureCity;
    private CitiesEntity destinationCity;

    @Column(name = "departure_city_id")
    @Id
    public CitiesEntity getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(CitiesEntity departureCityId) {
        this.departureCity = departureCity;
    }

    @Column(name = "destination_city_id")
    @Id
    public CitiesEntity getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(CitiesEntity destinationCityId) {
        this.destinationCity = destinationCity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapEntityPK that = (MapEntityPK) o;

        if (departureCity != that.departureCity) return false;
        if (destinationCity != that.destinationCity) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = departureCity.getId();
        result = 31 * result + destinationCity.getId();
        return result;
    }
}
