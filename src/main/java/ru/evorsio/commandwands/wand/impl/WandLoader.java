package ru.evorsio.commandwands.wand.impl;

import java.io.File;
import java.util.function.Function;
import org.spongepowered.configurate.reference.WatchServiceListener;
import ru.evorsio.commandwands.wand.Wand;

class WandLoader implements Function<String, Wand> {

  private final File wandsFolder;
  private final WatchServiceListener listener;

  public WandLoader(File wandsFolder, WatchServiceListener listener) {
    this.wandsFolder = wandsFolder;
    this.listener = listener;
  }

  @Override
  public Wand apply(String id) {
    File f = new File(wandsFolder, String.join(".", id, "yml"));
    if (!f.exists())
      return null;
    WandConfigLoader loader = new WandConfigLoader(listener);
    return loader.load(f, ItemStackWand.class, SerializableWand.class);
  }
}
