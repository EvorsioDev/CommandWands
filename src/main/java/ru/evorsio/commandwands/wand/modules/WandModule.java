package ru.evorsio.commandwands.wand.modules;

import com.google.inject.AbstractModule;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class WandModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ScheduledExecutorService.class).toInstance(Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()));
  }
}
