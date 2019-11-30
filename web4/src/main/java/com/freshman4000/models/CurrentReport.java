package com.freshman4000.models;

import javax.persistence.*;

@Entity
@Table(name="current_reports")
public class CurrentReport {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Transient
    private Car car;
    @Column(name="brand")
    private String brand;
    @Column(name="model")
    private String model;
    @Column(name="licensePlate")
    private String licensePlate;
    @Column(name="price")
    private Long price;

    public CurrentReport(Car car) {
        this.car = car;
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.licensePlate = car.getLicensePlate();
        this.price = car.getPrice();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
