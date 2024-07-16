package com.vanderis.talismans.events.equip;

import com.vanderis.talismans.items.ItemData;
import lombok.*;
import org.bukkit.entity.Player;

@Getter
public class UnEquipTalismanEvent extends IEquipTalismanEvent {
    public UnEquipTalismanEvent(Player player, ItemData equipTalisman, TalismanInventoryType talismanInventoryType) {
        super(player, equipTalisman, talismanInventoryType);
    }

    public UnEquipTalismanEvent(Player player, TalismanInventoryType talismanInventoryType) {
        super(player, talismanInventoryType);
    }
}
