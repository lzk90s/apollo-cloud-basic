package pers.kun.core.facade;


import org.springframework.web.bind.annotation.*;
import pers.kun.core.base.BaseVO;

import java.util.List;

public interface MService<V extends BaseVO> {
    @PostMapping
    void add(@RequestBody V t);

    @PostMapping("/returnId")
    Object addReturnId(@RequestBody V t);

    @PostMapping("/batch")
    void add(@RequestBody List<V> datas);

    @PutMapping("/batch")
    void update(@RequestBody List<V> datas);

    @PutMapping
    void update(@RequestBody V t);

    @GetMapping("/{id}")
    V getById(@PathVariable Object id);

//    Map<?, V> getMapByIds(Collection ids);
//
//    List<V> getByIds(Collection ids);

    @GetMapping("/page")
    List<V> getAll(Integer offset, Integer limit);
}
