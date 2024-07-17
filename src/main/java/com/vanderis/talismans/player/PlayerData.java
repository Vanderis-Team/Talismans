package com.vanderis.talismans.player;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.files.PathManager;
import com.vanderis.talismans.items.ItemData;
import com.vanderis.talismans.utils.Logging;
import lombok.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter
public class PlayerData {

    private Player player;
    @Setter
    private Integer bagSize;
    private List<ItemData> bagItems = new ArrayList<>();
    private final List<ItemData> inventoryCache = new ArrayList<>();
    private final List<ItemData> enderchestCache = new ArrayList<>();

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
        List<ItemData> items = new ArrayList<>();

        cacheInventories();

        items.addAll(bagItems);
        items.addAll(inventoryCache);
        items.addAll(enderchestCache);

        return items;
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

    /**
     * @return List of ItemData if there's change in inventory, null if none
     */
    public List<ItemData> cacheInventories() {
        List<ItemData> tempInv = new ArrayList<>(inventoryCache);
        List<ItemData> tempEnderchest = new ArrayList<>(enderchestCache);

        inventoryCache.clear();
        enderchestCache.clear();

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (Talismans.getInstance().getItemManager().getItem(itemStack) != null)
                this.inventoryCache.add(Talismans.getInstance().getItemManager().getItem(itemStack));
        }

        for (ItemStack itemStack : player.getEnderChest()) {
            if (Talismans.getInstance().getItemManager().getItem(itemStack) != null)
                this.enderchestCache.add(Talismans.getInstance().getItemManager().getItem(itemStack));
        }

        List<ItemData> result = new ArrayList<>();

        if (!tempInv.equals(inventoryCache))
            result = new ArrayList<>(inventoryCache);

        if (!tempEnderchest.equals(enderchestCache))
            result = new ArrayList<>(enderchestCache);

        if (result.isEmpty())
            return null;
        else
            result.removeAll(tempInv);

        return result;
    }

    public Boolean hasItem(ItemData itemData) {
        if (bagItems.contains(itemData))
            return true;
        else if (inventoryCache.contains(itemData))
            return true;
        else return enderchestCache.contains(itemData);
    }

}
