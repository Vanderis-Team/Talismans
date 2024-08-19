package com.vanderis.talismans.gui.edit;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.items.ItemData;
import com.vanderis.talismans.utils.*;
import my.plugin.utils.XSound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class EditManager {

    private final Talismans instance = Talismans.getInstance();

    public final Map<Player, EditData> playerChatEdit = new HashMap<>();

    public void onEditClick(InventoryClickEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof EditGUI))
            return;

        int slot = event.getRawSlot();

        Player player = (Player) event.getWhoClicked();
        player.playSound(player, XSound.BLOCK_WOODEN_BUTTON_CLICK_OFF.parseSound(), 20, 20);

        EditGUI gui = (EditGUI) instance.getGuiManager().getCacheGUI(player);

        if (slot >= 0 && slot < gui.getItemsPutInGUI().size()) {
            if (gui.getType(slot) == null)
                return;

            if (gui.isType(slot, "item-slot")) {
                Bukkit.getScheduler().runTaskLater(instance, () -> saveItem(player, gui), 1L);
            }

            if (gui.isType(slot, "item-border"))
                event.setCancelled(true);

            if (gui.isType(slot, "edit-name")) {
                event.setCancelled(true);

                if (gui.getItemData().getItemResult() == null) {
                    Message.sendMessage(player, Message.prefix() + " " + gui.getItemData().getName() + " &chas no item stand for it!");
                    Message.sendMessage(player, Message.prefix() + " &fPlease put an item into talismans slot!");

                    player.playSound(player, XSound.ENTITY_VILLAGER_NO.parseSound(), 20, 1);

                    return;
                }

                Message.sendMessage(player, "&fCurrent name: " +
                        (gui.getItemData().getItemResult().getItemMeta().hasDisplayName()
                        ? gui.getItemData().getItemResult().getItemMeta().getDisplayName()
                        : gui.getItemData().getItemResult().getType().name()));

                Message.sendMessage(player, "");
                Message.sendMessage(player, "&7Type in the chat to change the name.");
                Message.sendMessage(player, "&7Type &ccancel &7to cancel the edit.");

                playerChatEdit.put(player, new EditData<>(gui.getItemData(), "edit-name"));

                player.closeInventory();
            }

            if (gui.isType(slot, "edit-lore")) {
                event.setCancelled(true);

                if (gui.getItemData().getItemResult() == null) {
                    Message.sendMessage(player, Message.prefix() + " " + gui.getItemData().getName() + " &chas no item stand for it!");
                    Message.sendMessage(player, Message.prefix() + " &fPlease put an item into talismans slot!");

                    player.playSound(player, XSound.ENTITY_VILLAGER_NO.parseSound(), 20, 1);

                    return;
                }

                player.closeInventory();

                instance.getGuiManager().openGUI(player, new EditLoreGUI(gui.getItemData()));
            }

            if (gui.isType(slot, "edit-effect")) {
                event.setCancelled(true);

                if (gui.getItemData().getItemResult() == null) {
                    Message.sendMessage(player, Message.prefix() + " " + gui.getItemData().getName() + " &chas no item stand for it!");
                    Message.sendMessage(player, Message.prefix() + " &fPlease put an item into talismans slot!");

                    player.playSound(player, XSound.ENTITY_VILLAGER_NO.parseSound(), 20, 1);

                    return;
                }

                player.closeInventory();

                instance.getGuiManager().openGUI(player, new EditEffectGUI(gui.getItemData()));
            }

            if (gui.isType(slot, "allow-crafting") ||
                    gui.isType(slot, "prevent-crafting")) {

                event.setCancelled(true);

                gui.getItemData().toggleCraftable();

                if (gui.getItemData().isCraftable())
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

    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (!playerChatEdit.containsKey(player))
            return;

        EditData editData = playerChatEdit.get(player);

        event.setCancelled(true);

        if (message.trim().equalsIgnoreCase("cancel")) {
            instance.getGuiManager().openGUI(player, new EditGUI(editData.getItemData()));

            playerChatEdit.remove(player);

            return;
        }

        if (playerChatEdit.get(player).getEditType().equalsIgnoreCase("edit-name")) {
            ItemStack item = editData.getItemData().getItemResult();
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(Color.color(message));

            item.setItemMeta(meta);

            editData.getItemData().setItemResult(item);

            instance.getGuiManager().openGUI(player, new EditGUI(editData.getItemData()));

            playerChatEdit.remove(player);
        }
    }

    public void onEditClose(InventoryCloseEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof EditGUI))
            return;

        if (playerChatEdit.containsKey((Player) event.getPlayer())) // This means player still edit, so ignore close gui which lead to announce save item
            return;

        Player player = (Player) event.getPlayer();
        player.playSound(player, XSound.BLOCK_WOODEN_BUTTON_CLICK_OFF.parseSound(), 20, 20);

        EditGUI gui = (EditGUI) instance.getGuiManager().getCacheGUI(player);

        saveItem(player, gui);

        Message.sendMessage(player, Message.prefix() + " &aYour changes have been saved!");
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

        playerChatEdit.remove(player);
    }

}
