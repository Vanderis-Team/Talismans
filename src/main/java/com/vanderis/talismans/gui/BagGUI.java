package com.vanderis.talismans.gui;

import com.vanderis.talismans.containers.*;

import java.util.List;

public class BagGUI extends GUIHolder implements Instance {

    public BagGUI() {
        super(instance.getFileManager().bagGUI);
    }

    @Override
    public boolean updateInventory(List<String> guiItemTypeUpdate) {
        super.updateInventory(guiItemTypeUpdate);

        for (int i = 0; i < itemsPutInGUI.size(); i++)
            if (isType(i, "item-slot"))
                this.inventory.setItem(i, instance.getBagManager().getItem(viewer, i).getItemResult());

        // TODO: Add lore when has same talismans in bag
        // TODO: Add lore when higher talisman in bag

        return true;
    }
}
