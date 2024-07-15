package com.vanderis.talismans.events;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.items.ItemData;
import lombok.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;

@Getter
public class EquipTalismanEvent extends Event {

    @Getter(AccessLevel.NONE)
    private final Talismans instance = Talismans.getInstance();

    private static final HandlerList handlers = new HandlerList();

    private final Player player;

    private final ItemData equipTalisman;
    private final InventoryType inventoryType;

    public EquipTalismanEvent(Player player, ItemData equipTalisman, InventoryType inventoryType) {
        this.player = player;
        this.equipTalisman = equipTalisman;
        this.inventoryType = inventoryType;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public enum InventoryType {
        BAG, PLAYER_INVENTORY, ENDERCHEST
    }

}
