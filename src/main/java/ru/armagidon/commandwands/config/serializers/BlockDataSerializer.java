package ru.armagidon.commandwands.config.serializers;

import java.lang.reflect.Type;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

public class BlockDataSerializer implements TypeSerializer<BlockData> {

  @Override
  public BlockData deserialize(Type type, ConfigurationNode node) throws SerializationException {
    if (node.empty()) {
      throw new SerializationException("Node is empty");
    }
    return Bukkit.createBlockData(node.getString("minecraft:air"));
  }

  @Override
  public void serialize(Type type, @Nullable BlockData obj, ConfigurationNode node)
      throws SerializationException {
    if (obj == null) {
      node.raw(null);
      return;
    }

    node.set(obj.getAsString());
  }

  @Override
  public BlockData emptyValue(Type specificType, ConfigurationOptions options) {
    return Bukkit.createBlockData(Material.AIR);
  }
}
