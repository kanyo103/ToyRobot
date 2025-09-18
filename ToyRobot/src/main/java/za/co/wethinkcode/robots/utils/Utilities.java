package za.co.wethinkcode.robots.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utilities {
    public static boolean isInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isRightOrLeft(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.equalsIgnoreCase("right") || str.equalsIgnoreCase("left");
    }

    public static int getIntInput(String prompt) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(prompt);
        String input = br.readLine();
        while (!Utilities.isInteger(input)){
            System.out.println("Please enter valid an integer.");
            System.out.println(prompt);
            input = br.readLine();
        }
        return Integer.parseInt(input);
    }
}
