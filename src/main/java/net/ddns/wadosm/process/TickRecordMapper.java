package net.ddns.wadosm.process;

import net.ddns.wadosm.process.dto.TickRecordDto;
import net.ddns.wadosm.process.entity.TickRecordEntity;
import org.mapstruct.Mapper;

@Mapper
public interface TickRecordMapper {

    TickRecordEntity mapTickRecord(TickRecordDto recordDto);

}
