package com.vanderis.talismans.messages;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.util.logging.Level;

@UtilityClass
public class Logging {

    public boolean debug = false;

    public void debug(String title, String message) {
        if (debug)
            Bukkit.getLogger().log(Level.INFO, Color.color("[Talismans-DEBUG] <" + title + "> " + message));
    }

    public void log(String message) {
        Bukkit.getLogger().log(Level.INFO, Color.color("[Talismans] " + message));
    }

    public void warn(String message) {
        Bukkit.getLogger().log(Level.WARNING, Color.color("[Talismans] " + message));
    }

}
