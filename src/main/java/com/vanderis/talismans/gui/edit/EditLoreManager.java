package com.vanderis.talismans.gui.edit;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.utils.*;
import my.plugin.utils.XSound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class EditLoreManager {

    private final Talismans instance = Talismans.getInstance();
    private final EditManager editManager = instance.getEditManager();

    public void onEditClick(InventoryClickEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof EditLoreGUI))
            return;

        int slot = event.getRawSlot();

        Player player = (Player) event.getWhoClicked();
        player.playSound(player, XSound.BLOCK_WOODEN_BUTTON_CLICK_OFF.parseSound(), 20, 20);

        EditLoreGUI gui = (EditLoreGUI) instance.getGuiManager().getCacheGUI(player);

        if (slot >= 0 && slot < gui.getItemsPutInGUI().size()) {
            if (gui.getType(slot) == null)
                return;

            if (gui.isType(slot, "add-line")) {
                event.setCancelled(true);

                Message.sendMessage(player, "");
                Message.sendMessage(player, "&fType in the chat to add a line.");
                Message.sendMessage(player, "");
                Message.sendMessage(player, "&7Type &e\"\" &7to add empty line.");
                Message.sendMessage(player, "&7Type &ccancel &7to cancel the edit.");

                editManager.playerChatEdit.put(player, new EditData<Integer>(gui.getItemData(), "add-line"));

                player.closeInventory();
            }

            if (gui.isType(slot, "insert-line")) {
                event.setCancelled(true);

                Message.sendMessage(player, "&fPlease type the line number to &einsert&f.");
                Message.sendMessage(player, "");
                Message.sendMessage(player, "&7Type &ccancel &7to cancel the edit.");

                editManager.playerChatEdit.put(player, new EditData<Integer>(gui.getItemData(), "insert-line"));

                player.closeInventory();
            }

            if (gui.isType(slot, "remove-line")) {
                event.setCancelled(true);

                if (event.getClick() == ClickType.LEFT) {
                    Message.sendMessage(player, "&fPlease type the line number to &cremove&f.");
                    Message.sendMessage(player, "");
                    Message.sendMessage(player, "&7Type &ccancel &7to cancel the edit.");

                    editManager.playerChatEdit.put(player, new EditData<Integer>(gui.getItemData(), "insert-line"));

                    player.closeInventory();
                }

                if (event.getClick() == ClickType.RIGHT) {
                    ItemMeta itemMeta = gui.getItemData().getItemResult().getItemMeta();
                    itemMeta.setLore(null);

                    gui.getItemData().getItemResult().setItemMeta(itemMeta);

                    gui.getItemData().saveToFile();

                    Message.sendMessage(player, "");
                    Message.sendMessage(player, "&aTalismans lore have been cleared.");
                    Message.sendMessage(player, "");
                }
            }

            if (gui.isType(slot, "close")) {
                event.setCancelled(true);

                player.closeInventory();

                instance.getGuiManager().openGUI(player, new EditGUI(gui.getItemData()));
            }
        }
    }

    public void onEditClose(InventoryCloseEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof EditLoreGUI))
            return;

        if (editManager.playerChatEdit.containsKey((Player) event.getPlayer())) // This means player still edit, so ignore close gui which lead to announce save item
            return;

        Player player = (Player) event.getPlayer();
        player.playSound(player, XSound.BLOCK_WOODEN_BUTTON_CLICK_OFF.parseSound(), 20, 20);

        Message.sendMessage(player, Message.prefix() + " &aYour changes have been saved!");
    }

    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (!editManager.playerChatEdit.containsKey(player))
            return;

        EditData editData = editManager.playerChatEdit.get(player);

        event.setCancelled(true);

        if (message.trim().equalsIgnoreCase("cancel")) {
            instance.getGuiManager().openGUI(player, new EditLoreGUI(editData.getItemData()));

            editManager.playerChatEdit.remove(player);

            return;
        }

        if (editData.getEditType().equalsIgnoreCase("add-line")) {
            ItemStack item = addLoreItem(editData, message);

            editData.getItemData().setItemResult(item);

            editData.getItemData().saveToFile();

            instance.getGuiManager().openGUI(player, new EditLoreGUI(editData.getItemData()));

            editManager.playerChatEdit.remove(player);
        }

        if (editData.getEditType().equalsIgnoreCase("insert-line")) {
            if (editData.getEditValue_Number() == null) {
                try {
                    int lineNumber = Integer.parseInt(message);

                    if (lineNumber <= 0) {
                        Message.sendMessage(player, Message.prefix() + " &cNumber must be positive and over 0.");

                        return;
                    }

                    editManager.playerChatEdit.put(player, new EditData(editData.getItemData(), "insert-line", lineNumber));

                    Message.sendMessage(player, "");
                    Message.sendMessage(player, "&fType in the chat to insert a line.");
                    Message.sendMessage(player, "");
                    Message.sendMessage(player, "&7Type &e\"\" &7to add empty line.");
                    Message.sendMessage(player, "&7Type &ccancel &7to cancel the edit.");
                } catch (NumberFormatException ignored) {
                    instance.getGuiManager().openGUI(player, new EditLoreGUI(editData.getItemData()));

                    editManager.playerChatEdit.remove(player);
                }

                return;
            }

            ItemStack item = insertLoreItem(editData, message);

            editData.getItemData().setItemResult(item);

            editData.getItemData().saveToFile();

            instance.getGuiManager().openGUI(player, new EditLoreGUI(editData.getItemData()));

            editManager.playerChatEdit.remove(player);
        }

        if (editData.getEditType().equalsIgnoreCase("remove-line")) {
            if (editData.getEditValue_Number() == null) {
                try {
                    int lineNumber = Integer.parseInt(message);

                    if (lineNumber <= 0) {
                        Message.sendMessage(player, Message.prefix() + " &cNumber must be positive and over 0.");

                        return;
                    }

                    if (loreSize(editData) < lineNumber) {
                        Message.sendMessage(player, Message.prefix() + " &cThe line number must be below " + loreSize(editData) + ".");

                        return;
                    }

                    editManager.playerChatEdit.put(player, new EditData(editData.getItemData(), "insert-line", lineNumber));

                    ItemStack item = removeLoreItem(editData, lineNumber);

                    editData.getItemData().setItemResult(item);

                    editData.getItemData().saveToFile();
                } catch (NumberFormatException ignored) {
                    instance.getGuiManager().openGUI(player, new EditLoreGUI(editData.getItemData()));

                    editManager.playerChatEdit.remove(player);
                }

                return;
            }

            instance.getGuiManager().openGUI(player, new EditLoreGUI(editData.getItemData()));

            editManager.playerChatEdit.remove(player);
        }
    }

    private ItemStack addLoreItem(EditData editData, String message) {
        ItemStack item = editData.getItemData().getItemResult();
        ItemMeta meta = item.getItemMeta();

        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        if (message.equalsIgnoreCase("\"\"")) {
            lore.add("");
        } else
            lore.add(Color.color(message));

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack insertLoreItem(EditData<Integer> editData, String message) {
        ItemStack item = editData.getItemData().getItemResult();
        ItemMeta meta = item.getItemMeta();

        List<String> originalLore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        List<String> insertedLore = new ArrayList<>();

        for (int i = 0; i < originalLore.size(); ++i) {
            if (i == editData.getEditValue_Number() - 1) {
                insertedLore.add(Color.color(message));

                continue;
            }

            insertedLore.add(originalLore.get(i));
        }

        meta.setLore(insertedLore);

        item.setItemMeta(meta);
        return item;
    }

    private ItemStack removeLoreItem(EditData editData, int lineNumber) {
        ItemStack item = editData.getItemData().getItemResult();
        ItemMeta meta = item.getItemMeta();

        List<String> lore = meta.getLore();
        lore.remove(lineNumber - 1);

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    private Integer loreSize(EditData editData) {
        ItemStack item = editData.getItemData().getItemResult();
        ItemMeta meta = item.getItemMeta();

        return meta.getLore().size();
    }

}
