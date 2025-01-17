package net.ddns.wadosm.process;

import net.ddns.wadosm.process.entity.TickRecordEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TickRecordRepository extends MongoRepository<TickRecordEntity, String> {
}
