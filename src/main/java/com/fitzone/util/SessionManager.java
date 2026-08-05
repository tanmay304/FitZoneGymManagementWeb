package com.fitzone.util;

import com.fitzone.model.Admin;

/**
 * Thread-safe Singleton session manager holding the active authenticated user.
 */
public class SessionManager {

    private static SessionManager instance;
    private Admin currentAdmin;

    private SessionManager() {}

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setCurrentAdmin(Admin admin) {
        this.currentAdmin = admin;
    }

    public Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public boolean isLoggedIn() {
        return currentAdmin != null;
    }

    public void logout() {
        this.currentAdmin = null;
    }
}
