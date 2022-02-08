package tech.xixing.proxy.statics;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhifei
 * @date 2022/2/7 7:44 下午
 * 代理类(ProxyObject)和目标类实现同一个接口
 */
@Slf4j
public class MDaoTime implements MDao{

    //目标对象 TargetObject
    MDao mDao;

    public MDaoTime(MDao mDao){
        this.mDao =mDao;
    }
    @Override
    public String query(int id, String name) {
        log.debug("proxy before");
        String query = mDao.query(id, name);
        log.debug("proxy after");
        return query;
    }
}
