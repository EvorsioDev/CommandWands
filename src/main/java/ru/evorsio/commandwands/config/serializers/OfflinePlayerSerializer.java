package ru.evorsio.commandwands.config.serializers;

import java.lang.reflect.Type;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

public class OfflinePlayerSerializer implements TypeSerializer<OfflinePlayer> {

  @Override
  public OfflinePlayer deserialize(Type type, ConfigurationNode node)
      throws SerializationException {
    UUID uuid = node.get(UUID.class);
    if (uuid == null)
      return null;
    return Bukkit.getOfflinePlayer(uuid);
  }

  @Override
  public void serialize(Type type, @Nullable OfflinePlayer obj, ConfigurationNode node)
      throws SerializationException {
    if (obj == null) {
      node.raw(null);
      return;
    }
    node.set(obj.getUniqueId());
  }
}
