package ru.evorsio.commandwands.config.serializers.itemstack;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.KnowledgeBookMeta;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

class KnowledgeBookMetaSerializer implements ItemMetaVariantSerializer<KnowledgeBookMeta> {

  @Override
  public void serializeMeta(KnowledgeBookMeta meta, ConfigurationNode node, Material context)
      throws SerializationException {
    if (!meta.hasRecipes()) {
      return;
    }
    node.node("recipes").setList(NamespacedKey.class, meta.getRecipes());
  }

  @Override
  public void deserializeMeta(KnowledgeBookMeta meta, ConfigurationNode node,
      Material context) throws SerializationException {
    if (!node.hasChild("recipes")) {
      return;
    }
    meta.setRecipes(node.node("recipes").getList(NamespacedKey.class, new ArrayList<>()));
  }
}
