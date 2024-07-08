package com.vanderis.talismans.utils;

import lombok.experimental.UtilityClass;
import me.orineko.pluginspigottools.MethodDefault;
import org.bukkit.Bukkit;

@UtilityClass
public class Version {

    public int getServerVersion() {
        try {
            Logging.log(Bukkit.getServer().getClass().getPackage().getName());
            String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

            Logging.log(String.valueOf(MethodDefault.formatNumber(version.split("_")[1], 0.0)));

            return (int) MethodDefault.formatNumber(version.split("_")[1], 0.0);
        } catch (Exception ignored) {
            return 20;
        }

    }

}
