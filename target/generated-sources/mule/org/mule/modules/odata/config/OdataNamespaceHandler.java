
package org.mule.modules.odata.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/odata</code>.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.3", date = "2015-12-18T03:15:35-03:00", comments = "Build UNNAMED.2049.ec39f2b")
public class OdataNamespaceHandler
    extends NamespaceHandlerSupport
{

    private static Logger logger = LoggerFactory.getLogger(OdataNamespaceHandler.class);

    private void handleException(String beanName, String beanScope, NoClassDefFoundError noClassDefFoundError) {
        String muleVersion = "";
        try {
            muleVersion = MuleManifest.getProductVersion();
        } catch (Exception _x) {
            logger.error("Problem while reading mule version");
        }
        logger.error(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [odata] is not supported in mule ")+ muleVersion));
        throw new FatalBeanException(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [odata] is not supported in mule ")+ muleVersion), noClassDefFoundError);
    }

    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        try {
            this.registerBeanDefinitionParser("config", new ODataConnectorConfigDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("config", "@Config", ex);
        }
        try {
            this.registerBeanDefinitionParser("force-disconnect", new ForceDisconnectDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("force-disconnect", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-entities", new GetEntitiesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-entities", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("create-entity", new CreateEntityDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create-entity", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("update-entity", new UpdateEntityDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("update-entity", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-entity", new DeleteEntityDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-entity", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("batch", new BatchDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("batch", "@Processor", ex);
        }
    }

}
