package org.ewul.model;

import java.io.Serializable;
import java.security.Principal;
import java.util.*;

public interface User extends Principal, Serializable {

    UUID getId();

    @Override
    String getName();

    Set<String> getRoles();

    Map<String, String> getProperties();

}
