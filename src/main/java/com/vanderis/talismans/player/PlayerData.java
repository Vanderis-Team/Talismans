package com.vanderis.talismans.player;

import com.vanderis.talismans.files.PathManager;
import com.vanderis.talismans.items.ItemData;
import lombok.Getter;

import java.util.*;

@Getter
public class PlayerData {

    private Integer bagSize;
    private List<ItemData> bagItems;

    public PlayerData() {
        bagSize = PathManager.DEFAULT_BAG_SIZE;
        bagItems = new ArrayList<>();
    }

    public PlayerData(List<ItemData> bagItems) {
        this.bagSize = PathManager.DEFAULT_BAG_SIZE;
        this.bagItems = bagItems;
    }

    public PlayerData(Integer bagSize, List<ItemData> bagItems) {
        this.bagSize = bagSize;
        this.bagItems = bagItems;
    }

}
