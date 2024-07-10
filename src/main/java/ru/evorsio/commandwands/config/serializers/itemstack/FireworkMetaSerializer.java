package ru.evorsio.commandwands.config.serializers.itemstack;

import java.util.ArrayList;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.meta.FireworkMeta;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

final class FireworkMetaSerializer implements ItemMetaVariantSerializer<FireworkMeta> {

  @Override
  public void serializeMeta(FireworkMeta meta, ConfigurationNode node, Material context)
      throws SerializationException {
    node.node("power").set(meta.getPower());
    if (meta.hasEffects()) {
      return;
    }
    node.node("effects").setList(FireworkEffect.class, meta.getEffects());
  }

  @Override
  public void deserializeMeta(FireworkMeta meta, ConfigurationNode node, Material context)
      throws SerializationException {
    meta.setPower(node.node("power").getInt());
    if (!node.hasChild("effects")) {
      return;
    }
    meta.addEffects(node.node("effects").getList(FireworkEffect.class, new ArrayList<>()));
  }
}
