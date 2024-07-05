package ru.armagidon.commandwands.wand.impl;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.configurate.reference.WatchServiceListener;
import ru.armagidon.commandwands.wand.Wand;
import ru.armagidon.commandwands.wand.WandRegistry;

public class WandRegistryImpl implements WandRegistry {

  private final Map<String, Wand> loadedWands;
  private final File wandsDir;
  private final WandConfigLoader wandConfigLoader;
  private final Logger logger;
  private final ItemWandProcessor processor;


  @Inject
  public WandRegistryImpl(@Named("wands") File wandsDir, WatchServiceListener listener,
      Logger logger, ItemWandProcessor processor) {
    this.wandsDir = wandsDir;
    this.wandConfigLoader = new WandConfigLoader(listener);
    this.processor = processor;
    this.loadedWands = new ConcurrentHashMap<>();
    this.logger = logger;
  }

  @Override
  public boolean wandExists(String id) {
    File file = new File(wandsDir, id);
    return file.exists();
  }

  @Override
  public boolean createWand(String id)
  {
    Validate.notBlank(id);
    File file = new File(wandsDir, id);
    try {
      wandConfigLoader.save(file.toPath(), WandImpl.class, new WandImpl());
    } catch (Exception e) {
      logger.error("Failed to save wand {}: {}", id, e.getMessage());
      return false;
    }
    return true;
  }

  @Override
  public @Nullable Wand getWandById(String id) {
    return loadedWands.computeIfAbsent(id, new WandLoader(wandsDir, wandConfigLoader));
  }

  @Override
  public boolean giveWand(String id, Player player, int amount) {
    ItemStackWand wand = (ItemStackWand) getWandById(id);
    if (wand == null)
      return false;
    ItemStack item =  wand.getTexture().asQuantity(amount);
    processor.burn(item, id);
    return true;
  }

  @Override
  public @Nullable Wand findWand(ItemStack item) {
    return getWandById(processor.extract(item));
  }
}
