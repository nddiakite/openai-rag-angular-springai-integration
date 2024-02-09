package fr.dtek.lab.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MessageEntityRepository extends JpaRepository<MessageEntity, String> {
    //Save a Message
    MessageEntity save(MessageEntity message);

    //Get all message for a user
    /*
        @Transaction is used here because PostgreSQL don't manage well without it
        Useful URL : https://github.com/jhipster/generator-jhipster/issues/8146

        There are 3 ways to solve this:

    Mark the repository @transactional : I don't like it very much, because that gives more work to the service layer (which has to join the transaction, etc). So there's a little performance hit, for nothing much.
    Mark the REST endpoint with @transactional(readOnly=true) : I don't like having transactions in the view layer. There's one added benefit from the point above: you can mark your transaction as readonly, so you have a performance gain.
    Configure the connection pool to have autocommit = false. In fact, I thought we were already doing that, as the (older) connections pools I used were doing this. But that's not the case with HikariCP!!
     */
    @Transactional
    Iterable<MessageEntity> findAllByUserId(String userId);

    void deleteAllByUserId(String userId);
}
