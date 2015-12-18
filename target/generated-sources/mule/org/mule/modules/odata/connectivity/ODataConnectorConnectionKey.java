
package org.mule.modules.odata.connectivity;

import javax.annotation.Generated;


/**
 * A tuple of connection parameters
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.3", date = "2015-12-18T03:15:35-03:00", comments = "Build UNNAMED.2049.ec39f2b")
public class ODataConnectorConnectionKey {

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

    public ODataConnectorConnectionKey(String username, String password, String serviceUri) {
        this.username = username;
        this.password = password;
        this.serviceUri = serviceUri;
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

    @Override
    public int hashCode() {
        int result = ((this.username!= null)?this.username.hashCode(): 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ODataConnectorConnectionKey)) {
            return false;
        }
        ODataConnectorConnectionKey that = ((ODataConnectorConnectionKey) o);
        if (((this.username!= null)?(!this.username.equals(that.username)):(that.username!= null))) {
            return false;
        }
        return true;
    }

}
