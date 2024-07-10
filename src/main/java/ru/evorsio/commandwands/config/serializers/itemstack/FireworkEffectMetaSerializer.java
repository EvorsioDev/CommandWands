package ru.evorsio.commandwands.config.serializers.itemstack;

import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

final class FireworkEffectMetaSerializer implements ItemMetaVariantSerializer<FireworkEffectMeta>{

  @Override
  public void serializeMeta(FireworkEffectMeta meta, ConfigurationNode node, Material context)
      throws SerializationException {
    if (!meta.hasEffect()) {
      return;
    }
    node.node("firework-effect").set(FireworkEffect.class, meta.getEffect());
  }

  @Override
  public void deserializeMeta(FireworkEffectMeta meta, ConfigurationNode node,
      Material context) throws SerializationException {
    if (!node.hasChild("firework-effect")) {
      return;
    }
    meta.setEffect(node.node("firework-effect").get(FireworkEffect.class));
  }
}
