package org.noticeboard2011.model;

import java.io.Serializable;

import org.noticeboard2011.util.StringUtil;
import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;

/**
 * Personモデル
 * 
 * @author akiraabe
 *
 */
@Model(schemaVersion = 3)
public class Person implements Serializable {

    private static final long serialVersionUID = 3492243894122238997L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    private String firstName;
    private String lastName;
    private String place;
    private String twitterId;
    private String memo;
    private String mailAddress;

    /**
     * Returns the key.
     *
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the version.
     *
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Person other = (Person) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return StringUtil.htmlEscape(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return StringUtil.htmlEscape(lastName);
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlace() {
        return StringUtil.htmlEscape(place);
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    
    public String getMemo() {
        if (memo == null) {
            return memo;
        }
        return StringUtil.htmlEscape(memo);
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getMailAddress() {
        if (mailAddress == null) {
            return mailAddress;
        }
        return StringUtil.htmlEscape(mailAddress);
    }
}
