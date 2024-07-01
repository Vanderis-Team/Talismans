package com.vanderis.talismans.managers;

import com.vanderis.talismans.containers.EffectData;
import com.vanderis.talismans.effects.AddDamage;
import com.vanderis.talismans.enums.EffectType;

import java.util.*;

public class EffectManager {

    private final Map<EffectType, EffectData> effectList = new HashMap<>();

    public void register() {
        effectList.put(EffectType.ADD_DAMAGE, new AddDamage());
    }

}
