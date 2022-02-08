package tech.xixing.proxy.statics;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhifei
 * @date 2022/2/7 7:38 下午
 */
@Slf4j
public class MemberDaoSub extends MemberDao{
    @Override
    public String query(int id, String name) {
        log.debug("static proxy query");
        return super.query(id, name);
    }

    public static void main(String[] args) {
        MDao mDao =new MemberDaoSub();
        System.out.println(mDao.query(1, "qaq"));
    }
}
