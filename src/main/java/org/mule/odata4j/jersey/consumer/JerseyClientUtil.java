/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.jersey.consumer;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Set;

import javax.ws.rs.ext.RuntimeDelegate;

import org.mule.modules.odata.odata4j.extensions.BatchMultipartWriter;
import org.mule.odata4j.consumer.ODataClientRequest;
import org.mule.odata4j.internal.PlatformUtil;
import org.mule.odata4j.jersey.consumer.behaviors.JerseyClientBehavior;
import org.mule.odata4j.consumer.behaviors.OClientBehavior;
import org.mule.odata4j.core.ODataVersion;
import org.mule.odata4j.core.Throwables;
import org.mule.odata4j.format.Entry;
import org.mule.odata4j.format.FormatType;
import org.mule.odata4j.format.FormatWriter;
import org.mule.odata4j.format.FormatWriterFactory;
import org.mule.odata4j.format.SingleLink;
import org.mule.odata4j.jersey.internal.StringProvider2;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.impl.provider.header.MediaTypeProvider;
import com.sun.jersey.core.spi.factory.AbstractRuntimeDelegate;
import com.sun.jersey.spi.HeaderDelegateProvider;

public class JerseyClientUtil {

  static {
    if (PlatformUtil.runningOnAndroid())
      androidJerseyClientHack();
  }

  @SuppressWarnings("unchecked")
  private static void androidJerseyClientHack() {
    try {
      RuntimeDelegate rd = RuntimeDelegate.getInstance();
      Field f = AbstractRuntimeDelegate.class.getDeclaredField("hps");
      f.setAccessible(true);
      Set<HeaderDelegateProvider<?>> hps = (Set<HeaderDelegateProvider<?>>) f.get(rd);
      hps.clear();
      hps.add(new MediaTypeProvider());
    } catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }

  public static Client newClient(JerseyClientFactory clientFactory, OClientBehavior[] behaviors) {
    DefaultClientConfig cc = new DefaultClientConfig();
    
    cc.getSingletons().add(new StringProvider2());
    cc.getSingletons().add(new BatchMultipartWriter());
    
    if (behaviors != null) {
      for (OClientBehavior behavior : behaviors)
      {
        if (behavior instanceof JerseyClientBehavior) {
          ((JerseyClientBehavior) behavior).modify(cc);
        }
      }
    }
    Client client = clientFactory.createClient(cc);
    if (behaviors != null)
    {
      for (OClientBehavior behavior : behaviors)
      {
        if (behavior instanceof JerseyClientBehavior) {
          ((JerseyClientBehavior) behavior).modifyClientFilters(client);
        }
      }
    }
    return client;
  }

  public static WebResource resource(Client client, String url, OClientBehavior[] behaviors) {
    WebResource resource = client.resource(url);
    if (behaviors != null)
    {
      for (OClientBehavior behavior : behaviors)
      {
        if (behavior instanceof JerseyClientBehavior) {
          ((JerseyClientBehavior) behavior).modifyWebResourceFilters(resource);
        }
      }
    }
    
    return resource;
  }
  
  @SuppressWarnings("unchecked")
  public static FormatWriter<Object> newFormatWriter(ODataClientRequest request, FormatType format, ODataVersion version) {
	  Class<?> payloadClass;
      if (request.getPayload() instanceof Entry)
        payloadClass = Entry.class;
      else if (request.getPayload() instanceof SingleLink)
        payloadClass = SingleLink.class;
      else
        throw new UnsupportedOperationException("Unsupported payload: " + request.getPayload());

      return (FormatWriter<Object>) (Object) FormatWriterFactory.getFormatWriter(payloadClass, null, format.toString(), null, version);
  }
  
  public static String toString(ODataClientRequest request, FormatWriter<Object> format) {
	  StringWriter sw = new StringWriter();
      format.write(null, sw, request.getPayload());
      
      return sw.toString();
  }

}
