
package org.mule.modules.odata.adapters;

import javax.annotation.Generated;
import org.mule.modules.odata.ODataConnector;
import org.mule.modules.odata.connection.Connection;


/**
 * A <code>ODataConnectorConnectionIdentifierAdapter</code> is a wrapper around {@link ODataConnector } that implements {@link org.mule.devkit.dynamic.api.helper.Connection} interface.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.3", date = "2015-12-18T03:15:35-03:00", comments = "Build UNNAMED.2049.ec39f2b")
public class ODataConnectorConnectionIdentifierAdapter
    extends ODataConnectorProcessAdapter
    implements Connection
{


    public String getConnectionIdentifier() {
        return super.getConnectionIdentifier();
    }

}
