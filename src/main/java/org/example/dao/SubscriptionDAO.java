package org.example.dao;

import org.example.Subscription;
import java.util.List;

public interface SubscriptionDAO {
    void create(Subscription subscription);
    Subscription read(int id);
    void update(Subscription subscription);
    void delete(int id);
    List<Subscription> readAll();
}
