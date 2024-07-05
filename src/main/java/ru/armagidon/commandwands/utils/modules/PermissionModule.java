package ru.armagidon.commandwands.utils.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import ru.armagidon.commandwands.utils.PermissionMap;

public class PermissionModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(PermissionMap.class).in(Scopes.SINGLETON);
  }
}
