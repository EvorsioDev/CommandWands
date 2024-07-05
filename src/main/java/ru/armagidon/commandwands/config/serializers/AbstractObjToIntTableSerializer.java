package ru.armagidon.commandwands.config.serializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AbstractObjToIntTableSerializer<K, T extends Map<K, Integer>, E extends AbstractObjToIntTableSerializer.ObjMapToIntEntry<K>> implements
    TypeSerializer<T> {

  Function<Map<K, Integer>, T> factory;
  BiFunction<K, Integer, E> mapEntryFactory;
  Class<E> entryClass;

  @Override
  public T deserialize(Type type, ConfigurationNode node) throws SerializationException {
    if (node.empty()) {
      return factory.apply(Map.of());
    }
    if (!node.isList()) {
      throw new SerializationException("Not a list");
    }
    var blockEntries = node.getList(entryClass, new ArrayList<>());

    Map<K, Integer> blockTable = blockEntries.stream()
        .map(e -> Map.entry(e.getObject(), e.getIntValue()))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

    return factory.apply(blockTable);
  }

  @Override
  public void serialize(Type type, @Nullable T obj, ConfigurationNode node)
      throws SerializationException {
    if (obj == null) {
      node.raw(null);
      return;
    }

    List<E> blockTableEntries = obj.entrySet().stream()
        .map(entry -> mapEntryFactory.apply(entry.getKey(), entry.getValue()))
        .toList();

    node.setList(entryClass, blockTableEntries);
  }

  @Override
  public @Nullable T emptyValue(Type specificType, ConfigurationOptions options) {
    return factory.apply(new HashMap<>());
  }

  public interface ObjMapToIntEntry<K> {

    int getIntValue();

    K getObject();
  }
}
