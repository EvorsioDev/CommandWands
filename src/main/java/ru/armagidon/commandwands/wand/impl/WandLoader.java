package ru.armagidon.commandwands.wand.impl;

import java.io.File;
import java.util.function.Function;
import ru.armagidon.commandwands.wand.Wand;

class WandLoader implements Function<String, Wand> {

  private final File wandsFolder;
  private final WandConfigLoader loader;

  public WandLoader(File wandsFolder, WandConfigLoader loader) {
    this.wandsFolder = wandsFolder;
    this.loader = loader;
  }

  @Override
  public Wand apply(String id) {
    File f = new File(wandsFolder, String.join(".", id, "yml"));
    if (!f.exists())
      return null;
    return loader.load(f, ItemStackWand.class, WandImpl.class);
  }
}
