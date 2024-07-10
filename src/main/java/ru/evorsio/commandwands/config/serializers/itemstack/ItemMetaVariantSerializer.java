package ru.evorsio.commandwands.config.serializers.itemstack;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public interface ItemMetaVariantSerializer<T extends ItemMeta>
{
  void deserializeMeta(T holder, ConfigurationNode node, Material context) throws SerializationException;

  void serializeMeta(T holder, ConfigurationNode node, Material context) throws SerializationException;
}
