package com.zenika.zencontact.persistence.datastore;

import com.google.appengine.api.datastore.*;
import com.zenika.zencontact.persistence.UserDao;
import com.zenika.zencontact.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoDatastore implements UserDao {

    private static UserDaoDatastore INSTANCE = new UserDaoDatastore();

    public static UserDaoDatastore getInstance() {
        return INSTANCE;
    }

    public DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public long save(User contact) {
        Entity entity = new Entity("User");
        if(contact.id != null) {
            Key k = KeyFactory.createKey("User", contact.id);
            try {
                entity = datastore.get(k);
            } catch (EntityNotFoundException e) {
                e.printStackTrace();
            }
        }
        entity.setProperty("firstName", contact.firstName);
        entity.setProperty("lastName", contact.lastName);
        entity.setProperty("email", contact.email);
        entity.setProperty("notes", contact.notes);
        return datastore.put(entity).getId();
    }

    public void delete(Long id) {
        Key k = KeyFactory.createKey("User", id);
        datastore.delete(k);
    }

    public User get(Long id) {
        Entity entity = null;
        try {
            entity = datastore.get(KeyFactory.createKey("User", id));
        } catch(EntityNotFoundException e) {
            e.printStackTrace();
        }
        return User.create()
                .id(entity.getKey().getId())
                .firstName((String) entity.getProperty("firstName"))
                .lastName((String) entity.getProperty("lastName"))
                .email((String) entity.getProperty("email"))
                .notes((String) entity.getProperty("notes"));
    }

    public List<User> getAll() {
        List<User> contacts = new ArrayList<>();
        Query q = new Query("User")
                .addProjection(new PropertyProjection("firstName", String.class))
                .addProjection(new PropertyProjection("lastName", String.class))
                .addProjection(new PropertyProjection("email", String.class))
                .addProjection(new PropertyProjection("notes", String.class));

        return contacts;
    }
}