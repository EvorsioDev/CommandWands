package ru.armagidon.commandwands.config.serializers;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import io.leangen.geantyref.TypeToken;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.BasicConfigurationNode;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

public class MultimapSerializer implements TypeSerializer<Multimap<?, ?>> {

  public static final TypeToken<Multimap<?, ?>> TYPE = new TypeToken<>() {};

  @Override
  public Multimap<?, ?> deserialize(Type type, ConfigurationNode node)
      throws SerializationException {
    Multimap<Object, Object> ret = LinkedHashMultimap.create();
    if (node.isMap()) {
      if (!(type instanceof ParameterizedType param)) {
        throw new SerializationException(type, "Raw types are not supported for collections");
      }

      if (param.getActualTypeArguments().length != 2) {
        throw new SerializationException(type, "Map expected two type arguments!");
      }

      Type keyType = param.getActualTypeArguments()[0];
      Type valueType = param.getActualTypeArguments()[1];
      TypeSerializer<?> keySerial = node.options().serializers().get(keyType);
      TypeSerializer<?> valueSerial = node.options().serializers().get(valueType);

      if (keySerial == null) {
        throw new SerializationException(type,
            "No type serializer available for key type " + keyType);
      }

      if (valueSerial == null) {
        throw new SerializationException(type,
            "No type serializer available for value type " + valueType);
      }

      var keyToCollection = node.childrenMap().entrySet();
      BasicConfigurationNode keyHolder = BasicConfigurationNode.root(node.options());

      for (Entry<Object, ? extends ConfigurationNode> mapEntry : keyToCollection) {
        ConfigurationNode childNode = mapEntry.getValue();
        if (!mapEntry.getValue().isList()) {
          continue;
        }

        Object key = keySerial.deserialize(keyType, keyHolder.set(mapEntry.getKey()));

        var values = childNode.childrenList();
        for (ConfigurationNode valueNode : values) {
          Object value = valueSerial.deserialize(valueType, valueNode);
          ret.put(key, value);
        }
      }

    }

    return ret;
  }

  @Override
  public void serialize(Type type, @Nullable Multimap<?, ?> obj, ConfigurationNode node)
      throws SerializationException {
    if (!(type instanceof ParameterizedType param)) {
      throw new SerializationException(type, "Raw types are not supported for collections");
    } else {
      if (param.getActualTypeArguments().length != 2) {
        throw new SerializationException(type, "Map expected two type arguments!");
      } else {
        Type keyType = param.getActualTypeArguments()[0];
        Type valueType = param.getActualTypeArguments()[1];
        TypeSerializer<Object> keySerial = (TypeSerializer<Object>) node.options().serializers()
            .get(keyType);
        TypeSerializer<Object> valueSerial = (TypeSerializer<Object>) node.options().serializers()
            .get(valueType);
        if (keySerial == null) {
          throw new SerializationException(type,
              "No type serializer available for key type " + keyType);
        } else if (valueSerial == null) {
          throw new SerializationException(type,
              "No type serializer available for value type " + valueType);
        } else {
          if (obj != null && !obj.isEmpty()) {
            Set<Object> unvisitedKeys;
            if (node.empty()) {
              node.raw(Collections.emptyMap());
              unvisitedKeys = Collections.emptySet();
            } else {
              unvisitedKeys = new HashSet<>(node.childrenMap().keySet());
            }

            BasicConfigurationNode keyHolder = BasicConfigurationNode.root(node.options());

            for (Entry<?, ? extends Collection<?>> entry : obj.asMap().entrySet()) {
              keySerial.serialize(keyType, entry.getKey(), keyHolder);
              Object keyObj = Objects.requireNonNull(keyHolder.raw(), "Key must not be null!");
              ConfigurationNode keyNode = node.node(keyObj);
              keyNode.raw(Collections.emptyList());
              for (Object value : entry.getValue()) {
                ConfigurationNode valueNode = keyNode.appendListNode();
                try {
                  valueSerial.serialize(valueType, value, valueNode);
                } catch (SerializationException var19) {
                  Objects.requireNonNull(keyNode);
                  var19.initPath(keyNode::path);
                } finally {
                  unvisitedKeys.remove(keyObj);
                }

              }
            }

            for (Object unvisitedKey : unvisitedKeys) {
              node.removeChild(unvisitedKey);
            }
          } else {
            node.set(Collections.emptyMap());
          }
        }
      }
    }
  }

  @Override
  public @Nullable Multimap<?, ?> emptyValue(Type specificType, ConfigurationOptions options) {
    return LinkedHashMultimap.create();
  }
}
