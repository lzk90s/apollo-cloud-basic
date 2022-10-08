package pers.kun.core.web.model;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

/**
 * @author : qihang.liu
 * @date 2021-11-23
 */
public abstract class ExcelListener<T> extends AnalysisEventListener<T> {

    @Override
    public void invoke(T o, AnalysisContext analysisContext) {
        process(o);
    }

    abstract protected void process(T object);

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //data.clear();
        //解析结束销毁不用的资源
    }
}
