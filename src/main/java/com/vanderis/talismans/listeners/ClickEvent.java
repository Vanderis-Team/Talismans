package com.vanderis.talismans.listeners;

import com.vanderis.talismans.Talismans;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickEvent implements Listener {

    private final Talismans instance = Talismans.getInstance();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        instance.getBagManager().onBagClick(event);
        instance.getEditManager().onEditClick(event);
        instance.getEditLoreManager().onEditClick(event);
        instance.getEditEffectManager().onEditClick(event);
        instance.getCollectionsManager().onCollectionsClick(event);
        instance.getCraftingManager().onCraftingClick(event);
    }

}
