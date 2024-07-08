package com.vanderis.talismans.gui.bag;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.gui.GUIHolder;
import com.vanderis.talismans.utils.Logging;

import java.util.List;

public class BagGUI extends GUIHolder {

    private final Talismans instance = Talismans.getInstance();
    private final BagManager bagManager = instance.getBagManager();

    private final String bagOwner;

    public BagGUI(String bagOwner) {
        super(Talismans.getInstance().getFileManager().bagGUI);

        this.bagOwner = bagOwner;
    }

    @Override
    public boolean updateInventory(List<String> guiItemTypeUpdate) {
        super.updateInventory(guiItemTypeUpdate);

        for (int i = 0; i < itemsPutInGUI.size(); i++)
            if (isType(i, "item-slot")) {
                if (!viewer.getName().equalsIgnoreCase(bagOwner)) {
                    if (bagManager.getItems(bagOwner).isEmpty()) {
                        Logging.debug("BagGUI", "Bag owner [" + bagOwner + "] has empty bag.");

                        break;
                    }

                    if (!bagManager.hasCached(bagOwner)) {
                        Logging.debug("BagGUI", "Bag owner [" + bagOwner + "] hasn't cached yet.");

                        bagManager.fromFileToCache(bagOwner);
                    }

                    this.inventory.setItem(i, bagManager.getItem(bagOwner, i).getItemResult());

                    continue;
                }

                if (bagManager.getItems(viewer).isEmpty()) {
                    Logging.debug("BagGUI", "Bag owner [" + viewer.getName() + "] has empty bag.");

                    break;
                }

                this.inventory.setItem(i, bagManager.getItem(viewer, i).getItemResult());
            }

        // TODO: Add lore when has same talismans in bag
        // TODO: Add lore when higher talisman in bag

        return true;
    }
}
