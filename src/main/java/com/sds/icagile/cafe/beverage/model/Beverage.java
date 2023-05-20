package com.sds.icagile.cafe.beverage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Beverage {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @Column
    private int cost;

    @Column
    private BeverageSize beverageSize;

    public Beverage() {
    }

    public Beverage(String name, int cost, BeverageSize beverageSize) {
        this.name = name;
        this.cost = cost;
        this.beverageSize = beverageSize;
    }

    public Beverage(int id, String name, int cost, BeverageSize beverageSize) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.beverageSize = beverageSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public BeverageSize getSize() {
        return beverageSize;
    }

    public void setSize(BeverageSize beverageSize) {
        this.beverageSize = beverageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beverage beverage = (Beverage) o;
        return id == beverage.id &&
                cost == beverage.cost &&
                Objects.equals(name, beverage.name) &&
                beverageSize == beverage.beverageSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cost, beverageSize);
    }

    @Override
    public String toString() {
        return "Beverage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", size=" + beverageSize +
                '}';
    }
}
