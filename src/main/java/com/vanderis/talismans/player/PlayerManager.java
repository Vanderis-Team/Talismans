package com.vanderis.talismans.player;

import com.vanderis.talismans.Talismans;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.*;

import java.util.*;

public class PlayerManager {

    private final Talismans instance = Talismans.getInstance();

    public final Map<UUID, PlayerData> playerData = new HashMap<>();
    public final Map<String, PlayerData> offlinePlayerData = new HashMap<>(); // Use when admin open offline player bag

    public void register() {
        offlinePlayerData.clear();

        if (Bukkit.getOnlinePlayers().isEmpty())
            return;

        playerData.clear();

        for (Player player : Bukkit.getOnlinePlayers())
            fromFileToCache(player);
    }

    public void unregister() {
        if (Bukkit.getOnlinePlayers().isEmpty())
            return;

        for (Player player : Bukkit.getOnlinePlayers())
            fromCacheToFile(player);
    }

    public Boolean hasCached(String playerName) {
        if (Bukkit.getPlayer(playerName) == null) {
            return instance.getPlayerManager().offlinePlayerData.containsKey(playerName);
        }

        return instance.getPlayerManager().playerData.containsKey(Bukkit.getPlayer(playerName).getUniqueId());
    }

    public void removeCache(Player player) {
        removeCache(player.getUniqueId());
    }

    public void removeCache(UUID uuid) {
        fromCacheToFile(uuid);

        playerData.remove(uuid);
    }

    public PlayerData getPlayerData(Player player) {
        if (!playerData.containsKey(player.getUniqueId()))
            fromFileToCache(player);

        return playerData.get(player.getUniqueId());
    }

    public PlayerData getPlayerData(String playerName) {
        if (Bukkit.getPlayer(playerName) != null)
            return playerData.get(Bukkit.getPlayer(playerName).getUniqueId());

        fromFileToCache(playerName);
        return offlinePlayerData.get(playerName);
    }

    public void fromFileToCache(Player player) {
        instance.getBagManager().fromFileToCache(player);
    }

    public void fromFileToCache(String playerName) {
        instance.getBagManager().fromCacheToFile(playerName);
    }

    public void fromCacheToFile(Player player) {
        instance.getBagManager().fromCacheToFile(player);
    }

    public void fromCacheToFile(UUID uuid) {
        instance.getBagManager().fromCacheToFile(uuid);
    }

    public void fromCacheToFile(String playerName) {
        instance.getBagManager().fromCacheToFile(playerName);
    }

    public void onJoin(PlayerJoinEvent event) {
        fromFileToCache(event.getPlayer());
    }

    public void onQuit(PlayerQuitEvent event) {
        removeCache(event.getPlayer());
    }

}
