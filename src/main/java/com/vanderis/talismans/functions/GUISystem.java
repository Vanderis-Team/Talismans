package com.vanderis.talismans.functions;

import com.vanderis.talismans.containers.*;
import com.vanderis.talismans.gui.BagGUI;
import com.vanderis.talismans.managers.*;
import com.vanderis.talismans.messages.Message;
import my.plugin.utils.XSound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class GUISystem implements Instance {

    private final Map<Player, GUIHolder> cacheGUI = new HashMap<>();

    private final ItemManager itemManager = instance.getItemManager();
    private final BagManager bagManager = instance.getBagManager();

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

        if (slot >= 0 && slot <= gui.getItemsPutInGUI().size()) { // Top Inventory
            if (gui.getType(slot) == null)
                return;

            if (gui.isType(slot, "item-slot")) { // Remove Item From Bag
                if (bagManager.isBagEmpty(player))
                    return;

                ItemData itemData = bagManager.getItem(player, gui.getTypeOrder(slot, "item-slot"));

                if (itemData == null)
                    return;

                bagManager.removeItem(player, itemData);
                updateGUI(player);

                itemData.giveItemResult(player);
            }

            if (gui.isType(slot, "block-slot")) {
                Message.sendMessage(player, "&cThis slot is blocked, please purchase new slot to open!");

                player.playSound(player, XSound.BLOCK_ANVIL_FALL.parseSound(), 1, 1);
            }
        }

        if (slot > gui.getItemsPutInGUI().size() && slot <= gui.getItemsPutInGUI().size() + 36) { // Bottom Inventory
            if (itemManager.getItem(itemPressOn) == null)
                return;

            ItemData itemData = itemManager.getItem(itemPressOn);

            /* Add Item To Bag */
            bagManager.addItem(player, itemData);
            updateGUI(player);

            itemPressOn.setAmount(itemPressOn.getAmount() - 1);
        }
    }

}
