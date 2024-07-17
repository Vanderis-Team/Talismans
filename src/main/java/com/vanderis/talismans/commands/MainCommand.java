package com.vanderis.talismans.commands;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.files.PathManager;
import com.vanderis.talismans.gui.bag.BagGUI;
import com.vanderis.talismans.gui.collections.CollectionsGUI;
import com.vanderis.talismans.gui.crafting.CraftingGUI;
import com.vanderis.talismans.gui.edit.EditGUI;
import com.vanderis.talismans.items.*;
import com.vanderis.talismans.player.*;
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
    
    private final PlayerManager playerManager = instance.getPlayerManager();

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

    @SneakyThrows
    @CommandSub(length = 4, names = "give", permissions = "talismans.give")
    public void onGive(CommandSender sender, String[] args) {
        Logging.debug("Give Command", "Args Length: " + args.length);

        if (args.length == 4) {
            String targetName = args[1];
            ItemName itemName = ItemName.valueOf(args[2].toUpperCase());
            Integer level = Integer.valueOf(args[3]);

            Player target = findPlayerOnline(targetName, sender, Color.color(Message.prefix() + " &cThat player is not online!"));

            if (target == null) {
                if (sender instanceof Player)
                    ((Player) sender).playSound((Player) sender, XSound.ENTITY_VILLAGER_NO.parseSound(), 20, 1);

                return;
            }

            Logging.debug("Give Command", "TalismansName/Level: " + itemName + "/" + level);

            ItemData itemData = instance.getItemManager().getItem(itemName, level);

            if (itemData == null) {
                Message.sendMessage(sender, Message.prefix() + " &cCannot find &e" + itemName + ":" + level + " &cin database!");

                if (sender instanceof Player)
                    ((Player) sender).playSound((Player) sender, XSound.ENTITY_VILLAGER_NO.parseSound(), 20, 1);

                return;
            }

            if (itemData.getItemResult() == null) {
                Message.sendMessage(sender, Message.prefix() + " &e" + itemName + ":" + level + " &chas no item stand for it!");

                if (sender instanceof Player) {
                    Message.sendMessage(sender, Message.prefix() + " &fPlease do &e/talismans edit " + itemName + " " + level + " &fto edit the talisman.");

                    ((Player) sender).playSound((Player) sender, XSound.ENTITY_VILLAGER_NO.parseSound(), 20, 1);
                }

                return;
            }

            instance.getItemManager().getItem(itemName, level).giveItemResult(target);

            if (sender instanceof Player)
                if (sender.getName().equalsIgnoreCase(targetName)) {
                    Message.sendMessage(sender, Message.prefix() + " &aYou just give &e" + itemName + ":" + level + " &ato &fyourself&a.");
                } else {
                    Message.sendMessage(sender, Message.prefix() + " &aYou just give &e" + itemName + ":" + level + " &ato &f" + target.getName() + "&a.");
                }
            else
                Message.sendMessage(sender, Message.prefix() + " &aYou just give &e" + itemName + ":" + level + " &ato &f" + target.getName() + "&a.");

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

    @SneakyThrows
    @CommandSub(length = 3, names = {"bag", "resize"}, permissions = "talismans.bag.resize")
    public void onBagResize(CommandSender sender, String[] args) {
        if (args.length == 3) {
            String targetName = args[1];
            Integer size = Integer.valueOf(args[2]);

            PlayerData playerData = playerManager.getPlayerData(targetName);

            playerData.setBagSize(size);

            playerManager.fromCacheToFile(targetName);
        }
    }

    @CommandSub(length = 3, names = "edit", permissions = "talismans.edit", justPlayerUseCmd = true)
    public void onOpenEdit(Player player, String[] args) {
        if (args.length == 3) {
            ItemName id = ItemName.valueOf(args[1].toUpperCase());
            Integer level = Integer.valueOf(args[2]);

            instance.getGuiManager().openGUI(player, new EditGUI(instance.getItemManager().getItem(id, level)));
        }
    }

    @CommandSub(length = 0, names = "collections", permissions = "talismans.collections", justPlayerUseCmd = true)
    public void onOpenCollections(Player player, String[] args) {
        instance.getGuiManager().openGUI(player, new CollectionsGUI());
    }

    @CommandSub(length = 0, names = "crafting", permissions = "talismans.crafting", justPlayerUseCmd = true)
    public void onOpenCrafting(Player player, String[] args) {
        instance.getGuiManager().openGUI(player, new CraftingGUI());
    }

    @CommandSub(length = 2, names = "status", permissions = "talismans.status")
    public void onStatus(CommandSender sender, String[] args) {
        if (args.length == 1 && sender instanceof Player)
            status(sender, (Player) sender);

        if (args.length == 2) {
            String targetName = args[1];

            Player target = findPlayerOnline(targetName, sender, Color.color(Message.prefix() + " &cThat player is not online!"));

            if (target == null) {
                if (sender instanceof Player)
                    ((Player) sender).playSound((Player) sender, XSound.ENTITY_VILLAGER_NO.parseSound(), 20, 1);

                return;
            }

            status(sender, target);
        }
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

    private void status(CommandSender sender, Player target) {

        for (String line : PathManager.STATUS_COMMAND_MESSAGE) {
            if (line.contains("<talismans-list>")) {
                List<String> itemsName = new ArrayList<>(playerManager.getPlayerData(target).getAllItemsName());

                Logging.debug("Status Command", "Items List: " + itemsName);

                if (itemsName.isEmpty()) {
                    Message.sendMessage(sender, PathManager.STATUS_COMMAND_NONE_FOUND);
                } else {
                    Message.sendMessages(sender,  itemsName);
                }

                continue;
            }

            Message.sendMessage(target, line);
        }
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
