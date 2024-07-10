package ru.evorsio.commandwands.config.serializers.itemstack;

import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import ru.evorsio.commandwands.config.serializers.EnchantmentMapSerializer;

final class StoredEnchantsMetaSerializer implements
    ItemMetaVariantSerializer<EnchantmentStorageMeta> {

  @Override
  public void serializeMeta(EnchantmentStorageMeta meta, ConfigurationNode node, Material context)
      throws SerializationException {
    if (meta.hasStoredEnchants()) {
      node.node("stored-enchantments")
          .set(EnchantmentMapSerializer.TYPE, meta.getStoredEnchants());
    }
  }

  @Override
  public void deserializeMeta(EnchantmentStorageMeta meta, ConfigurationNode node,
      Material context) throws SerializationException {
    if (!node.hasChild("stored-enchantments")) {
      return;
    }
    Map<Enchantment, Integer> enchantmentMap = node.node("stored-enchantments")
        .get(EnchantmentMapSerializer.TYPE, Map.of());

    enchantmentMap.forEach((enchantment, level) -> {
      meta.addStoredEnchant(enchantment, level, true);
    });
  }
}
