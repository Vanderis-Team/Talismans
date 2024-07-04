package com.vanderis.talismans.functions;

import com.vanderis.talismans.containers.GUIHolder;
import org.bukkit.entity.Player;

public class GUISystem {

    public void openGUI(Player player, GUIHolder holder) {
        holder.openInventory(player);
        holder.updateInventory();
    }

    public void updateGUI(Player player) {
        if (player.getOpenInventory().getTopInventory().getHolder() instanceof GUIHolder) {
            GUIHolder holder = (GUIHolder) player.getOpenInventory().getTopInventory().getHolder();

            holder.updateInventory();
        }
    }

}
