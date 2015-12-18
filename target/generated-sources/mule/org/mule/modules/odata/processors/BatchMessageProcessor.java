
package org.mule.modules.odata.processors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.NestedProcessor;
import org.mule.api.devkit.NestedProcessorChain;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.odata.ODataConnector;
import org.mule.modules.odata.connectivity.ODataConnectorConnectionManager;
import org.mule.modules.odata.exception.NotAuthorizedException;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * BatchMessageProcessor invokes the {@link org.mule.modules.odata.ODataConnector#batch(org.mule.api.MuleMessage, java.util.List)} method in {@link ODataConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.3", date = "2015-12-18T03:15:35-03:00", comments = "Build UNNAMED.2049.ec39f2b")
public class BatchMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor
{

    protected Object message;
    protected MuleMessage _messageType;
    protected Object processors;
    protected List<NestedProcessor> _processorsType;

    public BatchMessageProcessor(String operationName) {
        super(operationName);
    }

    /**
     * Obtains the expression manager from the Mule context and initialises the connector. If a target object  has not been set already it will search the Mule registry for a default one.
     * 
     * @throws InitialisationException
     */
    public void initialise()
        throws InitialisationException
    {
        if (processors instanceof List) {
            for (MessageProcessor messageProcessor: ((List<MessageProcessor> ) processors)) {
                if (messageProcessor instanceof Initialisable) {
                    ((Initialisable) messageProcessor).initialise();
                }
            }
        }
    }

    @Override
    public void start()
        throws MuleException
    {
        super.start();
        if (processors instanceof List) {
            for (MessageProcessor messageProcessor: ((List<MessageProcessor> ) processors)) {
                if (messageProcessor instanceof Startable) {
                    ((Startable) messageProcessor).start();
                }
            }
        }
    }

    @Override
    public void stop()
        throws MuleException
    {
        super.stop();
        if (processors instanceof List) {
            for (MessageProcessor messageProcessor: ((List<MessageProcessor> ) processors)) {
                if (messageProcessor instanceof Stoppable) {
                    ((Stoppable) messageProcessor).stop();
                }
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (processors instanceof List) {
            for (MessageProcessor messageProcessor: ((List<MessageProcessor> ) processors)) {
                if (messageProcessor instanceof Disposable) {
                    ((Disposable) messageProcessor).dispose();
                }
            }
        }
    }

    /**
     * Sets message
     * 
     * @param value Value to set
     */
    public void setMessage(Object value) {
        this.message = value;
    }

    /**
     * Sets processors
     * 
     * @param value Value to set
     */
    public void setProcessors(Object value) {
        this.processors = value;
    }

    /**
     * Invokes the MessageProcessor.
     * 
     * @param event MuleEvent to be processed
     * @throws Exception
     */
    public MuleEvent doProcess(final MuleEvent event)
        throws Exception
    {
        Object moduleObject = null;
        try {
            moduleObject = findOrCreate(ODataConnectorConnectionManager.class, false, event);
            final List<NestedProcessor> _transformedProcessors = new ArrayList<NestedProcessor>();
            if (processors!= null) {
                for (MessageProcessor messageProcessor: ((List<MessageProcessor> ) processors)) {
                    _transformedProcessors.add(new NestedProcessorChain(event, getMuleContext(), messageProcessor));
                }
            }
            Object resultPayload;
            final ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            resultPayload = processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class<? extends Exception>> getManagedExceptions() {
                    return Arrays.asList(((Class<? extends Exception> []) new Class[] {NotAuthorizedException.class }));
                }

                public boolean isProtected() {
                    return false;
                }

                public Object process(Object object)
                    throws Exception
                {
                    return ((ODataConnector) object).batch(event.getMessage(), _transformedProcessors);
                }

            }
            , this, event);
            event.getMessage().setPayload(resultPayload);
            return event;
        } catch (Exception e) {
            throw e;
        }
    }

}
