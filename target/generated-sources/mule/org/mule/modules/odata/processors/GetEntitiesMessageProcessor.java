
package org.mule.modules.odata.processors;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.odata.ODataConnector;
import org.mule.modules.odata.connectivity.ODataConnectorConnectionManager;
import org.mule.modules.odata.exception.NotAuthorizedException;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * GetEntitiesMessageProcessor invokes the {@link org.mule.modules.odata.ODataConnector#getEntities(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.String)} method in {@link ODataConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.3", date = "2015-12-18T03:15:35-03:00", comments = "Build UNNAMED.2049.ec39f2b")
public class GetEntitiesMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor
{

    protected Object returnClass;
    protected String _returnClassType;
    protected Object entitySetName;
    protected String _entitySetNameType;
    protected Object filter;
    protected String _filterType;
    protected Object orderBy;
    protected String _orderByType;
    protected Object expand;
    protected String _expandType;
    protected Object skip;
    protected Integer _skipType;
    protected Object top;
    protected Integer _topType;
    protected Object select;
    protected String _selectType;

    public GetEntitiesMessageProcessor(String operationName) {
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
     * Sets returnClass
     * 
     * @param value Value to set
     */
    public void setReturnClass(Object value) {
        this.returnClass = value;
    }

    /**
     * Sets orderBy
     * 
     * @param value Value to set
     */
    public void setOrderBy(Object value) {
        this.orderBy = value;
    }

    /**
     * Sets select
     * 
     * @param value Value to set
     */
    public void setSelect(Object value) {
        this.select = value;
    }

    /**
     * Sets expand
     * 
     * @param value Value to set
     */
    public void setExpand(Object value) {
        this.expand = value;
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
     * Sets skip
     * 
     * @param value Value to set
     */
    public void setSkip(Object value) {
        this.skip = value;
    }

    /**
     * Sets filter
     * 
     * @param value Value to set
     */
    public void setFilter(Object value) {
        this.filter = value;
    }

    /**
     * Sets top
     * 
     * @param value Value to set
     */
    public void setTop(Object value) {
        this.top = value;
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
            final String _transformedReturnClass = ((String) evaluateAndTransform(getMuleContext(), event, GetEntitiesMessageProcessor.class.getDeclaredField("_returnClassType").getGenericType(), null, returnClass));
            final String _transformedEntitySetName = ((String) evaluateAndTransform(getMuleContext(), event, GetEntitiesMessageProcessor.class.getDeclaredField("_entitySetNameType").getGenericType(), null, entitySetName));
            final String _transformedFilter = ((String) evaluateAndTransform(getMuleContext(), event, GetEntitiesMessageProcessor.class.getDeclaredField("_filterType").getGenericType(), null, filter));
            final String _transformedOrderBy = ((String) evaluateAndTransform(getMuleContext(), event, GetEntitiesMessageProcessor.class.getDeclaredField("_orderByType").getGenericType(), null, orderBy));
            final String _transformedExpand = ((String) evaluateAndTransform(getMuleContext(), event, GetEntitiesMessageProcessor.class.getDeclaredField("_expandType").getGenericType(), null, expand));
            final Integer _transformedSkip = ((Integer) evaluateAndTransform(getMuleContext(), event, GetEntitiesMessageProcessor.class.getDeclaredField("_skipType").getGenericType(), null, skip));
            final Integer _transformedTop = ((Integer) evaluateAndTransform(getMuleContext(), event, GetEntitiesMessageProcessor.class.getDeclaredField("_topType").getGenericType(), null, top));
            final String _transformedSelect = ((String) evaluateAndTransform(getMuleContext(), event, GetEntitiesMessageProcessor.class.getDeclaredField("_selectType").getGenericType(), null, select));
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
                    return ((ODataConnector) object).getEntities(_transformedReturnClass, _transformedEntitySetName, _transformedFilter, _transformedOrderBy, _transformedExpand, _transformedSkip, _transformedTop, _transformedSelect);
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
