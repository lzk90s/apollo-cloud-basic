package team.uni.apollo.basic.core.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import team.uni.apollo.basic.core.base.BaseDO;
import team.uni.apollo.basic.core.base.BaseVO;
import team.uni.apollo.basic.core.convert.ObjectFactory;
import team.uni.apollo.basic.core.exception.ItemNotExistException;
import team.uni.apollo.basic.core.rest.R;
import team.uni.apollo.basic.core.util.JsonUtil;
import team.uni.apollo.basic.core.web.model.BaseAddCmd;
import team.uni.apollo.basic.core.web.model.BasePageQuery;
import team.uni.apollo.basic.core.web.model.BaseUpdateCmd;
import team.uni.apollo.basic.core.web.model.PageData;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : qihang.liu
 * @date 2022-04-19
 */
public interface BaseCurdController<
        MAPPER extends BaseMapper<DO>,
        DO extends BaseDO,
        VO extends BaseVO,
        ADD_CMD extends BaseAddCmd<DO>,
        UPDATE_CMD extends BaseUpdateCmd<DO>,
        PAGE_QUERY extends BasePageQuery<DO>
        > {

    /**
     * 获取dao service
     *
     * @return dao service
     */
    DaoServiceImpl<MAPPER, DO> getDaoService();

    /**
     * 获取vo class
     *
     * @return
     */
    Class<VO> getVoClass();

    /**
     * 获取do class
     *
     * @return
     */
    Class<DO> getDoClass();

    @PostMapping
    default R<Void> save(@Valid @RequestBody ADD_CMD cmd) {
        DO entity = Optional.ofNullable(cmd.convertToDO()).orElse(ObjectFactory.newInstance(this.getDoClass()));
        BeanUtils.copyProperties(cmd, entity);
        getDaoService().save(entity);
        return R.ok();
    }

    @PutMapping
    default R<Void> update(@Valid @RequestBody UPDATE_CMD cmd) {
        DO entity = Optional.ofNullable(cmd.convertToDO()).orElse(ObjectFactory.newInstance(this.getDoClass()));
        BeanUtils.copyProperties(cmd, entity);
        getDaoService().updateById(entity);
        return R.ok();
    }

    @PutMapping("/status/{id}")
    default R<Void> updateStatus(@PathVariable("id") String id, @RequestParam("status") Integer status) {
        // 这里用json进行转换
        Map<String, Object> objMap = new HashMap<>();
        objMap.put("id", id);
        objMap.put("status", status);
        DO entity = JsonUtil.json2pojo(JsonUtil.obj2json(objMap), this.getDoClass());
        getDaoService().updateById(entity);
        return R.ok();
    }

    @DeleteMapping("{id}")
    default R<Void> delete(@PathVariable("id") String id) {
        var record = getDaoService().getById(id);
        Optional.ofNullable(record).orElseThrow(ItemNotExistException::new);
        // 软删除
        record.setDeleted(true);
        getDaoService().updateById(record);
        return R.ok();
    }

    @GetMapping("{id}")
    default R<VO> get(@PathVariable("id") String id) {
        var record = getDaoService().getById(id);
        if (null == record || record.getDeleted()) {
            return R.ok();
        }
        VO vo = ObjectFactory.newInstance(getVoClass());
        var vo1 = vo.convertFromDO(record);
        if (null != vo1) {
            BeanUtils.copyProperties(vo1, vo);
        } else {
            BeanUtils.copyProperties(record, vo);
        }
        return R.ok(vo);
    }

    @GetMapping("/page")
    default R<PageData<VO>> getPage(PAGE_QUERY query) {
        var entity = query.buildQueryWrapper();
        if (null == entity) {
            entity = new QueryWrapper<DO>();
        }
        entity.eq("deleted", false);
        var resultDo = getDaoService().page(new Page<>(query.getPageNo(), query.getPageSize()), entity);
        if (CollectionUtils.isEmpty(resultDo.getRecords())) {
            return R.ok(new PageData<>(0L, 0L, null));
        }
        var results = resultDo.getRecords().stream().map(record -> {
            VO vo = ObjectFactory.newInstance(getVoClass());
            var vo1 = vo.convertFromDO(record);
            if (null != vo1) {
                BeanUtils.copyProperties(vo1, vo);
            } else {
                BeanUtils.copyProperties(record, vo);
            }
            return vo;
        }).collect(Collectors.toList());
        return R.ok(new PageData<>(resultDo.getCurrent(), resultDo.getTotal(), results));
    }
}
