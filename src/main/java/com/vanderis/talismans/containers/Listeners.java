package com.vanderis.talismans.containers;

import org.bukkit.event.block.*;
import org.bukkit.event.enchantment.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.event.raid.RaidFinishEvent;
import org.bukkit.event.vehicle.*;

public interface Listeners {

    default void onJoin(PlayerJoinEvent event) {}

    default void onQuit(PlayerQuitEvent event) {}

    default void onKillPlayer(PlayerDeathEvent event) {}

    default void onDeath(PlayerDeathEvent event) {}

    default void onMobDeath(EntityDeathEvent event) {}

    default void onInteract(PlayerInteractEvent event) {}

    default void onCreatePotion(BrewEvent event) {}

    default void onPotionEffect(EntityPotionEffectEvent event) {}

    default void onRightClickedEntity(PlayerInteractEntityEvent event) {}

    default void onInventoryClick(InventoryClickEvent event) {}

    default void onDamageEntity(EntityDamageByEntityEvent event) {}

    default void onFishing(PlayerFishEvent event) {}

    default void onChangeExp(PlayerExpChangeEvent event) {}

    default void onBreed(EntityBreedEvent event) {}

    default void onMove(PlayerMoveEvent event) {}

    default void onFinishRaid(RaidFinishEvent event) {}

    default void onProjectileHit(ProjectileHitEvent event) {}

    default void onCloseInventory(InventoryCloseEvent event) {}

    default void onBreak(BlockBreakEvent event) {}

    default void onPrepareInventory(PrepareInventoryResultEvent event) {}

    default void onDamageBlock(BlockDamageEvent event) {}

    default void onOpenInventory(InventoryOpenEvent event) {}

    default void onPrepareEnchantable(PrepareItemEnchantEvent event) {}

    default void onTeleport(PlayerTeleportEvent event) {}

    default void onConsumeItem(PlayerItemConsumeEvent event) {}

    default void onHungry(FoodLevelChangeEvent event) {}

    default void onDamageEntity(EntityDamageEvent event) {}

    default void onSprint(PlayerToggleSprintEvent event) {}

    default void onPotionSplash(PotionSplashEvent event) {}

    default void onChangeHeldItem(PlayerItemHeldEvent event) {}

    default void onCraftItem(CraftItemEvent event) {}

    default void onItemDamage(PlayerItemDamageEvent event) {}

    default void onProjectileLaunch(ProjectileLaunchEvent event) {}

    default void onEntityTarget(EntityTargetEvent event) {}

    default void onBlockPlace(BlockPlaceEvent event) {}

    default void onLeaveBed(PlayerBedLeaveEvent event) {}

    default void onItemBroken(PlayerItemBreakEvent event) {}

    default void onToggleFly(PlayerToggleFlightEvent event) {}

    default void onVehicleEnter(VehicleEnterEvent event) {}

    default void onVehicleExit(VehicleExitEvent event) {}

    default void onVehicleMove(VehicleMoveEvent event) {}

    default void onDropItem(PlayerDropItemEvent event) {}

    default void onLevelChange(PlayerLevelChangeEvent event) {}

    default void onCommand(PlayerCommandPreprocessEvent event) {}

    default void onEnterBed(PlayerBedEnterEvent event) {}

    default void onBlockDrop(BlockDropItemEvent event) {}

    default void onShootBow(EntityShootBowEvent event) {}

    default void onSculkSensorActive(BlockReceiveGameEvent event) {}

    default void onChat(AsyncPlayerChatEvent event) {}

    default void onItemDespawn(ItemDespawnEvent event) {}

    default void onEntityBurned(EntityCombustEvent event) {}

    default void onEditBook(PlayerEditBookEvent event) {}

    default void onEntityRegainHealth(EntityRegainHealthEvent event) {}

    default void onSwapHand(PlayerSwapHandItemsEvent event) {}

    default void onBlockGrow(BlockGrowEvent event) {}

    default void onEnchantItem(EnchantItemEvent event) {}

}
