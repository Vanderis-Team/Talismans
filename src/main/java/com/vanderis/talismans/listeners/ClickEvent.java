package com.vanderis.talismans.listeners;

import com.vanderis.talismans.containers.Instance;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickEvent implements Listener, Instance {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        instance.getGuiSystem().onBagClick(event);
    }

}
