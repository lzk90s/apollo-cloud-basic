package team.uni.apollo.basic.system.web.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import team.uni.apollo.basic.core.converter.ConverterConfig;
import team.uni.apollo.basic.system.dal.entity.UserDO;
import team.uni.apollo.basic.system.web.vo.UserRes;

import java.util.List;

/**
 * @author : qihang.liu
 * @date 2022-10-08
 */
@Mapper(config = ConverterConfig.class, componentModel = "spring")
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    UserRes toRes(UserDO userDO);

    List<UserRes> toRes(List<UserDO> userDOS);
}
