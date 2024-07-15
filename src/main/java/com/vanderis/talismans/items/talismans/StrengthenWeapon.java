package com.vanderis.talismans.items.talismans;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.items.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class StrengthenWeapon extends ItemData {

    public StrengthenWeapon(Integer level) {
        super(ItemName.STRENGTHEN_WEAPON, level);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();

        if (!hasItemData(player))
            return;

        if (MaterialBundle.MELEE.isPlayerHeldType(player) || MaterialBundle.BOW.isPlayerHeldType(player)) {
            event.setDamage(event.getDamage() + (event.getDamage() * getValueAsInt("damage_multiplier")));
        }
    }

}
