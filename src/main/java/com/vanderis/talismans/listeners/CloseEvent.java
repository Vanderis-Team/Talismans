package com.vanderis.talismans.listeners;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.utils.Logging;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloseEvent implements Listener {

    private final Talismans instance = Talismans.getInstance();

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event) {
        Logging.log("Close inventory");

        instance.getEditManager().onEditClose(event);

        instance.getGuiManager().closeInventory(event);
    }

}
