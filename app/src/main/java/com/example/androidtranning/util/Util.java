package com.example.androidtranning.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Manabendu on 2019-09-20.
 */
public class Util {
    public static boolean isStringValid(String value) {
        boolean isValid = false;

        if (value != null && !value.isEmpty() && !value.equalsIgnoreCase("null")) {
            isValid = true;
        }

        return isValid;
    }

    public static String getDateAs_dd_MMM_yyyy(String inputDate) {
        String formatedDate = "";

        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());

        try {
            Date date = inputFormat.parse(inputDate);
            formatedDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatedDate;
    }

}
