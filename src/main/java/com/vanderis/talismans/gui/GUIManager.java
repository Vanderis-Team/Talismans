package com.vanderis.talismans.gui;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.gui.bag.BagGUI;
import com.vanderis.talismans.gui.bag.BagManager;
import com.vanderis.talismans.items.*;
import com.vanderis.talismans.utils.Message;
import my.plugin.utils.XSound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class GUIManager {

    private final Map<Player, GUIHolder> cacheGUI = new HashMap<>();

    public void unregister() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getOpenInventory().getTopInventory().getHolder() instanceof GUIHolder)
                player.closeInventory();
        }
    }

    public void openGUI(Player player, GUIHolder holder) {
        holder.openInventory(player);
        holder.updateInventory();

        cacheGUI.put(player, holder);
    }

    public void updateGUI(Player player) {
        cacheGUI.get(player).updateInventory();
    }

    public GUIHolder getCacheGUI(Player player) {
        return cacheGUI.get(player);
    }

    public void closeInventory(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof GUIHolder)
            cacheGUI.remove((Player) event.getPlayer());
    }

}
