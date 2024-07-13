package com.vanderis.talismans.gui.edit;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.utils.*;
import my.plugin.utils.XSound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;

import java.util.ArrayList;

public class EditManager {

    private final Talismans instance = Talismans.getInstance();

    public void onEditClick(InventoryClickEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof EditGUI))
            return;

        int slot = event.getRawSlot();

        Player player = (Player) event.getWhoClicked();
        player.playSound(player, XSound.BLOCK_WOODEN_BUTTON_CLICK_OFF.parseSound(), 20, 20);

        EditGUI gui = (EditGUI) instance.getGuiManager().getCacheGUI(player);

        if (slot >= 0 && slot < gui.getItemsPutInGUI().size()) { // Top Inventory
            if (gui.getType(slot) == null)
                return;

            if (gui.isType(slot, "item-border"))
                event.setCancelled(true);

            if (gui.isType(slot, "allow-crafting") ||
                    gui.isType(slot, "prevent-crafting")) {

                event.setCancelled(true);

                gui.getItemData().toggleCraftable();

                if (gui.getItemData().getCraftable())
                    Message.sendMessage(player, Message.prefix() + " &aCraftable Enabled!");
                else
                    Message.sendMessage(player, Message.prefix() + " &cCraftable Disabled!");

                gui.replaceCraftableIcon();
            }

            if (gui.isType(slot, "close")) {
                event.setCancelled(true);

                player.closeInventory(); // Close gui will automatically save the item in the onEditClose() method
            }
        }
    }

    public void onEditClose(InventoryCloseEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof EditGUI))
            return;

        Player player = (Player) event.getPlayer();
        player.playSound(player, XSound.BLOCK_WOODEN_BUTTON_CLICK_OFF.parseSound(), 20, 20);

        EditGUI gui = (EditGUI) instance.getGuiManager().getCacheGUI(player);

        saveItem(player, gui);
    }

    private void saveItem(Player player, EditGUI gui) {
        gui.getItemData().setRecipe(new ArrayList<>());

        for (int i = 0; i < gui.getItemsPutInGUI().size(); i++) {

            if (gui.isType(i, "item-slot")) {
                gui.getItemData().setItemResult(player.getOpenInventory().getTopInventory().getItem(i));
            }

            if (gui.isType(i, "allow-crafting") || gui.isType(i, "prevent-crafting")) {
                gui.getItemData().toggleCraftable();
            }

            if (gui.isType(i, "recipe")) {
                gui.getItemData().getRecipe().add(player.getOpenInventory().getTopInventory().getItem(i));
            }

        }

        gui.getItemData().saveToFile();

        Message.sendMessage(player, Message.prefix() + " &aYour changes have been saved!");
    }

}
