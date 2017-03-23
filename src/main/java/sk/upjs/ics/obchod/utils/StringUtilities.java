package sk.upjs.ics.obchod.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtilities {

    public static final Pattern VALIDNY_FORMAT_EMAILU_REGEX = 
    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    
    public static String naformatujString(String retazec) {
        String naformatovanyRetazec = retazec.substring(0, 1).toUpperCase() + retazec.substring(1).toLowerCase();

        return naformatovanyRetazec;
    }

    public static boolean jeCislo(String retazec) {
        try {
            Integer.parseInt(retazec);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
    
    public static boolean jeValidnyFormatEmailu(String email) {
        Matcher matcher = VALIDNY_FORMAT_EMAILU_REGEX .matcher(email);
        return matcher.find();
    }
}
