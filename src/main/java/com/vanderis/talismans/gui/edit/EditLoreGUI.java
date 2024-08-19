package com.vanderis.talismans.gui.edit;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.gui.GUIHolder;
import com.vanderis.talismans.items.ItemData;
import com.vanderis.talismans.utils.*;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class EditLoreGUI extends GUIHolder {

    private final Talismans instance = Talismans.getInstance();

    protected final ItemData itemData;

    public EditLoreGUI(ItemData itemData) {
        super(Talismans.getInstance().getFileManager().editLoreGUI);

        this.itemData = itemData;
    }

    @Override
    public void openInventory(Player player) {
        super.openInventory(player);

        player.getOpenInventory().setTitle(Placeholder.replacePlaceholders(Color.color(fileConfiguration.getString("title")), itemData.getId()));
    }

}
