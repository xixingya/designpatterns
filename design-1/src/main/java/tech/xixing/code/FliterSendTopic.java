package tech.xixing.code;

import java.util.*;

/**
 * @author liuzhifei
 * @date 2022/9/7 11:18 上午
 */
public class FliterSendTopic {


    public static void main(String[] args) {
        String s= "YPP-PROFILE-ORDER\n" +
                "144\t0\t0.0000%\tL S\t6.0\t1.7\t2.2\t2.2\t2.2\t2.2\t0.0\n" +
                "[:: show ::]\t\n" +
                "YPP-PROFILE-GAME\n" +
                "1,444\t0\t0.0000%\tL S\t7.0\t1.0\t2.9\t2.9\t2.9\t2.9\t0.0\n" +
                "[:: show ::]\t\n" +
                "bigdata_user_alias\n" +
                "4,816\t0\t0.0000%\tL S\t30.0\t0.7\t3.4\t3.4\t3.4\t3.4\t0.0\n" +
                "[:: show ::]\t\n" +
                "bigdata_es_update_kafka\n" +
                "5,946\t0\t0.0000%\tL S\t14.0\t0.4\t2.9\t3.0\t3.0\t3.0\t0.0\n" +
                "[:: show ::]\t\n" +
                "bigdata_gd_category\n" +
                "5\t0\t0.0000%\tL S\t1.0\t0.4\t1.0\t1.0\t1.0\t1.0\t0.0\n" +
                "[:: show ::]\t\n" +
                "bxgame_player_list\n" +
                "18,831\t0\t0.0000%\tL S\t35.0\t0.2\t1.7\t1.9\t1.9\t1.9\t0.0\n" +
                "[:: show ::]\t\n" +
                "bigdata_biggie_complain_punish\n" +
                "17,088\t0\t0.0000%\tL S\t45.0\t0.1\t1.5\t2.1\t2.1\t2.1\t0.0\n" +
                "[:: show ::]\t\n" +
                "HOMEPAGE_GAME_CARD_REC\n" +
                "7,769\t0\t0.0000%\tL S\t19.0\t0.1\t1.2\t1.6\t2.0\t2.0\t0.0\n" +
                "[:: show ::]\t\n" +
                "bigdata_dubbing_show\n" +
                "14,597\t0\t0.0000%\tL S\t7.0\t0.1\t1.3\t1.6\t1.6\t1.6\t0.0\n" +
                "[:: show ::]\t\n" +
                "YPP-REALTIME-FEATURE-USER\n" +
                "67,240,372\t0\t0.0000%\tL S\t92.0\t0.0\t1.0\t1.0\t1.0\t1.0\t97.3\n" +
                "[:: show ::]\t\n" +
                "recommend_content_original_liveroom\n" +
                "5,865,744\t0\t0.0000%\tL S\t93.0\t0.0\t1.0\t1.0\t1.0\t1.0\t8.5\n" +
                "[:: show ::]\t\n" +
                "YPP-REALTIME-FEATURE-SLIDING\n" +
                "1,072,455\t0\t0.0000%\tL S\t8.0\t0.0\t1.0\t1.0\t1.0\t1.0\t1.6\n" +
                "[:: show ::]\t\n" +
                "CHATROOM_FEATURE_SYNC_TOPIC\n" +
                "1,760,840\t0\t0.0000%\tL S\t79.0\t0.0\t1.0\t1.0\t1.0\t1.1\t2.5\n" +
                "[:: show ::]\t\n" +
                "YPP-PROFILE-LIVE\n" +
                "11,040,254\t0\t0.0000%\tL S\t100.0\t0.0\t1.0\t1.0\t1.0\t1.0\t16.0\n" +
                "[:: show ::]\t\n" +
                "bigdata_es_kafka\n" +
                "429,338,997\t0\t0.0000%\tL S\t155.0\t0.0\t1.0\t1.0\t1.0\t1.0\t621.2\n" +
                "[:: show ::]\t\n" +
                "bigdata_biggie_batch_kafka\n" +
                "2,078,107\t0\t0.0000%\tL S\t65.0\t0.0\t1.0\t1.0\t1.0\t1.3\t3.0\n" +
                "[:: show ::]\t\n" +
                "bigdata_rec_im_time_stamp\n" +
                "10,388,960\t0\t0.0000%\tL S\t70.0\t0.0\t1.0\t1.0\t1.0\t1.0\t15.0\n" +
                "[:: show ::]\t\n" +
                "bigdata_aitm.profile_relation\n" +
                "914,142\t0\t0.0000%\tL S\t42.0\t0.0\t1.0\t1.0\t1.0\t1.6\t1.3\n" +
                "[:: show ::]\t\n" +
                "BIXIN_TIMELINE_DECENTRALIZATION_EXPOSURE_PROCESS\n" +
                "1,685,689\t0\t0.0000%\tL S\t75.0\t0.0\t1.0\t1.0\t1.0\t1.2\t2.4\n" +
                "[:: show ::]\t\n" +
                "bigdata_tablestore_write\n" +
                "24,203,564\t0\t0.0000%\tL S\t113.0\t0.0\t1.0\t1.0\t1.0\t1.0\t35.0\n" +
                "[:: show ::]\t\n" +
                "BIGDATA-YUER-SOUND\n" +
                "4,268,220\t0\t0.0000%\tL S\t80.0\t0.0\t1.0\t1.0\t1.0\t1.1\t6.2\n" +
                "[:: show ::]\t\n" +
                "YPP-REALTIME-FEATURE-DYNAMIC\n" +
                "9,156,310\t0\t0.0000%\tL S\t85.0\t0.0\t1.0\t1.0\t1.0\t1.0\t13.2\n" +
                "[:: show ::]\t\n" +
                "customer_service_robot\n" +
                "3,297,597\t0\t0.0000%\tL S\t65.0\t0.0\t1.0\t1.0\t1.0\t1.0\t4.8\n" +
                "[:: show ::]\t\n" +
                "bigdata_biggie_kafka\n" +
                "2,159,940\t0\t0.0000%\tL S\t70.0\t0.0\t1.0\t1.0\t1.0\t1.7\t3.1\n" +
                "[:: show ::]\t\n" +
                "YPP-REALTIME-FEATURE-LIVEROOM\n" +
                "316,001,671\t0\t0.0000%\tL S\t115.0\t0.0\t1.0\t1.0\t1.0\t1.0\t457.2\n" +
                "[:: show ::]\t\n" +
                "YPP-REALTIME-FEATURE-CHATROOM\n" +
                "19,659,365\t0\t0.0000%\tL S\t110.0\t0.0\t1.0\t1.0\t1.0\t1.0\t28.4\n" +
                "[:: show ::]\t\n" +
                "bigdata_yuer_timeline\n" +
                "1,317,167\t0\t0.0000%\tL S\t45.0\t0.0\t1.0\t1.0\t1.0\t1.1\t1.9\n" +
                "[:: show ::]\t\n" +
                "bigdata_gd_relation\n" +
                "914,142\t0\t0.0000%\tL S\t60.0\t0.0\t1.0\t1.0\t1.0\t1.6\t1.3\n" +
                "[:: show ::]\t\n" +
                "YPP-REALTIME-FEATURE-YUERVOICE\n" +
                "58,243\t0\t0.0000%\tL S\t12.0\t0.0\t1.0\t1.0\t1.1\t1.1\t0.1\n" +
                "[:: show ::]\t\n" +
                "bigdata_gd_user\n" +
                "36,981,929\t0\t0.0000%\tL S\t85.0\t0.0\t1.0\t1.0\t1.0\t1.0\t53.5\n" +
                "[:: show ::]\t\n" +
                "YPP-PROFILE-USER\n" +
                "26,137,209\t0\t0.0000%\tL S\t165.0\t0.0\t1.0\t1.0\t1.0\t1.0\t37.8\n" +
                "[:: show ::]\t\n" +
                "bigdata_gd_god\n" +
                "9,095,966\t0\t0.0000%\tL S\t72.0\t0.0\t1.0\t1.0\t1.0\t1.0\t13.2\n" +
                "[:: show ::]\t\n" +
                "bigdata_aitm.profile_kv_put\n" +
                "11,731,488\t0\t0.0000%\tL S\t58.0\t0.0\t1.0\t1.0\t1.0\t1.0\t17.0\n" +
                "[:: show ::]\t\n" +
                "bigdata_biggie_kafka_new\n" +
                "11,234,801\t0\t0.0000%\tL S\t75.0\t0.0\t1.0\t1.0\t1.0\t1.0\t16.3\n" +
                "[:: show ::]\t\n" +
                "user-gdb-node\n" +
                "12,450,974\t0\t0.0000%\tL S\t100.0\t0.0\t1.0\t1.0\t1.0\t1.0\t18.0\n" +
                "[:: show ::]\t\n" +
                "YPP-REALTIME-FEATURE\n" +
                "23,108,788\t0\t0.0000%\tL S\t83.0\t0.0\t1.0\t1.0\t1.0\t1.0\t33.4\n" +
                "[:: show ::]\t\n" +
                "YPP-REALTIME-FEATURE-ORDER\n" +
                "20,712,124\t0\t0.0000%\tL S\t95.0\t0.0\t1.0\t1.0\t1.0\t1.0\t30.0\n" +
                "[:: show ::]\t\n" +
                "bigdata_gd_play_order\n" +
                "2,622\t0\t0.0000%\tL S\t1.0\t0.0\t1.0\t1.0\t1.0\t1.0\t0.0\n" +
                "[:: show ::]\t\n" +
                "bigdata_user_kafka\n" +
                "4,037,167\t0\t0.0000%\tL S\t80.0\t0.0\t1.0\t1.0\t1.0\t2.5\t5.8\n" +
                "[:: show ::]\t\n" +
                "BIGDATA_OPENAI_PLATFORM_INPUT\n" +
                "55,216\t0\t0.0000%\tL S\t19.0\t0.0\t1.0\t1.1\t1.1\t1.1\t0.1\n" +
                "[:: show ::]\t\n" +
                "YPP-REALTIME-FEATURE-GAME\n" +
                "1,294,871\t0\t0.0000%\tL S\t50.0\t0.0\t1.0\t1.0\t1.0\t1.1\t1.9\n" +
                "[:: show ::]\t\n" +
                "aitm_history_to_es\n" +
                "32,238\t0\t0";

        String s2 = "bigdata_xxq_rec_party_user\n" +
                "3\t0\t0.0000%\tL S\t6.0\t4.3\t4.3\t4.3\t4.3\t4.3\t0.0\n" +
                "[:: show ::]\t\n" +
                "bigdata_biggie_date_play\n" +
                "2\t0\t0.0000%\tL S\t6.0\t3.5\t3.5\t3.5\t3.5\t3.5\t0.0\n" +
                "[:: show ::]\t\n" +
                "YPP-PROFILE-ORDER\n" +
                "455\t0\t0.0000%\tL S\t20.0\t1.9\t2.6\t2.6\t2.6\t2.6\t0.0\n" +
                "[:: show ::]\t\n" +
                "YPP-PROFILE-GAME\n" +
                "5,251\t0\t0.0000%\tL S\t35.0\t1.1\t3.2\t3.2\t3.2\t3.2\t0.0\n" +
                "[:: show ::]\t\n" +
                "bigdata_user_alias\n" +
                "23,715\t0\t0.0000%\tL S\t30.0\t0.4\t2.5\t2.7\t2.7\t2.7\t0.0\n" +
                "[:: show ::]\t\n" +
                "bigdata_es_update_kafka\n" +
                "32,052\t0\t0.0000%\tL S\t40.0\t0.2\t2.1\t2.3\t2.3\t2.3\t0.0\n" +
                "[:: show ::]\t\n" +
                "bxgame_player_list\n" +
                "89,664\t0\t0.0000%\tL S\t30.0\t0.1\t1.4\t1.6\t1.6\t1.6\t0.0\n" +
                "[:: show ::]\t\n" +
                "HOMEPAGE_GAME_CARD_REC\n" +
                "31,326\t0\t0.0000%\tL S\t20.0\t0.1\t1.3\t1.6\t2.2\t2.2\t0.0\n" +
                "[:: show ::]\t\n" +
                "bigdata_biggie_complain_punish\n" +
                "143,236\t0\t0.0000%\tL S\t25.0\t0.0\t1.1\t1.3\t1.4\t1.5\t0.1\n" +
                "[:: show ::]\t\n" +
                "YPP-REALTIME-FEATURE-USER\n" +
                "295,976,155\t0\t0.0000%\tL S\t120.0\t0.0\t1.0\t1.0\t1.0\t1.0\t110.5\n" +
                "[:: show ::]\t\n" +
                "recommend_content_original_liveroom\n" +
                "25,301,133\t0\t0.0000%\tL S\t114.0\t0.0\t1.0\t1.0\t1.0\t1.0\t9.4\n" +
                "[:: show ::]\t\n" +
                "YPP-REALTIME-FEATURE-SLIDING\n" +
                "6,989,079\t0\t0.0000%\tL S\t65.0\t0.0\t1.0\t1.0\t1.0\t1.0\t2.6\n" +
                "[:: show ::]\t\n" +
                "CHATROOM_FEATURE_SYNC_TOPIC\n" +
                "10,556,018\t0\t0.0000%\tL S\t75.0\t0.0\t1.0\t1.0\t1.0\t1.0\t3.9\n" +
                "[:: show ::]\t\n" +
                "YPP-PROFILE-LIVE\n" +
                "47,609,349\t0\t0.0000%\tL S\t100.0\t0.0\t1.0\t1.0\t1.0\t1.0\t17.8\n" +
                "[:: show ::]\t\n" +
                "bigdata_es_kafka\n" +
                "1,822,029,826\t0\t0.0000%\tL S\t135.0\t0.0\t1.0\t1.0\t1.0\t1.0\t680.3\n" +
                "[:: show ::]\t\n" +
                "bigdata_biggie_batch_kafka\n" +
                "8,933,967\t0\t0.0000%\tL S\t65.0\t0.0\t1.0\t1.0\t1.0\t1.2\t3.3\n" +
                "[:: show ::]\t\n" +
                "bigdata_rec_im_time_stamp\n" +
                "60,279,483\t0\t0.0000%\tL S\t136.0\t0.0\t1.0\t1.0\t1.0\t1.0\t22.5\n" +
                "[:: show ::]\t\n" +
                "bigdata_aitm.profile_relation\n" +
                "4,470,264\t0\t0.0000%\tL S\t239.0\t0.0\t1.0\t1.0\t1.0\t1.8\t1.7\n" +
                "[:: show ::]\t\n" +
                "BIXIN_TIMELINE_DECENTRALIZATION_EXPOSURE_PROCESS\n" +
                "9,557,335\t0\t0.0000%\tL S\t70.0\t0.0\t1.0\t1.0\t1.0\t1.1\t3.6\n" +
                "[:: show ::]\t\n" +
                "BIGDATA-YUER-SOUND\n" +
                "20,303,101\t0\t0.0000%\tL S\t55.0\t0.0\t1.0\t1.0\t1.0\t1.0\t7.6\n" +
                "[:: show ::]\t\n" +
                "bigdata_tablestore_write\n" +
                "104,347,637\t0\t0.0000%\tL S\t220.0\t0.0\t1.0\t1.0\t1.0\t1.0\t39.0\n" +
                "[:: show ::]\t\n" +
                "YPP-REALTIME-FEATURE-DYNAMIC\n" +
                "55,543,908\t0\t0.0000%\tL S\t120.0\t0.0\t1.0\t1.0\t1.0\t1.0\t20.7\n" +
                "[:: show ::]\t\n" +
                "customer_service_robot\n" +
                "14,393,699\t0\t0.0000%\tL S\t80.0\t0.0\t1.0\t1.0\t1.0\t1.0\t5.4\n" +
                "[:: show ::]\t\n" +
                "bigdata_biggie_kafka\n" +
                "8,465,830\t0\t0.0000%\tL S\t75.0\t0.0\t1.0\t1.0\t1.0\t1.3\t3.2\n" +
                "[:: show ::]\t\n" +
                "YPP-REALTIME-FEATURE-LIVEROOM\n" +
                "1,300,561,552\t0\t0.0000%\tL S\t190.0\t0.0\t1.0\t1.0\t1.0\t1.0\t485.6\n" +
                "[:: show ::]\t\n" +
                "YPP-REALTIME-FEATURE-CHATROOM\n" +
                "82,431,656\t0\t0.0000%\tL S\t130.0\t0.0\t1.0\t1.0\t1.0\t1.0\t30.8\n" +
                "[:: show ::]\t\n" +
                "bigdata_yuer_timeline\n" +
                "7,514,551\t0\t0.0000%\tL S\t95.0\t0.0\t1.0\t1.0\t1.0\t1.1\t2.8\n" +
                "[:: show ::]\t\n" +
                "YPP-REALTIME-FEATURE-YUERVOICE\n" +
                "304,533\t0\t0.0000%\tL S\t40.0\t0.0\t1.0\t1.0\t1.1\t1.2\t0.1\n" +
                "[:: show ::]\t\n" +
                "bigdata_gd_relation\n" +
                "4,470,264\t0\t0.0000%\tL S\t152.0\t0.0\t1.0\t1.0\t1.0\t1.6\t1.7\n" +
                "[:: show ::]\t\n" +
                "bigdata_gd_user\n" +
                "186,212,575\t0\t0.0000%\tL S\t85.0\t0.0\t1.0\t1.0\t1.0\t1.0\t69.5\n" +
                "[:: show ::]\t\n" +
                "YPP-PROFILE-USER\n" +
                "131,821,840\t0\t0.0000%\tL S\t175.0\t0.0\t1.0\t1.0\t1.0\t1.0\t49.2\n" +
                "[:: show ::]\t\n" +
                "bigdata_gd_god\n" +
                "37,831,279\t0\t0.0000%\tL S\t80.0\t0.0\t1.0\t1.0\t1.0\t1.0\t14.1\n" +
                "[:: show ::]\t\n" +
                "bigdata_dubbing_show\n" +
                "195,018\t0\t0.0000%\tL S\t25.0\t0.0\t1.0\t1.1\t1.1\t1.1\t0.1\n" +
                "[:: show ::]\t\n" +
                "bigdata_aitm.profile_kv_put\n" +
                "50,602,266\t0\t0.0000%\tL S\t72.0\t0.0\t1.0\t1.0\t1.0\t1.0\t18.9\n" +
                "[:: show ::]\t\n" +
                "bigdata_biggie_kafka_new\n" +
                "46,377,415\t0\t0.0000%\tL S\t220.0\t0.0\t1.0\t1.0\t1.0\t1.0\t17.3\n" +
                "[:: show ::]\t\n" +
                "user-gdb-node\n" +
                "56,167,262\t0\t0.0000%\tL S\t95.0\t0.0\t1.0\t1.0\t1.0\t1.0\t21.0\n" +
                "[:: show ::]\t\n" +
                "YPP-REALTIME-FEATURE\n" +
                "104,923,873\t0\t0.0000%\tL S\t120.0\t0.0\t1.0\t1.0\t1.0\t1.0\t39.2\n" +
                "[:: show ::]\t\n" +
                "YPP-REALTIME-FEATURE-ORDER\n" +
                "130,927,851\t0\t0.0000%\tL S\t240.0\t0.0\t1.0\t1.0\t1.0\t1.0\t48.9\n" +
                "[:: show ::]\t\n" +
                "bigdata_gd_play_order\n" +
                "2,027\t0\t0.0000%\tL S\t1.0\t0.0\t1.0\t1.0\t1.0\t1.0\t0.0\n" +
                "[:: show ::]\t\n" +
                "YPP-REALTIME-FEATURE-GAME\n" +
                "8,828,196\t0\t0.0000%\tL S\t105.0\t0.0\t1.0\t1.0\t1.0\t1.1\t3.3\n" +
                "[:: show ::]\t\n" +
                "bigdata_user_kafka\n" +
                "17,647,056\t0\t0.0000%\tL S\t110.0\t0.0\t1.0\t1.0\t1.0\t2.0\t6.6\n" +
                "[:: show ::]\t\n" +
                "BIGDATA_OPENAI_PLATFORM_INPUT\n" +
                "234,773\t0\t0.0000%\tL S\t104.0\t0.0\t1.0\t1.1\t1.1\t1.1\t0.1\n" +
                "[:: show ::]\t\n" +
                "aitm_history_to_es\n" +
                "194,814\t0\t0.0";

        Set<String> set = new LinkedHashSet<>();
        String[] split = s.split("\n");
        String[] split2 = s2.split("\n");
        for (int i = 0; i < split.length; i++) {
            if(i%3==0){
                set.add(split[i].split(":")[0]);
            }
        }
        for (int i = 0; i < split2.length; i++) {
            if(i%3==0){
                set.add(split2[i].split(":")[0]);
            }
        }
        for (String s1 : set) {
            System.out.println(s1);
        }
    }
}
