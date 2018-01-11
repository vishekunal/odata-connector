/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.format;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.mule.odata4j.producer.CollectionResponse;
import org.mule.odata4j.producer.ComplexObjectResponse;
import org.mule.odata4j.producer.EntitiesResponse;
import org.mule.odata4j.producer.PropertyResponse;
import org.mule.odata4j.producer.exceptions.NotImplementedException;
import org.mule.odata4j.core.ODataVersion;
import org.mule.odata4j.edm.EdmDataServices;
import org.mule.odata4j.format.json.JsonCollectionFormatWriter;
import org.mule.odata4j.format.json.JsonComplexObjectFormatWriter;
import org.mule.odata4j.format.json.JsonEntryFormatWriter;
import org.mule.odata4j.format.json.JsonFeedFormatWriter;
import org.mule.odata4j.format.json.JsonPropertyFormatWriter;
import org.mule.odata4j.format.json.JsonRequestEntryFormatWriter;
import org.mule.odata4j.format.json.JsonServiceDocumentFormatWriter;
import org.mule.odata4j.format.json.JsonSingleLinkFormatWriter;
import org.mule.odata4j.format.json.JsonSingleLinksFormatWriter;
import org.mule.odata4j.format.xml.AtomEntryFormatWriter;
import org.mule.odata4j.format.xml.AtomFeedFormatWriter;
import org.mule.odata4j.format.xml.AtomRequestEntryFormatWriter;
import org.mule.odata4j.format.xml.AtomServiceDocumentFormatWriter;
import org.mule.odata4j.format.xml.AtomSingleLinkFormatWriter;
import org.mule.odata4j.format.xml.AtomSingleLinksFormatWriter;
import org.mule.odata4j.format.xml.XmlPropertyFormatWriter;
import org.mule.odata4j.producer.EntityResponse;

public class FormatWriterFactory {

  private static interface FormatWriters {

    FormatWriter<EdmDataServices> getServiceDocumentFormatWriter(ODataVersion version);

    FormatWriter<EntitiesResponse> getFeedFormatWriter(ODataVersion version);

    FormatWriter<EntityResponse> getEntryFormatWriter(ODataVersion version);

    FormatWriter<PropertyResponse> getPropertyFormatWriter(ODataVersion version);

    FormatWriter<Entry> getRequestEntryFormatWriter(ODataVersion version);

    FormatWriter<SingleLink> getSingleLinkFormatWriter(ODataVersion version);

    FormatWriter<SingleLinks> getSingleLinksFormatWriter(ODataVersion version);

    FormatWriter<ComplexObjectResponse> getComplexObjectFormatWriter(ODataVersion version);

    FormatWriter<CollectionResponse<?>> getCollectionFormatWriter(ODataVersion version);
  }

  @SuppressWarnings("unchecked")
  public static <T> FormatWriter<T> getFormatWriter(Class<T> targetType, List<MediaType> acceptTypes, String format, String callback, ODataVersion version) {

    FormatType type = null;

    // if format is explicitly specified, use that
    if (format != null)
      type = FormatType.parse(format);

    // if header accepts json, use that
    if (type == null && acceptTypes != null) {
      for (MediaType acceptType : acceptTypes) {
        if (acceptType.equals(MediaType.APPLICATION_JSON_TYPE)) {
          type = FormatType.JSON;
          break;
        }
      }
    }

    // else default to atom
    if (type == null)
      type = FormatType.ATOM;

    FormatWriters formatWriters = type.equals(FormatType.JSON) ? new JsonWriters(callback) : new AtomWriters();

    if (targetType.equals(EdmDataServices.class))
      return (FormatWriter<T>) formatWriters.getServiceDocumentFormatWriter(version);

    if (targetType.equals(EntitiesResponse.class))
      return (FormatWriter<T>) formatWriters.getFeedFormatWriter(version);

    if (targetType.equals(EntityResponse.class))
      return (FormatWriter<T>) formatWriters.getEntryFormatWriter(version);

    if (targetType.equals(PropertyResponse.class))
      return (FormatWriter<T>) formatWriters.getPropertyFormatWriter(version);

    if (Entry.class.isAssignableFrom(targetType))
      return (FormatWriter<T>) formatWriters.getRequestEntryFormatWriter(version);

    if (SingleLink.class.isAssignableFrom(targetType))
      return (FormatWriter<T>) formatWriters.getSingleLinkFormatWriter(version);

    if (SingleLinks.class.isAssignableFrom(targetType))
      return (FormatWriter<T>) formatWriters.getSingleLinksFormatWriter(version);

    if (targetType.equals(ComplexObjectResponse.class)) {
      return (FormatWriter<T>) formatWriters.getComplexObjectFormatWriter(version);
    }

    if (targetType.equals(CollectionResponse.class)) {
      return (FormatWriter<T>) formatWriters.getCollectionFormatWriter(version);
    }

    throw new IllegalArgumentException("Unable to locate format writer for " + targetType.getName() + " and format " + type);

  }

