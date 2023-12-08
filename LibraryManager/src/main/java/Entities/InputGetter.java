package Entities;

import lombok.Getter;

import java.util.Scanner;

@Getter
public class InputGetter {

    public static int getInt(String message){
        Scanner scanner = new Scanner(System.in);

        int input = 0;
        System.out.println(message);
        try{
            input = scanner.nextInt();
        }
        catch(Exception e) {
            if (e instanceof java.util.InputMismatchException) {
                System.out.println("Invalid input, please try again\n");
                return getInt(message);
            }
            throw e;
        }
        return input;
    };

    public static String getString(String message){
        Scanner scanner = new Scanner(System.in);

        String input = "";
        System.out.println(message);
        try{
            input = scanner.nextLine();
        }
        catch(Exception e) {
            if (e instanceof java.util.InputMismatchException) {
                System.out.println("Invalid input, please try again\n");
                return getString(message);
            }
            throw e;
        }
        return input;
    };

    public static double getDouble(String message){
        Scanner scanner = new Scanner(System.in);

        double input = 0;
        System.out.println(message);
        try{
            input = scanner.nextDouble();
        }
        catch(Exception e) {
            if (e instanceof java.util.InputMismatchException) {
                System.out.println("Invalid input, please try again");
                return getDouble(message);
            }
            throw e;
        }
        return input;
    };

}
