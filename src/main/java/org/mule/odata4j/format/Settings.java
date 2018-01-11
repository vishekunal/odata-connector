/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.format;

import org.mule.odata4j.core.OEntityKey;
import org.mule.odata4j.core.ODataVersion;
import org.mule.odata4j.edm.EdmDataServices;
import org.mule.odata4j.edm.EdmType;
import org.mule.odata4j.internal.FeedCustomizationMapping;

public class Settings {

  public final ODataVersion version;
  public final EdmDataServices metadata;
  public final String entitySetName;
  public final OEntityKey entityKey;
  public final FeedCustomizationMapping fcMapping;
  public final boolean isResponse;
  public final EdmType parseType;

  public Settings(ODataVersion version, EdmDataServices metadata,
      String entitySetName, OEntityKey entityKey, FeedCustomizationMapping fcMapping) {
    this(version, metadata, entitySetName, entityKey, fcMapping, true, null);
  }

  public Settings(ODataVersion version, EdmDataServices metadata,
      String entitySetName, OEntityKey entityKey, FeedCustomizationMapping fcMapping,
      boolean isResponse) {
    this(version, metadata, entitySetName, entityKey, fcMapping, isResponse, null);
  }

  public Settings(ODataVersion version, EdmDataServices metadata,
      String entitySetName, OEntityKey entityKey, FeedCustomizationMapping fcMapping,
      boolean isResponse, EdmType parseType) {
    this.version = version;
    this.metadata = metadata;
    this.entitySetName = entitySetName;
    this.entityKey = entityKey;
    this.fcMapping = fcMapping;
    this.isResponse = isResponse;
    this.parseType = parseType;
  }

}
