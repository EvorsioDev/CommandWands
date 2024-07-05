package ru.armagidon.commandwands.config.serializers.itemstack;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

class LeatherArmorMetaSerializer implements ItemMetaVariantSerializer<LeatherArmorMeta> {

  @Override
  public void serializeMeta(LeatherArmorMeta meta, ConfigurationNode node, Material context)
      throws SerializationException {
    node.node("color").set(Color.class, meta.getColor());
  }

  @Override
  public void deserializeMeta(LeatherArmorMeta meta, ConfigurationNode node,
      Material context) throws SerializationException {
    meta.setColor(node.node("color").get(Color.class));
  }
}
