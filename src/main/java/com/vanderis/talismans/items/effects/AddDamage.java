package com.vanderis.talismans.items.effects;

import com.vanderis.talismans.items.*;
import com.vanderis.talismans.utils.Logging;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.*;

import java.util.*;

public class AddDamage extends EffectData {

    public AddDamage() {
        super("add-damage");
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player attacker = (Player) event.getDamager();

            List<ItemData> itemsHasEffect = getBagItemsHasSpecificEffect(attacker, "add-damage");

            if (!itemsHasEffect.isEmpty())
                for (ItemData itemData : itemsHasEffect) {
                    double valueAfterChange = Math.max(0, getNewValueAfterAddEffect(effectID, itemData, event.getDamage(), false));

                    event.setDamage(valueAfterChange);

                    callEvent(attacker, itemData);

                    Logging.debug("AddDamage Effect", attacker.getName() + " | " + itemData.getName() + " | " + valueAfterChange);
                }
        }
    }

}
