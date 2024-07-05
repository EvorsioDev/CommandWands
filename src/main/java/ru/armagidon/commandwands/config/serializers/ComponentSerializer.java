package ru.armagidon.commandwands.config.serializers;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

public class ComponentSerializer implements TypeSerializer<Component> {

  private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

  @Override
  public Component deserialize(Type type, ConfigurationNode node) throws SerializationException {
    String raw;
    if (node.isList()) {
      raw = Joiner.on('\n').join(node.getList(String.class, new ArrayList<>()));
    } else {
      raw = node.getString("");
    }
    return MINI_MESSAGE.deserialize(raw);
  }

  @Override
  public void serialize(Type type, @Nullable Component obj, ConfigurationNode node)
      throws SerializationException {
    if (obj == null) {
      node.raw(null);
      return;
    }
    String serialized = MINI_MESSAGE.serialize(obj);
    List<String> lines = Splitter.on('\n').splitToList(serialized);
    switch (lines.size()) {
      case 0 -> node.set("");
      case 1 -> node.set(lines.get(0));
      default -> node.setList(String.class, lines);
    }
  }

  @Override
  public @Nullable Component emptyValue(Type specificType, ConfigurationOptions options) {
    return Component.empty();
  }
}
