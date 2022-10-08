package team.javis.apollo.basic.core.web;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.web.bind.annotation.GetMapping;
import team.javis.apollo.basic.core.base.BaseDO;
import team.javis.apollo.basic.core.web.model.BasePageQuery;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : qihang.liu
 * @date 2022-04-19
 */
public interface BaseExportController<
        MAPPER extends BaseMapper<DO>,
        DO extends BaseDO,
        PAGE_QUERY extends BasePageQuery<DO>
        > {

    @GetMapping("/export_excel")
    default void exportExcel(PAGE_QUERY query, HttpServletResponse response) throws IOException {
        query.setPageNo(1);
        query.setPageSize(Integer.MAX_VALUE - 1);

//        var result = getPage(query);
//        if (null == result || !result.isOk() || null == result.getResult()) {
//            throw new BizException("export failed");
//        }
//        if (result.getResult().getData() == null) {
//            return;
//        }

//        InputStream inputStream = classPathResource.getInputStream();
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding("utf-8");
//        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
//        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
//        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
//        // 如果不用模板的方式导出的话，是doWrite
//        EasyExcel.write(response.getOutputStream(), importClass).withTemplate(inputStream).sheet("模板")
//                .doFill(result.getResult().getData());
    }
}
