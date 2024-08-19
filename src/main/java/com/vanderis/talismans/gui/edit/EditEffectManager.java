package com.vanderis.talismans.gui.edit;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.items.*;
import com.vanderis.talismans.utils.Message;
import my.plugin.utils.XSound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class EditEffectManager {

    private final Talismans instance = Talismans.getInstance();
    private final EditManager editManager = instance.getEditManager();
    private final EffectManager effectManager = instance.getEffectManager();

    public void onEditClick(InventoryClickEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof EditEffectGUI))
            return;

        int slot = event.getRawSlot();

        Player player = (Player) event.getWhoClicked();
        player.playSound(player, XSound.BLOCK_WOODEN_BUTTON_CLICK_OFF.parseSound(), 20, 20);

        EditEffectGUI gui = (EditEffectGUI) instance.getGuiManager().getCacheGUI(player);

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

                String effectID = effectManager.effectNameList.get(itemOrder);

                Message.sendMessage(player, "");
                Message.sendMessage(player, "&fPlease type a number to change the value of effect &e" + effectID + "&f.");
                Message.sendMessage(player, "");
                Message.sendMessage(player, "&cAccepted type: &enumber, multiply, percent");
                Message.sendMessage(player, "&cExample: &e10, 50x, 100%");
                Message.sendMessage(player, "");
                Message.sendMessage(player, "&7Type &ccancel &7to cancel the edit.");

                editManager.playerChatEdit.put(player, new EditData(gui.getItemData(), "edit-effect", effectID));

                player.closeInventory();
            }

            if (gui.isType(slot, "close")) {
                event.setCancelled(true);

                player.closeInventory();

                instance.getGuiManager().openGUI(player, new EditGUI(gui.getItemData()));
            }
        }
    }

    public void onEditClose(InventoryCloseEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof EditEffectGUI))
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
            instance.getGuiManager().openGUI(player, new EditEffectGUI(editData.getItemData()));

            editManager.playerChatEdit.remove(player);

            return;
        }

        if (editData.getEditType().equalsIgnoreCase("edit-effect")) {
            try {
                String effectValue = tryReproduceEffectValueFormat(message);

                editData.getItemData().setEffectValue(editData.getEditValue_String(), effectValue);

                editData.getItemData().saveToFile();

                instance.getGuiManager().openGUI(player, new EditEffectGUI(editData.getItemData()));

                editManager.playerChatEdit.remove(player);

            } catch (NumberFormatException ignored) {
            }

        }
    }

    /**
     * @return String if can reproduce the right format, null if cannot reproduce
     */
    private String tryReproduceEffectValueFormat(String text) {
        if (text.contains("%")) {
            try {
                Double value = Double.parseDouble(text.split("%")[0]);

                return value + "%";
            } catch (NumberFormatException ignored) {
                return null;
            }
        }

        if (text.contains("x")) {
            try {
                Double value = Double.parseDouble(text.split("x")[0]);

                return value + "x";
            } catch (NumberFormatException ignored) {
                return null;
            }
        }

        try {
            Double value = Double.parseDouble(text);

            return String.valueOf(value);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

}
