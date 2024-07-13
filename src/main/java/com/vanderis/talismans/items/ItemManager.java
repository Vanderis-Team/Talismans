package com.vanderis.talismans.items;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.utils.Logging;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.*;

public class ItemManager {

    private final Talismans instance = Talismans.getInstance();

    public List<ItemData> itemList = new ArrayList<>();

    public void register() {
        itemList.clear();

        instance.getFileManager().loadItems();
    }

    public void removeItemFromCache(ItemData itemData) {
        Logging.log("ItemManager: before -> " + itemList);

        itemList = itemList.stream()
                .filter(cache -> cache.getId().equals(itemData.getId()) && cache.getLevel().equals(itemData.level)).toList();

        Logging.log("ItemManager: after -> " + itemList);
    }

    public Boolean hasItem(Player player, ItemData itemData) {
        return instance.getBagManager().hasItem(player, itemData); // TODO: inventory & enderchest
    }

    @Nullable
    public ItemData getItem(ItemName id, Integer level) {
        return itemList.stream()
                .filter(itemData -> itemData.getId().equals(id) && itemData.getLevel().equals(level))
                .findAny().orElse(null);
    }

    public ItemData getItem(ItemStack itemStack) {
        for (ItemData itemData : itemList) {
            if (itemData.getItemResult() == null)
                continue;

            if (itemData.getItemResult().isSimilar(itemStack))
                return itemData;
        }

        return null;
    }

}
