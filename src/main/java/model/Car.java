package model;

import java.io.Serializable;
import java.time.*;

public class Car implements Serializable {
    private String id;
    private String brand;
    private String model;
    private int release_year;
    private int price;

    public Car(String id, String brand, String model, int release_year, int price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.release_year = release_year;
        this.price = price;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {return brand;}

    public void setBrand(String brand) {this.brand = brand;}

    public String getModel() {return model;}

    public void setModel(String model) {this.model = model;}

    public int getRelease_year() {return release_year;}

    public void setRelease_year(int release_year) {this.release_year = release_year;}

    public int getPrice() {return price;}

    public void setPrice(int price) {this.price = price;}

    public int getYearsUsed(){
        int currentYear = Year.now().getValue();
        return currentYear - release_year;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", release_year=" + release_year +
                ", price=" + price +
                '}';
    }
}