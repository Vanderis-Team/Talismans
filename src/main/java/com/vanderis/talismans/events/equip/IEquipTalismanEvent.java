package com.vanderis.talismans.events.equip;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.items.ItemData;
import lombok.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;

@Getter
public abstract class IEquipTalismanEvent extends Event {

    @Getter(AccessLevel.NONE)
    private final Talismans instance = Talismans.getInstance();

    private static final HandlerList handlers = new HandlerList();

    protected Player player;

    @Setter
    protected ItemData equipTalisman;
    protected TalismanInventoryType talismanInventoryType;

    public IEquipTalismanEvent(Player player, ItemData equipTalisman, TalismanInventoryType talismanInventoryType) {
        this.player = player;
        this.equipTalisman = equipTalisman;
        this.talismanInventoryType = talismanInventoryType;
    }

    public IEquipTalismanEvent(Player player, TalismanInventoryType talismanInventoryType) {
        this.player = player;
        this.talismanInventoryType = talismanInventoryType;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
