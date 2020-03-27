package ca.bc.gov.open.cpf.plugin.impl.log;

import java.io.Serializable;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.ErrorHandler;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;

public class WrappedAppender implements Appender {

  private final Appender appender;

  public WrappedAppender(final Appender appender) {
    this.appender = appender;
  }

  @Override
  public void append(final LogEvent event) {
    this.getAppender().append(event);
  }

  protected Appender getAppender() {
    return this.appender;
  }

  @Override
  public ErrorHandler getHandler() {
    return this.getAppender().getHandler();
  }

  @Override
  public Layout<? extends Serializable> getLayout() {
    return this.getAppender().getLayout();
  }

  @Override
  public String getName() {
    return this.getAppender().getName();
  }

  @Override
  public State getState() {
    return this.getAppender().getState();
  }

  @Override
  public boolean ignoreExceptions() {
    return this.getAppender().ignoreExceptions();
  }

  @Override
  public void initialize() {
    this.getAppender().initialize();
  }

  @Override
  public boolean isStarted() {
    return this.getAppender().isStarted();
  }

  @Override
  public boolean isStopped() {
    return this.getAppender().isStopped();
  }

  @Override
  public void setHandler(final ErrorHandler handler) {
    this.getAppender().setHandler(handler);
  }

  @Override
  public void start() {
    this.getAppender().start();
  }

  @Override
  public void stop() {
    this.getAppender().stop();
  }
}
