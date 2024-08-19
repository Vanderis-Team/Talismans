package com.vanderis.talismans.listeners;

import com.vanderis.talismans.Talismans;
import org.bukkit.event.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    private final Talismans instance = Talismans.getInstance();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        instance.getEditManager().onChat(event);
        instance.getEditLoreManager().onChat(event);
        instance.getEditEffectManager().onChat(event);
    }

}
