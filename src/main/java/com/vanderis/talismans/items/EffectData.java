package com.vanderis.talismans.items;

import com.vanderis.talismans.Talismans;
import com.vanderis.talismans.events.effect.TalismanEffectEvent;
import com.vanderis.talismans.utils.Logging;
import org.bukkit.Bukkit;
import org.bukkit.attribute.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.*;
import java.util.stream.Collectors;

public abstract class EffectData implements Listener {

    public final String effectID;

    public EffectData(String effectID) {
        this.effectID = effectID;

        Talismans.getInstance().getEffectManager().effectNameList.add(effectID);

        Bukkit.getServer().getPluginManager().registerEvents(this, Talismans.getInstance());
    }

    public EffectData(String mainEffectID, String... subEffectIDs) {
        this.effectID = mainEffectID;

        Talismans.getInstance().getEffectManager().effectNameList.add(effectID);

        Collections.addAll(Talismans.getInstance().getEffectManager().effectNameList, subEffectIDs);

        Bukkit.getServer().getPluginManager().registerEvents(this, Talismans.getInstance());
    }


    protected List<ItemData> getBagItems(Player player) {
        return Talismans.getInstance().getPlayerManager().getPlayerData(player).getBagItems();
    }

    protected Boolean itemHasEffect(ItemData item, String effectID) {
        if (item.getEffectValues().containsKey(effectID))
            return item.getNumberFromEffectValue(effectID) > 0;

        return false;
    }

    protected List<ItemData> getBagItemsHasSpecificEffect(Player player, String effectID) {
        List<ItemData> bagItems = getBagItems(player);

        return bagItems.stream().filter(itemData -> itemHasEffect(itemData, effectID)).collect(Collectors.toList());
    }

    protected TalismanEffectEvent callEvent(Player player, ItemData itemData) {
        TalismanEffectEvent talismanEffectEvent = new TalismanEffectEvent(player, itemData);

        Talismans.getInstance().callEvent(talismanEffectEvent);

        return talismanEffectEvent;
    }

    /**
     * @param negativeValue change the math of effect value to negative when true and vice versa
     * */
    protected Double getNewValueAfterAddEffect(String effectID, ItemData itemData, Double originalValue, Boolean negativeValue) {
        String effectValue = itemData.getEffectValue(effectID);

        if (effectValue.contains("%")) {
            if (negativeValue)
                return originalValue - (originalValue * itemData.getNumberFromEffectValue(effectID) / 100);
            else
                return originalValue + (originalValue * itemData.getNumberFromEffectValue(effectID) / 100);
        }

        if (effectValue.contains("x")) {
            if (negativeValue)
                return originalValue * -itemData.getNumberFromEffectValue(effectID);
            else
                return originalValue * itemData.getNumberFromEffectValue(effectID);
        }

        if (negativeValue)
            return originalValue - itemData.getNumberFromEffectValue(effectID);
        else
            return originalValue + itemData.getNumberFromEffectValue(effectID);
    }

    protected void addAttribute(Player player, Attribute attribute, ItemData itemData, String effectID) {
        player.getAttribute(attribute).addModifier(new AttributeModifier(itemData.getId(), getNewValueAfterAddEffect(effectID, itemData, 0.0, false), AttributeModifier.Operation.ADD_NUMBER));
    }

    protected boolean hasAttribute(Player player, Attribute attribute, ItemData itemData, String effectID) {
        return player.getAttribute(attribute).getModifiers().contains(new AttributeModifier(itemData.getId(), getNewValueAfterAddEffect(effectID, itemData, 0.0, false), AttributeModifier.Operation.ADD_NUMBER));
    }

    protected void removeAttribute(Player player, Attribute attribute, ItemData itemData, String effectID) {
        player.getAttribute(attribute).removeModifier(new AttributeModifier(itemData.getId(), getNewValueAfterAddEffect(effectID, itemData, 0.0, false), AttributeModifier.Operation.ADD_NUMBER));
    }

}
