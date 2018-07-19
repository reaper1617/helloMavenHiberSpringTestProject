package com.gerasimchuk.dto;

import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.RoutePoint;
import com.gerasimchuk.enums.CargoStatus;

public class CargoDTOImpl implements CargoDTO {

    private String cargoId;
    private String name;
    private String weight;
    private String status;
    private String loadPoint;
    private String unloadPoint;

    public CargoDTOImpl() {
    }

    public CargoDTOImpl(String name, String weight, String status, String loadPoint, String unloadPoint) {
        this.name = name;
        this.weight = weight;
        this.status = status;
        this.loadPoint = loadPoint;
        this.unloadPoint = unloadPoint;
    }

    @Override
    public String getCargoId() {
        return cargoId;
    }

    @Override
    public void setCargoId(String cargoId) {
        this.cargoId = cargoId;
    }

    @Override
    public int getCargoIdVal() {
        int id = 0;
        try{
            id = Integer.parseInt(cargoId);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public int getLoadPointId(){
        int id = 0;
        try{
            id = Integer.parseInt(loadPoint);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public int getUnloadPointId(){
        int id = 0;
        try {
            id = Integer.parseInt(unloadPoint);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getWeight() {
        return weight;
    }

    @Override
    public double getWeightVal() {
        double res = 0;
        try{
            res = Double.parseDouble(weight);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public CargoStatus getStatusVal(){
        if (status.equals("Prepared")) return CargoStatus.PREPARED;
        if (status.equals("Shipped")) return CargoStatus.SHIPPED;
        if (status.equals("Delivered")) return CargoStatus.DELIVERED;
        return CargoStatus.PREPARED;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getLoadPoint() {
        return loadPoint;
    }

    @Override
    public void setLoadPoint(String loadPoint) {
        this.loadPoint = loadPoint;
    }

    @Override
    public String getUnloadPoint() {
        return unloadPoint;
    }

    @Override
    public void setUnloadPoint(String unloadPoint) {
        this.unloadPoint = unloadPoint;
    }
}
