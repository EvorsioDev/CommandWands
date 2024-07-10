package ru.evorsio.commandwands.config.serializers;

import static io.leangen.geantyref.GenericTypeReflector.erase;

import io.leangen.geantyref.TypeToken;
import java.lang.reflect.Type;
import java.util.function.Predicate;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.serialize.ScalarSerializer;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.util.EnumLookup;

public class LowerCaseEnumSerializer extends ScalarSerializer<Enum<?>> {

  public LowerCaseEnumSerializer() {
    super(new TypeToken<>() {
    });
  }

  @Override
  public Enum<?> deserialize(final Type type, final Object obj) throws SerializationException {
    final String enumConstant = obj.toString().toUpperCase();
    final @Nullable Enum<?> ret = EnumLookup.lookupEnum(erase(type).asSubclass(Enum.class), enumConstant);
    if (ret == null) {
      throw new SerializationException(type, "Invalid enum constant provided, expected a value of enum, got " + enumConstant);
    }
    return ret;
  }

  @Override
  public Object serialize(final Enum<?> item, final Predicate<Class<?>> typeSupported) {
    return item.name().toLowerCase();
  }

}
