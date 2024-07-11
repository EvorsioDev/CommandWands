package ru.evorsio.commandwands.wand.impl;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import java.nio.file.Path;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.loader.HeaderMode;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.objectmapping.guice.GuiceObjectMapperProvider;
import org.spongepowered.configurate.reference.WatchServiceListener;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import ru.armagidon.advcolo.loader.FileConfigLoader;
import ru.evorsio.commandwands.config.serializers.itemstack.ItemStackSerializer;

@Singleton
public class WandConfigLoader extends FileConfigLoader {

  private final TypeSerializerCollection serializers;

  @Inject
  public WandConfigLoader(WatchServiceListener listener, Injector injector) {
    super(listener);
    this.serializers = TypeSerializerCollection
        .defaults()
        .childBuilder()
        .registerAll(ItemStackSerializer.getSerializers())
        .registerAnnotatedObjects(ObjectMapper.factoryBuilder()
            .addDiscoverer(GuiceObjectMapperProvider.injectedObjectDiscoverer(injector)).build())
        .build();
  }

  @Override
  protected ConfigurationLoader<CommentedConfigurationNode> getLoader(Path path) {
    return YamlConfigurationLoader.builder()
        .nodeStyle(NodeStyle.BLOCK)
        .indent(4)
        .path(path)
        .headerMode(HeaderMode.PRESERVE)
        .defaultOptions(options -> options.serializers(serializers))
        .build();
  }

}
