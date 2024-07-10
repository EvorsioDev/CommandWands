package ru.evorsio.commandwands.config.serializers;

import java.lang.reflect.Type;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

public class PotionEffectSerializer implements TypeSerializer<PotionEffect> {


  @Override
  public PotionEffect deserialize(Type type, ConfigurationNode node) throws SerializationException {
    if (!node.isMap()) {
      throw new SerializationException(node, type, "This is not a Map");
    }

    return new PotionEffect(
        PotionEffectType.getByName(node.node("type").getString("")),
        node.node("duration").getInt(0),
        node.node("amplifier").getInt(0),
        node.node("ambient").getBoolean(false),
        node.node("particles").getBoolean(false),
        node.node("icon").getBoolean(false));
  }

  @Override
  public void serialize(Type type, @Nullable PotionEffect obj, ConfigurationNode node)
      throws SerializationException {
    if (obj == null) {
      throw new SerializationException("Serializing null key");
    }

    node.node("type").set(obj.getType().getName());
    node.node("duration").set(obj.getDuration());
    node.node("amplifier").set(obj.getAmplifier());
    node.node("ambient").set(obj.isAmbient());
    node.node("particles").set(obj.hasParticles());
    node.node("icon").set(obj.hasIcon());
  }
}
