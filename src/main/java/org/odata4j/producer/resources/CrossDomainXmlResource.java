/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.producer.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("crossdomain.xml")
public class CrossDomainXmlResource {

  @GET
  @Produces("text/xml")
  public String getCrossDomainXml() {
    String content = "<?xml version=\"1.0\"?>" +
        "<!DOCTYPE cross-domain-policy SYSTEM \"http://www.adobe.com/xml/dtds/cross-domain-policy.dtd\">" +
        "<cross-domain-policy>" +
        "  <allow-access-from domain=\"*\"/>" +
        "</cross-domain-policy>";
    return content;
  }

}
