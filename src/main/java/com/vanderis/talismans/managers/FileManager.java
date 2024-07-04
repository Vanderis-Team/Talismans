package com.vanderis.talismans.managers;

import com.vanderis.talismans.containers.Instance;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class FileManager implements Instance {

    public FileConfiguration collectionsGUI;
    public FileConfiguration bagGUI;
    public FileConfiguration editGUI;
    public FileConfiguration craftingGUI;

    @SneakyThrows
    public void register() {
        collectionsGUI.load(new File(instance.getDataFolder(), "/gui/collections.yml"));
        bagGUI.load(new File(instance.getDataFolder(), "/gui/bag.yml"));
        editGUI.load(new File(instance.getDataFolder(), "/gui/edit.yml"));
        craftingGUI.load(new File(instance.getDataFolder(), "/gui/crafting.yml"));
    }

}
