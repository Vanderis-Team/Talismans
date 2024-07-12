package com.vanderis.talismans.gui.edit;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.gui.*;
import com.vanderis.talismans.items.ItemData;
import com.vanderis.talismans.utils.*;
import lombok.Getter;
import org.bukkit.entity.HumanEntity;

@Getter
public class EditGUI extends GUIHolder {

    private final Talismans instance = Talismans.getInstance();

    private final ItemData itemData;

    public EditGUI(ItemData itemData) {
        super(Talismans.getInstance().getFileManager().editGUI);

        this.itemData = itemData;
    }

    @Override
    public boolean updateInventory() {
        super.updateInventory();

        for (HumanEntity inventoryViewer : inventory.getViewers()) {
            inventoryViewer.getOpenInventory().setTitle(Placeholder.replacePlaceholder(inventoryViewer.getOpenInventory().getTitle(), "<talismans-id>", itemData.getId().toString()));
        }

        int recipeOrder = 0;

        for (int i = 0; i < itemsPutInGUI.size(); i++) {

            if (isType(i, "item-slot")) {
                if (itemData.getItemResult() == null)
                    continue;

                inventory.setItem(i, itemData.getItemResult());
            }

            if (isType(i, "recipe")) {
                if (itemData.getRecipe().isEmpty())
                    continue;

                inventory.setItem(i, itemData.getRecipe().get(++recipeOrder));
            }

        }

        return true;
    }

    public void maskCraftable() {
        for (int i = 0; i < itemsPutInGUI.size(); i++) {

            if (itemData.getCraftable()) {
                if (isType(i, "prevent-crafting"))
                    setItem(i);
            }
            else
                setMaskItem(i, "prevent-crafting");

        }
    }
}
