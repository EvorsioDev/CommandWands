package ru.evorsio.commandwands.config.serializers.itemstack;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.meta.BannerMeta;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

final class BannerMetaSerializer implements ItemMetaVariantSerializer<BannerMeta> {

  @Override
  public void deserializeMeta(BannerMeta holder, ConfigurationNode node, Material context)
      throws SerializationException {
    holder.setPatterns(node.getList(Pattern.class, new ArrayList<>()));
  }

  @Override
  public void serializeMeta(BannerMeta holder, ConfigurationNode node, Material context)
      throws SerializationException {
    if (holder.numberOfPatterns() > 0) {
      node.node("patterns").setList(Pattern.class, holder.getPatterns());
    }
  }
}
