/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.producer.jpa.eclipselink;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.metamodel.SingularAttribute;

import org.core4j.CoreUtils;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.internal.jpa.metamodel.AttributeImpl;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.odata4j.edm.EdmSimpleType;
import org.odata4j.edm.EdmType;

public class EclipseLink {

  private EclipseLink() {}

  public static Map<String, Object> getPropertyInfo(SingularAttribute<?, ?> sa, EdmType type) {
    Map<String, Object> rt = new HashMap<String, Object>();
    AttributeImpl<?, ?> ai = (AttributeImpl<?, ?>) sa;
    DatabaseMapping dm = CoreUtils.getFieldValue(ai, "mapping", DatabaseMapping.class);
    DatabaseField df = dm.getField();
    if (df != null && EdmSimpleType.STRING.equals(type)) {
      rt.put("MaxLength", df.getLength());
    }
    return rt;
  }

}
