package com.vanderis.talismans.events.equip;

import com.vanderis.talismans.items.ItemData;
import lombok.*;
import org.bukkit.entity.Player;

@Getter
public class EquipTalismanEvent extends IEquipTalismanEvent {
    public EquipTalismanEvent(Player player, ItemData equipTalisman, Boolean isJoinEquip) {
        super(player, equipTalisman, isJoinEquip);
    }

    public EquipTalismanEvent(Player player, ItemData equipTalisman) {
        super(player, equipTalisman);
    }
}
