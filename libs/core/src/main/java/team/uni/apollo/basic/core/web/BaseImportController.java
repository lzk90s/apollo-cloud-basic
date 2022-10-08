package team.uni.apollo.basic.core.web;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import team.uni.apollo.basic.core.base.BaseDO;
import team.uni.apollo.basic.core.convert.ObjectFactory;
import team.uni.apollo.basic.core.rest.R;
import team.uni.apollo.basic.core.web.model.BaseImportCmd;
import team.uni.apollo.basic.core.web.model.ExcelListener;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author : qihang.liu
 * @date 2022-04-19
 */
public interface BaseImportController<
        MAPPER extends BaseMapper<DO>,
        DO extends BaseDO,
        IMPORT_CMD extends BaseImportCmd<DO>
        > {

    /**
     * 获取dao service
     *
     * @return
     */
    DaoServiceImpl<MAPPER, DO> getDaoService();

    /**
     * 获取import class
     *
     * @return
     */
    Class<IMPORT_CMD> getImportClass();

    /**
     * 获取do class
     *
     * @return
     */
    Class<DO> getDoClass();

    /**
     * 获取handler
     *
     * @return
     */
    Consumer<IMPORT_CMD> getBeforeImportHandler();

    @PostMapping("/import_excel")
    default R<Void> importExcel(MultipartFile file) {
        try {
            var excelListener = new ExcelListener<IMPORT_CMD>() {
                @Override
                protected void process(IMPORT_CMD cmd) {
                    if (null != getBeforeImportHandler()) {
                        getBeforeImportHandler().accept(cmd);
                    }
                    DO entity = ObjectFactory.newInstance(getDoClass());
                    BeanUtils.copyProperties(cmd, entity);
                    getDaoService().saveOrUpdate(entity);
                }
            };
            EasyExcel.read(file.getInputStream(), getImportClass(), excelListener).sheet().doRead();
            return R.ok();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
