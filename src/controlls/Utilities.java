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
        int n = 0;

        while (true) {
            try {
                System.out.print(sms);
                n = Integer.parseInt(scanner.nextLine());
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
        String s = "";
        while (true) {
            try {
                System.out.print(sms);
                s = scanner.nextLine();
                if (!s.isEmpty() && s.length() > min && s.length() < max) {
                    return s;
                } else {
                    System.out.printf("Please enter a string of at least %d character and at most %d!!!", min, max);
                }
            } catch (Exception e) {
                System.out.println("PLease enter the valid information!");
            }

        }
    }

    public static String getString(String sms) {
        return getString(sms, 0, 1000000);
    }

    public static String getPhone(String msg) {
        String s = "";
        while (true) {
            try {
                System.out.print(msg);
                s = scanner.nextLine();
                if (!s.isEmpty() && s.matches("^[0-9]{9,11}$")) {

                    return s;

                } else {
                    System.out.println("Please enter an valid phone number");
                }
            } catch (Exception e) {
                System.out.println("Please enter an valid phone number");
            }
        }
    }

    public static String inputDate(String msg, int opt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate now = LocalDate.now();
        LocalDate check = null;
        String input = "";

        do {
            try {
                System.out.println(msg);
                input = scanner.nextLine().trim();

                if (input.isEmpty() || input.equals("")) {
                    return null;
                }

                check = LocalDate.parse(input, formatter);

                if (opt == 1) {
                    if (check.isAfter(now)) {
                        System.out.println("Please input a date before the current date!");
                        continue;
                    }

                }

                return check.format(formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Please input a valid date (yyyy/MM/dd).");
            }
        } while (true);
    }

}

   

