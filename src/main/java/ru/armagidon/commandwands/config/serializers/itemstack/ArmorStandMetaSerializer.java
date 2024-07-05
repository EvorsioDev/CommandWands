package ru.armagidon.commandwands.config.serializers.itemstack;

import com.destroystokyo.paper.inventory.meta.ArmorStandMeta;
import org.bukkit.Material;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class ArmorStandMetaSerializer implements ItemMetaVariantSerializer<ArmorStandMeta>  {

  @Override
  public void deserializeMeta(ArmorStandMeta holder, ConfigurationNode node, Material context)
      throws SerializationException {
    
    holder.setNoBasePlate(node.node("no-baseplate").getBoolean());
    holder.setInvisible(node.node("invisible").getBoolean());
    holder.setMarker(node.node("marker").getBoolean());
    holder.setShowArms(node.node("show-arms").getBoolean());
    holder.setSmall(node.node("small").getBoolean());
  }

  @Override
  public void serializeMeta(ArmorStandMeta holder, ConfigurationNode node, Material context)
      throws SerializationException {
    node.node("no-baseplate").set(holder.hasNoBasePlate());
    node.node("invisible").set(holder.isInvisible());
    node.node("marker").set(holder.isMarker());
    node.node("show-arms").set(holder.shouldShowArms());
    node.node("small").set(holder.isSmall());
  }
}
