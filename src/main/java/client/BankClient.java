package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BankClient
{
    public static void main(String[] args)
    {
        try (Socket socket = new Socket("localhost", 9999);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
             Scanner sc = new Scanner(System.in))
        {
            System.out.println("Connected to Bank Server");

            while (true)
            {
                System.out.println("Enter input (CHECK, DEPOSIT, WITHDRAW, EXIT): ");

                String command = sc.next().toUpperCase();

                if ("EXIT".equals(command))
                {
                    output.writeObject("exit");

                    break;
                }

                System.out.print("Enter User ID: ");

                long userId = sc.nextLong();

                System.out.print("Enter Account Number: ");

                long accountNumber = sc.nextLong();

                if (command.equals("DEPOSIT") || command.equals("WITHDRAW"))
                {
                    System.out.print("Enter Amount: ");

                    double amount = sc.nextDouble();

                    output.writeObject(command + " " + userId + " " + accountNumber + " " + amount);
                }
                else
                {
                    output.writeObject(command + " " + userId + " " + accountNumber);
                }

                String response = (String) input.readObject();

                System.out.println("Server Response: " + response);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
