package com.gerasimchuk.entities;

import com.gerasimchuk.enums.RoutePointType;

import javax.persistence.*;

@Entity(name = "RoutePoint")
@Table(name = "route_points", schema = "logisticon", catalog = "")
public class RoutePoint {
    private int id;
    private RoutePointType type;
    private City city;


    public RoutePoint() {
    }

    public RoutePoint(RoutePointType type, City city) {
        this.type = type;
        this.city = city;
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


    @Column(name = "_type")
    @Enumerated(EnumType.STRING)
    public RoutePointType getType() {
        return type;
    }

    public void setType(RoutePointType type) {
        this.type = type;
    }

    @ManyToOne
    @JoinColumn(name = "city_id")
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoutePoint that = (RoutePoint) o;

        if (id != that.id) return false;
        if (city != that.city) return false;
        if (type != that.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + city.getId();
        return result;
    }
}
