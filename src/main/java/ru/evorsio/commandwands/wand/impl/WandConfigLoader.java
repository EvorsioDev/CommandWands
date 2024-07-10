package ru.evorsio.commandwands.wand.impl;

import java.nio.file.Path;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.loader.HeaderMode;
import org.spongepowered.configurate.reference.WatchServiceListener;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import ru.armagidon.advcolo.loader.FileConfigLoader;
import ru.evorsio.commandwands.config.serializers.itemstack.ItemStackSerializer;
import ru.evorsio.commandwands.wand.Wand;

public class WandConfigLoader extends FileConfigLoader {

  protected WandConfigLoader(WatchServiceListener watchServiceListener) {
    super(watchServiceListener);
  }

  @Override
  protected ConfigurationLoader<CommentedConfigurationNode> getLoader(Path path) {
    return YamlConfigurationLoader.builder()
        .nodeStyle(NodeStyle.BLOCK)
        .indent(4)
        .path(path)
        .headerMode(HeaderMode.PRESERVE)
        .defaultOptions(options -> options.serializers(builder ->
            builder.registerAll(ItemStackSerializer.getSerializers())
                .register(Wand.class, new WandSerializer())))
        .build();
  }

  public <T> void save(Path path, Class<T> type, T instance) throws ConfigurateException {
    ConfigurationLoader<CommentedConfigurationNode> loader = getLoader(path);
    CommentedConfigurationNode node = loader.load();
    node.set(type, instance);
    loader.save(node);
  }
}
