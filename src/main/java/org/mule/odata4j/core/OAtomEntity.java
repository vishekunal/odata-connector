/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.core;

import org.joda.time.LocalDateTime;

/**
 * Interface providing information for ATOM serialization.
 *
 * <p>Enables feed customization of ATOM entries for certain properties instead of using &lt;m:Properties&gt;
 */
public interface OAtomEntity extends OExtension<OEntity> {

  /**
   * @return Atom Entity title or null
   */
  String getAtomEntityTitle();

  /**
   * If null returned, there will be no &lt;summary&gt; element in the response
   * @return Atom Entity title or null
   */
  String getAtomEntitySummary();

  /**
   * @return Atom Entity author value or null
   */
  String getAtomEntityAuthor();

  /**
   * @return Atom Entity updated value or null to use current date
   */
  LocalDateTime getAtomEntityUpdated();

}
