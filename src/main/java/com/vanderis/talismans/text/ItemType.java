package com.vanderis.talismans.text;

import com.vanderis.talismans.Talismans;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@UtilityClass
public class ItemType {

    private final Set<String> melee = Set.of("SWORD", "AXE", "TRIDENT", "HOE", "PICKAXE", "SHOVEL");

    public Boolean isMelee(ItemStack itemStack) {
        return melee.stream().anyMatch(type -> itemStack.getType().name().contains(type));
    }

    @SuppressWarnings("deprecation")
    public Boolean isPlayerHeldMelee(Player player) {
        if (Talismans.getInstance().getVersionSystem().getServerVersion() == 8) {
            return isMelee(player.getItemInHand());
        } else {
            return isMelee(player.getInventory().getItemInMainHand()) || isMelee(player.getInventory().getItemInOffHand());
        }
    }

}
