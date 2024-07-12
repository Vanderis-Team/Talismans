package com.vanderis.talismans.files;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.items.*;
import com.vanderis.talismans.items.talismans.*;
import com.vanderis.talismans.utils.Logging;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class FileManager {

    private final Talismans instance = Talismans.getInstance();

    public FileConfiguration config;
    public FileConfiguration messages;

    public FileConfiguration collectionsGUI;
    public FileConfiguration bagGUI;
    public FileConfiguration editGUI;
    public FileConfiguration craftingGUI;

    @SneakyThrows
    public void register() {
        config = new me.orineko.pluginspigottools.FileManager("config.yml", instance).copyDefault();
        messages = new me.orineko.pluginspigottools.FileManager("messages.yml", instance).copyDefault();

        collectionsGUI = new me.orineko.pluginspigottools.FileManager("gui/collections.yml", instance).copyDefault();
        bagGUI = new me.orineko.pluginspigottools.FileManager("gui/bag.yml", instance).copyDefault();
        editGUI = new me.orineko.pluginspigottools.FileManager("gui/edit.yml", instance).copyDefault();
        craftingGUI = new me.orineko.pluginspigottools.FileManager("gui/crafting.yml", instance).copyDefault();
    }

    public void loadItems() {
        File folder = new File(Talismans.getInstance().getDataFolder() + "/talismans");
        if (!folder.exists()) folder.mkdirs();

        File[] talismans = folder.listFiles();

        if (talismans == null)
            return;

        for (File file : talismans) {
            File[] levelFile = file.listFiles();

            if (levelFile == null)
                continue;


            for (File level : levelFile) {

                new me.orineko.pluginspigottools.FileManager("talismans/" + file.getName() + "/" + level.getName(), instance).copyDefault();

                Integer talismanLevel = Integer.parseInt(level.getName().replace(".yml", ""));

                ItemName itemName = ItemName.valueOf(file.getName().toUpperCase());
                switch (itemName) { // TODO: Something better
                    case STRENGTHEN_WEAPON:
                        load(new StrengthenWeapon(itemName, talismanLevel));
                        break;
                    case STRENGTHEN_MELEE:
                        load(new StrengthenMelee(itemName, talismanLevel));
                        break;
                    case STRENGTHEN_BOW:
                        load(new StrengthenBow(itemName, talismanLevel));
                        break;
                    case STRENGTHEN_SWORD:
                        load(new StrengthenSword(itemName, talismanLevel));
                        break;
                    case FLAME_RELIC:
                        load(new FlameRelic(itemName, talismanLevel));
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
    public File getPlayerFile(Player player) {
        return getPlayerFile(player.getName());
    }

    @SneakyThrows
    public File getPlayerFile(String playerName) {
        File folder = new File(Talismans.getInstance().getDataFolder() + "\\playerdata");
        if (!folder.exists()) folder.mkdirs();

        File file = new File(instance.getDataFolder() + "\\playerdata\\" + playerName + ".yml");
        if (!isDataExist(playerName)) {
            file.createNewFile();

            YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
            yml.set("bag-size", PathManager.DEFAULT_BAG_SIZE);
            yml.set("bag-items", new ArrayList<>());
            yml.save(file);
        }

        return file;
    }

    public Boolean isDataExist(String playerName) {
        File file = new File(instance.getDataFolder() + "\\playerdata\\" + playerName + ".yml");

        return file.exists();
    }

    public List<String> getBagItems(Player player) {
        return getBagItems(player.getName());
    }

    public List<String> getBagItems(String playerName) {
        File file = getPlayerFile(playerName);

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);

        return yml.getStringList("bag-items");
    }

    public Integer getSize(Player player) {
        return getSize(player.getName());
    }

    public Integer getSize(String playerName) {
        File file = getPlayerFile(playerName);

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);

        return yml.getInt("bag-size");
    }

    @SneakyThrows
    public void setBagItems(Player player, List<String> items) {
        setBagItems(player.getName(), items);
    }

    @SneakyThrows
    public void setBagItems(String playerName, List<String> items) {
        File file = getPlayerFile(playerName);

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);

        yml.set("bag-items", items);
        yml.save(file);
    }

    @SneakyThrows
    public void setBagSize(Player player, Integer size) {
        setBagSize(player.getName(), size);
    }

    @SneakyThrows
    public void setBagSize(String playerName, Integer size) {
        File file = getPlayerFile(playerName);

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);

        yml.set("bag-size", size);
        yml.save(file);
    }

}
