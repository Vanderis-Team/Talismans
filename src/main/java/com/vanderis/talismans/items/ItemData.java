package com.vanderis.talismans.items;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.utils.*;
import com.vanderis.talismans.utils.Color;
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
public class ItemData implements Listener {

    protected String id;

    protected FileManager fileManager;
    protected ItemStack itemResult;
    protected boolean craftable;
    protected List<ItemStack> recipe;
    protected Map<String, String> effectValues = new HashMap<>();

    public ItemData(String id) {
        this.id = id;

        renewItemToCache();
    }

    public String getName() {
        return itemResult == null ?
                Color.color("&e" + id) : Color.color(itemResult.getItemMeta().getDisplayName().matches("\\s*") ?
                Color.color("&e" + id) : itemResult.getItemMeta().getDisplayName());
    }

    public void getEffectValuesFromFile(FileConfiguration fileConfiguration) {
        ConfigurationSection configSec = fileConfiguration.getConfigurationSection("effects-values");

        if (configSec != null)
            configSec.getKeys(false).forEach(key -> {
                effectValues.put(key, configSec.getString(key));
            });

    }

    public String getEffectValue(String effectID) {
        return getEffectValues().get(effectID);
    }

    public Double getNumberFromEffectValue(String effectID) {
        String effectValue = getEffectValue(effectID);

        if (effectValue.contains("%")) {
            try {
                return Double.parseDouble(effectValue.split("%")[0]);
            } catch (NumberFormatException ignored) {
                return null;
            }
        }

        if (effectValue.contains("x")) {
            try {

                return Double.parseDouble(effectValue.split("x")[0]);
            } catch (NumberFormatException ignored) {
                return null;
            }
        }

        try {
            return Double.parseDouble(effectValue);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    public void setEffectValue(String effectID, String effectValue) {
        effectValues.put(effectID, effectValue);
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

        fileManager.set("effects-values", effectValues);

        fileManager.save();

        renewItemToCache();
    }

    private void renewItemToCache() {
        fileManager = new FileManager(id + ".yml", Talismans.getInstance());
        fileManager.createFolder("talismans");
        fileManager.createFile();

        this.itemResult = fileManager.getItemStack("item-result");
        this.craftable = fileManager.getBoolean("craftable", false);

        this.recipe = new ArrayList<>();

        for (int i = 1; i <= 9; i++)
            recipe.add(fileManager.getItemStack("recipe." + i));

        getEffectValuesFromFile(fileManager);
    }

    public String toString() {
        return id + ":" + itemResult;
    }

}
