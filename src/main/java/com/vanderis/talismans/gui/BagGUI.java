package com.vanderis.talismans.gui;

import com.vanderis.talismans.containers.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.InventoryHolder;

public class BagGUI extends GUIHolder implements Instance {

    public BagGUI() {
        super(instance.getFileManager().bagGUI);
    }

}
