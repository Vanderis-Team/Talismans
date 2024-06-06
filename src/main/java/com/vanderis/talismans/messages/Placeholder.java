package com.vanderis.talismans.messages;

import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.regex.*;

@UtilityClass
public class Placeholder {

    /**
     * Use when want to replace every placeholder, one by one, without knowing what placeholder name.
     * <p><p>
     * Easier to say it will find {}, what is in that bracket doesn't matter, it will simply replace the value you give it.<p>
     * After replacing one, it will continue with the next one with the next value.
     * <p><p>
     * Ex: String ex = "I have {burgur} and {coke}, I {love} it."<p>
     * String result = Placeholder.replacePlaceholders(ex, "tissue", "water", "hate");<p>
     * -> I have tissue and water, I hate it.
     * */
    public static String replacePlaceholders(String original, String... values) {
        List<String> replacements = Arrays.asList(values);

        String pattern = "\\{([^}]*)}";
        Pattern regex = Pattern.compile(pattern);

        StringBuilder sb = new StringBuilder();
        int lastIndex = 0;
        int next = 0;
        Matcher matcher = regex.matcher(original);
        while (matcher.find()) {
            if (next >= replacements.size()) {
                throw new IllegalArgumentException("Not enough replacement values for placeholders");
            }

            sb.append(original, lastIndex, matcher.start());
            sb.append(replacements.get(next++));
            lastIndex = matcher.end();
        }

        if (next != replacements.size()) {
            throw new IllegalArgumentException("Too many replacement values for placeholders");
        }

        sb.append(original.substring(lastIndex));

        return sb.toString();
    }

    public String replacePlaceholders(String original, Map<String, String> placeholderAndValue) {
        String temp = "";

        for (var entry : placeholderAndValue.entrySet()) {
            if (temp.isEmpty()) {
                temp = replacePlaceholder(original, entry.getKey(), entry.getValue());

                continue;
            }

            temp = replacePlaceholder(temp, entry.getKey(), entry.getValue());
        }

        return temp;
    }

    public List<String> replacePlaceholders(List<String> original, Map<String, String> placeholderAndValue) {
        List<String> result = new ArrayList<>();

        String tempLore;

        for (String line : original) {
            tempLore = "";

            for (var entry : placeholderAndValue.entrySet()) {
                if (tempLore.isEmpty()) {
                    tempLore = replacePlaceholder(line, entry.getKey(), entry.getValue());

                    continue;
                }

                tempLore = replacePlaceholder(tempLore, entry.getKey(), entry.getValue());
            }

            result.add(tempLore);
        }

        return result;
    }

    public String replacePlaceholder(String original, String placeholder, String value) {
        return Color.color(original.replace(placeholder, value));
    }

}
