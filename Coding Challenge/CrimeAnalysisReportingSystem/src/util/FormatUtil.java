package util;

public class FormatUtil {
    public static String toTitleCase(String input) {
        if (input == null || input.isEmpty()) return input;
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c) || c == ',' || c == '-') {
                nextTitleCase = true;
                titleCase.append(c);
            } else if (nextTitleCase) {
                titleCase.append(Character.toUpperCase(c));
                nextTitleCase = false;
            } else {
                titleCase.append(Character.toLowerCase(c));
            }
        }

        return titleCase.toString();
    }
}
