package com.vanderis.talismans.commands;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.files.PathManager;
import com.vanderis.talismans.gui.bag.BagGUI;
import com.vanderis.talismans.items.ItemName;
import com.vanderis.talismans.utils.Logging;
import lombok.SneakyThrows;
import me.orineko.pluginspigottools.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@CommandManager.CommandInfo(aliases = {"talismans", "talisman"}, permissions = "talismans.admin")
public class MainCommand extends CommandManager {

    private final Talismans instance = Talismans.getInstance();

    public MainCommand(@Nonnull Plugin plugin) {
        super(plugin);
    }

    @CommandSub(length = 0, names = "help", permissions = "talismans.help")
    public void onHelp(CommandSender sender, String[] args) {
        if (PathManager.HELP.isEmpty()) return;

        Map<String, List<String>> helpCache = hasPermission(sender, "talismans.admin") ? PathManager.ADMIN_HELP : PathManager.HELP;

        List<String> list = null;
        if (args.length >= 1) {
            Logging.debug("Help Command", "Args Length >= 1");

            if (checkEqualArgs(args, 0, "help")) {
                if (args.length >= 2) {
                    list = helpCache.getOrDefault("1", null);
                } else
                    list = helpCache.getOrDefault("1", null);
            }

        } else {
            list = helpCache.getOrDefault("1", null);
        }

        if (list == null) return;

        list.forEach(sender::sendMessage);
    }

    @CommandSub(length = 3, names = "give", permissions = "talismans.give")
    @SneakyThrows
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

    @CommandSub(length = 0, names = "bag", permissions = "talismans.bag", justPlayerUseCmd = true)
    public void onOpenBag(Player player, String[] args) {
        switch (args.length) {
            case 1:
                instance.getGuiSystem().openGUI(player, new BagGUI(player.getName()));
                break;
            case 2:
                instance.getGuiSystem().openGUI(player, new BagGUI(args[1]));
        }
    }

    @CommandSub(length = 0, names = "status", permissions = "talismans.bag", justPlayerUseCmd = true)
    public void onStatus(Player player, String[] args) {

    }

    @CommandSub(length = 0, names = "debug", permissions = "talismans.debug", justPlayerUseCmd = true)
    public void onDebug(Player player, String[] args) {
        Logging.debug = !Logging.debug;
    }

    @Nullable
    @Override
    public List<String> executeTabCompleter(@Nonnull CommandSender commandSender, @Nonnull String s, @Nonnull String[] args) {
        if (checkEqualArgs(args, 0, "give")) {
            if (args.length == 3) {
                return Arrays.stream(ItemName.values()).map(ItemName::name).collect(Collectors.toList());
            }
            if (args.length == 4) {
                if (args[2].isEmpty()) return null;

                ItemName itemName = ItemName.valueOf(args[2].toUpperCase());


                long count = instance.getItemManager().itemList.stream().filter(itemData -> itemData.getId() == itemName).count();

                Logging.log("count: " + count);
                List<String> result = new ArrayList<>();

                for (long i = 1; i <= count; i++) {
                    result.add(String.valueOf(i));
                }

                return result;
            }
        }

        if (checkEqualArgs(args, 0, "bag", "status")) {
            if (args.length == 2) {
                return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
            }
        }

        return null;
    }

    @Nonnull
    @Override
    protected String getErrorCommandMessage() {
        return "";
    }

    private Boolean hasPermission(CommandSender sender, String permission) {
        if (!(sender instanceof Player)) return true;

        return sender.hasPermission(permission);
    }

}
