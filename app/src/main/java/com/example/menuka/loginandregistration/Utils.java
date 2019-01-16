package com.example.menuka.loginandregistration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by menuka on 5/16/17.
 */

public class Utils {
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isLegitModuleCode(String input) {
        Pattern p = Pattern.compile("^[A-Z|a-z]{2}[0-9]{4}$");
        Matcher m = p.matcher(input);
        return m.find();
    }

    public static boolean isLegitIndexNumber(String input) {
        Pattern p = Pattern.compile("^[0-9]{6}[A-Z|a-z]$");
        Matcher m = p.matcher(input);
        return m.find();
    }

    public static void main(String[] args) {
        String[] indexNumbers = {"140650E", "456789A", "asfd465", "", " ", "/", "456123 A", "1234564A"};
        for (String s : indexNumbers) {
            System.out.println(">>" + s.toUpperCase() + "<<: " + isLegitIndexNumber(s));
        }

        String[] moduleCodes = {"CE1012", "CE101", "CE 1012", "ce1012", "asfdfa", "asf!"};
        for (String s : moduleCodes) {
            System.out.println(">>" + s.toUpperCase() + "<<: " + isLegitModuleCode(s));
        }

        System.out.println(isDateFuture("05/16/2017"));
    }

    public static boolean isLegitDateFormat(String input) {
        return input.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})") ;
    }

    public static boolean isDateFuture(String input){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date today = new Date();
        System.out.println(dateFormat.format(today));
        try {
            Date inputDate = dateFormat.parse(input);
            return inputDate.after(today);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }

}
