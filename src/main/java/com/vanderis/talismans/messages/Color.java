package com.vanderis.talismans.messages;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

import java.util.*;
import java.util.regex.*;

@UtilityClass
public class Color {

    private final Pattern pattern = Pattern.compile("#[a-fA-F\\d]{6}");

    public String color(String text) {
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String color = text.substring(matcher.start(), matcher.end());
            text = text.replace(color, ChatColor.of(color) + "");
            matcher = pattern.matcher(text);
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public List<String> color(List<String> text) {
        List<String> result = new ArrayList<>();

        for (String s : text) {
            Matcher matcher = pattern.matcher(s);

            while (matcher.find()) {
                String color = s.substring(matcher.start(), matcher.end());
                s = s.replace(color, ChatColor.of(color) + "");
                matcher = pattern.matcher(s);
            }
            result.add(ChatColor.translateAlternateColorCodes('&', s));
        }

        return result;
    }

}
