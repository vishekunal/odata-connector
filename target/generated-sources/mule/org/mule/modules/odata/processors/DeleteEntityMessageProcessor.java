
package org.mule.modules.odata.processors;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.odata.ODataConnector;
import org.mule.modules.odata.connectivity.ODataConnectorConnectionManager;
import org.mule.modules.odata.exception.NotAuthorizedException;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * DeleteEntityMessageProcessor invokes the {@link org.mule.modules.odata.ODataConnector#deleteEntity(org.mule.api.MuleMessage, java.lang.Object, java.lang.String, java.lang.String)} method in {@link ODataConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.3", date = "2015-12-18T03:15:35-03:00", comments = "Build UNNAMED.2049.ec39f2b")
public class DeleteEntityMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor
{

    protected Object message;
    protected MuleMessage _messageType;
    protected Object entity;
    protected Object _entityType;
    protected Object entitySetName;
    protected String _entitySetNameType;
    protected Object keyAttribute;
    protected String _keyAttributeType;

    public DeleteEntityMessageProcessor(String operationName) {
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
    }

    @Override
    public void start()
        throws MuleException
    {
        super.start();
    }

    @Override
    public void stop()
        throws MuleException
    {
        super.stop();
    }

    @Override
    public void dispose() {
        super.dispose();
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
     * Sets entity
     * 
     * @param value Value to set
     */
    public void setEntity(Object value) {
        this.entity = value;
    }

    /**
     * Sets keyAttribute
     * 
     * @param value Value to set
     */
    public void setKeyAttribute(Object value) {
        this.keyAttribute = value;
    }

    /**
     * Sets entitySetName
     * 
     * @param value Value to set
     */
    public void setEntitySetName(Object value) {
        this.entitySetName = value;
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
            final Object _transformedEntity = ((Object) evaluateAndTransform(getMuleContext(), event, DeleteEntityMessageProcessor.class.getDeclaredField("_entityType").getGenericType(), null, entity));
            final String _transformedEntitySetName = ((String) evaluateAndTransform(getMuleContext(), event, DeleteEntityMessageProcessor.class.getDeclaredField("_entitySetNameType").getGenericType(), null, entitySetName));
            final String _transformedKeyAttribute = ((String) evaluateAndTransform(getMuleContext(), event, DeleteEntityMessageProcessor.class.getDeclaredField("_keyAttributeType").getGenericType(), null, keyAttribute));
            final ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class<? extends Exception>> getManagedExceptions() {
                    return Arrays.asList(((Class<? extends Exception> []) new Class[] {NotAuthorizedException.class }));
                }

                public boolean isProtected() {
                    return false;
                }

                public Object process(Object object)
                    throws Exception
                {
                    ((ODataConnector) object).deleteEntity(event.getMessage(), _transformedEntity, _transformedEntitySetName, _transformedKeyAttribute);
                    return null;
                }

            }
            , this, event);
            return event;
        } catch (Exception e) {
            throw e;
        }
    }

}
