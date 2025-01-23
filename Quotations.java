package main.java;

import java.io.IOException;

public class Quotations {
    public static StringBuilder quote(String input) throws IOException {
        List<String> tokens = new ArrayList<>();
        StringBuilder currenttoken = new StringBuilder();
        Boolean isSingleQuoted = false;

        for(int i=0; i<input.length(); i++){
            char c = input.charAt(i);
            if(c = '\''){
                isSingleQuoted = !isSingleQuoted;
                continue;
            }
            if(c.hasWhitespcae()&&!isSingleQuoted){
                tokens.add(currenttoken.toString());
                currenttoken.length = 0;
            }else{
                currenttoken.append(c);
            }
        }
        if(currenttoken.length() > 0){
            tokens.add(currenttoken.toString());
        }
        if(isSingleQuoted){
            System.err.println("inmatched single quote");
        }
        return tokens;
    }
}