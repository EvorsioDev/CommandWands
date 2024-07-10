package ru.evorsio.commandwands.config.serializers.itemstack;

import static ru.evorsio.commandwands.config.serializers.KeyedSerializer.keyedEnumSerializer;

import com.destroystokyo.paper.inventory.meta.ArmorStandMeta;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.common.collect.ImmutableMap;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.KnowledgeBookMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;
import ru.evorsio.commandwands.config.serializers.BannerPatternSerializer;
import ru.evorsio.commandwands.config.serializers.BlockDataSerializer;
import ru.evorsio.commandwands.config.serializers.ColorSerializer;
import ru.evorsio.commandwands.config.serializers.ComponentSerializer;
import ru.evorsio.commandwands.config.serializers.EnchantmentMapSerializer;
import ru.evorsio.commandwands.config.serializers.FireworkEffectSerializer;
import ru.evorsio.commandwands.config.serializers.KeyedSerializer;
import ru.evorsio.commandwands.config.serializers.MultimapSerializer;
import ru.evorsio.commandwands.config.serializers.NamespacedKeySerializer;
import ru.evorsio.commandwands.config.serializers.OfflinePlayerSerializer;
import ru.evorsio.commandwands.config.serializers.PotionDataSerializer;
import ru.evorsio.commandwands.config.serializers.PotionEffectSerializer;
import ru.evorsio.commandwands.config.serializers.ProfilePropertySerializer;

public class ItemStackSerializer implements TypeSerializer<ItemStack> {

  @Getter
  private static final TypeSerializerCollection serializers = TypeSerializerCollection.builder()
      .register(Material.class, keyedEnumSerializer(Material.class))
      .registerExact(EnchantmentMapSerializer.TYPE, new EnchantmentMapSerializer())
      .register(Component.class, new ComponentSerializer())
      .register(NamespacedKey.class, new NamespacedKeySerializer())
      .register(Color.class, new ColorSerializer())
      .register(PotionEffect.class, new PotionEffectSerializer())
      .register(ProfileProperty.class, new ProfilePropertySerializer())
      .register(PotionData.class, new PotionDataSerializer())
      .register(FireworkEffect.class, new FireworkEffectSerializer())
      .register(Enchantment.class, new KeyedSerializer<>(Enchantment::getByKey))
      .register(Attribute.class, keyedEnumSerializer(Attribute.class))
      .register(MultimapSerializer.TYPE, new MultimapSerializer())
      .register(ItemStack.class, new ItemStackSerializer())
      .register(BlockData.class, new BlockDataSerializer())
      .register(OfflinePlayer.class, new OfflinePlayerSerializer())
      .register(Pattern.class, new BannerPatternSerializer())
      .build();

  private static final Map<Class<? extends ItemMeta>, ItemMetaVariantSerializer<?>> SUPPORTED_METAS =
      ImmutableMap.<Class<? extends ItemMeta>, ItemMetaVariantSerializer<?>>builder()
          .put(ArmorStandMeta.class, new ArmorStandMetaSerializer())
          .put(BannerMeta.class, new BannerMetaSerializer())
          .put(BookMeta.class, new BookMetaSerializer())
          .put(CrossbowMeta.class, new CrossbowMetaSerializer())
          .put(FireworkEffectMeta.class, new FireworkEffectMetaSerializer())
          .put(FireworkMeta.class, new FireworkEffectMetaSerializer())
          .put(KnowledgeBookMeta.class, new KnowledgeBookMetaSerializer())
          .put(LeatherArmorMeta.class, new LeatherArmorMetaSerializer())
          .put(PotionMeta.class, new PotionMetaSerializer())
          .put(EnchantmentStorageMeta.class, new StoredEnchantsMetaSerializer())
          .put(SuspiciousStewMeta.class, new SuspiciousStewMetaSerializer())
          .put(TropicalFishBucketMeta.class, new TropicalFishBucketMetaSerializer())
          .put(BlockDataMeta.class, new BlockDataMetaSerializer())
          .build();

