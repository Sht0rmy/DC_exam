package sockets;

import model.Car;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientSocketTask8 {

    private static int port = 9876;

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        Scanner scan = new Scanner(System.in);
        while (true)
        {
            System.out.println("Choose option:\n"
                    + "1 - display Cars of the given brand\n"
                    + "2 - display Cars that are used more than N years\n"
                    + "3 - display Cars that were released in N year and with price > M\n");
            socket = new Socket(host.getHostName(), port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Sending request to Socket Server");
            int commandIndex = scan.nextInt();
            if (commandIndex == 3)
            {
                socket = new Socket(host.getHostName(), port);
                oos = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("Sending close Request");
                oos.writeInt(commandIndex); oos.flush();
                break;
            }
            switch (commandIndex) {
                case 1:  {
                    System.out.println("Enter brand ");
                    String brand = scan.nextLine();
                    String message = "" + commandIndex + "#" + brand;
                    oos.writeBytes(message);
                    oos.flush();
                    break;
                }
                case 2: {
                    System.out.println("Enter model ");
                    String model = scan.nextLine();
                    System.out.println("Enter years used for ");
                    int years_used = scan.nextInt();
                    String message = "" + commandIndex + "#" + model + "#" + years_used;
                    oos.writeBytes(message);
                    oos.flush();
                    break;
                }
                case 3: {
                    System.out.println("Enter release year ");
                    int release_year = scan.nextInt();
                    System.out.println("Enter price ");
                    int price = scan.nextInt();
                    String message = "" + commandIndex + "#" + release_year + "#" + price;
                    oos.writeBytes(message);
                    oos.flush();
                    break;
                }

            }
            System.out.println("Results: ");
            ois = new ObjectInputStream(socket.getInputStream());
            ArrayList<Car> results = (ArrayList<Car>) ois.readObject();
            for (Car car: results)
            {
                System.out.println(car);
            }
            ois.close();
            oos.close();
            Thread.sleep(100);
        }
        oos.writeInt(4);
        System.out.println("Shutting down client!!");
    }
}

