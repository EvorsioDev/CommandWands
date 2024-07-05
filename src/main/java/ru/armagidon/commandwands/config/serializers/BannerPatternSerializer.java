package ru.armagidon.commandwands.config.serializers;

import java.lang.reflect.Type;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

public class BannerPatternSerializer implements TypeSerializer<Pattern> {

  @Override
  public Pattern deserialize(Type type, ConfigurationNode node) throws SerializationException {
    if (node.empty()) {
      return null;
    }
    if (!node.hasChild("pattern") || !node.hasChild("color")) {
      return null;
    }
    return new Pattern(node.node("color").get(DyeColor.class, DyeColor.BLACK),
        node.node("pattern").get(PatternType.class, PatternType.BASE));
  }

  @Override
  public void serialize(Type type, @Nullable Pattern obj, ConfigurationNode node)
      throws SerializationException {
    if (obj == null) {
      node.raw(null);
      return;
    }

    node.node("pattern").set(obj.getPattern());
    node.node("color").set(obj.getColor());
  }
}
