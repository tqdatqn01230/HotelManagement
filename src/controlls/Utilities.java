/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlls;

import com.sun.corba.se.impl.io.IIOPOutputStream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import jdk.nashorn.internal.runtime.regexp.joni.constants.SyntaxProperties;


public class Utilities {

    private static final Scanner scanner = new Scanner(System.in);

    public static int getInt(String sms, int min, int max) {
        return getInt(sms, min,max, null);
    }
    public static int getInt(String sms, int min, int max, Integer defaultWhenBlank) {
        int n;
        while (true) {
            try {
                System.out.print(sms);
                String value = scanner.nextLine();
                if (defaultWhenBlank != null && value.isEmpty()) {
                    return defaultWhenBlank;
                }
                n = Integer.parseInt(value);
                if (n >= min && n < max) {
                    return n;
                } else {
                    System.out.println("Out of range!!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Wrong format");
            }
        }
    }


    public static double getDouble(String prompt, double min, double max) {
        double number = 0;
        boolean isValid = false;

        do {
            try {
                System.out.println(prompt);
                number = Double.parseDouble(scanner.nextLine());

                if (number > min && number < max) {
                    isValid = true;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        } while (!isValid);

        return number;
    }

    public static String getUserInputString(String regex, String inputMessage, String errorMessage, String defaultWhenBlank) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        while (true) {
            System.out.print(inputMessage);
            String input = scanner.nextLine();

            if (input.trim().isEmpty()) {
                if (defaultWhenBlank != null) {
                    return defaultWhenBlank;
                } else {
                    System.out.println("Please enter input!");
                    continue;
                }
            }

            if (pattern.matcher(input).matches()) {
                return input;
            } else {
                System.out.println(errorMessage);
            }
        }
    }

    public static String getString(String sms, int min, int max) {
        return getUserInputString(String.format("^.{%d,%d}$", min, max),sms, "Invalid length of String!", null);
    }

    public static String getString(String sms) {
        return getString(sms, 1, 1000000);
    }

    public static String getString(String sms, String defaultWhenBlank) {
        return getUserInputString(String.format("^.{%d,%d}$", 1, 100000),sms, "Invalid length of String!", defaultWhenBlank);
    }

    public static String getPhone(String msg) {
        return getUserInputString("^[0-9]{9,11}$",msg, "Please enter an valid phone number",null );
    }

    public static String getPhone(String msg, String defaultWhenBlank) {
        return getUserInputString("^[0-9]{9,11}$",msg, "Please enter an valid phone number",defaultWhenBlank );
    }


}

   

