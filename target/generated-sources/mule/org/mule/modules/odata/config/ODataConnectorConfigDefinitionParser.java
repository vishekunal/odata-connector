
package org.mule.modules.odata.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.mule.config.PoolingProfile;
import org.mule.modules.odata.connectivity.ODataConnectorConnectionManager;
import org.mule.security.oauth.config.AbstractDevkitBasedDefinitionParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanDefinitionParsingException;
import org.springframework.beans.factory.parsing.Location;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

@Generated(value = "Mule DevKit Version 3.5.3", date = "2015-12-18T03:15:35-03:00", comments = "Build UNNAMED.2049.ec39f2b")
public class ODataConnectorConfigDefinitionParser
    extends AbstractDevkitBasedDefinitionParser
{

    private static Logger logger = LoggerFactory.getLogger(ODataConnectorConfigDefinitionParser.class);

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        parseConfigName(element);
        BeanDefinitionBuilder builder = getBeanDefinitionBuilder(parserContext);
        builder.setScope(BeanDefinition.SCOPE_SINGLETON);
        setInitMethodIfNeeded(builder, ODataConnectorConnectionManager.class);
        setDestroyMethodIfNeeded(builder, ODataConnectorConnectionManager.class);
        if (hasAttribute(element, "consumerFactory-ref")) {
            if (element.getAttribute("consumerFactory-ref").startsWith("#")) {
                builder.addPropertyValue("consumerFactory", element.getAttribute("consumerFactory-ref"));
            } else {
                builder.addPropertyValue("consumerFactory", new RuntimeBeanReference(element.getAttribute("consumerFactory-ref")));
            }
        }
        parseProperty(builder, element, "consumerVersion", "consumerVersion");
        parseProperty(builder, element, "namingFormat", "namingFormat");
        parseProperty(builder, element, "formatType", "formatType");
        parseProperty(builder, element, "username", "username");
        parseProperty(builder, element, "password", "password");
        parseProperty(builder, element, "serviceUri", "serviceUri");
        BeanDefinitionBuilder poolingProfileBuilder = BeanDefinitionBuilder.rootBeanDefinition(PoolingProfile.class.getName());
        Element poolingProfileElement = DomUtils.getChildElementByTagName(element, "connection-pooling-profile");
        if (poolingProfileElement!= null) {
            parseProperty(poolingProfileBuilder, poolingProfileElement, "maxActive");
            parseProperty(poolingProfileBuilder, poolingProfileElement, "maxIdle");
            parseProperty(poolingProfileBuilder, poolingProfileElement, "maxWait");
            if (hasAttribute(poolingProfileElement, "exhaustedAction")) {
                poolingProfileBuilder.addPropertyValue("exhaustedAction", PoolingProfile.POOL_EXHAUSTED_ACTIONS.get(poolingProfileElement.getAttribute("exhaustedAction")));
            }
            if (hasAttribute(poolingProfileElement, "initialisationPolicy")) {
                poolingProfileBuilder.addPropertyValue("initialisationPolicy", PoolingProfile.POOL_INITIALISATION_POLICIES.get(poolingProfileElement.getAttribute("initialisationPolicy")));
            }
            if (hasAttribute(poolingProfileElement, "evictionCheckIntervalMillis")) {
                parseProperty(poolingProfileBuilder, poolingProfileElement, "evictionCheckIntervalMillis");
            }
            if (hasAttribute(poolingProfileElement, "minEvictionMillis")) {
                parseProperty(poolingProfileBuilder, poolingProfileElement, "minEvictionMillis");
            }
            builder.addPropertyValue("poolingProfile", poolingProfileBuilder.getBeanDefinition());
        }
        BeanDefinition definition = builder.getBeanDefinition();
        setNoRecurseOnDefinition(definition);
        parseRetryPolicyTemplate("reconnect", element, parserContext, builder, definition);
        parseRetryPolicyTemplate("reconnect-forever", element, parserContext, builder, definition);
        parseRetryPolicyTemplate("reconnect-custom-strategy", element, parserContext, builder, definition);
        return definition;
    }

    private BeanDefinitionBuilder getBeanDefinitionBuilder(ParserContext parserContext) {
        try {
            return BeanDefinitionBuilder.rootBeanDefinition(ODataConnectorConnectionManager.class.getName());
        } catch (NoClassDefFoundError noClassDefFoundError) {
            String muleVersion = "";
            try {
                muleVersion = MuleManifest.getProductVersion();
            } catch (Exception _x) {
                logger.error("Problem while reading mule version");
            }
            logger.error(("Cannot launch the mule app, the configuration [config] within the connector [odata] is not supported in mule "+ muleVersion));
            throw new BeanDefinitionParsingException(new Problem(("Cannot launch the mule app, the configuration [config] within the connector [odata] is not supported in mule "+ muleVersion), new Location(parserContext.getReaderContext().getResource()), null, noClassDefFoundError));
        }
    }

}