  public static class JsonWriters implements FormatWriters {

    private final String callback;

    public JsonWriters(String callback) {
      this.callback = callback;
    }

    @Override
    public FormatWriter<EdmDataServices> getServiceDocumentFormatWriter(ODataVersion version) {
      return new JsonServiceDocumentFormatWriter(callback, version);
    }

    @Override
    public FormatWriter<EntitiesResponse> getFeedFormatWriter(ODataVersion version) {
      return new JsonFeedFormatWriter(callback, version);
    }

    @Override
    public FormatWriter<EntityResponse> getEntryFormatWriter(ODataVersion version) {
      return new JsonEntryFormatWriter(callback, version);
    }

    @Override
    public FormatWriter<PropertyResponse> getPropertyFormatWriter(ODataVersion version) {
      return new JsonPropertyFormatWriter(callback, version);
    }

    @Override
    public FormatWriter<Entry> getRequestEntryFormatWriter(ODataVersion version) {
      return new JsonRequestEntryFormatWriter(callback, version);
    }

    @Override
    public FormatWriter<SingleLink> getSingleLinkFormatWriter(ODataVersion version) {
      return new JsonSingleLinkFormatWriter(callback, version);
    }

    @Override
    public FormatWriter<SingleLinks> getSingleLinksFormatWriter(ODataVersion version) {
      return new JsonSingleLinksFormatWriter(callback, version);
    }

    @Override
    public FormatWriter<ComplexObjectResponse> getComplexObjectFormatWriter(ODataVersion version) {
      return new JsonComplexObjectFormatWriter(callback, version);
    }

    @Override
    public FormatWriter<CollectionResponse<?>> getCollectionFormatWriter(ODataVersion version) {
      return new JsonCollectionFormatWriter(callback, version);
    }
  }

  public static class AtomWriters implements FormatWriters {

    @Override
    public FormatWriter<EdmDataServices> getServiceDocumentFormatWriter(ODataVersion version) {
      return new AtomServiceDocumentFormatWriter();
    }

    @Override
    public FormatWriter<EntitiesResponse> getFeedFormatWriter(ODataVersion version) {
      return new AtomFeedFormatWriter();
    }

    @Override
    public FormatWriter<EntityResponse> getEntryFormatWriter(ODataVersion version) {
      return new AtomEntryFormatWriter();
    }

    @Override
    public FormatWriter<PropertyResponse> getPropertyFormatWriter(ODataVersion version) {
      return new XmlPropertyFormatWriter();
    }

    @Override
    public FormatWriter<Entry> getRequestEntryFormatWriter(ODataVersion version) {
      return new AtomRequestEntryFormatWriter();
    }

    @Override
    public FormatWriter<SingleLink> getSingleLinkFormatWriter(ODataVersion version) {
      return new AtomSingleLinkFormatWriter();
    }

    @Override
    public FormatWriter<SingleLinks> getSingleLinksFormatWriter(ODataVersion version) {
      return new AtomSingleLinksFormatWriter();
    }

    @Override
    public FormatWriter<ComplexObjectResponse> getComplexObjectFormatWriter(ODataVersion version) {
      throw new NotImplementedException();
    }

    @Override
    public FormatWriter<CollectionResponse<?>> getCollectionFormatWriter(ODataVersion version) {
      throw new NotImplementedException();
    }

  }

}
