package com.vanderis.talismans.events.equip;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.items.ItemData;
import lombok.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;

@Getter
public class EquipTalismanEvent extends IEquipTalismanEvent {
    public EquipTalismanEvent(Player player, ItemData equipTalisman, TalismanInventoryType talismanInventoryType) {
        super(player, equipTalisman, talismanInventoryType);
    }

    public EquipTalismanEvent(Player player, TalismanInventoryType talismanInventoryType) {
        super(player, talismanInventoryType);
    }
}
