package ru.armagidon.commandwands.config.serializers.itemstack;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

class SuspiciousStewMetaSerializer implements ItemMetaVariantSerializer<SuspiciousStewMeta> {

  @Override
  public void serializeMeta(SuspiciousStewMeta meta, ConfigurationNode node, Material context)
      throws SerializationException {
    if (meta.hasCustomEffects()) {
      node.node("custom-effects").setList(PotionEffect.class, meta.getCustomEffects());
    }
  }

  @Override
  public void deserializeMeta(SuspiciousStewMeta meta, ConfigurationNode node,
      Material context) throws SerializationException {
    if (node.hasChild("custom-effects")) {
      node.node("custom-effects").getList(PotionEffect.class, new ArrayList<>())
          .forEach(potionEffect ->
              meta.addCustomEffect(potionEffect, true));
    }
  }
}
