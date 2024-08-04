package com.vanderis.talismans.player;

import com.vanderis.talismans.files.PathManager;
import com.vanderis.talismans.items.ItemData;
import lombok.*;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class PlayerData {

    private Player player;
    @Setter
    private Integer bagSize;
    private List<ItemData> bagItems = new ArrayList<>();

    public PlayerData() {
        this.bagSize = PathManager.DEFAULT_BAG_SIZE;
    }

    public PlayerData(Player player) {
        this.player = player;
        this.bagSize = PathManager.DEFAULT_BAG_SIZE;
    }

    public PlayerData(List<ItemData> bagItems) {
        this.bagSize = PathManager.DEFAULT_BAG_SIZE;
        this.bagItems = bagItems;
    }

    public PlayerData(Player player, Integer bagSize, List<ItemData> bagItems) {
        this.player = player;
        this.bagSize = bagSize;
        this.bagItems = bagItems;
    }

    public List<ItemData> getAllItems() {
        return new ArrayList<>(bagItems);
    }

    public List<String> getAllItemsName() {
        List<ItemData> items = new ArrayList<>(new HashSet<>(getAllItems()));

        if (items.isEmpty())
            return new ArrayList<>();

        List<String> names = new ArrayList<>();

        int count = 0;
        StringBuilder nameLine = new StringBuilder();

        for (int i = 0; i < items.size(); i++) {
            ItemData item = items.get(i);

            nameLine.append(item.getName());

            if (count != 4 && i + 1 != items.size())
                nameLine.append(", ");

            count++;

            if (count == 5) {
                names.add(nameLine.toString());

                nameLine = new StringBuilder();

                count = 0;
            }
        }

        names.add(nameLine.toString());

        return names;
    }

    public Boolean hasItem(ItemData itemData) {
        return bagItems.contains(itemData);
    }

}
