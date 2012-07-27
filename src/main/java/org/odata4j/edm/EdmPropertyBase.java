/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.edm;

import org.odata4j.core.ImmutableList;
import org.odata4j.core.Named;

/** Shared base class for {@link EdmProperty} and {@link EdmNavigationProperty} */
public abstract class EdmPropertyBase extends EdmItem implements Named {

  private final String name;

  protected EdmPropertyBase(EdmDocumentation documentation, ImmutableList<EdmAnnotation<?>> annotations, String name) {
    super(documentation, annotations);
    this.name = name;
  }

  public String getName() {
    return name;
  }

  /** Mutable builder for {@link EdmPropertyBase} objects. */
  public abstract static class Builder<T, TBuilder> extends EdmItem.Builder<T, TBuilder> implements Named {

    private String name;

    Builder(String name) {
      this.name = name;
    }

    @Override
    public String getName() {
      return name;
    }

    @SuppressWarnings("unchecked")
    public TBuilder setName(String name) {
      this.name = name;
      return (TBuilder) this;
    }

  }

}
