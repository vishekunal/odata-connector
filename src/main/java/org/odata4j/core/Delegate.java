/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.core;

/**
 * Base interface for delegates, also known as decorators.
 *
 * <p>Implementations are expected to implement all methods of type T, and forward to the delegate instance by default.
 */
public interface Delegate<T> {
  T getDelegate();
}
