package ru.evorsio.commandwands.wand;

import com.google.inject.ImplementedBy;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import ru.evorsio.commandwands.wand.impl.WandRegistryImpl;

@ImplementedBy(WandRegistryImpl.class)
public interface WandRegistry {

  boolean wandExists(String id);

  boolean createWand(String id);

  @Nullable Wand getWandById(String id);

  boolean giveWand(String id, Player player, int amount);

  @Nullable Wand findWand(ItemStack item);

}
