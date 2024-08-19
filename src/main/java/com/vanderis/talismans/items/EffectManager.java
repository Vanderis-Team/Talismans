package com.vanderis.talismans.items;

import com.vanderis.talismans.items.effects.*;

import java.util.*;

public class EffectManager {

    public List<String> effectNameList = new ArrayList<>();

    public void register() {
        new AddDamage();
        new AddHealth();
        new HealthRegen();
        new ReduceDamage();
    }

}
