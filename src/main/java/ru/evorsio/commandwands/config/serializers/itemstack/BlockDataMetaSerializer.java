package ru.evorsio.commandwands.config.serializers.itemstack;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

final class BlockDataMetaSerializer implements ItemMetaVariantSerializer<BlockDataMeta> {

  @Override
  public void serializeMeta(BlockDataMeta meta, ConfigurationNode node, Material context)
      throws SerializationException {
    if (meta.hasBlockData()) {
      node.node("blockdata").set(meta.getBlockData(context));
    }
  }

  @Override
  public void deserializeMeta(BlockDataMeta meta, ConfigurationNode node, Material context)
      throws SerializationException {
    if (!node.hasChild("blockdata")) {
      return;
    }
    var blockdata = node.node("blockdata").get(BlockData.class);
    if (blockdata != null) {
      meta.setBlockData(blockdata);
    }
  }
}
