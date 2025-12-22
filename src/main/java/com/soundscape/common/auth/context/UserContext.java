package com.soundscape.common.auth.context;

public class UserContext {

    private static final ThreadLocal<String> userContext = new ThreadLocal<>();

    public static void setUserContext(String userContext) {
        UserContext.userContext.set(userContext);
    }

    public static String getUserContext() {
        return userContext.get();
    }

    public static void clear() {
        userContext.remove();
    }
}
