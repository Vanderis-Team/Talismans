package com.vanderis.talismans.listeners;

import com.vanderis.talismans.Talismans;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    private final Talismans instance = Talismans.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        instance.getPlayerManager().onJoin(event);
    }

}
