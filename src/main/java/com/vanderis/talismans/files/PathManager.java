package com.vanderis.talismans.files;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.utils.*;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class PathManager {

    private final Talismans instance = Talismans.getInstance();

    private final FileConfiguration config = instance.getFileManager().config;
    private final FileConfiguration messages = instance.getFileManager().messages;

    public static HashMap<String, List<String>> HELP;
    public static HashMap<String, List<String>> ADMIN_HELP;

    public static Integer DEFAULT_BAG_SIZE = 0;

    public static String PREFIX_16UP;
    public static String PREFIX_16DOWN;

    public static List<String> STATUS_COMMAND_MESSAGE;
    public static String STATUS_COMMAND_NONE_FOUND;

    public static String BAG_EQUIP_WRONG_ITEM;
    public static String BAG_UNEQUIP;
    public static String BAG_BLOCK_SLOT;

    public static String CRAFTING_SUCCESS;

    public void register() {
        HELP = new HashMap<>();
        HELP.put("1", help1());

        ADMIN_HELP = new HashMap<>();
        ADMIN_HELP.put("1", adminHelp1());

        DEFAULT_BAG_SIZE = config.getInt("bag.default-size");

        PREFIX_16UP = messages.getString("prefix.16up");
        PREFIX_16DOWN = messages.getString("prefix.16down");

        STATUS_COMMAND_MESSAGE = new ArrayList<>();
        STATUS_COMMAND_MESSAGE = messages.getStringList("command.status.message");
        STATUS_COMMAND_NONE_FOUND = messages.getString("command.status.none-found");

        BAG_EQUIP_WRONG_ITEM = messages.getString("bag.equip-wrong-item");
        BAG_UNEQUIP = messages.getString("bag.unequip-item");
        BAG_BLOCK_SLOT = messages.getString("bag.block-slot");

        CRAFTING_SUCCESS = messages.getString("crafting.success-craft-item");
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
                " &5/talismans collections&f: Talismans list.",
                " &5/talismans status [player]&f: Show active talismans on player.",
                " &5/talismans debug&f: Debug the plugin."
        ));
    }

}
