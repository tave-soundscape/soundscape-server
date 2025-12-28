package com.soundscape.common.auth.context;

public class UserContextHolder {

    private static final ThreadLocal<String> userContext = new ThreadLocal<>();

    public static void setUserContext(String userContext) {
        UserContextHolder.userContext.set(userContext);
    }

    public static String getUserContext() {
        return userContext.get();
    }

    public static void clear() {
        userContext.remove();
    }
}
