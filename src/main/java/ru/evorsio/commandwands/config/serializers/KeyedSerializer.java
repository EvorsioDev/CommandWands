package ru.evorsio.commandwands.config.serializers;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KeyedSerializer<T extends Keyed> implements TypeSerializer<T> {

  @NonNull Function<NamespacedKey, T> finder;

  @Override
  public T deserialize(Type type, ConfigurationNode node) throws SerializationException {
    if (node.empty()) {
      throw new SerializationException("Configuration node is empty");
    }

    return Optional.ofNullable(node.get(NamespacedKey.class))
        .map(finder)
        .orElseThrow(() -> new SerializationException("Invalid key"));
  }

  @Override
  public void serialize(Type type, @Nullable T obj, ConfigurationNode node)
      throws SerializationException {
    if (obj == null) {
      node.raw(null);
      return;
    }
    node.set(obj.getKey());
  }

  public static <E extends Enum<E> & Keyed> KeyedSerializer<E> keyedEnumSerializer(Class<E> clazz) {
    return new KeyedSerializer<>(key -> {
      for (E enumConstant : clazz.getEnumConstants()) {
        if (enumConstant.getKey().equals(key))
          return enumConstant;
      }
      return null;
    });
  }
}
