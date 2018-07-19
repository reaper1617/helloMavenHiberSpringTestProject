package com.gerasimchuk.entities;

import javax.persistence.*;

@Entity(name = "Route")
@Table(name = "route",schema = "logisticon", catalog = "" )
public class Route {
    private int id;
    private City departureCity;
    private City destinationCity;
    private double distance;

    public Route() {
    }

    public Route(City departureCity, City destinationCity, double distance) {
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.distance = distance;
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

    @ManyToOne
    @JoinColumn(name = "departure_city_id")
    public City getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(City departureCity) {
        this.departureCity = departureCity;
    }

    @ManyToOne
    @JoinColumn(name = "destination_city_id")
    public City getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(City destinationCity) {
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

        Route route = (Route) o;

        if (id != route.id) return false;
        if (departureCity != route.departureCity) return false;
        if (destinationCity != route.destinationCity) return false;
        if (Double.compare(route.distance, distance) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + departureCity.getId();
        result = 31 * result + destinationCity.getId();
        temp = Double.doubleToLongBits(distance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
