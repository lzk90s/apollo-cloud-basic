package team.uni.apollo.basic.core.facade;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import team.uni.apollo.basic.core.base.BaseDO;
import team.uni.apollo.basic.core.base.BaseVO;
import team.uni.apollo.basic.core.convert.ObjectFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : qihang.liu
 * @date 2022-01-14
 */

public abstract class BaseMService<D extends BaseDO, M extends BaseMapper<D>, V extends BaseVO> implements MService<V> {

    protected ServiceImpl<M, D> baseDAO;

    private Class<D> doClass;
    private Class<V> voClazz;

    public void setBaseDAO(Class<D> doClazz, Class<V> voClazz, ServiceImpl<M, D> service) {
        this.baseDAO = service;
        this.doClass = doClazz;
        this.voClazz = voClazz;
    }

    @Override
    public void add(V v) {
        D t = convert2DO(v);
        baseDAO.save(t);
        v.setId(t.getId());
    }

    @Override
    public Object addReturnId(V v) {
        D t = convert2DO(v);
        baseDAO.save(t);
        return t.getId();
    }

    @Override
    public void add(List<V> datas) {
        baseDAO.saveBatch(convert2DO(datas));
    }

    @Override
    public void update(List<V> datas) {
        baseDAO.updateBatchById(convert2DO(datas));
    }

    @Override
    public void update(V v) {
        baseDAO.updateById(convert2DO(v));
    }

    @Override
    public V getById(Object id) {
        D t = (D) baseDAO.getById(id.toString());
        if (t == null) {
            return null;
        }
        return convert2VO(t);
    }
//
//    @Override
//    public Map<?, V> getMapByIds(Collection ids) {
//        if (ids.size() == 0) {
//            return Collections.emptyMap();
//        }
//
//        Map<Object, V> map = new HashMap<>();
//        List<D> dos = baseDAO.listByIds(new ArrayList<>(ids));
//        for (D t : dos) {
//            map.put(t.getId(), convert2VO(t));
//        }
//        return map;
//    }
//
//    @Override
//    public List<V> getByIds(Collection ids) {
//        if (ids.size() == 0) {
//            return Collections.emptyList();
//        }
//        List<D> dos = baseDAO.listByIds(new ArrayList<>(ids));
//        return convert2VO(dos);
//    }

    @Override
    public List<V> getAll(Integer offset, Integer limit) {
        var dos = baseDAO.page(new Page<>(offset / limit, limit));
        return convert2VO(dos.getRecords());
    }

    public V newVO() {
        return ObjectFactory.newInstance(this.voClazz);
    }

    public V convert2VO(D t) {
        if (t == null) {
            return null;
        }

        V v = newVO();
        BeanUtils.copyProperties(t, v);
        return v;
    }

    public List<V> convert2VO(List<D> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        return list.stream().map(this::convert2VO).collect(Collectors.toList());
    }

    public D newDO() {
        return ObjectFactory.newInstance(this.doClass);
    }

    public D convert2DO(V v) {
        if (v == null) {
            return null;
        }

        D t = newDO();
        BeanUtils.copyProperties(v, t);
        return t;
    }

    public List<D> convert2DO(List<V> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        return list.stream().map(this::convert2DO).collect(Collectors.toList());
    }
}