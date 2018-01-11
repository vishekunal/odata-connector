/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.jpa.eclipselink;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.SingularAttribute;

import org.core4j.Enumerable;
import org.mule.odata4j.edm.EdmProperty;
import org.mule.odata4j.edm.EdmSimpleType;
import org.mule.odata4j.edm.EdmType;
import org.mule.odata4j.producer.jpa.JPAEdmGenerator;

public class EclipseLinkJPAEdmGenerator extends JPAEdmGenerator {

  public EclipseLinkJPAEdmGenerator(EntityManagerFactory emf, String namespace) {
    super(emf, namespace);
  }

  protected EdmProperty.Builder toEdmProperty(String modelNamespace, SingularAttribute<?, ?> sa) {
    EdmProperty.Builder p = super.toEdmProperty(modelNamespace, sa);

    Integer maxLength = null;
    Map<String, Object> eclipseLinkProps = EclipseLink.getPropertyInfo(sa, p.getType());
    if (Enumerable.<EdmType> create(EdmSimpleType.STRING, EdmSimpleType.BINARY).contains(p.getType())
        && eclipseLinkProps.containsKey("MaxLength"))
      maxLength = (Integer) eclipseLinkProps.get("MaxLength");

    return p.setMaxLength(maxLength);
  }

}
