package com.vanderis.talismans.items.effects;

import com.vanderis.talismans.items.*;
import com.vanderis.talismans.utils.Logging;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.*;

import java.util.List;

public class ReduceDamage extends EffectData {

    public ReduceDamage() {
        super("reduce-overall-damage", "reduce-fire-damage", "reduce-fall-damage");
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player victim = (Player) event.getEntity();

            List<ItemData> bagItems = getBagItems(victim);

            if (!bagItems.isEmpty())
                for (ItemData itemData : bagItems) {
                    if (itemHasEffect(itemData, "reduce-overall-damage")) {
                        double valueAfterChange = Math.max(0, getNewValueAfterAddEffect("reduce-overall-damage", itemData, event.getDamage(), true));

                        event.setDamage(valueAfterChange);

                        callEvent(victim, itemData);

                        Logging.debug("ReduceOverallDamage Effect", victim.getName() + " | " + itemData.getName() + " | " + valueAfterChange);
                    }

                    if (itemHasEffect(itemData, "reduce-fire-damage")
                            && event.getCause() == EntityDamageEvent.DamageCause.FIRE
                            || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                        double valueAfterChange = Math.max(0, getNewValueAfterAddEffect("reduce-fire-damage", itemData, event.getDamage(), true));

                        event.setDamage(valueAfterChange);

                        callEvent(victim, itemData);

                        Logging.debug("ReduceFireDamage Effect", victim.getName() + " | " + itemData.getName() + " | " + valueAfterChange);
                    }

                    if (itemHasEffect(itemData, "reduce-fall-damage")
                            && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                        double valueAfterChange = Math.max(0, getNewValueAfterAddEffect("reduce-fall-damage", itemData, event.getDamage(), true));

                        event.setDamage(valueAfterChange);

                        callEvent(victim, itemData);

                        Logging.debug("ReduceFallDamage Effect", victim.getName() + " | " + itemData.getName() + " | " + valueAfterChange);
                    }
                }
        }
    }

}
