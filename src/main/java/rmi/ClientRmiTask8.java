package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientRmiTask8 {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        int choice = 1000;
        int x, y;
        Scanner in = new Scanner(System.in);
        try {
            RMIServer st = (RMIServer) Naming.lookup("//localhost:123/exam");
            System.out.println("Choose option:\n"
                    + "1 - display Cars of the given brand\n"
                    + "2 - display Cars that are used more than N years\n"
                    + "3 - display Cars that were released in N year and with price > M\n");
            choice = in.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter brand name");
                    String brand = in.nextLine();
                    System.out.println(st.displayByBrand(brand));
                    break;
                case 2:
                    System.out.print("Enter model name");
                    String model = in.nextLine();
                    System.out.print("Enter years used for");
                    int years_used = in.nextInt();
                    System.out.println(st.displayByModelAndYearsUsed(model, years_used));
                    break;
                case 3:
                    System.out.print("Enter release year");
                    int release_year = in.nextInt();
                    System.out.print("Enter price");
                    int price = in.nextInt();
                    System.out.println(st.displayByReleaseAndPrice(release_year, price));
            }
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

