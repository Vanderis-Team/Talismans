package com.vanderis.talismans.commands;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.files.PathManager;
import com.vanderis.talismans.gui.bag.BagGUI;
import com.vanderis.talismans.gui.collections.CollectionsGUI;
import com.vanderis.talismans.gui.crafting.CraftingGUI;
import com.vanderis.talismans.gui.edit.EditGUI;
import com.vanderis.talismans.items.*;
import com.vanderis.talismans.utils.*;
import lombok.SneakyThrows;
import me.orineko.pluginspigottools.CommandManager;
import my.plugin.utils.XSound;
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

    @CommandSub(length = 4, names = "give", permissions = "talismans.give")
    @SneakyThrows
    public void onGive(Player player, String[] args) {
        Logging.debug("Give Command", "Args Length: " + args.length);

        if (args.length == 4) {
            String targetName = args[1];
            ItemName itemName = ItemName.valueOf(args[2].toUpperCase());
            Integer level = Integer.valueOf(args[3]);

            Player target = findPlayerOnline(targetName, player, Color.color(Message.prefix() + " &cThat player is not online!"));

            if (target == null) {
                player.playSound(player, XSound.ENTITY_VILLAGER_NO.parseSound(), 20, 1);

                return;
            }

            Logging.debug("Give Command", "TalismansName/Level: " + itemName + "/" + level);

            ItemData itemData = instance.getItemManager().getItem(itemName, level);

            if (itemData == null) {
                Message.sendMessage(player, Message.prefix() + " &cCannot find &e" + itemName + ":" + level + " &cin database!");

                player.playSound(player, XSound.ENTITY_VILLAGER_NO.parseSound(), 20, 1);

                return;
            }

            if (itemData.getItemResult() == null) {
                Message.sendMessage(player, Message.prefix() + " &e" + itemName + ":" + level + " &chas no item stand for it!");
                Message.sendMessage(player, Message.prefix() + " &fPlease do &e/talismans edit " + itemName + " " + level + " &fto edit the talisman.");

                player.playSound(player, XSound.ENTITY_VILLAGER_NO.parseSound(), 20, 1);

                return;
            }

            instance.getItemManager().getItem(itemName, level).giveItemResult(player);

            if (player.getName().equalsIgnoreCase(targetName)) {
                Message.sendMessage(player, Message.prefix() + " &aYou just give &e" + itemName + ":" + level + " &ato &fyourself&a.");
            } else {
                Message.sendMessage(player, Message.prefix() + " &aYou just give &e" + itemName + ":" + level + " &ato &f" + target.getName() + "&a.");
            }

            Logging.debug("Give Command", "ItemResult: " + instance.getItemManager().getItem(itemName, level).getItemResult());
        }
    }

    @CommandSub(length = 2, names = "bag", permissions = "talismans.bag", justPlayerUseCmd = true)
    public void onOpenBag(Player player, String[] args) {
        switch (args.length) {
            case 1:
                instance.getGuiManager().openGUI(player, new BagGUI(player.getName()));
                break;
            case 2:
                instance.getGuiManager().openGUI(player, new BagGUI(args[1]));
        }
    }

    @CommandSub(length = 3, names = "edit", permissions = "talismans.edit", justPlayerUseCmd = true)
    public void onEdit(Player player, String[] args) {
        if (args.length == 3) {
            ItemName id = ItemName.valueOf(args[1].toUpperCase());
            Integer level = Integer.valueOf(args[2]);

            instance.getGuiManager().openGUI(player, new EditGUI(instance.getItemManager().getItem(id, level)));
        }
    }

    @CommandSub(length = 0, names = "collections", permissions = "talismans.collections", justPlayerUseCmd = true)
    public void onCollections(Player player, String[] args) {
        instance.getGuiManager().openGUI(player, new CollectionsGUI());
    }

    @CommandSub(length = 0, names = "crafting", permissions = "talismans.crafting", justPlayerUseCmd = true)
    public void onCrafting(Player player, String[] args) {
        instance.getGuiManager().openGUI(player, new CraftingGUI());
    }

    @CommandSub(length = 0, names = "status", permissions = "talismans.status", justPlayerUseCmd = true)
    public void onStatus(Player player, String[] args) {

    }

    @CommandSub(length = 0, names = "debug", permissions = "talismans.debug", justPlayerUseCmd = true)
    public void onDebug(Player player, String[] args) {
        Logging.debug = !Logging.debug;

        if (Logging.debug) {
            Message.sendMessage(player, Message.prefix() + " &aDebug has been enabled!");
        } else {
            Message.sendMessage(player, Message.prefix() + " &cDebug has been disabled!");
        }
    }

    @Nullable
    @Override
    public List<String> executeTabCompleter(@Nonnull CommandSender commandSender, @Nonnull String s, @Nonnull String[] args) {
        if (checkEqualArgs(args, 0, "give")) {
            if (args.length == 3) {
                return Arrays.stream(ItemName.values()).map(ItemName::name).collect(Collectors.toList());
            }
            if (args.length == 4) {
                return getTalismansLevels(args[2]);
            }
        }

        if (checkEqualArgs(args, 0, "edit")) {
            if (args.length == 2) {
                return Arrays.stream(ItemName.values()).map(ItemName::name).collect(Collectors.toList());
            }
            if (args.length == 3) {
                return getTalismansLevels(args[1]);
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

    private List<String> getTalismansLevels(String idArgs) {
        if (idArgs.isEmpty()) return null;

        ItemName id = ItemName.valueOf(idArgs.toUpperCase());

        long count = instance.getItemManager().itemList.stream().filter(itemData -> itemData.getId() == id).count();

        List<String> result = new ArrayList<>();

        for (long i = 1; i <= count; i++) {
            result.add(String.valueOf(i));
        }

        return result;
    }

}
