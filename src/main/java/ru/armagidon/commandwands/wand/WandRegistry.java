package ru.armagidon.commandwands.wand;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface WandRegistry {

  boolean wandExists(String id);

  boolean createWand(String id);

  @Nullable Wand getWandById(String id);

  boolean giveWand(String id, Player player, int amount);

  @Nullable Wand findWand(ItemStack item);

}
