package com.vanderis.talismans.utils;

import lombok.experimental.UtilityClass;
import me.orineko.pluginspigottools.MethodDefault;
import org.bukkit.Bukkit;

@UtilityClass
public class Version {

    public int getServerVersion() {
        try {
            String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

            return (int) MethodDefault.formatNumber(version.split("_")[1], 0.0);
        } catch (Exception ignored) {
            return 20;
        }

    }

}
