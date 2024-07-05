package ru.armagidon.commandwands.config.serializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Color;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

public class ColorSerializer implements TypeSerializer<Color> {

  @Override
  public Color deserialize(Type type, ConfigurationNode node) throws SerializationException {
    if (node.empty()) {
      throw new SerializationException("Deserializing empty node");
    }
    List<Integer> rgb = node.getList(Integer.class, new ArrayList<>());
    if (rgb.size() != 3) {
      throw new SerializationException("Invalid RGB color");
    }
    return Color.fromRGB(rgb.get(0), rgb.get(1), rgb.get(2));
  }

  @Override
  public void serialize(Type type, @Nullable Color obj, ConfigurationNode node)
      throws SerializationException {
    if (obj == null) {
      throw new SerializationException("Serializing null item");
    }

    node.setList(Integer.class, Arrays.asList(obj.getRed(), obj.getGreen(), obj.getBlue()));
  }
}
