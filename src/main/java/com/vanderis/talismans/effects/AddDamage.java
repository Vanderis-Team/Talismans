package com.vanderis.talismans.effects;

import com.vanderis.talismans.containers.EffectData;
import com.vanderis.talismans.enums.EffectType;

public class AddDamage extends EffectData {

    public AddDamage() {
        super(EffectType.ADD_DAMAGE);
    }

    @Override
    public void runEffect() {

    }

    @Override
    public void canRunEffect() {

    }

    @Override
    public Boolean passConditions() {
        return null;
    }

    @Override
    public Boolean passFilters() {
        return null;
    }

}
