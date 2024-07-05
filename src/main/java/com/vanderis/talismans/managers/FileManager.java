package com.vanderis.talismans.managers;

import com.vanderis.talismans.containers.Instance;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.*;

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

}
