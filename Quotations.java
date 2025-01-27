package main.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Quotations {
    public static List<String> quote(String input) throws IOException {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        boolean isSingleQuoted = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '\'') {
                isSingleQuoted = !isSingleQuoted;
                continue;
            }
            if (Character.isWhitespace(c) && !isSingleQuoted) {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
            } else {
                currentToken.append(c);
            }
        }
        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }
        if (isSingleQuoted) {
            System.err.println("unmatched single quote");
        }
        return tokens;
    }
}