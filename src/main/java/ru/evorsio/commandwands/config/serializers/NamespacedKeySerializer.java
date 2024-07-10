package ru.evorsio.commandwands.config.serializers;

import java.lang.reflect.Type;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.NamespacedKey;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class NamespacedKeySerializer implements TypeSerializer<NamespacedKey> {

  @Override
  public NamespacedKey deserialize(Type type, ConfigurationNode node)
      throws SerializationException {
    if (node.empty()) {
      throw new SerializationException("Configuration node is empty");
    }

    return Optional.ofNullable(node.getString())
        .map(NamespacedKey::fromString)
        .orElseThrow(() -> new SerializationException("Invalid key"));
  }

  @Override
  public void serialize(Type type, @Nullable NamespacedKey obj, ConfigurationNode node)
      throws SerializationException {
    if (obj == null) {
      node.raw(null);
      return;
    }
    node.set(obj.toString());
  }
}
