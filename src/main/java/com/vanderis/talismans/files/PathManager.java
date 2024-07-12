package com.vanderis.talismans.files;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.utils.*;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class PathManager {

    private final Talismans instance = Talismans.getInstance();

    private final FileConfiguration config = instance.getFileManager().config;

    public static HashMap<String, List<String>> HELP;
    public static HashMap<String, List<String>> ADMIN_HELP;

    public static Integer DEFAULT_BAG_SIZE = 0;

    public void register() {
        HELP = new HashMap<>();
        HELP.put("1", help1());

        ADMIN_HELP = new HashMap<>();
        ADMIN_HELP.put("1", adminHelp1());

        DEFAULT_BAG_SIZE = config.getInt("bag.default-size");
    }

    private List<String> help1() {
        String title;

        if (Version.getServerVersion() >= 16)
            title = "           #8413FBT#8B20FBA#922EFBL#993BFCI#A049FCS#A756FCM#AE64FCA#B571FDN#BC7FFDS #C99AFDC#D0A7FEO#D7B5FEM#DEC2FEM#E5D0FEA#ECDDFFN#F3EBFFD#FAF8FFS";
        else
            title = "           &5&lTALISMANS COMMANDS";

        return Color.color(Arrays.asList(
                "",
                title,
                " &5/talismans bag&f: Open talismans bag.",
                " &5/talismans status&f: Show active talismans."
        ));
    }

    private List<String> adminHelp1() {
        String title;

        if (Version.getServerVersion() >= 16)
            title = "           #8413FBT#8B20FBA#922EFBL#993BFCI#A049FCS#A756FCM#AE64FCA#B571FDN#BC7FFDS #C99AFDC#D0A7FEO#D7B5FEM#DEC2FEM#E5D0FEA#ECDDFFN#F3EBFFD#FAF8FFS";
        else
            title = "           &5&lTALISMANS COMMANDS";

        return Color.color(Arrays.asList(
                "",
                title,
                " &5/talismans give <player> <name> <level>&f: Give talismans to player.",
                " &5/talismans bag [player]&f: Open talismans bag.",
                " &5/talismans edit <id> <level>&f: Edit talismans.",
                " &5/talismans status [player]&f: Show active talismans on player.",
                " &5/talismans debug&f: Debug the plugin."
        ));
    }

}
