package com.vanderis.talismans.listeners;

import com.vanderis.talismans.containers.Instance;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener, Instance {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        instance.getBagManager().onQuit(event);
    }

}
