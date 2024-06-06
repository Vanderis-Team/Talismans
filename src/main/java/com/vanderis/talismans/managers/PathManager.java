package com.vanderis.talismans.managers;

import com.vanderis.talismans.containers.Instance;
import com.vanderis.talismans.messages.Color;

import java.util.*;

public class PathManager implements Instance {

    public static HashMap<String, List<String>> HELP;

    public void register() {
        HELP = new HashMap<>();
        HELP.put("1", getHelp1());
    }

    private List<String> getHelp1() {
        String title;

        if (instance.getVersionSystem().getServerVersion() >= 16)
            title = "           #8413FBT#8B20FBA#922EFBL#993BFCI#A049FCS#A756FCM#AE64FCA#B571FDN#BC7FFDS #C99AFDC#D0A7FEO#D7B5FEM#DEC2FEM#E5D0FEA#ECDDFFN#F3EBFFD#FAF8FFS";
        else
            title = "           &5&lTALISMANS COMMANDS";

        return Color.color(Arrays.asList(
                "",
                title,
                " &5/talismans give <player> <name>&f: Give talismans to player."
        ));
    }

}
