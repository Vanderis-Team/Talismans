package com.vanderis.talismans.listeners;

import com.vanderis.talismans.Talismans;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    private final Talismans instance = Talismans.getInstance();

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        instance.getBagManager().onQuit(event);
    }

}
