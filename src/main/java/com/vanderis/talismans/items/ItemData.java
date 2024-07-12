package com.vanderis.talismans.items;

import com.vanderis.talismans.Talismans;
import lombok.*;
import me.orineko.pluginspigottools.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Setter
@Getter
public abstract class ItemData implements Listener {

    protected ItemName id;
    protected Integer level;
    protected FileManager fileManager;
    protected ItemStack itemResult;
    protected Boolean craftable;
    protected List<ItemStack> recipe;
    protected Map<String, String> values = new HashMap<>();

    public ItemData(ItemName id, Integer level) {
        this.id = id;
        this.level = level;

        fileManager = new FileManager(level + ".yml", Talismans.getInstance());
        fileManager.createFolder("talismans", id.name().toLowerCase());
        fileManager.createFile();

        this.craftable = fileManager.getBoolean("craftable", false);

        this.recipe = new ArrayList<>();

        for (int i = 1; i <= 9; i++)
            recipe.add(fileManager.getItemStack("recipe." + i));

        getValueFile(fileManager);

        Bukkit.getServer().getPluginManager().registerEvents(this, Talismans.getInstance());
    }

    public Integer getValueAsInt(String key) {
        return Integer.parseInt(values.get(key));
    }

    public void getValueFile(FileConfiguration fileConfiguration) {
        ConfigurationSection configSec = fileConfiguration.getConfigurationSection("values");

        if (configSec != null)
            configSec.getKeys(false).forEach(key -> {
                values.put(key, configSec.getString(key));
            });

    }

    public void giveItemResult(Player player) {
        player.getInventory().addItem(itemResult);
    }

    public void saveToFile() {
        fileManager.set("item-result", itemResult);
        fileManager.set("craftable", craftable);

        for (int i = 1; i <= 9; i++)
            fileManager.set("recipe." + i, recipe.get(i - 1));

        fileManager.set("values", values);

        fileManager.save();
    }

}
