package com.vanderis.talismans.items.containers;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.items.enums.ItemName;
import lombok.Getter;
import me.orineko.pluginspigottools.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter
public abstract class ItemData implements Listener {

    protected ItemName id;
    protected String name;
    protected Integer level;
    protected FileManager fileManager;
    protected ItemStack itemResult;
    protected Boolean craftable;
    protected List<ItemStack> recipe;
    protected HashMap<String, String> values = new HashMap<>();

    public ItemData(ItemName id, Integer level) {
        this.id = id;
        this.level = level;

        fileManager = new FileManager(level + ".yml", Talismans.getInstance());
        fileManager.createFolder("talismans", id.name().toLowerCase());
        fileManager.createFile();

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

}
