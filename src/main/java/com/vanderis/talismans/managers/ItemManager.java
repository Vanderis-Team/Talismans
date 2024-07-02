package com.vanderis.talismans.managers;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.containers.ItemData;
import com.vanderis.talismans.enums.TalismanName;
import com.vanderis.talismans.items.*;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;

public class ItemManager {

    private List<ItemData> itemList = new ArrayList<>();

    public void load(ItemData data) {
        itemList.add(data);
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
                Integer talismanLevel = Integer.parseInt(level.getName().replace(".yml", ""));

                TalismanName talismanName = TalismanName.valueOf(file.getName().toUpperCase());
                switch (talismanName) { // TODO: Something better
                    case STRENGTHEN_SWORD:
                        load(new StrengthenSword(talismanLevel));
                        break;
                    case FLAME_RELIC:
                        load(new FlameRelic(talismanLevel));
                        break;
                    default:
                        break;
                }
            }


        }
    }

    @Nullable
    public ItemData getItem(TalismanName id, Integer level) {
        return itemList.stream()
                .filter(itemData -> itemData.getId().equals(id) && itemData.getLevel().equals(level))
                .findAny().orElse(null);
    }

}
