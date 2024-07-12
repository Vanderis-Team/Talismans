package com.vanderis.talismans.listeners;

import com.vanderis.talismans.Talismans;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloseEvent implements Listener {

    private final Talismans instance = Talismans.getInstance();

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event) {
        instance.getEditManager().onEditClose(event);

        instance.getGuiManager().closeInventory(event);
    }

}
