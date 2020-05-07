package com.example.androidapp.data;

import com.example.androidapp.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            String name = "";
            if (username.contains("@")) {
                name = username.substring(0, username.indexOf('@'));
            } else {
                name = username;
            }
            LoggedInUser user =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            name);
            return new Result.Success<>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
