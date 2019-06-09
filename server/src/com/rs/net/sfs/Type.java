package com.rs.net.sfs;

import java.util.Locale;

/**
 * @author Kohsuke Kawaguchi
 */
public enum Type {
    IP, USERNAME, EMAIL;

    String lowerName() {
        return name().toLowerCase(Locale.ENGLISH);
    }

    String asQueryString(String value) {
        return lowerName()+"[]="+value;
    }
}
