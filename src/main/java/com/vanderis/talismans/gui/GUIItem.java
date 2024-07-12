package com.vanderis.talismans.gui;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter
public class GUIItem {

    private final char character;
    private final String mask;
    private final String type;
    private final ItemStack itemStack;

    public GUIItem(char character, String mask, String type, ItemStack itemStack) {
        this.character = character;
        this.mask = mask;
        this.type = type;
        this.itemStack = itemStack;
    }

    public String toString() {
        return character + " / " + mask + " / " + type;
    }

}
