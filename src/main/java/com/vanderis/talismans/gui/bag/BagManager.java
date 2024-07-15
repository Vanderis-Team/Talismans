package com.vanderis.talismans.gui.bag;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.events.EquipTalismanEvent;
import com.vanderis.talismans.items.*;
import com.vanderis.talismans.player.PlayerData;
import com.vanderis.talismans.utils.*;
import my.plugin.utils.XSound;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BagManager {

    private final Talismans instance = Talismans.getInstance();

    private final ItemManager itemManager = instance.getItemManager();

    public void addItem(Player player, ItemData itemData) {
        instance.getPlayerManager().playerData.getOrDefault(player.getUniqueId(), new PlayerData()).getBagItems().add(itemData);

        fromCacheToFile(player);
    }

    public void removeItem(Player player, ItemData itemData) {
        instance.getPlayerManager().playerData.getOrDefault(player.getUniqueId(), new PlayerData()).getBagItems().remove(itemData);

        fromCacheToFile(player);
    }

    public void fromFileToCache(Player player) {
        Integer size = instance.getFileManager().getSize(player);
        List<ItemData> items = convertStringListToCacheData(instance.getFileManager().getBagItems(player));
        instance.getPlayerManager().playerData.put(player.getUniqueId(), new PlayerData(player, size, items));

        Logging.debug("BagGUI", "Bag owner [" + player.getName() + "] has been cached (Online).");
    }

    public void fromFileToCache(String playerName) {
        Integer size = instance.getFileManager().getSize(playerName);
        List<ItemData> items = convertStringListToCacheData(instance.getFileManager().getBagItems(playerName));

        instance.getPlayerManager().offlinePlayerData.put(playerName, new PlayerData(null, size, items));

        Logging.debug("BagGUI", "Bag owner [" + playerName + "] has been cached (Offline).");
    }

    public void fromCacheToFile(Player player) {
        fromCacheToFile(player.getUniqueId());
    }

    public void fromCacheToFile(UUID uuid) {
        String playerName = Bukkit.getPlayer(uuid).getName();

        if (!instance.getPlayerManager().hasCached(playerName))
            return;

        Integer size = instance.getPlayerManager().playerData.get(uuid).getBagSize();
        List<String> items = convertCacheDataToStringList(instance.getPlayerManager().playerData.get(uuid).getBagItems());

        instance.getFileManager().setBagSize(playerName, size);
        instance.getFileManager().setBagItems(playerName, items);
    }

    public void fromCacheToFile(String playerName) {
        Integer size = instance.getPlayerManager().offlinePlayerData.get(playerName).getBagSize();
        List<String> items = convertCacheDataToStringList(instance.getPlayerManager().offlinePlayerData.get(playerName).getBagItems());

        instance.getFileManager().setBagSize(playerName, size);
        instance.getFileManager().setBagItems(playerName, items);
    }

    private List<ItemData> convertStringListToCacheData(List<String> items) {
        List<ItemData> result = new ArrayList<>();

        for (String item : items) {
            ItemName id = ItemName.valueOf(item.split(":")[0]);
            Integer level = Integer.parseInt(item.split(":")[1]);

            result.add(instance.getItemManager().getItem(id, level));
        }

        return result;
    }

    private List<String> convertCacheDataToStringList(List<ItemData> items) {
        List<String> result = new ArrayList<>();

        for (ItemData item : items)
            result.add(item.getId() + ":" + item.getLevel());

        return result;
    }

    public Boolean isBagEmpty(Player player) {
        return instance.getPlayerManager().playerData.getOrDefault(player.getUniqueId(), new PlayerData()).getBagItems().isEmpty();
    }

    public ItemData getItem(Player player, Integer index) {
        if (index >= instance.getPlayerManager().playerData.getOrDefault(player.getUniqueId(), new PlayerData(player)).getBagItems().size())
            return null;

        return instance.getPlayerManager().playerData.getOrDefault(player.getUniqueId(), new PlayerData(player)).getBagItems().get(index);
    }

    public ItemData getItem(String playerName, Integer index) {
        UUID uuid = Bukkit.getPlayer(playerName) == null ? Bukkit.getOfflinePlayer(playerName).getUniqueId() : Bukkit.getPlayer(playerName).getUniqueId();

        if (index > instance.getPlayerManager().playerData.getOrDefault(uuid, new PlayerData()).getBagItems().size())
            return null;

        return instance.getPlayerManager().playerData.getOrDefault(uuid, new PlayerData()).getBagItems().get(index);
    }

    public List<ItemData> getItems(Player player) {
        return instance.getPlayerManager().playerData.getOrDefault(player.getUniqueId(), new PlayerData(player)).getBagItems();
    }

    public List<ItemData> getItems(String playerName) {
        if (Bukkit.getPlayer(playerName) == null)
            return instance.getPlayerManager().offlinePlayerData.getOrDefault(playerName, new PlayerData()).getBagItems();

        UUID uuid = Bukkit.getPlayer(playerName).getUniqueId();

        return instance.getPlayerManager().playerData.getOrDefault(uuid, new PlayerData()).getBagItems();
    }

    public void onBagClick(InventoryClickEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof BagGUI))
            return;

        int slot = event.getRawSlot();
        ItemStack itemPressOn = event.getCurrentItem();

        Player player = (Player) event.getWhoClicked();
        player.playSound(player, XSound.BLOCK_WOODEN_BUTTON_CLICK_OFF.parseSound(), 20, 20);

        event.setCancelled(true);

        BagGUI gui = (BagGUI) instance.getGuiManager().getCacheGUI(player);

        if (slot >= 0 && slot < gui.getItemsPutInGUI().size()) { // Top Inventory
            if (gui.getType(slot) == null)
                return;

            if (gui.isType(slot, "item-slot")) { // Remove Item From Bag
                if (isBagEmpty(player))
                    return;

                ItemData itemData = getItem(player, gui.getTypeOrder(slot, "item-slot"));

                Logging.log("BagManager: " + gui.getTypeOrder(slot, "item-slot"));

                if (itemData == null)
                    return;

                removeItem(player, itemData);
                instance.getGuiManager().updateGUI(player);

                itemData.giveItemResult(player);

                Message.sendMessage(player, Message.prefix() + " &aYou just remove " + itemData.getName() + " &aout of your bag!");
            }

            if (gui.isType(slot, "block-slot")) {
                Message.sendMessage(player, Message.prefix() + " &cThis slot is blocked, please purchase new slot to open!");

                player.playSound(player, XSound.BLOCK_ANVIL_LAND.parseSound(), 20, 20);
            }
        }

        if (slot >= gui.getItemsPutInGUI().size() && slot < gui.getItemsPutInGUI().size() + 36) { // Bottom Inventory
            if (itemManager.getItem(itemPressOn) == null) {
                Message.sendMessage(player, Message.prefix() + " &cThat item is not a talisman.");

                player.playSound(player, XSound.ENTITY_VILLAGER_NO.parseSound(), 20, 10);

                return;
            }

            ItemData itemData = itemManager.getItem(itemPressOn);

            /* Add Item To Bag */
            addItem(player, itemData);
            instance.getGuiManager().updateGUI(player);

            itemPressOn.setAmount(itemPressOn.getAmount() - 1);

            EquipTalismanEvent equipTalismanEvent = new EquipTalismanEvent(player, itemData, EquipTalismanEvent.InventoryType.BAG);

            instance.callEvent(equipTalismanEvent);
        }
    }

}
