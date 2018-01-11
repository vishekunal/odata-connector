/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.resources;

import java.io.StringReader;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.mule.odata4j.core.ODataConstants;
import org.mule.odata4j.core.OEntity;
import org.mule.odata4j.core.OEntityKey;
import org.mule.odata4j.format.Settings;
import org.mule.odata4j.producer.exceptions.ODataException;
import org.mule.odata4j.core.ODataVersion;
import org.mule.odata4j.edm.EdmDataServices;
import org.mule.odata4j.format.Entry;
import org.mule.odata4j.format.FormatParser;
import org.mule.odata4j.format.FormatParserFactory;
import org.mule.odata4j.internal.InternalUtil;
import org.mule.odata4j.producer.exceptions.NotAcceptableException;

public abstract class BaseResource {

  protected OEntity getRequestEntity(HttpHeaders httpHeaders, UriInfo uriInfo, String payload, EdmDataServices metadata, String entitySetName, OEntityKey entityKey) throws ODataException {
    // TODO validation of MaxDataServiceVersion against DataServiceVersion
    // see spec [ms-odata] section 1.7

    ODataVersion version = InternalUtil.getDataServiceVersion(httpHeaders.getRequestHeaders().getFirst(ODataConstants.Headers.DATA_SERVICE_VERSION));
    return convertFromString(payload, httpHeaders.getMediaType(), version, metadata, entitySetName, entityKey);
  }

  private static OEntity convertFromString(String requestEntity, MediaType type, ODataVersion version, EdmDataServices metadata, String entitySetName, OEntityKey entityKey) throws NotAcceptableException {
    FormatParser<Entry> parser = FormatParserFactory.getParser(Entry.class, type,
        new Settings(version, metadata, entitySetName, entityKey, null, false));
    Entry entry = parser.parse(new StringReader(requestEntity));
    return entry.getEntity();
  }

}