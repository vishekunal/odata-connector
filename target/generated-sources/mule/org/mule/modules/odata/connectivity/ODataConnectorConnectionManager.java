
package org.mule.modules.odata.connectivity;

import javax.annotation.Generated;
import org.apache.commons.pool.KeyedObjectPool;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.MetadataAware;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.config.MuleProperties;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.context.MuleContextAware;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.devkit.capability.Capabilities;
import org.mule.api.devkit.capability.ModuleCapability;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.retry.RetryPolicyTemplate;
import org.mule.common.DefaultResult;
import org.mule.common.DefaultTestResult;
import org.mule.common.FailureType;
import org.mule.common.TestResult;
import org.mule.common.Testable;
import org.mule.config.PoolingProfile;
import org.mule.devkit.processor.ExpressionEvaluatorSupport;
import org.mule.modules.odata.ODataConnector;
import org.mule.modules.odata.PropertyNamingFormat;
import org.mule.modules.odata.adapters.ODataConnectorConnectionIdentifierAdapter;
import org.mule.modules.odata.connection.ConnectionManager;
import org.mule.modules.odata.connection.UnableToAcquireConnectionException;
import org.mule.modules.odata.factory.ODataConsumerFactory;
import org.mule.modules.odata.pooling.DevkitGenericKeyedObjectPool;
import org.odata4j.core.ODataVersion;
import org.odata4j.format.FormatType;


