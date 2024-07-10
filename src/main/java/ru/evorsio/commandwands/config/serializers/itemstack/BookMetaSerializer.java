package ru.evorsio.commandwands.config.serializers.itemstack;

import java.util.ArrayList;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.meta.BookMeta;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

final class BookMetaSerializer implements ItemMetaVariantSerializer<BookMeta> {

  @Override
  public void serializeMeta(BookMeta meta, ConfigurationNode node, Material context)
      throws SerializationException {
    node.node("title").set(meta.author());
    node.node("content").setList(Component.class, meta.pages());
    node.node("author").set(meta.author());
    if (meta.hasGeneration()) {
      node.node("generation").set(meta.getGeneration());
    }

  }

  @Override
  public void deserializeMeta(BookMeta meta, ConfigurationNode node, Material context)
      throws SerializationException {
    BookMeta out = meta.toBuilder().author(node.node("author").get(Component.class))
        .pages(node.node("content").getList(Component.class, new ArrayList<>()))
        .title(node.node("title").get(Component.class)).build();

    if (node.hasChild("generation")) {
      out.setGeneration(node.get(BookMeta.Generation.class, BookMeta.Generation.ORIGINAL));
    }
  }
}
