package com.vanderis.talismans.files;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.items.*;
import com.vanderis.talismans.items.talismans.*;
import com.vanderis.talismans.utils.Logging;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.net.URL;
import java.nio.file.*;
import java.security.CodeSource;
import java.util.*;
import java.util.zip.*;

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

    @SneakyThrows
    public void loadItems() {
        List<String> cache = getYamlFilesFromSource("talismans");

        for (String talismanPath : cache) {
            new me.orineko.pluginspigottools.FileManager("talismans/" + talismanPath, instance).copyDefault();

            ItemName id = ItemName.valueOf(talismanPath.split("/")[0].toUpperCase());
            Integer talismanLevel = Integer.parseInt(talismanPath.split("/")[1].replace(".yml", ""));

            loadItemsFromID(id, talismanLevel);
        }

    }

    public void loadItemsFromID(ItemName id, Integer level) {
        switch (id) { // TODO: Something better
            case STRENGTHEN_WEAPON:
                load(new StrengthenWeapon(level));
                break;
            case STRENGTHEN_MELEE:
                load(new StrengthenMelee(level));
                break;
            case STRENGTHEN_BOW:
                load(new StrengthenBow(level));
                break;
            case STRENGTHEN_SWORD:
                load(new StrengthenSword(level));
                break;
            case FLAME_RELIC:
                load(new FlameRelic(level));
                break;
            default:
                break;
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

    @SneakyThrows
    private List<String> getYamlFilesFromSource(String folderName) {
        List<String> temporaryFiles = new ArrayList<>();

        File folder = new File(instance.getDataFolder(), folderName);
        if (!folder.exists()) folder.mkdirs();

        CodeSource src = getClass().getProtectionDomain().getCodeSource();
        if (src != null) {
            URL jar = src.getLocation();
            ZipInputStream zip = new ZipInputStream(jar.openStream());
            while (true) {
                ZipEntry e = zip.getNextEntry();
                if (e == null)
                    break;
                String name = e.getName();

                if (name.startsWith(folderName)) {
                    String resultName = name.replace(folderName + "/", "");

                    if (resultName.contains(".yml"))
                        temporaryFiles.add(resultName);

                }
            }
        }

        return temporaryFiles;
    }

}
