package com.vanderis.talismans.items.talismans;

import com.vanderis.talismans.events.effect.TalismanEffectEvent;
import com.vanderis.talismans.items.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class FlameRelic extends ItemData {

    public FlameRelic(Integer level) {
        super(ItemName.FLAME_RELIC, level);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();

        if (!hasItemData(player))
            return;

        TalismanEffectEvent talismanEffectEvent = callEvent(player);

        if (talismanEffectEvent.isCancelled())
            return;

        if (event.getCause() == EntityDamageEvent.DamageCause.FIRE ||
                event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK)
            event.setDamage(event.getDamage() - (event.getDamage() * talismanEffectEvent.getItemData().getValueAsInt("reduce_damage")));
    }

}
