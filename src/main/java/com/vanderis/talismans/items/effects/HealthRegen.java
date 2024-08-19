package com.vanderis.talismans.items.effects;

import com.vanderis.talismans.items.*;
import com.vanderis.talismans.utils.Logging;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.List;

public class HealthRegen extends EffectData {

    public HealthRegen() {
        super("health-regen");
    }

    @EventHandler
    public void onHealthRegen(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            List<ItemData> itemsHasEffect = getBagItemsHasSpecificEffect(player, "health-regen");

            if (!itemsHasEffect.isEmpty())
                for (ItemData itemData : itemsHasEffect) {
                    double valueAfterChange = Math.max(0, getNewValueAfterAddEffect(effectID, itemData, event.getAmount(), false));

                    event.setAmount(valueAfterChange);

                    callEvent(player, itemData);

                    Logging.debug("HealthRegen Effect", player.getName() + " | " + itemData.getName() + " | " + valueAfterChange);
                }
        }
    }

}
