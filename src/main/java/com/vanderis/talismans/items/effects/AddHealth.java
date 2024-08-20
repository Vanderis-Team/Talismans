package com.vanderis.talismans.items.effects;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.events.effect.TalismanEffectEvent;
import com.vanderis.talismans.events.equip.*;
import com.vanderis.talismans.items.*;
import com.vanderis.talismans.utils.Logging;
import org.bukkit.Bukkit;
import org.bukkit.attribute.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;

public class AddHealth extends EffectData {

    public AddHealth() {
        super("add-health");
    }

    @EventHandler
    public void onEquipTalismanEvent(EquipTalismanEvent event) {
        Player player = event.getPlayer();
        ItemData itemData = event.getEquipTalisman();

        TalismanEffectEvent talismanEffectEvent = callEvent(player, itemData);

        if (talismanEffectEvent.isCancelled())
            return;

        Bukkit.getScheduler().runTaskLaterAsynchronously(Talismans.getInstance(), () -> {
            if (!getBagItemsHasSpecificEffect(player, "add-health").isEmpty())
                if (!hasAttribute(player, Attribute.GENERIC_MAX_HEALTH, itemData, "add-health")) {
                    addAttribute(player, Attribute.GENERIC_MAX_HEALTH, itemData, "add-health");

                    Logging.debug("AddHealth Effect (Equip)", player.getName() + " | " + itemData.getName() + " | " + getNewValueAfterAddEffect(effectID, itemData, 0.0, false));
                }
        }, 1L);
    }

    @EventHandler
    public void onUnequipTalismanEvent(UnEquipTalismanEvent event) {
        Player player = event.getPlayer();
        ItemData itemData = event.getEquipTalisman();

        Bukkit.getScheduler().runTaskLaterAsynchronously(Talismans.getInstance(), () -> {
            if (itemHasEffect(itemData, "add-health")) {
                Logging.log("unequip");

                if (hasAttribute(player, Attribute.GENERIC_MAX_HEALTH, itemData, "add-health")) {
                    removeAttribute(player, Attribute.GENERIC_MAX_HEALTH, itemData, "add-health");

                    Logging.debug("AddHealth Effect (UnEquip)", player.getName() + " | " + itemData.getName() + " | " + getNewValueAfterAddEffect(effectID, itemData, 0.0, false));
                }
            }
        }, 1L);


    }

}
