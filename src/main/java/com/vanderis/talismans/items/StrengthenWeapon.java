package com.vanderis.talismans.items;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.containers.ItemData;
import com.vanderis.talismans.enums.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class StrengthenWeapon extends ItemData {

    public StrengthenWeapon(TalismanName talismanName, Integer level) {
        super(talismanName, level);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();

        if (!Talismans.getInstance().getItemManager().hasItem(player, this))
            return;

        if (ItemType.MELEE.isPlayerHeldType(player) || ItemType.BOW.isPlayerHeldType(player)) {
            event.setDamage(event.getDamage() + (event.getDamage() * getValueAsInt("damage_multiplier")));
        }
    }

}
