/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.core;

import org.odata4j.edm.EdmType;

/**
 * OData instance object of the given {@link EdmType}.
 *
 * @see OEntity
 * @see OSimpleObject
 * @see OComplexObject
 * @see OCollection
 */
public interface OObject {

  /** Gets the edm type of this object */
  EdmType getType();

}