/**
 * A {@code ODataConnectorConnectionManager} is a wrapper around {@link ODataConnector } that adds connection management capabilities to the pojo.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.3", date = "2015-12-18T03:15:35-03:00", comments = "Build UNNAMED.2049.ec39f2b")
public class ODataConnectorConnectionManager
    extends ExpressionEvaluatorSupport
    implements MetadataAware, MuleContextAware, ProcessAdapter<ODataConnectorConnectionIdentifierAdapter> , Capabilities, Disposable, Initialisable, Testable, ConnectionManager<ODataConnectorConnectionKey, ODataConnectorConnectionIdentifierAdapter>
{

    /**
     * 
     */
    private String username;
    /**
     * 
     */
    private String password;
    /**
     * 
     */
    private String serviceUri;
    private ODataConsumerFactory consumerFactory;
    private ODataVersion consumerVersion;
    private PropertyNamingFormat namingFormat;
    private FormatType formatType;
    /**
     * Mule Context
     * 
     */
    protected MuleContext muleContext;
    /**
     * Flow Construct
     * 
     */
    protected FlowConstruct flowConstruct;
    /**
     * Connector Pool
     * 
     */
    private KeyedObjectPool connectionPool;
    protected PoolingProfile poolingProfile;
    protected RetryPolicyTemplate retryPolicyTemplate;
    private final static String MODULE_NAME = "OData";
    private final static String MODULE_VERSION = "2.0.0";
    private final static String DEVKIT_VERSION = "3.5.3";
    private final static String DEVKIT_BUILD = "UNNAMED.2049.ec39f2b";
    private final static String MIN_MULE_VERSION = "3.5.0";

    /**
     * Sets consumerFactory
     * 
     * @param value Value to set
     */
    public void setConsumerFactory(ODataConsumerFactory value) {
        this.consumerFactory = value;
    }

    /**
     * Retrieves consumerFactory
     * 
     */
    public ODataConsumerFactory getConsumerFactory() {
        return this.consumerFactory;
    }

    /**
     * Sets consumerVersion
     * 
     * @param value Value to set
     */
    public void setConsumerVersion(ODataVersion value) {
        this.consumerVersion = value;
    }

    /**
     * Retrieves consumerVersion
     * 
     */
    public ODataVersion getConsumerVersion() {
        return this.consumerVersion;
    }

    /**
     * Sets namingFormat
     * 
     * @param value Value to set
     */
    public void setNamingFormat(PropertyNamingFormat value) {
        this.namingFormat = value;
    }

    /**
     * Retrieves namingFormat
     * 
     */
    public PropertyNamingFormat getNamingFormat() {
        return this.namingFormat;
    }

    /**
     * Sets formatType
     * 
     * @param value Value to set
     */
    public void setFormatType(FormatType value) {
        this.formatType = value;
    }

    /**
     * Retrieves formatType
     * 
     */
    public FormatType getFormatType() {
        return this.formatType;
    }

    /**
     * Sets muleContext
     * 
     * @param value Value to set
     */
    public void setMuleContext(MuleContext value) {
        this.muleContext = value;
    }

    /**
     * Retrieves muleContext
     * 
     */
    public MuleContext getMuleContext() {
        return this.muleContext;
    }

    /**
     * Sets flowConstruct
     * 
     * @param value Value to set
     */
    public void setFlowConstruct(FlowConstruct value) {
        this.flowConstruct = value;
    }

    /**
     * Retrieves flowConstruct
     * 
     */
    public FlowConstruct getFlowConstruct() {
        return this.flowConstruct;
    }

    /**
     * Sets poolingProfile
     * 
     * @param value Value to set
     */
    public void setPoolingProfile(PoolingProfile value) {
        this.poolingProfile = value;
    }

    /**
     * Retrieves poolingProfile
     * 
     */
    public PoolingProfile getPoolingProfile() {
        return this.poolingProfile;
    }

    /**
     * Sets retryPolicyTemplate
     * 
     * @param value Value to set
     */
    public void setRetryPolicyTemplate(RetryPolicyTemplate value) {
        this.retryPolicyTemplate = value;
    }

    /**
     * Retrieves retryPolicyTemplate
     * 
     */
    public RetryPolicyTemplate getRetryPolicyTemplate() {
        return this.retryPolicyTemplate;
    }

    /**
     * Sets serviceUri
     * 
     * @param value Value to set
     */
    public void setServiceUri(String value) {
        this.serviceUri = value;
    }

    /**
     * Retrieves serviceUri
     * 
     */
    public String getServiceUri() {
        return this.serviceUri;
    }

    /**
     * Sets username
     * 
     * @param value Value to set
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Retrieves username
     * 
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets password
     * 
     * @param value Value to set
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Retrieves password
     * 
     */
    public String getPassword() {
        return this.password;
    }

    public void initialise() {
        connectionPool = new DevkitGenericKeyedObjectPool(new ODataConnectorConnectionFactory(this), poolingProfile);
        if (retryPolicyTemplate == null) {
            retryPolicyTemplate = muleContext.getRegistry().lookupObject(MuleProperties.OBJECT_DEFAULT_RETRY_POLICY_TEMPLATE);
        }
    }

    @Override
    public void dispose() {
        try {
            connectionPool.close();
        } catch (Exception e) {
        }
    }

    public ODataConnectorConnectionIdentifierAdapter acquireConnection(ODataConnectorConnectionKey key)
        throws Exception
    {
        return ((ODataConnectorConnectionIdentifierAdapter) connectionPool.borrowObject(key));
    }

    public void releaseConnection(ODataConnectorConnectionKey key, ODataConnectorConnectionIdentifierAdapter connection)
        throws Exception
    {
        connectionPool.returnObject(key, connection);
    }

    public void destroyConnection(ODataConnectorConnectionKey key, ODataConnectorConnectionIdentifierAdapter connection)
        throws Exception
    {
        connectionPool.invalidateObject(key, connection);
    }

    /**
     * Returns true if this module implements such capability
     * 
     */
    public boolean isCapableOf(ModuleCapability capability) {
        if (capability == ModuleCapability.LIFECYCLE_CAPABLE) {
            return true;
        }
        if (capability == ModuleCapability.CONNECTION_MANAGEMENT_CAPABLE) {
            return true;
        }
        return false;
    }

    @Override
    public<P >ProcessTemplate<P, ODataConnectorConnectionIdentifierAdapter> getProcessTemplate() {
        return new ODataConnectorManagedConnectionProcessTemplate(this, muleContext);
    }

    @Override
    public ODataConnectorConnectionKey getDefaultConnectionKey() {
        return new ODataConnectorConnectionKey(getUsername(), getPassword(), getServiceUri());
    }

    @Override
    public ODataConnectorConnectionKey getEvaluatedConnectionKey(MuleEvent event)
        throws Exception
    {
        if (event!= null) {
            final String _transformedUsername = ((String) evaluateAndTransform(muleContext, event, this.getClass().getDeclaredField("username").getGenericType(), null, getUsername()));
            final String _transformedPassword = ((String) evaluateAndTransform(muleContext, event, this.getClass().getDeclaredField("password").getGenericType(), null, getPassword()));
            final String _transformedServiceUri = ((String) evaluateAndTransform(muleContext, event, this.getClass().getDeclaredField("serviceUri").getGenericType(), null, getServiceUri()));
            if (_transformedServiceUri == null) {
                throw new UnableToAcquireConnectionException("Parameter serviceUri in method connect can't be null because is not @Optional");
            }
            return new ODataConnectorConnectionKey(_transformedUsername, _transformedPassword, _transformedServiceUri);
        }
        return getDefaultConnectionKey();
    }

    public String getModuleName() {
        return MODULE_NAME;
    }

    public String getModuleVersion() {
        return MODULE_VERSION;
    }

    public String getDevkitVersion() {
        return DEVKIT_VERSION;
    }

    public String getDevkitBuild() {
        return DEVKIT_BUILD;
    }

    public String getMinMuleVersion() {
        return MIN_MULE_VERSION;
    }

    public TestResult test() {
        ODataConnectorConnectionIdentifierAdapter connection = null;
        DefaultTestResult result;
        ODataConnectorConnectionKey key = getDefaultConnectionKey();
        try {
            connection = acquireConnection(key);
            result = new DefaultTestResult(org.mule.common.Result.Status.SUCCESS);
        } catch (Exception e) {
            try {
                destroyConnection(key, connection);
            } catch (Exception ie) {
            }
            result = ((DefaultTestResult) buildFailureTestResult(e));
        } finally {
            if (connection!= null) {
                try {
                    releaseConnection(key, connection);
                } catch (Exception ie) {
                }
            }
        }
        return result;
    }

    public DefaultResult buildFailureTestResult(Exception exception) {
        DefaultTestResult result;
        if (exception instanceof ConnectionException) {
            ConnectionExceptionCode code = ((ConnectionException) exception).getCode();
            if (code == ConnectionExceptionCode.UNKNOWN_HOST) {
                result = new DefaultTestResult(org.mule.common.Result.Status.FAILURE, exception.getMessage(), FailureType.UNKNOWN_HOST, exception);
            } else {
                if (code == ConnectionExceptionCode.CANNOT_REACH) {
                    result = new DefaultTestResult(org.mule.common.Result.Status.FAILURE, exception.getMessage(), FailureType.RESOURCE_UNAVAILABLE, exception);
                } else {
                    if (code == ConnectionExceptionCode.INCORRECT_CREDENTIALS) {
                        result = new DefaultTestResult(org.mule.common.Result.Status.FAILURE, exception.getMessage(), FailureType.INVALID_CREDENTIALS, exception);
                    } else {
                        if (code == ConnectionExceptionCode.CREDENTIALS_EXPIRED) {
                            result = new DefaultTestResult(org.mule.common.Result.Status.FAILURE, exception.getMessage(), FailureType.INVALID_CREDENTIALS, exception);
                        } else {
                            if (code == ConnectionExceptionCode.UNKNOWN) {
                                result = new DefaultTestResult(org.mule.common.Result.Status.FAILURE, exception.getMessage(), FailureType.UNSPECIFIED, exception);
                            } else {
                                result = new DefaultTestResult(org.mule.common.Result.Status.FAILURE, exception.getMessage(), FailureType.UNSPECIFIED, exception);
                            }
                        }
                    }
                }
            }
        } else {
            result = new DefaultTestResult(org.mule.common.Result.Status.FAILURE, exception.getMessage(), FailureType.UNSPECIFIED, exception);
        }
        return result;
    }

}
