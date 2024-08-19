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

    protected Boolean isJoinEquip; // When player join, this event will run for every talisman player have

    @Setter
    protected ItemData equipTalisman; // Use as equip and unequip talismans

    public IEquipTalismanEvent(Player player, ItemData equipTalisman, Boolean isJoinEquip) {
        this.player = player;
        this.equipTalisman = equipTalisman;
        this.isJoinEquip = isJoinEquip;
    }

    public IEquipTalismanEvent(Player player, ItemData equipTalisman) {
        this.player = player;
        this.equipTalisman = equipTalisman;
        this.isJoinEquip = false;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
