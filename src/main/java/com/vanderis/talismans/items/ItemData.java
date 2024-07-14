package com.vanderis.talismans.items;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.utils.Color;
import com.vanderis.talismans.utils.Logging;
import lombok.*;
import me.orineko.pluginspigottools.FileManager;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.*;

import java.util.*;

@Setter
@Getter
public abstract class ItemData implements Listener {

    protected ItemName id;
    protected Integer level;

    protected FileManager fileManager;
    protected ItemStack itemResult;
    protected boolean craftable;
    protected List<ItemStack> recipe;
    protected Map<String, String> values = new HashMap<>();

    public ItemData(ItemName id, Integer level) {
        this.id = id;
        this.level = level;

        renewItemToCache();

        Bukkit.getServer().getPluginManager().registerEvents(this, Talismans.getInstance());
    }

    public String getName() {
        return itemResult == null ?
                Color.color("&e" + id + ":" + level) : Color.color(itemResult.getItemMeta().getDisplayName().matches("\\s*") ?
                Color.color("&e" + id + ":" + level) : itemResult.getItemMeta().getDisplayName());
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

    public void toggleCraftable() {
        this.craftable = !this.craftable;
    }

    public boolean isRecipeValid() {
        boolean valid = false;

        for (ItemStack recipe : recipe) {
            if (recipe != null) {
                valid = true;
                break;
            }
        }

        return valid;
    }

    public void saveToFile() {
        fileManager.set("item-result", itemResult);
        fileManager.set("craftable", craftable);

        for (int i = 1; i <= recipe.size(); i++)
            fileManager.set("recipe." + i, recipe.get(i - 1));

        fileManager.set("values", values);

        fileManager.save();

        renewItemToCache();
    }

    private void renewItemToCache() {
        fileManager = new FileManager(level + ".yml", Talismans.getInstance());
        fileManager.createFolder("talismans", id.name().toLowerCase());
        fileManager.createFile();

        this.itemResult = fileManager.getItemStack("item-result");
        this.craftable = fileManager.getBoolean("craftable", false);

        this.recipe = new ArrayList<>();

        for (int i = 1; i <= 9; i++)
            recipe.add(fileManager.getItemStack("recipe." + i));

        getValueFile(fileManager);
    }

    public String toString() {
        return id.name() + ":" + level + ":" + itemResult;
    }

}
