package com.vanderis.talismans.items;

import com.vanderis.talismans.utils.Version;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public enum MaterialBundle {

    MELEE, SWORD, BOW;

    public final Set<String> melee = Set.of("SWORD", "AXE", "TRIDENT", "HOE", "PICKAXE", "SHOVEL");
    public final Set<String> sword = Set.of("SWORD");
    public final Set<String> bow = Set.of("BOW", "CROSSBOW");

    public Boolean isRightType(ItemStack itemStack) {
        switch (this) {
            case MELEE:
                return melee.stream().anyMatch(type -> itemStack.getType().name().contains(type));
            case SWORD:
                return sword.stream().anyMatch(type -> itemStack.getType().name().contains(type));
            case BOW:
                return bow.stream().anyMatch(type -> itemStack.getType().name().contains(type));
        }

        return false;
    }

    @SuppressWarnings("deprecation")
    public Boolean isPlayerHeldType(Player player) {
        if (Version.getServerVersion() == 8) {
            return isRightType(player.getItemInHand());
        } else {
            return isRightType(player.getInventory().getItemInMainHand()) || isRightType(player.getInventory().getItemInOffHand());
        }
    }

}
