package com.vanderis.talismans.gui.crafting;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.items.ItemData;
import com.vanderis.talismans.utils.*;
import my.plugin.utils.XSound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;

public class CraftingManager {

    private final Talismans instance = Talismans.getInstance();

    public void onCraftingClick(InventoryClickEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof CraftingGUI))
            return;

        int slot = event.getRawSlot();

        Player player = (Player) event.getWhoClicked();
        player.playSound(player, XSound.BLOCK_WOODEN_BUTTON_CLICK_OFF.parseSound(), 20, 20);

        CraftingGUI gui = (CraftingGUI) instance.getGuiManager().getCacheGUI(player);

        Bukkit.getScheduler().runTaskLaterAsynchronously(instance, gui::checkRecipe, 1L);

        if (slot >= 0 && slot < gui.getItemsPutInGUI().size()) {
            if (gui.getType(slot) == null)
                return;

            if (gui.isType(slot, "border") ||
                    gui.isType(slot, "recipe-border") ||
                    gui.isType(slot, "result-border"))
                event.setCancelled(true);


            if (gui.isType(slot, "result-slot")) {

                event.setCancelled(true);

                if (gui.result != null) {
                    ItemData result = gui.result;

                    result.giveItemResult(player);

                    gui.result = null;

                    Message.sendMessage(player, Message.prefix() + " &aYou just successfully crafted " + result.getName() + "&a!");

                    gui.getInventory().clear();
                    gui.updateInventory();
                }
            }

        }
    }

    public void onCraftingClose(InventoryCloseEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof CraftingGUI))
            return;

        Player player = (Player) event.getPlayer();
        player.playSound(player, XSound.BLOCK_WOODEN_BUTTON_CLICK_OFF.parseSound(), 20, 20);

        CraftingGUI gui = (CraftingGUI) instance.getGuiManager().getCacheGUI(player);

        gui.returnRecipe(player);
    }

}
