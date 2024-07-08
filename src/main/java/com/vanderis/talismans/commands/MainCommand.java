package com.vanderis.talismans.commands;

import com.vanderis.talismans.containers.Instance;
import com.vanderis.talismans.items.enums.ItemName;
import com.vanderis.talismans.bag.gui.BagGUI;
import com.vanderis.talismans.managers.PathManager;
import com.vanderis.talismans.utils.Logging;
import lombok.SneakyThrows;
import me.orineko.pluginspigottools.CommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.*;
import java.util.List;

@SuppressWarnings("unused")
@CommandManager.CommandInfo(aliases = {"talismans", "talisman"}, permissions = "talismans.admin")
public class MainCommand extends CommandManager implements Instance {

    public MainCommand(@Nonnull Plugin plugin) {
        super(plugin);
    }

    @CommandSub(length = 0, names = "help", permissions = "talismans.help")
    public void onHelp(CommandSender sender, String[] args) {
        if (PathManager.HELP.isEmpty()) return;

        List<String> list = null;
        if (args.length >= 1) {
            Logging.debug("Help Command", "Args Length >= 1");

            if (checkEqualArgs(args, 0, "help")) {
                if (args.length >= 2) {
                    list = PathManager.HELP.getOrDefault("1", null);
                } else
                    list = PathManager.HELP.getOrDefault("1", null);
            }

        } else {
            list = PathManager.HELP.getOrDefault("1", null);
        }

        if (list == null) return;

        list.forEach(sender::sendMessage);
    }

    @CommandSub(length = 0, names = "debug", permissions = "talismans.debug", justPlayerUseCmd = true)
    public void onDebug(Player player, String[] args) {
        Logging.debug = !Logging.debug;
    }

    @CommandSub(length = 0, names = "bag", permissions = "talismans.bag", justPlayerUseCmd = true)
    public void onOpenBag(Player player, String[] args) {
        instance.getGuiSystem().openGUI(player, new BagGUI());
    }

    @CommandSub(length = 3, names = "give", permissions = "talismans.give") @SneakyThrows
    public void onGive(Player player, String[] args) {
        Logging.debug("Give Command", "Args Length: " + args.length);

        if (args.length == 3) {
            ItemName itemName = ItemName.valueOf(args[1].toUpperCase());
            Integer level = Integer.valueOf(args[2]);

            Logging.debug("Give Command", "TalismansName/Level: " + itemName + "/" + level);

            instance.getItemManager().getItem(itemName, level).giveItemResult(player);

            Logging.debug("Give Command", "ItemResult: " + instance.getItemManager().getItem(itemName, level).getItemResult());
        }
    }

    @Nullable
    @Override
    public List<String> executeTabCompleter(@Nonnull CommandSender commandSender, @Nonnull String s, @Nonnull String[] args) {

        return null;
    }

    @Nonnull
    @Override
    protected String getErrorCommandMessage() {
        return "";
    }

}
