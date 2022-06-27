package tech.xixing.proxy.statics;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiChatSendRequest;
import com.dingtalk.api.response.OapiChatSendResponse;
import com.taobao.api.ApiException;

/**
 * @author liuzhifei
 * @date 2022/2/7 7:49 下午
 * 静态代理缺点：
 * 会产生很多代理类
 * 产生的代理类只能代理既定的接口
 */
public class Test {
    public static void main(String[] args) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?access_token=qaq");

        OapiChatSendRequest req = new OapiChatSendRequest();
        OapiChatSendRequest.Msg msg = new OapiChatSendRequest.Msg();
        OapiChatSendRequest.Text text = new OapiChatSendRequest.Text();
        text.setContent("请于本月底提交月度工作报告。");
        msg.setText(text);
        msg.setMsgtype("text");
        req.setMsg(msg);
        OapiChatSendResponse rsp = client.execute(req);
        System.out.println(rsp.getBody());
    }
}
