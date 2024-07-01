package com.vanderis.talismans.enums;

public enum EffectType {

    ADD_DAMAGE("add_damage"), ADD_ENCHANT("add_enchant"), ADD_GLOBAL_POINTS("add_global_points"), ADD_HOLDER("add_holder"), ADD_HOLDER_IN_RADIUS("add_holder_in_radius"),
    ADD_HOLDER_TO_VICTIM("add_holder_to_victim"), ADD_PERMANENT_HOLDER_IN_RADIUS("add_permanent_holder_in_radius"), ADD_POINTS("add_points"), AGE_CROP("age_crop"),
    ALL_PLAYERS("all_players"), ANIMATION("animation"), AOE("aoe"), AOE_BLOCKS("aoe_blocks"), ARMOR("armor"), ARMOR_TOUGHNESS("armor_toughness"), ARROW_RING("arrow_ring"),
    ATTACK_SPEED_MULTIPLIER("attack_speed_multiplier"), AUTOSMELT("autosmelt"), BLEED("bleed"), BLOCK_COMMANDS("block_commands"), BLOCK_REACH("block_reach"), BONUS_HEALTH("bonus_health"),
    BREAK_BLOCK("break_block"), BREW_TIME_MULTIPLIER("brew_time_multiplier"), BROADCAST("broadcast"), CANCEL_EVENT("cancel_event"), CLEAR_INVULNERABILITY("clear_invulnerability"),
    CLOSE_INVENTORY("close_inventory"), CONSUME_HELD_ITEM("consume_held_item"), CREATE_BOSS_BAR("create_boss_bar"), CREATE_EXPLOSION("create_explosion"), CREATE_HOLOGRAM("create_hologram"),
    CRIT_MULTIPLIER("crit_multiplier"), DAMAGE_ARMOR("damage_armor"), DAMAGE_ITEM("damage_item"), DAMAGE_MAINHAND("damage_mainhand"), DAMAGE_MULTIPLIER("damage_multiplier"),
    DAMAGE_NEARBY_ENTITIES("damage_nearby_entities"), DAMAGE_OFFHAND("damage_offhand"), DAMAGE_TWICE("damage_twice"), DAMAGE_VICTIM("damage_victim"), DONT_CONSUME_LAPIS_CHANCE("dont_consume_lapis_chance"),
    DONT_CONSUME_XP_CHANCE("dont_consume_xp_chance"), DRILL("drill"), DROP_ITEM("drop_item"), DROP_ITEM_SLOT("drop_item_slot"), DROP_PICKUP_ITEM("drop_pickup_item"), DROP_RANDOM_ITEM("drop_random_item"),
    DROP_WEIGHTED_RANDOM_ITEM("drop_weighted_random_item"), ELYTRA_BOOST_SAVE_CHANCE("elytra_boost_save_chance"), ENTITY_REACH("entity_reach"), EXTINGUISH("extinguish"), FEATHER_STEP("feather_step"),
    FLIGHT("flight"), FOOD_MULTIPLIER("food_multiplier"), GIVE_FOOD("give_food"), GIVE_GLOBAL_POINTS("give_global_points"), GIVE_HEALTH("give_health"), GIVE_ITEM("give_item"),
    GIVE_ITEM_POINTS("give_item_points"), GIVE_MONEY("give_money"), GIVE_OXYGEN("give_oxygen"), GIVE_PERMISSION("give_permission"), GIVE_POINTS("give_points"),
    GIVE_PRICE("give_price"), GIVE_SATURATION("give_saturation"), GIVE_XP("give_xp"), GLOW_NEARBY_BLOCKS("glow_nearby_blocks"), GRAVITY_MULTIPLIER("gravity_multiplier"),
    HOMING("homing"), HUNGER_MULTIPLIER("hunger_multiplier"), IGNITE("ignite"), INCREASE_STEP_HEIGHT("increase_step_height"), ITEM_DURABILITY_MULTIPLIER("item_durability_multiplier"),
    JUMP_STRENGTH_MULTIPLIER("jump_strength_multiplier"), KEEP_INVENTORY("keep_inventory"), KEEP_LEVEL("keep_level"), KICK("kick"), KNOCK_AWAY("knock_away"),
    KNOCKBACK_MULTIPLIER("knockback_multiplier"), KNOCKBACK_RESISTANCE_MULTIPLIER("knockback_resistance_multiplier"), LEVEL_ITEM("level_item"), LUCK_MULTIPLIER("luck_multiplier"),
    MINE_RADIUS("mine_radius"), MINE_RADIUS_ONE_DEEP("mine_radius_one_deep"), MINE_VEIN("mine_vein"), MINING_EFFICIENCY("mining_efficiency"), MINING_SPEED_MULTIPLIER("mining_speed_multiplier"),
    MOVEMENT_EFFICIENCY_MULTIPLIER("movement_efficiency_multiplier"), MOVEMENT_SPEED_MULTIPLIER("movement_speed_multiplier"), MULTIPLY_DROPS("multiply_drops"),
    MULTIPLY_GLOBAL_POINTS("multiply_global_points"), MULTIPLY_ITEM_POINTS("multiply_item_points"), MULTIPLY_POINTS("multiply_points"), MULTIPLY_VELOCITY("multiply_velocity"),
    NAME_ENTITY("name_entity"), OPEN_CRAFTING("open_crafting"), OPEN_ENDER_CHEST("open_ender_chest"), PARTICLE_ANIMATION("particle_animation"), PARTICLE_LINE("particle_line"),
    PAY_PRICE("pay_price"), PERMANENT_POTION_EFFECT("permanent_potion_effect"), PIERCING("piercing"), PLAY_SOUND("play_sound"), POTION_DURATION_MULTIPLIER("potion_duration_multiplier"),
    POTION_EFFECT("potion_effect"), PULL_IN("pull_in"), PULL_TO_LOCATION("pull_to_location"), RANDOM_PLAYER("random_player"), RAPID_BOWS("rapid_bows"),
    REEL_SPEED_MULTIPLIER("reel_speed_multiplier"), REGEN_MULTIPLIER("regen_multiplier"), REMOVE_BOSS_BAR("remove_boss_bar"), REMOVE_ENCHANT("remove_enchant"),
    REMOVE_ITEM("remove_item"), REMOVE_ITEM_DATA("remove_item_data"), REMOVE_POTION_EFFECT("remove_potion_effect"), REPAIR_ITEM("repair_item"),
    REPLACE_NEAR("replace_near"), REPLANT_CROPS("replant_crops"), ROTATE("rotate"), ROTATE_VICTIM("rotate_victim"), RUN_CHAIN("run_chain"),
    RUN_COMMAND("run_command"), RUN_PLAYER_COMMAND("run_player_command"), SAFE_FALL_DISTANCE("safe_fall_distance"), SELL_ITEMS("sell_items"),
    SELL_MULTIPLIER("sell_multiplier"), SEND_MESSAGE("send_message"), SEND_MINIMESSAGE("send_minimessage"), SEND_TITLE("send_title"), SET_ARMOR_TRIM("set_armor_trim"),
    SET_BLOCK("set_block"), SET_CUSTOM_MODEL_DATA("set_custom_model_data"), SET_FOOD("set_food"), SET_FREEZE_TICKS("set_freeze_ticks"), SET_GLOBAL_POINTS("set_global_points"),
    SET_ITEM_DATA("set_item_data"), SET_ITEM_POINTS("set_item_points"), SET_POINTS("set_points"), SET_SATURATION("set_saturation"),
    SET_VELOCITY("set_velocity"), SET_VICTIM_VELOCITY("set_victim_velocity"), SHOOT("shoot"),
    SHOOT_ARROW("shoot_arrow"), SHUFFLE_HOTBAR("shuffle_hotbar"), SKILL_XP_MULTIPLIER("skill_xp_multiplier"),
    SMITE("smite"), SNEAKING_SPEED_MULTIPLIER("sneaking_speed_multiplier"), SPAWN_ENTITY("spawn_entity"),
    SPAWN_MOBS("spawn_mobs"), SPAWN_PARTICLE("spawn_particle"), SPAWN_POTION_CLOUD("spawn_potion_cloud"),
    START_QUEST("start_quest"), STRIKE_LIGHTNING("strike_lightning"), STRIP_AI("strip_ai"),
    SWARM("swarm"), TAKE_MONEY("take_money"), TARGET_PLAYER("target_player"),
    TELEKINESIS("telekinesis"), TELEPORT("teleport"), TELEPORT_TO("teleport_to"), TELEPORT_TO_GROUND("teleport_to_ground"),
    TRACEBACK("traceback"), TRANSMISSION("transmission"), TRIGGER_CUSTOM("trigger_custom"),
    UNDERWATER_MINING_SPEED_MULTI("underwater_mining_speed_multi"), UPDATE_BOSS_BAR("update_boss_bar"), VICTIM_SPEED_MULTIPLIER("victim_speed_multiplier"),
    XP_MULTIPLIER("xp_multiplier");

    private final String id;

    EffectType(String id) {
        this.id = id;
    }

}
