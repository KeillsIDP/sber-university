package me.keills.repo;

import me.keills.model.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepo extends CrudRepository<Card,Long> {
}
