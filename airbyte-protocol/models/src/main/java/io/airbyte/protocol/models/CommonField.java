/*
 * Copyright (c) 2021 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.protocol.models;

import java.util.List;
import java.util.Objects;

public class CommonField<T> {

  private final String name;
  private final T type;

  private final List<CommonField<T>> properties;

  public CommonField(final String name, final T type) {
    this.name = name;
    this.type = type;
    this.properties = null;
  }

  public CommonField(final String name, final T type, final List<CommonField<T>> properties) {
    this.name = name;
    this.type = type;
    this.properties = properties;
  }

  public String getName() {
    return name;
  }

  public T getType() {
    return type;
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }

    final CommonField<T> field = (CommonField<T>) object;
    return name.equals(field.name) &&
        type.equals(field.type) && Objects.equals(properties, field.properties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type, properties);
  }

  public List<CommonField<T>> getProperties() {
    return properties;
  }

}
