package com.vanderis.talismans.items;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.items.containers.ItemData;
import com.vanderis.talismans.material.enums.*;
import com.vanderis.talismans.items.enums.ItemName;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class StrengthenBow extends ItemData {

    public StrengthenBow(ItemName itemName, Integer level) {
        super(itemName, level);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();

        if (!Talismans.getInstance().getItemManager().hasItem(player, this))
            return;

        if (MaterialBundle.BOW.isPlayerHeldType(player)) {
            event.setDamage(event.getDamage() + (event.getDamage() * getValueAsInt("damage_multiplier")));
        }
    }

}
