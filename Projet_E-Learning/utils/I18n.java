package utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
    private static Locale currentLocale = Locale.getDefault();
    private static ResourceBundle messages;

    static {
        setLanguage("fr");
    }

    public static void setLanguage(String language) {
        currentLocale = new Locale(language);
        messages = ResourceBundle.getBundle("messages", currentLocale);
    }

    public static String getString(String key) {
        try {
            return messages.getString(key);
        } catch (Exception e) {
            return key;
        }
    }

    public static String getCurrentLanguage() {
        return currentLocale.getLanguage();
    }
}