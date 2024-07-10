package ru.evorsio.commandwands.wand.impl;

import java.lang.reflect.Type;
import javax.naming.spi.ObjectFactory;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;
import ru.evorsio.commandwands.wand.Wand;

public class WandSerializer implements TypeSerializer<Wand> {

  @Override
  public Wand deserialize(Type type, ConfigurationNode node) throws SerializationException {
    return (Wand) ObjectMapper.factory().asTypeSerializer().deserialize(SerializableWand.class, node);
  }

  @Override
  public void serialize(Type type, @Nullable Wand obj, ConfigurationNode node)
      throws SerializationException {
    ObjectMapper.factory().asTypeSerializer().serialize(SerializableWand.class, obj, node);
  }
}
