package com.vanderis.talismans.messages;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@UtilityClass
public class Title {

    public void sendTitleToAll(String title, String subtitle) {
        Bukkit.getOnlinePlayers().forEach(player -> sendTitle(player, title, subtitle));
    }

    public void sendTitleToAll(String title, String subtitle, int fadeIn, int wait, int fadeOut) {
        Bukkit.getOnlinePlayers().forEach(player -> sendTitle(player, title, subtitle, fadeIn, wait, fadeOut));
    }

    public void sendTitle(Player player, String title, String subtitle) {
        player.sendTitle(Color.color(title), Color.color(subtitle), 20, 40, 20);
    }

    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int wait, int fadeOut) {
        player.sendTitle(Color.color(title), Color.color(subtitle), fadeIn, wait, fadeOut);
    }

}
