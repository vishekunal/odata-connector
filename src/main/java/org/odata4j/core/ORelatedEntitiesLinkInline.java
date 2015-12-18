/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.core;

import java.util.List;

/**
 * An <code>ORelatedEntitiesLinkInline</code> is an {@link ORelatedEntitiesLink} that also includes the referents inline.
 *
 * <p>The {@link OLinks} static factory class can be used to create <code>ORelatedEntitiesLink</code> instances.</p>
 *
 * @see OLinks
 */
public interface ORelatedEntitiesLinkInline extends ORelatedEntitiesLink {

  /**
   * Gets the inlined referents.
   */
  List<OEntity> getRelatedEntities();

}
