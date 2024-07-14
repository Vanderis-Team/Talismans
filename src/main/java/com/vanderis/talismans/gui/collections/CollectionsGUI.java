package com.vanderis.talismans.gui.collections;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.gui.GUIHolder;
import com.vanderis.talismans.items.*;
import com.vanderis.talismans.utils.*;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CollectionsGUI extends GUIHolder {

    private final Talismans instance = Talismans.getInstance();
    private final ItemManager itemManager = instance.getItemManager();

    @Getter
    private int currentPage;
    private int maxPage = 1;

    private Player viewer;

    public CollectionsGUI() {
        super(Talismans.getInstance().getFileManager().collectionsGUI);

        this.currentPage = 1;
    }

    @Override
    public void openInventory(Player player) {
        super.openInventory(player);

        this.viewer = player;
    }

    @Override
    public boolean updateInventory() {
        super.updateInventory();

        this.maxPage = (int) Math.ceil((double) itemManager.itemList.size() / getAmountOfType("item-slot"));

        viewer.getOpenInventory().setTitle(Placeholder.replacePlaceholders(Color.color(fileConfiguration.getString("title")), String.valueOf(currentPage), String.valueOf(maxPage)));

        int itemIndex = currentPage == 1 ? 0 : getAmountOfType("item-slot") * (currentPage - 1);
        int lastItemIndex = currentPage == maxPage ? itemManager.itemList.size() - 1 : getAmountOfType("item-slot") * currentPage - 1;

        if (lastItemIndex >= itemManager.itemList.size() - 1)
            lastItemIndex = itemManager.itemList.size() - 1;

        for (int i = 0; i < itemsPutInGUI.size(); i++) {

            if (isType(i, "item-slot")) {
                if (itemManager.itemList.isEmpty())
                    break;

                if (itemIndex > lastItemIndex)
                    break;

                if (itemManager.itemList.get(itemIndex).getItemResult() == null) {
                    ItemData itemData = itemManager.itemList.get(itemIndex++);

                    this.inventory.setItem(i, addNavigationLore(itemData, emptyItemResult(itemData)));

                    continue;
                }

                ItemData itemData = itemManager.itemList.get(itemIndex++);

                this.inventory.setItem(i, addNavigationLore(itemData, itemData.getItemResult()));
            }

        }

        return true;
    }

    public void nextPage() {
        if (currentPage != maxPage) {
            currentPage++;

            updateInventory();
        }
    }

    public void previousPage() {
        if (currentPage != 1) {
            currentPage--;

            updateInventory();
        }
    }

    private ItemStack emptyItemResult(ItemData itemData) {
        ItemStack itemStack = new ItemStack(Material.BARRIER);

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setItemName(itemData.getName());
        itemMeta.setLore(Color.color(List.of("&cThis talisman doesn't have the item stand for it!")));

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private ItemStack addNavigationLore(ItemData itemData, ItemStack itemStack) {
        ItemStack cloneItemStack = itemStack.clone();

        ItemMeta itemMeta = cloneItemStack.getItemMeta();

        if (itemMeta.getLore() == null)
            itemMeta.setLore(Color.color(List.of("", "&eLeft-Click to get " + itemData.getName(), "&eRight-Click to edit " + itemData.getName())));
        else
        {
            List<String> lore = itemMeta.getLore();
            lore.addAll(Color.color(List.of("", "&eLeft-Click to get " + itemData.getName(), "&eRight-Click to edit " + itemData.getName())));

            itemMeta.setLore(lore);
        }

        cloneItemStack.setItemMeta(itemMeta);

        return cloneItemStack;
    }

}
