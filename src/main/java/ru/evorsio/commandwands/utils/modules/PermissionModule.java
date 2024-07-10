package ru.evorsio.commandwands.utils.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import ru.evorsio.commandwands.utils.PermissionMap;

public class PermissionModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(PermissionMap.class).toInstance(new PermissionMap("commandwands"));
  }
}
