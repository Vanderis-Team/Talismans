package com.vanderis.talismans.constructors;

import com.vanderis.talismans.containers.EffectData;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ItemData {

    private String name;
    private List<String> description;
    private List<String> higherLevelOf;
    private ItemStack itemStack;
    private String texture;
    private Boolean craftable;
    private List<String> recipe;
    private Set<EffectData> effectList;
    private HashMap<String, String> conditions;

}
