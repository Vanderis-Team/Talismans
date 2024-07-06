package com.vanderis.talismans.functions;

import com.vanderis.talismans.containers.*;
import com.vanderis.talismans.gui.BagGUI;
import my.plugin.utils.XSound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class GUISystem implements Instance {

    private final Map<Player, GUIHolder> cacheGUI = new HashMap<>();

    public void openGUI(Player player, GUIHolder holder) {
        holder.openInventory(player);
        holder.updateInventory();

        cacheGUI.put(player, holder);
    }

    public void updateGUI(Player player) {
        cacheGUI.get(player).updateInventory();
    }

    public void updateGUI(Player player, List<String> guiItemTypeUpdate) {
        cacheGUI.get(player).updateInventory(guiItemTypeUpdate);
    }

    public GUIHolder getCacheGUI(Player player) {
        return cacheGUI.get(player);
    }

    public void onBagClick(InventoryClickEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof BagGUI))
            return;

        int slot = event.getRawSlot();
        ItemStack itemPressOn = event.getCurrentItem();

        Player player = (Player) event.getWhoClicked();
        player.playSound(player, XSound.BLOCK_WOODEN_BUTTON_CLICK_OFF.parseSound(), 1, 1);

        event.setCancelled(true);

        BagGUI gui = (BagGUI) instance.getGuiSystem().getCacheGUI(player);

        if (slot > gui.getItemsPutInGUI().size()) {


            return;
        }

        gui.getItemsPutInGUI().get(slot).getType().equalsIgnoreCase("");

        // TODO: add & remove from bag
    }

}
