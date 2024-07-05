package ru.armagidon.commandwands.config.serializers;

import io.leangen.geantyref.TypeToken;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.enchantments.Enchantment;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

public final class EnchantmentMapSerializer extends
    AbstractObjToIntTableSerializer<Enchantment, Map<Enchantment, Integer>, EnchantmentMapSerializer.EnchantmentMapEntry> {

  public static final TypeToken<Map<Enchantment, Integer>> TYPE = new TypeToken<>() {};

  public EnchantmentMapSerializer() {
    super(HashMap::new, EnchantmentMapEntry::new, EnchantmentMapEntry.class);
  }

  @ConfigSerializable
  @AllArgsConstructor
  @NoArgsConstructor
  static final class EnchantmentMapEntry implements
      AbstractObjToIntTableSerializer.ObjMapToIntEntry<Enchantment> {

    Enchantment enchantment;
    int level;


    @Override
    public int getIntValue() {
      return level;
    }

    @Override
    public Enchantment getObject() {
      return enchantment;
    }
  }
}
