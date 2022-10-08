package pers.kun.core.web;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author : qihang.liu
 * @date 2021-11-23
 */
public class DaoServiceImpl<MAPPER extends BaseMapper<DO>, DO> extends ServiceImpl<MAPPER, DO> {
}
