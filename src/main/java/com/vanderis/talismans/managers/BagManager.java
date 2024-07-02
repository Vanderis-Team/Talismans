package com.vanderis.talismans.managers;

import com.vanderis.talismans.containers.ItemData;
import org.bukkit.entity.Player;

import java.util.*;

public class BagManager {

    private final Map<UUID, List<ItemData>> playerBag = new HashMap<>();

    public void addTalisman(Player player, ItemData itemData) {
        playerBag.getOrDefault(player.getUniqueId(), new ArrayList<>()).add(itemData);
    }

    public Boolean hasTalisman(Player player, ItemData itemData) {
        return playerBag.getOrDefault(player.getUniqueId(), new ArrayList<>()).contains(itemData);
    }

}
