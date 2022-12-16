package rmi;

import model.Car;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

interface RMIServer extends Remote {
    List<Car> displayByBrand(String brand);
    List<Car> displayByModelAndYearsUsed(String model, int years_used);
    List<Car> displayByReleaseAndPrice(int release_year, int price);
}

public class ServerRmiTask8 {
    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(123);
        RMIServer service = new Service();
        registry.rebind("exam", service);
        System.out.println("Server started!");
    }

    static class Service extends UnicastRemoteObject implements RMIServer {
        List<Car> cars;

        Service() throws RemoteException {
            super();
            cars = new ArrayList<Car>() {
                {
                    add(new Car("001", "Ford", "Ranger",
                            2016, 20000 ));
                    add(new Car("002", "BMW", "M3",
                            2013, 50000 ));
                    add(new Car("003", "GMC", "Sierra",
                            2022, 100000 ));
                    add(new Car("004", "Audi", "S5",
                            2020, 70000 ));
                    add(new Car("005", "Dodge", "Ram",
                            2019, 55000 ));
                }
            };
        }

        @Override
        public List<Car> displayByBrand(String brand) {
            List<Car> results = new ArrayList<>();
            for (Car car : cars) {
                if (car.getBrand() == brand) {
                    results.add(car);
                }
            }
            return results;
        }

        @Override
        public List<Car> displayByModelAndYearsUsed(String model, int years_used){
            List<Car> results = new ArrayList<>();
            for (Car car : cars) {
                if (car.getModel() == model && car.getYearsUsed() > years_used) {
                    results.add(car);
                }
            }
            return results;
        }

        @Override
        public List<Car> displayByReleaseAndPrice(int release_year, int price){
            List<Car> results = new ArrayList<>();
            for (Car car : cars) {
                if (car.getRelease_year() == release_year && car.getPrice() > price) {
                    results.add(car);
                }
            }
            return results;
        }
    }
}
