
package org.mule.modules.odata.connectivity;

import javax.annotation.Generated;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessInterceptor;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.modules.odata.adapters.ODataConnectorConnectionIdentifierAdapter;
import org.mule.modules.odata.connection.ConnectionManager;
import org.mule.modules.odata.process.ODataConnectorManagedConnectionProcessInterceptor;
import org.mule.security.oauth.callback.ProcessCallback;
import org.mule.security.oauth.process.ProcessCallbackProcessInterceptor;
import org.mule.security.oauth.process.RetryProcessInterceptor;

@Generated(value = "Mule DevKit Version 3.5.3", date = "2015-12-18T03:15:35-03:00", comments = "Build UNNAMED.2049.ec39f2b")
public class ODataConnectorManagedConnectionProcessTemplate<P >implements ProcessTemplate<P, ODataConnectorConnectionIdentifierAdapter>
{

    private final ProcessInterceptor<P, ODataConnectorConnectionIdentifierAdapter> processInterceptor;

    public ODataConnectorManagedConnectionProcessTemplate(ConnectionManager<ODataConnectorConnectionKey, ODataConnectorConnectionIdentifierAdapter> connectionManager, MuleContext muleContext) {
        ProcessInterceptor<P, ODataConnectorConnectionIdentifierAdapter> processCallbackProcessInterceptor = new ProcessCallbackProcessInterceptor<P, ODataConnectorConnectionIdentifierAdapter>();
        ProcessInterceptor<P, ODataConnectorConnectionIdentifierAdapter> managedConnectionProcessInterceptor = new ODataConnectorManagedConnectionProcessInterceptor<P>(processCallbackProcessInterceptor, connectionManager, muleContext);
        ProcessInterceptor<P, ODataConnectorConnectionIdentifierAdapter> retryProcessInterceptor = new RetryProcessInterceptor<P, ODataConnectorConnectionIdentifierAdapter>(managedConnectionProcessInterceptor, muleContext, connectionManager.getRetryPolicyTemplate());
        processInterceptor = retryProcessInterceptor;
    }

    public P execute(ProcessCallback<P, ODataConnectorConnectionIdentifierAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
        throws Exception
    {
        return processInterceptor.execute(processCallback, null, messageProcessor, event);
    }

    public P execute(ProcessCallback<P, ODataConnectorConnectionIdentifierAdapter> processCallback, Filter filter, MuleMessage message)
        throws Exception
    {
        return processInterceptor.execute(processCallback, null, filter, message);
    }

}
