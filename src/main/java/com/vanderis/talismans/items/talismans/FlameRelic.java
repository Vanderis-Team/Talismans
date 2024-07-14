package com.vanderis.talismans.items.talismans;

import com.vanderis.talismans.Talismans;
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

        if (!Talismans.getInstance().getItemManager().hasItem(player, this))
            return;

        if (event.getCause() == EntityDamageEvent.DamageCause.FIRE ||
                event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK)
            event.setDamage(event.getDamage() - (event.getDamage() * getValueAsInt("reduce_damage")));
    }

}
