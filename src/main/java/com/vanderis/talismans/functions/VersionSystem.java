package com.vanderis.talismans.functions;

import com.vanderis.talismans.messages.Logging;
import me.orineko.pluginspigottools.MethodDefault;
import org.bukkit.Bukkit;

public class VersionSystem {

    public int getServerVersion() {
        Logging.log(Bukkit.getServer().getClass().getPackage().getName());
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        return (int) MethodDefault.formatNumber(version.split("_")[1], 0.0);
    }

}
