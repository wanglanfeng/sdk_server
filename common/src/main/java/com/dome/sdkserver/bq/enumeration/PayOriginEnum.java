package com.dome.sdkserver.bq.enumeration;


import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 支付来源
 * PayOriginEnum
 *
 * @author Zhang ShanMin
 * @date 2017/10/14
 * @time 17:46
 */
public enum PayOriginEnum {

    app,//app端
    wap,//wap端
    pc,//pc端
    JBP,//聚宝盆
    FULU,//福禄充值
    RC,//passport充值
    VR,//VR平台充值
    ;


    /**
     * 冰穹内部支付标识
     */
    private final static Set<String> InsidePaySet = new HashSet<String>() {
        {
            add(app.name());
            add(wap.name());
            add(pc.name());
            add(VR.name());
        }
    };

    /**
     * 是不是冰穹内部支付
     * @param payOrigin
     * @return
     */
    public static boolean isInsidePay(String payOrigin) {
        if (StringUtils.isBlank(payOrigin))
            return false;
        return InsidePaySet.contains(payOrigin);
    }
}
