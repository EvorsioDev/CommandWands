package ru.armagidon.commandwands.config.serializers.itemstack;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

final class CrossbowMetaSerializer implements ItemMetaVariantSerializer<CrossbowMeta> {

  @Override
  public void serializeMeta(CrossbowMeta meta, ConfigurationNode node, Material context)
      throws SerializationException {
    if (meta.hasChargedProjectiles()) {
      node.node("projectiles").setList(ItemStack.class, meta.getChargedProjectiles());
    }
  }

  @Override
  public void deserializeMeta(CrossbowMeta meta, ConfigurationNode node, Material context)
      throws SerializationException {
    if (!node.hasChild("projectiles")) {
      return;
    }
    meta.setChargedProjectiles(node.node("projectiles").getList(ItemStack.class));
  }
}
