package com.vanderis.talismans.events.equip;

import com.vanderis.talismans.items.ItemData;
import lombok.*;
import org.bukkit.entity.Player;

@Getter
public class UnEquipTalismanEvent extends IEquipTalismanEvent {
    public UnEquipTalismanEvent(Player player, ItemData equipTalisman, Boolean isJoinEquip) {
        super(player, equipTalisman, isJoinEquip);
    }

    public UnEquipTalismanEvent(Player player, ItemData equipTalisman) {
        super(player, equipTalisman);
    }
}
