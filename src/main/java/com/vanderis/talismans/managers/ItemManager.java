package com.vanderis.talismans.managers;

import com.vanderis.talismans.containers.*;
import com.vanderis.talismans.enums.TalismanName;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.*;

public class ItemManager implements Instance {

    public List<ItemData> itemList = new ArrayList<>();

    public void register() {
        instance.getFileManager().loadItems();
    }

    public Boolean hasItem(Player player, ItemData itemData) {
        return instance.getBagManager().hasItem(player, itemData); // TODO: inventory & enderchest
    }

    @Nullable
    public ItemData getItem(TalismanName id, Integer level) {
        return itemList.stream()
                .filter(itemData -> itemData.getId().equals(id) && itemData.getLevel().equals(level))
                .findAny().orElse(null);
    }

}
