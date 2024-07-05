package ru.armagidon.commandwands.wand.impl;

import java.lang.reflect.Type;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;
import ru.armagidon.commandwands.wand.Wand;

public class WandSerializer implements TypeSerializer<Wand> {

  @Override
  public Wand deserialize(Type type, ConfigurationNode node) throws SerializationException {
    return null;
  }

  @Override
  public void serialize(Type type, @Nullable Wand obj, ConfigurationNode node)
      throws SerializationException {

  }
}
