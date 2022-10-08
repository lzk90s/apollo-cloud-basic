package team.uni.apollo.basic.core.exception;

/**
 * @author : qihang.liu
 * @date 2021-11-23
 */
public class ItemNotExistException extends BizException {
    public ItemNotExistException(String type) {
        super("Item not exist, " + type);
    }

    public ItemNotExistException() {
        super("Item not exist");
    }
}
