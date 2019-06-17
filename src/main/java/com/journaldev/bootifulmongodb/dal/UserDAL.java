package com.journaldev.bootifulmongodb.dal;

import java.util.List;

import com.journaldev.bootifulmongodb.model.TestUser;

public interface UserDAL {

    List<TestUser> getAllUsers();

    TestUser getUserById(String userId);

    TestUser addNewUser(TestUser user);

    Object getAllUserSettings(String userId);

    String getUserSetting(String userId, String key);

    String addUserSetting(String userId, String key, String value);
}
