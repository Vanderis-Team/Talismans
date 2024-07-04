package com.vanderis.talismans.items;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.containers.ItemData;
import com.vanderis.talismans.enums.TalismanName;
import com.vanderis.talismans.enums.ItemType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class StrengthenBow extends ItemData {

    public StrengthenBow(TalismanName talismanName, Integer level) {
        super(talismanName, level);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();

        if (!Talismans.getInstance().getBagManager().hasTalisman(player, this))
            return;

        if (ItemType.BOW.isPlayerHeldType(player)) {
            event.setDamage(event.getDamage() + (event.getDamage() * getValueAsInt("damage_multiplier")));
        }
    }

}
