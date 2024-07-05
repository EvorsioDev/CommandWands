package ru.armagidon.commandwands.utils.modules;

import com.google.inject.AbstractModule;
import org.slf4j.Logger;

public class LoggerModule extends AbstractModule {

  private final Logger logger;

  public LoggerModule(Logger logger) {
    this.logger = logger;
  }

  @Override
  protected void configure() {
    bind(Logger.class).toInstance(logger);
  }
}
