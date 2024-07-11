package ru.evorsio.commandwands.wand.impl;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import lombok.SneakyThrows;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import ru.evorsio.commandwands.wand.Wand;
import ru.evorsio.commandwands.wand.WandRegistry;


public class WandRegistryImpl implements WandRegistry {

  private final Map<String, Wand> loadedWands;
  private final File wandsDir;
  private final Logger logger;
  private final ItemWandProcessor processor;
  private final WandConfigLoader loader;


  @Inject
  public WandRegistryImpl(@Named("wands") File wandsDir,
      ItemWandProcessor processor, WandConfigLoader loader, Logger logger) {
    this.wandsDir = wandsDir;
    this.processor = processor;
    this.loader = loader;
    this.loadedWands = new ConcurrentHashMap<>();
    this.logger = logger;
  }

  @Override
  public boolean wandExists(String id) {
    File file = new File(wandsDir, idToPath(id));
    return file.exists();
  }

  @Override
  public boolean createWand(String id) {
    Validate.notBlank(id);
    File file = new File(wandsDir, idToPath(id));
    try {
      loader.save(file.toPath(), SerializableWand.class, new SerializableWand(logger));
    } catch (Exception e) {
      logger.error("Failed to save wand {}: {}", id, e.getMessage());
      return false;
    }
    return true;
  }

  @Override
  public @Nullable Wand getWandById(String id) {
    return loadedWands.computeIfAbsent(id, new WandLoader());
  }

  @Override
  public boolean giveWand(String id, Player player, int amount) {
    ItemStackWand wand = (ItemStackWand) getWandById(id);
    if (wand == null) {
      return false;
    }
    ItemStack item = wand.getTexture().asQuantity(amount);
    processor.burn(item, id);
    player.getInventory().addItem(item);
    return true;
  }

  @Override
  public @Nullable Wand findWand(ItemStack item) {
    return getWandById(processor.extract(item));
  }

  class WandLoader implements Function<String, Wand> {

    @Override
    @SneakyThrows
    public Wand apply(String id) {
      File f = new File(wandsDir, idToPath(id));
      if (!f.exists())
        return null;
      return loader.load(f, ItemStackWand.class, SerializableWand.class);
    }
  }

  private String idToPath(String id) {
    return String.join(".", id, "yml");
  }
}
