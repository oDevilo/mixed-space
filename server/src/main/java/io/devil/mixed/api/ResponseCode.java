package io.devil.mixed.api;

/**
 * 业务状态码。业务状态码从 100000 开始；
 *
 *
 * 业务状态码由 6 位组成，根据位数从大到小，分为 高两位、中两位、低两位。
 *
 * 以错误码 100001 为例，如下：
 * 10 00 01
 * 高 中 低
 *
 * 可以参考下面格式定义错误码，但不强制要求遵守，这只是个约定：
 * 00 为公用内容
 * 错误码高两位取值范围为 01 ~ 99，可用于区分领域聚合（业务模块，如：日程、参与人、评论、历史记录）；
 * 中两位取值 00~99，可用于标识子业务操作（如：创建日程、删除日程）；
 * 低两位取值 00~99，可用于标识错误类型（如：无权执行操作、业务权限校验不通过）；
 *
 *
 * @author Brozen
 * @since 2024-02-01
 */
public enum ResponseCode {
    // 公用 00开头
    SUCCESS(200, "成功"),
    PARAM_ERROR(400, "参数异常"),
    UNAUTHORIZED(401, "请先登录"),
    FORBIDDEN(403, "无权访问"),
    NOT_FOUND_ERROR(404, "资源未找到"),
    SYSTEM_ERROR(500, "服务异常"),
    // 业务定义 01 ~ 99 开头
    ;


    public final int code;
    public final String defaultMsg;

    ResponseCode(int code, String defaultMsg) {
        this.code = code;
        this.defaultMsg = defaultMsg;
    }

}
