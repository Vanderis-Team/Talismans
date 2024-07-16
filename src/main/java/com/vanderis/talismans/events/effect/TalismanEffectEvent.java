package com.vanderis.talismans.events.effect;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.items.ItemData;
import lombok.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;

@Getter
public class TalismanEffectEvent extends Event implements Cancellable {

    @Getter(AccessLevel.NONE)
    private final Talismans instance = Talismans.getInstance();

    private static final HandlerList handlers = new HandlerList();

    private boolean cancel = false;

    private Player player;

    private ItemData itemData;

    public TalismanEffectEvent(Player player, ItemData itemData) {
        this.player = player;
        this.itemData = itemData;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
