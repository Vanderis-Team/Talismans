package com.vanderis.talismans.converters;

import com.cryptomorin.xseries.XMaterial;
import com.vanderis.talismans.messages.Color;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.List;

@UtilityClass
public class MaterialConverter {

    public ItemStack getItemAllVersion(@Nonnull String material) {
        if (material.isEmpty()) {
            return new ItemStack(Material.AIR);
        } else {
            material = material.toUpperCase();

            try {
                return new ItemStack(Material.valueOf(material));
            } catch (IllegalArgumentException var2) {
                return XMaterial.matchXMaterial(material).isPresent() ? XMaterial.matchXMaterial(material).get().parseItem() : new ItemStack(Material.AIR);
            }
        }
    }

    public ItemStack getItemStackByFile(@Nonnull FileConfiguration file, @Nonnull String path) {
        String typeItem = file.getString(path + ".material", "");
        ItemStack itemStack = getItemAllVersion(typeItem.toUpperCase());
        return getItemStackByFileAndItem(file, path, itemStack);
    }

    public ItemStack getItemStackByFileAndItem(@Nonnull FileConfiguration file, @Nonnull String path, @Nonnull ItemStack itemStack) {
        String nameItem = file.getString(path + ".name", "");
        List<String> loreItem = file.getStringList(path + ".lore");
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(Color.color(nameItem));
            meta.setLore(Color.color(loreItem));
            itemStack.setItemMeta(meta);
        }

        return itemStack;
    }

}
