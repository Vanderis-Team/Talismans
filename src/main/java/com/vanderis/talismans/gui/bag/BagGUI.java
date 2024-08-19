package com.vanderis.talismans.gui.bag;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.gui.GUIHolder;
import com.vanderis.talismans.player.PlayerManager;
import com.vanderis.talismans.utils.Logging;

public class BagGUI extends GUIHolder {

    private final Talismans instance = Talismans.getInstance();
    private final PlayerManager playerManager = instance.getPlayerManager();
    private final BagManager bagManager = instance.getBagManager();

    private final String bagOwner;

    public BagGUI(String bagOwner) {
        super(Talismans.getInstance().getFileManager().bagGUI);

        this.bagOwner = bagOwner;
    }

    @Override
    public boolean updateInventory() {
        super.updateInventory();

        Integer itemOrder = 0;

        for (int i = 0; i < itemsPutInGUI.size(); i++) {
            if (isType(i, "item-slot")) {
                if (getTypeOrder(i, "item-slot") + 1 > playerManager.getPlayerData(bagOwner).getBagSize()) {
                    setMaskItem(i, "block-slot");

                    continue;
                }

                if (!viewer.getName().equalsIgnoreCase(bagOwner)) {
                    if (bagManager.getItems(bagOwner).isEmpty()) {
                        Logging.debug("BagGUI", "Bag owner [" + bagOwner + "] has empty bag.");

                        continue;
                    }

                    if (!playerManager.hasCached(bagOwner)) {
                        Logging.debug("BagGUI", "Bag owner [" + bagOwner + "] hasn't cached yet.");

                        bagManager.fromFileToCache(bagOwner);
                    }

                    if (bagManager.getItem(bagOwner, itemOrder) == null)
                        continue;

                    this.inventory.setItem(i, bagManager.getItem(bagOwner, itemOrder++).getItemResult());

                    continue;
                }

                if (bagManager.getItems(viewer).isEmpty()) {
                    Logging.debug("BagGUI", "Bag owner [" + viewer.getName() + "] has empty bag.");

                    continue;
                }

                if (bagManager.getItem(viewer, itemOrder) == null)
                    continue;

                this.inventory.setItem(i, bagManager.getItem(viewer, itemOrder++).getItemResult());
            }
        }

        return true;
    }

}
