package com.vanderis.talismans.containers;

public interface EffectMethod {

    void runEffect();

    void canRunEffect();

    Boolean passConditions();

    Boolean passFilters();

}
