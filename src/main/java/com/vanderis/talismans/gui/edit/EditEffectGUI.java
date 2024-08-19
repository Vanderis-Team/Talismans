package com.vanderis.talismans.gui.edit;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.gui.GUIHolder;
import com.vanderis.talismans.items.*;
import com.vanderis.talismans.utils.*;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class EditEffectGUI extends GUIHolder {

    private final Talismans instance = Talismans.getInstance();
    private final EffectManager effectManager = instance.getEffectManager();

    @Getter
    private int currentPage;
    private int maxPage = 1;

    private Player viewer;

    @Getter
    protected final ItemData itemData;

    public EditEffectGUI(ItemData itemData) {
        super(Talismans.getInstance().getFileManager().editEffectGUI);

        this.itemData = itemData;

        this.currentPage = 1;
    }

    @Override
    public void openInventory(Player player) {
        super.openInventory(player);

        this.viewer = player;
    }

    @Override
    public boolean updateInventory() {
        super.updateInventory();

        this.maxPage = Math.max(1, (int) Math.ceil((double) effectManager.effectNameList.size() / getAmountOfType("item-slot")));

        viewer.getOpenInventory().setTitle(Placeholder.replacePlaceholders(Color.color(fileConfiguration.getString("title")), itemData.getName(), String.valueOf(currentPage), String.valueOf(maxPage)));

        int itemIndex = currentPage == 1 ? 0 : getAmountOfType("item-slot") * (currentPage - 1);
        int lastItemIndex = currentPage == maxPage ? effectManager.effectNameList.size() - 1 : getAmountOfType("item-slot") * currentPage - 1;

        if (lastItemIndex >= effectManager.effectNameList.size() - 1)
            lastItemIndex = effectManager.effectNameList.size() - 1;

        for (int i = 0; i < itemsPutInGUI.size(); i++) {

            if (isType(i, "item-slot")) {
                if (effectManager.effectNameList.isEmpty())
                    break;

                if (itemIndex > lastItemIndex)
                    break;

                this.inventory.setItem(i, effectItem(itemData, effectManager.effectNameList.get(itemIndex++)));
            }

        }

        return true;
    }

    public void nextPage() {
        if (currentPage != maxPage) {
            currentPage++;

            updateInventory();
        }
    }

    public void previousPage() {
        if (currentPage != 1) {
            currentPage--;

            updateInventory();
        }
    }

    private ItemStack effectItem(ItemData itemData, String effectID) {
        String effectValue = itemData.getEffectValue(effectID);
        Double effectValueNumber = itemData.getNumberFromEffectValue(effectID);
        Material effectMaterial = effectValueNumber <= 0 ? Material.RED_STAINED_GLASS_PANE : Material.GREEN_STAINED_GLASS_PANE;

        ItemStack itemStack = new ItemStack(effectMaterial);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(Color.color((effectValueNumber <= 0 ? "&c" : "&a") + effectID));

        if (itemMeta.getLore() == null)
            itemMeta.setLore(Color.color(List.of("", "&fCurrent value: " + (effectValueNumber <= 0 ? "&c" : "&a") + effectValue, "", "&eClick here to edit the value.")));
        else
        {
            List<String> lore = itemMeta.getLore();
            itemMeta.setLore(Color.color(List.of("", "&fCurrent value: " + (effectValueNumber <= 0 ? "&c" : "&a") + effectValue, "", "&eClick here to edit the value.")));

            itemMeta.setLore(lore);
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
