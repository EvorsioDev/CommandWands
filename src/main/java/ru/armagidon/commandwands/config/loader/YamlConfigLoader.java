package ru.armagidon.commandwands.config.loader;

import java.nio.file.Path;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.loader.HeaderMode;
import org.spongepowered.configurate.reference.WatchServiceListener;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import ru.armagidon.advcolo.loader.FileConfigLoader;
import ru.armagidon.commandwands.config.serializers.itemstack.ItemStackSerializer;

public class YamlConfigLoader extends FileConfigLoader {


  public YamlConfigLoader(WatchServiceListener watchServiceListener) {
    super(watchServiceListener);
  }

  @Override
  protected ConfigurationLoader<CommentedConfigurationNode> getLoader(Path path) {
    return YamlConfigurationLoader.builder()
        .nodeStyle(NodeStyle.BLOCK)
        .indent(4)
        .path(path)
        .headerMode(HeaderMode.PRESERVE)
        .defaultOptions(options -> options.serializers(builder -> builder.registerAll(
            ItemStackSerializer.getSerializers())))
        .build();
  }
}
