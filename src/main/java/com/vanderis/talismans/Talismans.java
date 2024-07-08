package com.vanderis.talismans;

import com.vanderis.talismans.bag.managers.BagManager;
import com.vanderis.talismans.commands.MainCommand;
import com.vanderis.talismans.functions.*;
import com.vanderis.talismans.gui.functions.GUISystem;
import com.vanderis.talismans.items.managers.ItemManager;
import com.vanderis.talismans.listeners.*;
import com.vanderis.talismans.managers.*;
import lombok.Getter;
import me.orineko.pluginspigottools.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Talismans extends JavaPlugin {

    private static Talismans instance;

    private PathManager pathManager;
    private FileManager fileManager;
    private BagManager bagManager;
    private ItemManager itemManager;

    private VersionSystem versionSystem;
    private GUISystem guiSystem;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        instance = this;

        registerSystems();

        registerManagers();

        registerCommands();

        registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        unregisterManagers();
    }

    private void registerManagers() {
        pathManager = new PathManager();
        pathManager.register();

        fileManager = new FileManager();
        fileManager.register();

        bagManager = new BagManager();
        bagManager.register();

        itemManager = new ItemManager();
        itemManager.register();
    }

    private void unregisterManagers() {
        bagManager.unregister();
    }

    private void registerSystems() {
        versionSystem = new VersionSystem();
        guiSystem = new GUISystem();
    }

    private void registerCommands() {
        CommandManager.CommandRegistry.register(true, this, new MainCommand(this));
    }

    private void registerEvents() {
        Bukkit.getServer().getPluginManager().registerEvents(new ClickEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new QuitEvent(), this);
    }

    public static Talismans getInstance() {
        return instance;
    }

}
