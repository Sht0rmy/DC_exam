package sockets;

import model.Car;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Callback
{
    public boolean shouldEnd = false;
}

class CarIterator implements Runnable
{
    private Socket socket;
    private Callback callback;
    private List<Car> cars;

    public CarIterator(Socket socket, Callback callback, List<Car> cars)
    {
        this.callback = callback;
        this.socket = socket;
        this.cars = cars;
    }

    @Override
    public void run() {
        try {
            InputStreamReader ois = new InputStreamReader(socket.getInputStream());
            System.out.println("Receiving input");
            BufferedReader reader = new BufferedReader(ois);
            String message = reader.readLine();
            String splitMessage[] = message.split("#");
            String commandIndex = splitMessage[0];
            System.out.println("Command " + splitMessage[0]);

            if (commandIndex.equals("4"))
            {
                System.out.println("Close command");
                callback.shouldEnd = true;
                return;
            }
            List<Car> result = new ArrayList<>();
            switch (commandIndex) {
                case "1": {
                    String brand = splitMessage[1];
                    for (Car car : cars) {
                        if (car.getBrand() == brand) {
                            result.add(car);
                        }
                    }
                    break;
                }
                case "2": {
                    String model = splitMessage[1];
                    Integer years_used = Integer.parseInt(splitMessage[2]);
                    for(Car car: cars) {
                        if (car.getModel() == model && car.getYearsUsed() > years_used) {
                            result.add(car);
                        }
                    }
                    break;
                }
                case "3": {
                    Integer release_year = Integer.parseInt(splitMessage[1]);
                    Integer price = Integer.parseInt(splitMessage[2]);
                    for (Car car : cars) {
                        if (car.getRelease_year() == release_year && car.getPrice() > price) {
                            result.add(car);
                        }
                    }
                    break;
                }

            }
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(result);
            ois.close();
            oos.close();
            socket.close();
        }
        catch (IOException e) { }
    }
}

public class ServerSocketTask8 {
    private static ServerSocket server;

    private static int port = 9876;

    public static Callback c = new Callback();

    public static void main(String args[]) throws IOException, ClassNotFoundException{
        server = new ServerSocket(port);
        List<Car> cars = new ArrayList<>() {
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


        while(!c.shouldEnd){
            System.out.println("Waiting for the client request");
            Socket socket = server.accept();
            CarIterator iterator = new CarIterator(socket, c, cars);
            iterator.run();
        }
        System.out.println("Shutting down Socket server!!");
        server.close();
    }
}

