package com.vanderis.talismans;

import com.vanderis.talismans.commands.MainCommand;
import com.vanderis.talismans.functions.VersionSystem;
import com.vanderis.talismans.managers.PathManager;
import lombok.Getter;
import me.orineko.pluginspigottools.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Talismans extends JavaPlugin {

    private static Talismans instance;

    private PathManager pathManager;

    private VersionSystem versionSystem;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        instance = this;

        registerSystems();

        registerManagers();

        registerCommands();
    }

    private void registerManagers() {
        pathManager = new PathManager();
        pathManager.register();
    }

    private void registerSystems() {
        versionSystem = new VersionSystem();
    }

    private void registerCommands() {
        CommandManager.CommandRegistry.register(true, this, new MainCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Talismans getInstance() {
        return instance;
    }

}
