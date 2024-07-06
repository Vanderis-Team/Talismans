package com.vanderis.talismans.managers;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.containers.*;
import com.vanderis.talismans.enums.TalismanName;
import com.vanderis.talismans.items.*;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class FileManager implements Instance {

    public FileConfiguration collectionsGUI;
    public FileConfiguration bagGUI;
    public FileConfiguration editGUI;
    public FileConfiguration craftingGUI;

    @SneakyThrows
    public void register() {
        collectionsGUI = new me.orineko.pluginspigottools.FileManager("gui/collections.yml", instance).copyDefault();
        bagGUI = new me.orineko.pluginspigottools.FileManager("gui/bag.yml", instance).copyDefault();
        editGUI = new me.orineko.pluginspigottools.FileManager("gui/edit.yml", instance).copyDefault();
        craftingGUI = new me.orineko.pluginspigottools.FileManager("gui/crafting.yml", instance).copyDefault();
    }

    public void loadItems() {
        File folder = new File(Talismans.getInstance().getDataFolder(), "talismans");
        if (!folder.exists()) folder.mkdirs();

        File[] talismans = folder.listFiles();

        if (talismans == null)
            return;

        for (File file : talismans) {
            File[] levelFile = file.listFiles();

            if (levelFile == null)
                continue;

            for (File level : levelFile) {
                new me.orineko.pluginspigottools.FileManager("talismans/" + file.getName() + "/" + level.getName() + ".yml", instance).copyDefault();

                Integer talismanLevel = Integer.parseInt(level.getName().replace(".yml", ""));

                TalismanName talismanName = TalismanName.valueOf(file.getName().toUpperCase());
                switch (talismanName) { // TODO: Something better
                    case STRENGTHEN_WEAPON:
                        load(new StrengthenWeapon(talismanName, talismanLevel));
                        break;
                    case STRENGTHEN_MELEE:
                        load(new StrengthenMelee(talismanName, talismanLevel));
                        break;
                    case STRENGTHEN_BOW:
                        load(new StrengthenBow(talismanName, talismanLevel));
                        break;
                    case STRENGTHEN_SWORD:
                        load(new StrengthenSword(talismanName, talismanLevel));
                        break;
                    case FLAME_RELIC:
                        load(new FlameRelic(talismanName, talismanLevel));
                        break;
                    default:
                        break;
                }
            }


        }
    }

    public void load(ItemData data) {
        instance.getItemManager().itemList.add(data);
    }

    @SneakyThrows
    public File getPlayerBagFile(Player player) {
        return getPlayerBagFile(player.getName());
    }

    @SneakyThrows
    public File getPlayerBagFile(String playerName) {
        File file = new File(instance.getDataFolder() + "/bag/" + playerName + ".yml");
        if (!file.exists()) {
            file.createNewFile();

            YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
            yml.set("items", new ArrayList<>());
            yml.save(file);
        }

        return file;
    }

    public List<String> getPlayerBagItems(Player player) {
        return getPlayerBagItems(player.getName());
    }

    public List<String> getPlayerBagItems(String playerName) {
        File file = getPlayerBagFile(playerName);

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);

        return yml.getStringList("items");
    }

    @SneakyThrows
    public void setPlayerBagItems(Player player, List<String> items) {
        setPlayerBagItems(player.getName(), items);
    }

    @SneakyThrows
    public void setPlayerBagItems(String playerName, List<String> items) {
        File file = getPlayerBagFile(playerName);

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);

        yml.set("items", items);
        yml.save(file);
    }

}
