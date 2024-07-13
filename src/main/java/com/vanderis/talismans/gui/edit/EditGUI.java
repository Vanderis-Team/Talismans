package com.vanderis.talismans.gui.edit;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.gui.*;
import com.vanderis.talismans.items.ItemData;
import com.vanderis.talismans.utils.*;
import lombok.Getter;
import org.bukkit.entity.*;

@Getter
public class EditGUI extends GUIHolder {

    private final Talismans instance = Talismans.getInstance();

    private final ItemData itemData;

    public EditGUI(ItemData itemData) {
        super(Talismans.getInstance().getFileManager().editGUI);

        this.itemData = itemData;
    }

    @Override
    public void openInventory(Player player) {
        super.openInventory(player);

        player.getOpenInventory().setTitle(Placeholder.replacePlaceholders(player.getOpenInventory().getTitle(), itemData.getId().toString(), itemData.getLevel().toString()));
    }

    @Override
    public boolean updateInventory() {
        super.updateInventory();

        int recipeOrder = -1;

        for (int i = 0; i < itemsPutInGUI.size(); i++) {

            maskCraftable(i);

            if (isType(i, "item-slot")) {
                if (itemData.getItemResult() == null)
                    continue;

                inventory.setItem(i, itemData.getItemResult());
            }

            if (isType(i, "recipe")) {
                if (itemData.getRecipe().isEmpty() || itemData.getRecipe().size() < recipeOrder)
                    continue;

                inventory.setItem(i, itemData.getRecipe().get(++recipeOrder));
            }

        }

        return true;
    }

    public void replaceCraftableIcon() {
        for (int i = 0; i < itemsPutInGUI.size(); i++) {

            maskCraftable(i);

        }
    }

    private void maskCraftable(int slot) {
        if (itemData.getCraftable()) {
            if (isType(slot, "prevent-crafting"))
                setItem(slot);
        }
        else
            setMaskItem(slot, "prevent-crafting");
    }

}
