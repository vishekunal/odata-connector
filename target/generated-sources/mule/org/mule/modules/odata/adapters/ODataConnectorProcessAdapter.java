
package org.mule.modules.odata.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.modules.odata.ODataConnector;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>ODataConnectorProcessAdapter</code> is a wrapper around {@link ODataConnector } that enables custom processing strategies.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.3", date = "2015-12-18T03:15:35-03:00", comments = "Build UNNAMED.2049.ec39f2b")
public class ODataConnectorProcessAdapter
    extends ODataConnectorLifecycleAdapter
    implements ProcessAdapter<ODataConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, ODataConnectorCapabilitiesAdapter> getProcessTemplate() {
        final ODataConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,ODataConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, ODataConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, ODataConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