  @Override
  public ItemStack deserialize(Type type, ConfigurationNode node) throws SerializationException {
    if (!node.isMap()) {
      throw new SerializationException(node, type, "Malformed node");
    }

    Material material = node.node("material").get(Material.class, Material.AIR);

    int amount = node.node("amount").getInt(1);

    ItemStack item = new ItemStack(material).asQuantity(amount);

    Map<Enchantment, Integer> enchantments = node.node("enchantments")
        .get(EnchantmentMapSerializer.TYPE, new HashMap<>());

    item.addUnsafeEnchantments(enchantments);

    ItemMeta meta = deserializeMeta(item, node.node("meta"));

    if (meta != null) {
      item.setItemMeta(meta);
    }

    return item;
  }

  private ItemMeta deserializeMeta(ItemStack item, ConfigurationNode node)
      throws SerializationException {
    ItemMeta meta = Bukkit.getItemFactory().getItemMeta(item.getType());
    if (meta == null) {
      return null;
    }

    if (meta instanceof Damageable damageable) {
      int damage = node.node("damage").getInt(0);
      damageable.setDamage(damage);
    }

    Component displayName = node.node("display-name").get(Component.class);
    meta.displayName(displayName);

    List<Component> lore = node.node("lore").getList(Component.class, new ArrayList<>());
    meta.lore(lore);

    Integer customModelData = node.node("custom-model-data").get(Integer.class);
    meta.setCustomModelData(customModelData);

    ItemFlag[] flags = node.node("item-flags").getList(ItemFlag.class, new ArrayList<>())
        .toArray(ItemFlag[]::new);
    meta.addItemFlags(flags);

    for (Entry<Class<? extends ItemMeta>, ItemMetaVariantSerializer<?>> entry : SUPPORTED_METAS.entrySet()) {
      Class<? extends ItemMeta> clazz = entry.getKey();
      ItemMetaVariantSerializer<?> serializer = entry.getValue();
      if (clazz.isInstance(meta)) {
        ((ItemMetaVariantSerializer) serializer).deserializeMeta(meta, node, item.getType());
      }
    }

    return meta;
  }

  @Override
  public void serialize(Type type, @Nullable ItemStack obj, ConfigurationNode node)
      throws SerializationException {
    if (obj == null) {
      node.raw(null);
      return;
    }

    node.node("material").set(obj.getType());

    if (obj.getAmount() > 1) {
      node.node("amount").set(obj.getAmount());
    }

    if (!obj.getEnchantments().isEmpty())
      node.node("enchantments").set(EnchantmentMapSerializer.TYPE, obj.getEnchantments());

    serializeMeta(obj, node.node("meta"));
  }

  private void serializeMeta(ItemStack item, ConfigurationNode node) throws SerializationException {
    ItemMeta meta = item.getItemMeta();
    if (meta == null) {
      return;
    }

    if (meta instanceof Damageable damageable && (damageable.getDamage() > 0)) {
      node.node("damage").set(damageable.getDamage());
    }

    if (meta.hasDisplayName()) {
      node.node("display-name").set(Component.class, meta.displayName());
    }

    if (meta.hasLore()) {
      node.node("lore").setList(Component.class, meta.lore());
    }

    if (meta.hasCustomModelData()) {
      node.node("custom-model-data").set(meta.getCustomModelData());
    }

    if (!meta.getItemFlags().isEmpty())
      node.node("item-flags").setList(ItemFlag.class, new ArrayList<>(meta.getItemFlags()));

    for (Entry<Class<? extends ItemMeta>, ItemMetaVariantSerializer<?>> entry : SUPPORTED_METAS.entrySet()) {
      Class<? extends ItemMeta> clazz = entry.getKey();
      ItemMetaVariantSerializer<?> serializer = entry.getValue();
      if (clazz.isInstance(meta)) {
        ((ItemMetaVariantSerializer) serializer).serializeMeta(meta, node, item.getType());
      }
    }
  }

  @Override
  public @Nullable ItemStack emptyValue(Type specificType, ConfigurationOptions options) {
    return new ItemStack(Material.AIR);
  }
}
