package com.vanderis.talismans;

import com.vanderis.talismans.gui.bag.BagManager;
import com.vanderis.talismans.commands.MainCommand;
import com.vanderis.talismans.files.*;
import com.vanderis.talismans.gui.GUIManager;
import com.vanderis.talismans.gui.collections.CollectionsManager;
import com.vanderis.talismans.gui.crafting.CraftingManager;
import com.vanderis.talismans.gui.edit.EditManager;
import com.vanderis.talismans.items.ItemManager;
import com.vanderis.talismans.listeners.*;
import com.vanderis.talismans.player.PlayerManager;
import lombok.Getter;
import me.orineko.pluginspigottools.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Talismans extends JavaPlugin {

    private static Talismans instance;

    private FileManager fileManager;
    private PathManager pathManager;
    private PlayerManager playerManager;
    private ItemManager itemManager;
    private GUIManager guiManager;
    private BagManager bagManager;
    private EditManager editManager;
    private CollectionsManager collectionsManager;
    private CraftingManager craftingManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        instance = this;

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
        fileManager = new FileManager();
        fileManager.register();

        pathManager = new PathManager();
        pathManager.register();

        itemManager = new ItemManager();
        itemManager.register();

        guiManager = new GUIManager();

        bagManager = new BagManager();

        editManager = new EditManager();

        collectionsManager = new CollectionsManager();

        craftingManager = new CraftingManager();

        playerManager = new PlayerManager();
        playerManager.register();
    }

    private void unregisterManagers() {
        playerManager.unregister();
        guiManager.unregister();
    }

    private void registerCommands() {
        CommandManager.CommandRegistry.register(true, this, new MainCommand(this));
    }

    private void registerEvents() {
        registerEvent(new ClickEvent());
        registerEvent(new CloseEvent());
        registerEvent(new JoinEvent());
        registerEvent(new QuitEvent());
    }

    private void registerEvent(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, this);
    }

    public static Talismans getInstance() {
        return instance;
    }

}
