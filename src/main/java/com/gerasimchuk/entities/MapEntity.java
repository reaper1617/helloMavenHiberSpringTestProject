package com.gerasimchuk.entities;

import javax.persistence.*;

@Entity
@Table(name = "map", schema = "logisticon", catalog = "")
@IdClass(MapEntityPK.class)
public class MapEntity {

    private CitiesEntity departureCity;

    private CitiesEntity destinationCity;

    private double distance;

    @Id
    @OneToOne(targetEntity = CitiesEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "departure_city_id")
    public CitiesEntity getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(CitiesEntity departureCityId) {
        this.departureCity = departureCity;
    }


    @Id
    @OneToOne(targetEntity = CitiesEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_city_id")
    public CitiesEntity getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(CitiesEntity destinationCity) {
        this.destinationCity = destinationCity;
    }

    @Basic
    @Column(name = "distance")
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapEntity mapEntity = (MapEntity) o;

        if (departureCity != mapEntity.departureCity) return false;
        if (destinationCity != mapEntity.destinationCity) return false;
        if (Double.compare(mapEntity.distance, distance) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = departureCity.getId();
        result = 31 * result + destinationCity.getId();
        temp = Double.doubleToLongBits(distance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
