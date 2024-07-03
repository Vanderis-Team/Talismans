package com.vanderis.talismans.items;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.containers.*;
import com.vanderis.talismans.enums.TalismanName;
import com.vanderis.talismans.text.ItemType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class StrengthenMelee extends ItemData {

    public StrengthenMelee(Integer level) {
        super(TalismanName.STRENGTHEN_MELEE, level);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();

        if (!Talismans.getInstance().getBagManager().hasTalisman(player, this))
            return;

        if (ItemType.MELEE.isPlayerHeldType(player)) {
            event.setDamage(event.getDamage() + (event.getDamage() * getValueAsInt("damage_multiplier")));
        }
    }

}
