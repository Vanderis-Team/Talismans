package com.vanderis.talismans.containers;

import com.vanderis.talismans.enums.EffectType;

import java.util.*;

public abstract class EffectData implements Listeners, EffectMethod {

    private EffectType type;
    private List<String> effectDescription;
    private HashMap<String, String> effectArguments;
    private HashMap<String, String> triggers;
    private HashMap<String, String> filters;
    private HashMap<String, String> mutators;
    private HashMap<String, String> conditions;

    public EffectData(EffectType type) {
        this.type = type;
    }

}
