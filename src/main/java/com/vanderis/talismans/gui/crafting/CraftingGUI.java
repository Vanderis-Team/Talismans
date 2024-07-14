package com.vanderis.talismans.gui.crafting;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.gui.GUIHolder;
import com.vanderis.talismans.items.ItemData;
import com.vanderis.talismans.utils.Logging;
import lombok.Getter;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CraftingGUI extends GUIHolder {

    private final Talismans instance = Talismans.getInstance();

    public ItemData result = null;

    public CraftingGUI() {
        super(Talismans.getInstance().getFileManager().craftingGUI);
    }

    public void checkRecipe() {
        List<ItemStack> recipe = new ArrayList<>();

        for (int i = 0; i < itemsPutInGUI.size(); i++) {

            if (isType(i, "recipe-slot"))
                recipe.add(inventory.getItem(i));

        }

        ItemData itemData = instance.getItemManager().getItem(recipe);

        for (int i = 0; i < itemsPutInGUI.size(); i++) {

            if (isType(i, "result-slot")) {
                if (itemData != null) {
                    inventory.setItem(i, itemData.getItemResult());

                    result = itemData;
                } else {
                    inventory.setItem(i, itemsPutInGUI.get(i).getItemStack());
                }
            }

        }

    }

    public void returnRecipe(Player player) {
        for (int i = 0; i < itemsPutInGUI.size(); i++) {

            if (isType(i, "recipe-slot")) {
                if (inventory.getItem(i) == null)
                    continue;

                for (ItemStack itemStack : player.getInventory().addItem(inventory.getItem(i)).values()) {
                    player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
                }
            }

        }
    }

}
