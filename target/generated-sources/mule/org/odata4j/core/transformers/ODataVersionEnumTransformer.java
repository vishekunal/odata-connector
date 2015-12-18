
package org.odata4j.core.transformers;

import javax.annotation.Generated;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;
import org.odata4j.core.ODataVersion;

@Generated(value = "Mule DevKit Version 3.5.3", date = "2015-12-18T03:15:35-03:00", comments = "Build UNNAMED.2049.ec39f2b")
public class ODataVersionEnumTransformer
    extends AbstractTransformer
    implements DiscoverableTransformer
{

    private int weighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING;

    public ODataVersionEnumTransformer() {
        registerSourceType(DataTypeFactory.create(String.class));
        setReturnClass(ODataVersion.class);
        setName("ODataVersionEnumTransformer");
    }

    protected Object doTransform(Object src, String encoding)
        throws TransformerException
    {
        ODataVersion result = null;
        result = Enum.valueOf(ODataVersion.class, ((String) src));
        return result;
    }

    public int getPriorityWeighting() {
        return weighting;
    }

    public void setPriorityWeighting(int weighting) {
        this.weighting = weighting;
    }

}
