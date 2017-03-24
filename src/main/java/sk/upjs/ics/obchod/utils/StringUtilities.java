package sk.upjs.ics.obchod.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtilities {

    public static final Pattern VALID_EMAIL_FORMA_REGEX = 
    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    
    public static String formatString(String s) {
        String formattedString = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();

        return formattedString;
    }

    public static boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
    
    public static boolean isValidEmailFormat(String email) {
        Matcher matcher = VALID_EMAIL_FORMA_REGEX .matcher(email);
        return matcher.find();
    }
}
