package org.ewul.server.util;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import java.io.Serializable;
import java.util.Date;

public class CookieBuilder implements Serializable {

    private String name;
    private String value;
    private int version = Cookie.DEFAULT_VERSION;
    private String path;
    private String domain;
    private String comment;
    private int maxAge = NewCookie.DEFAULT_MAX_AGE;
    private Date expiry;
    private boolean secure;
    private boolean httpOnly;

    public CookieBuilder() {
    }

    public CookieBuilder(Cookie cookie) {
        this.name = cookie.getName();
        this.value = cookie.getValue();
        this.version = cookie.getVersion();
        this.path = cookie.getPath();
        this.domain = cookie.getDomain();
    }

    public CookieBuilder(NewCookie cookie) {
        this((Cookie) cookie);
        this.comment = cookie.getComment();
        this.maxAge = cookie.getMaxAge();
        this.expiry = cookie.getExpiry();
        this.secure = cookie.isSecure();
        this.httpOnly = cookie.isHttpOnly();
    }

    public CookieBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CookieBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public CookieBuilder withVersion(int version) {
        this.version = version;
        return this;
    }

    public CookieBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public CookieBuilder withDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public CookieBuilder withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public CookieBuilder withMaxAge(int maxAge) {
        this.maxAge = maxAge;
        return this;
    }

    public CookieBuilder withExpiry(Date expiry) {
        this.expiry = expiry;
        return this;
    }

    public CookieBuilder withSecure(boolean secure) {
        this.secure = secure;
        return this;
    }

    public CookieBuilder withHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
        return this;
    }

    public Cookie buildCookie() {
        return new Cookie(name, value, path, domain, version);
    }

    public NewCookie buildNewCookie() {
        return new NewCookie(buildCookie(), comment, maxAge, expiry, secure, httpOnly);
    }

}
