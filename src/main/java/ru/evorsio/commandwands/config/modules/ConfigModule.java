package ru.evorsio.commandwands.config.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import java.io.File;
import java.io.IOException;
import org.spongepowered.configurate.reference.WatchServiceListener;
import ru.armagidon.advcolo.loader.FileConfigLoader;
import ru.evorsio.commandwands.config.holders.MessageConfigHolder;
import ru.evorsio.commandwands.config.interfaces.MessagesConfig;
import ru.evorsio.commandwands.config.loader.YamlConfigLoader;

public class ConfigModule extends AbstractModule {

  private final File dataFolder;

  public ConfigModule(File dataFolder) {
    this.dataFolder = dataFolder;
  }

  @Provides
  @Singleton
  private WatchServiceListener watchServiceProvider() throws IOException {
    return WatchServiceListener.create();
  }

  @Provides
  @Singleton
  @Named("yaml")
  private FileConfigLoader yamlConfigLoader(WatchServiceListener listener) {
    return new YamlConfigLoader(listener);
  }

  @Provides
  @Singleton
  private MessagesConfig messagesConfig(@Named("yaml") FileConfigLoader loader) {
    return loader.load(new File(dataFolder, "messages.yml"), MessagesConfig.class,
        MessageConfigHolder.class);
  }

  @Provides
  @Singleton
  @Named("data")
  private File dataFolder() {
    dataFolder.mkdirs();
    return dataFolder;
  }

  @Provides
  @Singleton
  @Named("wands")
  private File wandsFolder() {
    File wands = new File(dataFolder, "wands");
    wands.mkdirs();
    return wands;
  }
}
