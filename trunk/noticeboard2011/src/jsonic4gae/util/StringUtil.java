package jsonic4gae.util;

public class StringUtil {

    public static String htmlEscape(String strVal) {
        StringBuffer strResult = new StringBuffer();
        for (int i = 0; i < strVal.length(); i++) {
            switch (strVal.charAt(i)) {
            case '&':
                strResult.append("&amp;");
                break;
            case '<':
                strResult.append("&lt;");
                break;
            case '>':
                strResult.append("&gt;");
                break;
            default:
                strResult.append(strVal.charAt(i));
                break;
            }
        }
        return strResult.toString();
    }
}