package com.matuszak.engineer.repository;

import com.matuszak.engineer.model.ReceiverId;
import com.matuszak.engineer.model.entity.Receiver;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReceiverRepository extends MongoRepository<Receiver, ReceiverId>{
}
