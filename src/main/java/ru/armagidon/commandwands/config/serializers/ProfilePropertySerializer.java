package ru.armagidon.commandwands.config.serializers;

import com.destroystokyo.paper.profile.ProfileProperty;
import java.lang.reflect.Type;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

public class ProfilePropertySerializer implements TypeSerializer<ProfileProperty> {

  @Override
  public ProfileProperty deserialize(Type type, ConfigurationNode node)
      throws SerializationException {
    if (node.empty()) {
      throw new SerializationException();
    }

    return new ProfileProperty(node.node("name").getString(""),
        node.node("value").getString(""),
        node.hasChild("signature") ? node.node("signature").getString() : null);
  }

  @Override
  public void serialize(Type type, @Nullable ProfileProperty obj, ConfigurationNode node)
      throws SerializationException {
    if (obj == null) {
      node.raw(null);
      return;
    }
    node.node("name").set(obj.getName());
    node.node("value").set(obj.getValue());
    if (obj.isSigned()) {
      node.node("signature").set(obj.getSignature());
    }
  }
}
