package com.demo.app.service;

import com.demo.app.CollectionSetup;
import com.demo.app.dal.User;
import com.google.common.collect.ImmutableList;
import com.piiano.vault.client.openapi.ApiException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    public void beforeEach() throws ApiException {
        CollectionSetup.setUp();
    }

    @AfterEach
    public void afterEach() {
        CollectionSetup.tearDown();
    }

    @Test
    public void testCreateAndGetUser() {

        userService.addUser(getUser());

        List<Integer> userIds = StreamSupport.stream(userService.getAllUsers().spliterator(), false)
                .map(User::getId)
                .collect(Collectors.toList());

        assertEquals(1, userIds.size());

        // Cleanup.
        deleteUsers(userIds);
    }

    @Test
    public void testCreateMultipleUsers() {

        List<User> users = getUsers();
        users.forEach(u -> userService.addUser(u));

        List<Integer> userIds = StreamSupport.stream(userService.getAllUsers().spliterator(), false)
                .map(User::getId)
                .collect(Collectors.toList());

        assertEquals(3, userIds.size());

        // Cleanup.
        deleteUsers(userIds);
    }

    @Test
    public void testFindUsersByName() {

        List<User> users = Lists.newArrayList(getUsers());
        users.add(getUser());

        users.forEach(u -> userService.addUser(u));

        List<Integer> userIds = StreamSupport.stream(userService.getAllUsers().spliterator(), false)
                .map(User::getId)
                .collect(Collectors.toList());

        assertEquals(4, userIds.size());

        List<User> personNamedJohn = userService.findUserByName("John");
        assertEquals(2, personNamedJohn.size());

        // Cleanup.
        deleteUsers(userIds);
    }

    @Test
    public void testFindUsersByPhoneNumber() {

        List<User> users = Lists.newArrayList(getUsers());
        users.add(getUser());

        String phoneNumber = "+12345678";
        for (int i = 0; i < users.size(); i++) {
            if (i == 0) {
                continue;
            }
            users.get(i).setPhoneNumber(phoneNumber);
        }

        users.forEach(u -> userService.addUser(u));

        List<Integer> userIds = StreamSupport.stream(userService.getAllUsers().spliterator(), false)
                .map(User::getId)
                .collect(Collectors.toList());

        assertEquals(4, userIds.size());

        List<User> personNamedJohn = userService.findUserByPhoneNumber(phoneNumber);
        assertEquals(3, personNamedJohn.size());

        // Cleanup.
        deleteUsers(userIds);
    }

    @Test
    public void testUpdateUser() {

        List<User> users = Lists.newArrayList(getUsers());
        users.forEach(u -> userService.addUser(u));

        List<Integer> userIds = StreamSupport.stream(userService.getAllUsers().spliterator(), false)
                .map(User::getId)
                .collect(Collectors.toList());

        assertEquals(3, userIds.size());

        String newName = "Steven";
        User userToUpdate = new User();
        userToUpdate.setId(userIds.get(0));
        userToUpdate.setName(newName);
        userService.updateUser(userToUpdate);

        List<User> user = userService.findUserByName(newName);
        assertEquals(1, user.size());

        // Cleanup.
        deleteUsers(userIds);
    }

    @Test
    public void testDeleteUser() {

        List<User> users = Lists.newArrayList(getUsers());
        users.forEach(u -> userService.addUser(u));

        List<Integer> userIds = StreamSupport.stream(userService.getAllUsers().spliterator(), false)
                .map(User::getId)
                .collect(Collectors.toList());

        assertEquals(3, userIds.size());

        userService.deleteUser(userIds.get(0));

        userIds = StreamSupport.stream(userService.getAllUsers().spliterator(), false)
                .map(User::getId)
                .collect(Collectors.toList());
        assertEquals(2, userIds.size());

        // Cleanup.
        deleteUsers(userIds);
    }

    private static User getUser() {
        User user = new User();
        user.setName("John");
        user.setPhoneNumber("+8-888-88888");
        user.setCountry("USA");
        return user;
    }

    private static List<User> getUsers() {
        User user1 = getUser();

        User user2 = new User();
        user2.setName("Alice");
        user2.setPhoneNumber("+11111111");
        user2.setCountry("Brazil");

        User user3 = new User();
        user3.setName("Bob");
        user3.setPhoneNumber("+22222222");
        user3.setCountry("Australia");

        return ImmutableList.of(user1, user2, user3);
    }

    private void deleteUsers(List<Integer> userIds) {
        userIds.forEach(id -> userService.deleteUser(id));
    }
}
