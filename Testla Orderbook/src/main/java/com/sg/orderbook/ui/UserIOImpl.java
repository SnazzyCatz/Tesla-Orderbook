package com.sg.orderbook.ui;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UserIOImpl implements UserIO {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();
        return input;
    }

    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        int input = scanner.nextInt();
        return input;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        System.out.println(prompt + "(" + min + "-" + max + ")");
        int input = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                input = Integer.parseInt(scanner.nextLine());
            } catch(NumberFormatException E) {
                System.out.println("Please Input A Number");
                continue;
            }
            if (input <= max && input >= min) {
                validInput = true;
            } else {
                System.out.println("Invalid Input");
            }
        }
        return input;
    }

    @Override
    public double readDouble(String prompt) {
        System.out.println(prompt);
        double input = Double.parseDouble(scanner.nextLine());
        return input;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        System.out.println(prompt + "(" + min + "-" + max + ")");
        double input = 0;
        boolean validInput = false;
        while (!validInput) {
            input = Double.parseDouble(scanner.nextLine());
            if (input <= max && input >= min) {
                validInput = true;
            } else {
                System.out.println("Invalid Input");
            }
        }
        return input;
    }

    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);
        float input = Float.parseFloat(scanner.nextLine());
        return input;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        System.out.println(prompt + "(" + min + "-" + max + ")");
        float input = 0;
        boolean validInput = false;
        while (!validInput) {
            input = Float.parseFloat(scanner.nextLine());
            if (input <= max && input >= min) {
                validInput = true;
            } else {
                System.out.println("Invalid Input");
            }
        }
        return input;
    }

    @Override
    public long readLong(String prompt) {
        System.out.println(prompt);
        Long input = Long.parseLong(scanner.nextLine());
        return input;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        System.out.println(prompt + "(" + min + "-" + max + ")");
        long input = 0;
        boolean validInput = false;
        while (!validInput) {
            input = Long.parseLong(scanner.nextLine());
            if (input <= max && input >= min) {
                validInput = true;
            } else {
                System.out.println("Invalid Input");
            }
        }
        return input;
    }
}
