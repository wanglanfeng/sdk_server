package com.dome.sdkserver.service.web.requestEntity;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class HttpRequestOrderInfo {
    //外部订单号
    private String outOrderNo;
    private String orderNo;
    @NotNull(message = "必传参数为空")
    private String appCode;
    private String appName;
    @NotNull(message = "必传参数为空")
    private String buyerId;
    @NotNull(message = "道具码不能为空")
    private String chargePointCode="C0000000";//默认道具编码
    @NotNull(message = "道具金额不能为空")
    private double chargePointAmount;
    @NotNull(message = "道具名不能为空")
    private String chargePointName;
    //1-支付宝，2 -钱宝 3-银联，4-微信
    @NotNull(message = "必传参数为空")
    private int payType;
    private String pay_Type;
    @NotNull(message = "必传参数为空")
    private String channelCode;

    private String gameOrderNo;
    private String gameNotifyUrl;
    private String bwUserId;
    //支付来源:pc,wap,app
    @NotNull(message = "必传参数为空")
    private String payOrigin;
    //扩展属性
    private String extraField;
    //区服Id
    @NotNull(message = "必传参数为空")
    private String zoneId;
    //游戏方拓展字段1
    private String p1;
    //游戏方拓展字段2
    private String p2;
    private String desc;
    //扩展属性2
    private String extraField2;
    //冰趣H5游戏嵌入平台
    private String platformCode;
    @NotNull(message = "必传参数为空")
    private String sign;
    //商品名称
    private String subject;
    //商品的标题/交易标题/订单标题/订单关键字等。
    private String body;
    //交易金额
    private long totalFee;
    //页面跳转同步通知页面路径
    private String returnUrl;
    //服务器异步通知页面路径
    private String notifyUrl;

    private String signCode;
    private String reqIp;
    //响应格式  0:返回kv,1:返回支付宝form表单,默认：0
    private int resFormat = 0;
    //支付宝公用回传参数
    private String extraCommonParam;

    private String authCode;

    private String tradeType;

    //游戏角色id
    private String roleId;

    private Date createTime;
    //充值账户
    @NotNull(message = "充值账户不能为空")
    private String passport;
    //cp游戏服标识
    @NotNull(message = "必传参数为空")
    private String serverId;
    //系统类型:IOS|AD|WEB|WAP
    private String sysType;
    //区服名
    private String zoneName;
    //支付来源
    private String paySources;
    //订单支付年月 201710
    private String payYearMonth;

    public String getPayYearMonth() {
        return payYearMonth;
    }

    public void setPayYearMonth(String payYearMonth) {
        this.payYearMonth = payYearMonth;
    }

    public String getPaySources() {
        return paySources;
    }

    public void setPaySources(String paySources) {
        this.paySources = paySources;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }
    public String getPay_Type() {
        return pay_Type;
    }

    public void setPay_Type(String pay_Type) {
        this.pay_Type = pay_Type;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public double getChargePointAmount() {
        return chargePointAmount;
    }

    public void setChargePointAmount(double chargePointAmount) {
        this.chargePointAmount = chargePointAmount;
    }

    public String getChargePointName() {
        return chargePointName;
    }

    public void setChargePointName(String chargePointName) {
        this.chargePointName = chargePointName;
    }

    public String getChargePointCode() {
        return chargePointCode;
    }

    public void setChargePointCode(String chargePointCode) {
        this.chargePointCode = chargePointCode;
    }

    public String getGameOrderNo() {
        return gameOrderNo;
    }

    public void setGameOrderNo(String gameOrderNo) {
        this.gameOrderNo = gameOrderNo;
    }

    public String getGameNotifyUrl() {
        return gameNotifyUrl;
    }

    public void setGameNotifyUrl(String gameNotifyUrl) {
        this.gameNotifyUrl = gameNotifyUrl;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getBwUserId() {
        return bwUserId;
    }

    public void setBwUserId(String bwUserId) {
        this.bwUserId = bwUserId;
    }

    public String getPayOrigin() {
        return payOrigin;
    }

    public void setPayOrigin(String payOrigin) {
        this.payOrigin = payOrigin;
    }

    public String getExtraField() {
        return extraField;
    }

    public void setExtraField(String extraField) {
        this.extraField = extraField;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExtraField2() {
        return extraField2;
    }

    public void setExtraField2(String extraField2) {
        this.extraField2 = extraField2;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getSignCode() {
        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode;
    }

    public String getReqIp() {
        return reqIp;
    }

    public void setReqIp(String reqIp) {
        this.reqIp = reqIp;
    }

    public int getResFormat() {
        return resFormat;
    }

    public void setResFormat(int resFormat) {
        this.resFormat = resFormat;
    }

    public String getExtraCommonParam() {
        return extraCommonParam;
    }

    public void setExtraCommonParam(String extraCommonParam) {
        this.extraCommonParam = extraCommonParam;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }
}
