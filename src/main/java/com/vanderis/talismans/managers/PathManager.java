package com.vanderis.talismans.managers;

import com.vanderis.talismans.containers.Instance;
import com.vanderis.talismans.utils.Color;

import java.util.*;

public class PathManager implements Instance {

    public static HashMap<String, List<String>> HELP;
    public static HashMap<String, List<String>> ADMIN_HELP;

    public void register() {
        HELP = new HashMap<>();
        HELP.put("1", help1());

        ADMIN_HELP = new HashMap<>();
        ADMIN_HELP.put("1", adminHelp1());
    }

    private List<String> help1() {
        String title;

        if (instance.getVersionSystem().getServerVersion() >= 16)
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

        if (instance.getVersionSystem().getServerVersion() >= 16)
            title = "           #8413FBT#8B20FBA#922EFBL#993BFCI#A049FCS#A756FCM#AE64FCA#B571FDN#BC7FFDS #C99AFDC#D0A7FEO#D7B5FEM#DEC2FEM#E5D0FEA#ECDDFFN#F3EBFFD#FAF8FFS";
        else
            title = "           &5&lTALISMANS COMMANDS";

        return Color.color(Arrays.asList(
                "",
                title,
                " &5/talismans give <player> <name> <level>&f: Give talismans to player.",
                " &5/talismans bag [player]&f: Open talismans bag.",
                " &5/talismans status [player]&f: Show active talismans on player.",
                " &5/talismans debug&f: Debug the plugin."
        ));
    }

}
