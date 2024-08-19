package com.vanderis.talismans.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.util.logging.Level;

@UtilityClass
public class Logging {

    public boolean debug = false;

    public void debug(String title, String message) {
        if (debug)
            Bukkit.getConsoleSender().sendMessage(Color.color("&8[&5Talismans&8-&aDEBUG&8] &8<&e" + title + "&8> &f" + message));
    }

    public void log(String message) {
        Bukkit.getConsoleSender().sendMessage(Color.color("&8[&5Talismans&8-&fLOG&8] &f" + message));
    }

    public void warn(String message) {
        Bukkit.getConsoleSender().sendMessage(Color.color("&8[&5Talismans&8-&eWARN&8] &f" + message));
    }

}
