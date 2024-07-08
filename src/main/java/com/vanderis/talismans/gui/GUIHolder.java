package com.vanderis.talismans.gui;

import com.vanderis.talismans.utils.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public abstract class GUIHolder implements InventoryHolder {

    protected final FileConfiguration fileConfiguration;
    protected final List<GUIItem> items;
    protected final List<GUIItem> itemsPutInGUI;
    protected Player viewer;
    protected Inventory inventory;

    public GUIHolder(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;

        String title = Color.color(fileConfiguration.getString("title"));
        int rowSize = Math.min(fileConfiguration.getStringList("format").size(), 6);

        this.inventory = Bukkit.createInventory(this, rowSize * 9, title);

        this.items = new ArrayList<>();
        this.itemsPutInGUI = new ArrayList<>();
    }

    public void openInventory(Player player) {
        this.viewer = player;

        player.openInventory(inventory);

        updateInventory();
    }

    public boolean updateInventory() {
        return updateInventory(null);
    }

    public boolean updateInventory(List<String> guiItemTypeUpdate) {
        List<String> format = this.fileConfiguration.getStringList("format");

        this.inventory.clear();
        this.items.clear();

        format = format.stream().map(StringBuilder::new).map(s -> {
            if (s.length() > 9) return s.substring(0, 8);
            if (s.length() < 9) s.append("?".repeat(Math.max(0, 9 - s.length() + 1)));
            return s;
        }).map(Object::toString).collect(Collectors.toList());

        String formatString = String.join("", format);

        ConfigurationSection sectionItem = this.fileConfiguration.getConfigurationSection("items");

        if (sectionItem == null)
            return false;

        sectionItem.getKeys(false).forEach(sec -> {
            char character = sec.charAt(0);
            if (!formatString.contains(String.valueOf(character)))
                return;

            String mask = this.fileConfiguration.getString("items." + character + ".mask");
            String type = this.fileConfiguration.getString("items." + character + ".type");
            ItemStack itemStack = MaterialUtils.getItemStackByFile(this.fileConfiguration, "items." + character);

            GUIItem guiItem = new GUIItem(character, mask, type, itemStack);

            this.items.add(guiItem);
        });

        for (int i = 0; i < formatString.length(); i++) {
            boolean overrideItem = false;

            if (guiItemTypeUpdate != null) {
                for (String type : guiItemTypeUpdate) {
                    GUIItem guiItem = getGUIItem(type);
                    if (guiItem == null || guiItem.getMask() == null) continue;

                    if (guiItem.getMask().charAt(0) == formatString.charAt(i)) {
                        guiItem.getSlotList().add(i);
                        this.inventory.setItem(i, guiItem.getItemStack());
                        this.itemsPutInGUI.add(guiItem);

                        overrideItem = true;

                        break;
                    }
                }
            }

            if (overrideItem)
                continue;

            GUIItem guiItem = getGUIItem(formatString.charAt(i));
            if (guiItem == null) continue;

            guiItem.getSlotList().add(i);
            this.inventory.setItem(i, guiItem.getItemStack());
            this.itemsPutInGUI.add(guiItem);
        }

        return true;
    }

    @Nullable
    public GUIItem getGUIItem(char character) {
        return items.stream().filter(i -> i.getCharacter() == character).findAny().orElse(null);
    }

    @Nullable
    public GUIItem getGUIItem(String type) {
        return items.stream().filter(i -> i.getType().equalsIgnoreCase(type)).findAny().orElse(null);
    }

    public String getType(Integer slot) {
        return itemsPutInGUI.get(slot).getType();
    }

    public Boolean isType(Integer slot, String type) {
        return getType(slot).equalsIgnoreCase(type);
    }

    public Integer getTypeOrder(Integer slot, String type) {
        Integer order = -1;

        for (int i = 0; i < itemsPutInGUI.size(); i++) {
            GUIItem item = itemsPutInGUI.get(i);

            if (item.getType().equalsIgnoreCase(type))
                order++;

            if (slot == i)
                return order;
        }

        return order;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

}
