/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.core4j.Enumerable;
import org.mule.odata4j.edm.EdmType;

/**
 * A static factory to create immutable {@link OCollection} instances.
 */
public class OCollections {

  /** Starts a new {@link OCollection} builder using a given edm type. */
  public static <T extends OObject> OCollection.Builder<T> newBuilder(EdmType type) {
    return new BuilderImpl<T>(type);
  }

  private static class BuilderImpl<T extends OObject> implements OCollection.Builder<T> {

    private final EdmType type;
    private final List<T> values = new LinkedList<T>();

    BuilderImpl(EdmType type) {
      this.type = type;
    }

    @Override
    public OCollection.Builder<T> add(T value) {
      values.add(value);
      return this;
    }

    @Override
    public OCollection<T> build() {
      return new OCollectionImpl<T>(type, values);
    }

  }

  private static class OCollectionImpl<T extends OObject> implements OCollection<T> {

    private final EdmType type;
    private final List<T> values;

    OCollectionImpl(EdmType type, List<T> values) {
      this.type = type;
      this.values = values;
    }

    @Override
    public EdmType getType() {
      return type;
    }

    @Override
    public Iterator<T> iterator() {
      return values.iterator();
    }

    @Override
    public int size() {
      return values.size();
    }

    @Override
    public String toString() {
      return String.format("OCollection[%s,%s]", type, Enumerable.create(values).join(","));
    }
  }

}
