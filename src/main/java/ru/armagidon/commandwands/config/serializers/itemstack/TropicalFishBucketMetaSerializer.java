package ru.armagidon.commandwands.config.serializers.itemstack;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

class TropicalFishBucketMetaSerializer implements
    ItemMetaVariantSerializer<TropicalFishBucketMeta> {

  @Override
  public void serializeMeta(TropicalFishBucketMeta meta, ConfigurationNode node, Material context)
      throws SerializationException {
    node.node("body-color").set(meta.getBodyColor());
    node.node("pattern-color").set(meta.getPatternColor());
    node.node("pattern").set(meta.getPattern());
  }

  @Override
  public void deserializeMeta(TropicalFishBucketMeta meta, ConfigurationNode node,
      Material context) throws SerializationException {
    meta.setBodyColor(node.node("body-color").get(DyeColor.class, DyeColor.BLACK));
    meta.setPatternColor(node.node("pattern-color").get(DyeColor.class, DyeColor.BLACK));
    meta.setPattern(
        node.node("pattern").get(TropicalFish.Pattern.class, TropicalFish.Pattern.BETTY));
  }
}
