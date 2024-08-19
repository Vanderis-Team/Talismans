package com.vanderis.talismans.gui.collections;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.gui.edit.EditGUI;
import com.vanderis.talismans.items.*;
import com.vanderis.talismans.utils.Message;
import my.plugin.utils.XSound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;

public class CollectionsManager {

    private final Talismans instance = Talismans.getInstance();
    private final ItemManager itemManager = instance.getItemManager();

    public void onCollectionsClick(InventoryClickEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof CollectionsGUI))
            return;

        int slot = event.getRawSlot();

        Player player = (Player) event.getWhoClicked();
        player.playSound(player, XSound.BLOCK_WOODEN_BUTTON_CLICK_OFF.parseSound(), 20, 20);

        CollectionsGUI gui = (CollectionsGUI) instance.getGuiManager().getCacheGUI(player);

        if (slot >= 0 && slot < gui.getItemsPutInGUI().size()) {
            if (gui.getType(slot) == null)
                return;

            if (gui.isType(slot, "item-border"))
                event.setCancelled(true);

            if (gui.isType(slot, "previous-page")) {

                event.setCancelled(true);

                gui.previousPage();
            }

            if (gui.isType(slot, "next-page")) {

                event.setCancelled(true);

                gui.nextPage();
            }

            if (gui.isType(slot, "item-slot")) {
                event.setCancelled(true);

                int itemOrder = gui.getCurrentPage() > 1 ? gui.getTypeOrder(slot, "item-slot") + 36 : gui.getTypeOrder(slot, "item-slot");

                if (event.getClick() == ClickType.LEFT) {
                    ItemData itemData = itemManager.itemList.get(itemOrder);

                    if (itemData.getItemResult() == null) {
                        Message.sendMessage(player, Message.prefix() + " " + itemData.getName() + " &chas no item stand for it!");
                        Message.sendMessage(player, Message.prefix() + " &fPlease do &e/talismans edit " + itemData.getId() + " &fto edit the talismans.");

                        player.playSound(player, XSound.ENTITY_VILLAGER_NO.parseSound(), 20, 1);

                        return;
                    }

                    itemData.giveItemResult(player);
                }

                if (event.getClick() == ClickType.RIGHT) {
                    ItemData itemData = itemManager.itemList.get(itemOrder);

                    instance.getGuiManager().openGUI(player, new EditGUI(itemData));
                }
            }
        }
    }

}
