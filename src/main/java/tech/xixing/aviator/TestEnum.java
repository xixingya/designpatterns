package tech.xixing.aviator;

/**
 * @author liuzhifei
 * @date 2022/7/18 10:51 上午
 */
public enum TestEnum {
    PLATFORM_ADULT(1,"平台认证-成年人"),
    PLATFORM_TEEN(2,"平台认证-未成年人"),
    MOBILE_ADULT(3,"腾讯云手机号识别-成年人"),
    MOBILE_TEEN(4,"腾讯云手机号识别-未成年人"),
    MOBILE_UNKNOWN(5,"腾讯云手机号识别-未知");


    private int status;

    private String desc;

    TestEnum(Integer status, String desc){
        this.status = status;
        this.desc = desc;
    }
}
