package com.vanderis.talismans.gui.bag;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.items.*;
import com.vanderis.talismans.utils.Logging;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class BagManager {

    private final Talismans instance = Talismans.getInstance();

    private final Map<UUID, List<ItemData>> playerBag = new HashMap<>();
    private final Map<String, List<ItemData>> offlinePlayerBag = new HashMap<>(); // Use when admin open offline player bag

    public void register() {
        if (Bukkit.getOnlinePlayers().isEmpty())
            return;

        playerBag.clear();

        for (Player player : Bukkit.getOnlinePlayers())
            fromFileToCache(player);
    }

    public void unregister() {
        for (UUID uuid : playerBag.keySet())
            removeCache(uuid);
    }

    public void addItem(Player player, ItemData itemData) {
        playerBag.getOrDefault(player.getUniqueId(), new ArrayList<>()).add(itemData);

        fromCacheToFile(player);
    }

    public void removeItem(Player player, ItemData itemData) {
        playerBag.getOrDefault(player.getUniqueId(), new ArrayList<>()).remove(itemData);

        fromCacheToFile(player);
    }

    public Boolean hasItem(Player player, ItemData itemData) {
        return playerBag.getOrDefault(player.getUniqueId(), new ArrayList<>()).contains(itemData);
    }

    public Boolean hasCached(String playerName) {
        if (Bukkit.getPlayer(playerName) == null) {
            return offlinePlayerBag.containsKey(playerName);
        }

        return playerBag.containsKey(Bukkit.getPlayer(playerName).getUniqueId());
    }

    public void removeCache(Player player) {
        removeCache(player.getUniqueId());
    }

    public void removeCache(UUID uuid) {
        fromCacheToFile(uuid);

        playerBag.remove(uuid);
    }

    public void fromFileToCache(Player player) {
        List<ItemData> items = convertStringListToCacheData(instance.getFileManager().getPlayerBagItems(player));
        playerBag.put(player.getUniqueId(), items);

        Logging.debug("BagGUI", "Bag owner [" + player.getName() + "] has been cached (Online).");
    }

    public void fromFileToCache(String playerName) {
        List<ItemData> items = convertStringListToCacheData(instance.getFileManager().getPlayerBagItems(playerName));

        offlinePlayerBag.put(playerName, items);

        Logging.debug("BagGUI", "Bag owner [" + playerName + "] has been cached (Offline).");
    }

    public void fromCacheToFile(Player player) {
        fromCacheToFile(player.getUniqueId());
    }

    public void fromCacheToFile(UUID uuid) {
        String playerName = Bukkit.getPlayer(uuid) == null ? Bukkit.getOfflinePlayer(uuid).getName() : Bukkit.getPlayer(uuid).getName();

        List<String> items = convertCacheDataToStringList(playerBag.get(uuid));

        instance.getFileManager().setPlayerBagItems(playerName, items);
    }

    public void fromCacheToFile(String playerName) {
        List<String> items = convertCacheDataToStringList(offlinePlayerBag.get(playerName));

        instance.getFileManager().setPlayerBagItems(playerName, items);
    }

    private List<ItemData> convertStringListToCacheData(List<String> items) {
        List<ItemData> result = new ArrayList<>();

        for (String item : items) {
            ItemName id = ItemName.valueOf(item.split(":")[0]);
            Integer level = Integer.parseInt(item.split(":")[1]);

            result.add(instance.getItemManager().getItem(id, level));
        }

        return result;
    }

    private List<String> convertCacheDataToStringList(List<ItemData> items) {
        List<String> result = new ArrayList<>();

        for (ItemData item : items)
            result.add(item.getId() + ":" + item.getLevel());

        return result;
    }

    public Boolean isBagEmpty(Player player) {
        return playerBag.getOrDefault(player.getUniqueId(), new ArrayList<>()).isEmpty();
    }

    public ItemData getItem(Player player, Integer index) {
        if (index > playerBag.getOrDefault(player.getUniqueId(), new ArrayList<>()).size())
            return null;

        return playerBag.getOrDefault(player.getUniqueId(), new ArrayList<>()).get(index);
    }

    public ItemData getItem(String playerName, Integer index) {
        UUID uuid = Bukkit.getPlayer(playerName) == null ? Bukkit.getOfflinePlayer(playerName).getUniqueId() : Bukkit.getPlayer(playerName).getUniqueId();

        if (index > playerBag.getOrDefault(uuid, new ArrayList<>()).size())
            return null;

        return playerBag.getOrDefault(uuid, new ArrayList<>()).get(index);
    }

    public List<ItemData> getItems(Player player) {
        return playerBag.getOrDefault(player.getUniqueId(), new ArrayList<>());
    }

    public List<ItemData> getItems(String playerName) {
        if (Bukkit.getPlayer(playerName) == null) {

        }

        UUID uuid = Bukkit.getPlayer(playerName) == null ? Bukkit.getOfflinePlayer(playerName).getUniqueId() : Bukkit.getPlayer(playerName).getUniqueId();

        return playerBag.getOrDefault(uuid, new ArrayList<>());
    }

    public void onQuit(PlayerQuitEvent event) {
        removeCache(event.getPlayer());
    }

}
