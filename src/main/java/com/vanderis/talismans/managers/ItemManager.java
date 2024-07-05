package com.vanderis.talismans.managers;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.containers.*;
import com.vanderis.talismans.enums.TalismanName;
import com.vanderis.talismans.items.*;
import me.orineko.pluginspigottools.FileManager;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;

public class ItemManager implements Instance {

    private List<ItemData> itemList = new ArrayList<>();

    public void register() {
        loadTalismans();
    }

    public void loadTalismans() {
        File folder = new File(Talismans.getInstance().getDataFolder(), "talismans");
        if (!folder.exists()) folder.mkdirs();

        File[] talismans = folder.listFiles();

        if (talismans == null)
            return;

        for (File file : talismans) {
            File[] levelFile = file.listFiles();

            if (levelFile == null)
                continue;

            for (File level : levelFile) {
                new FileManager("talismans/" + file.getName() + "/" + level.getName() + ".yml", instance).copyDefault();

                Integer talismanLevel = Integer.parseInt(level.getName().replace(".yml", ""));

                TalismanName talismanName = TalismanName.valueOf(file.getName().toUpperCase());
                switch (talismanName) { // TODO: Something better
                    case STRENGTHEN_WEAPON:
                        load(new StrengthenWeapon(talismanName, talismanLevel));
                        break;
                    case STRENGTHEN_MELEE:
                        load(new StrengthenMelee(talismanName, talismanLevel));
                        break;
                    case STRENGTHEN_BOW:
                        load(new StrengthenBow(talismanName, talismanLevel));
                        break;
                    case STRENGTHEN_SWORD:
                        load(new StrengthenSword(talismanName, talismanLevel));
                        break;
                    case FLAME_RELIC:
                        load(new FlameRelic(talismanName, talismanLevel));
                        break;
                    default:
                        break;
                }
            }


        }
    }

    public void load(ItemData data) {
        itemList.add(data);
    }

    @Nullable
    public ItemData getItem(TalismanName id, Integer level) {
        return itemList.stream()
                .filter(itemData -> itemData.getId().equals(id) && itemData.getLevel().equals(level))
                .findAny().orElse(null);
    }

}
