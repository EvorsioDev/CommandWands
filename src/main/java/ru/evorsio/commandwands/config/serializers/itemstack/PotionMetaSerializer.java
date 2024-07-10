package ru.evorsio.commandwands.config.serializers.itemstack;

import java.util.ArrayList;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

class PotionMetaSerializer implements ItemMetaVariantSerializer<PotionMeta> {

  @Override
  public void serializeMeta(PotionMeta meta, ConfigurationNode node, Material context)
      throws SerializationException {
    node.node("potion-data").set(meta.getBasePotionData());
    if (meta.hasColor()) {
      node.node("color").set(Color.class, meta.getColor());
    }
    if (meta.hasCustomEffects()) {
      node.node("custom-effects").setList(PotionEffect.class, meta.getCustomEffects());
    }
  }

  @Override
  public void deserializeMeta(PotionMeta meta, ConfigurationNode node, Material context)
      throws SerializationException {
    node.node("potion-data").get(PotionData.class);
    if (node.hasChild("color")) {
      meta.setColor(node.node("color").get(Color.class));
    }
    if (node.hasChild("custom-effects")) {
      node.node("custom-effects").getList(PotionEffect.class, new ArrayList<>())
          .forEach(potionEffect ->
              meta.addCustomEffect(potionEffect, true));
    }
  }
}
