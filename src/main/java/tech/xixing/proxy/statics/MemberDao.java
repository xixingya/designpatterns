package tech.xixing.proxy.statics;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhifei
 * @date 2022/2/7 7:34 下午
 */
@Slf4j
public class MemberDao implements MDao{

    @Override
    public String query(int id, String name) {
        log.debug("id --- query");
        return id+"---"+name;
    }
}
