package ru.armagidon.commandwands.config.serializers;

import java.lang.reflect.Type;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

public class PotionDataSerializer implements TypeSerializer<PotionData> {

  @Override
  public PotionData deserialize(Type type, ConfigurationNode node) throws SerializationException {
    return new PotionData(node.node("type").get(PotionType.class, PotionType.AWKWARD),
        node.node("extended").getBoolean(false),
        node.node("upgraded").getBoolean(false));
  }

  @Override
  public void serialize(Type type, @Nullable PotionData obj, ConfigurationNode node)
      throws SerializationException {
    if (obj == null) {
      node.raw(null);
      return;
    }

    node.node("type").set(obj.getType());
    node.node("extended").set(obj.isExtended());
    node.node("upgraded").set(obj.isUpgraded());
  }
}
