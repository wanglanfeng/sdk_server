package com.dome.sdkserver.metadata.entity.qbao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PayTransEntity implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** 支付交易id */
	private Long payTransId;
	/** 登录账户 */
	private String loginName;
	/** 商户交易流水号 */
	private String bizCode;
	/** 支付交易流水号 */
	private String payTransCode;
	/** 支付渠道 */
	private Integer appSource;
	/** 交易类型 */
	private String transType;
	/** 商户编码 */
	private String appCode;
	/** 会员系统用户ID */
	private Integer merchantUserId;
	/** 计费点编码 **/
	private String chargingPointCode;
	/** 交易金额 */
	private BigDecimal transAmount;
	/** 版本号 */
	private String versionCode;
	/** 交易日期 */
	private String transDate;
	/** 交易时间 */
	private String transTime;
	/** 交易简介 */
	private String transIntro;
	/** 服务器异步通知服务 */
	private String callbackUrl;
	/** 交易关闭时间 */
	private Date transCloseTime;
	/** 签名类型 **/
	private String signType;
	/** 签名信息 */
	private String signCode;
	/** 账户返回码 */
	private String bbResCode;
	/** 账户返回信息 */
	private String bbResMsg;
	/** 支付用户ID */
	private Long payUserId;
	/** 钱宝币账户支付费用 */
	private BigDecimal accountAmount;
	/** 宝券账户支付费用 */
	private BigDecimal bqAccountAmount;
	/** 账户流水号 */
	private String accountFlowId;
	/** 账户子流水号 */
	private String accountChildflowId;
	/** 宝券返回码 **/
	private String bqResCode;
	/** 宝券返回信息 **/
	private String bqResMsg;
	/** 宝券返回流水 **/
	private String bqBizId;
	/** 状态 */
	private String status;
	/** 备注 */
	private String remark;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;

	private String channelCode;
	// --------------------------------
	/** 会员等级 */
	private String memberLevel;

    //冰趣UserId
    private String bqUserId;
    //支付来源:pc,wap,app
    private String payOrigin;
    //扩展属性
    private String extraField;
    //扩展属性2
    private String extraField2;
    /** 手续费 */
    private BigDecimal feeAmount;
    //支付方式(0：组合支付，1：人民币，2：宝券)
    private Integer payType;
    //商肃支付返回流水号
    private String payTradeNo;
    //商肃支付返回码
    private String resCode;
    //商肃支付返回信息
    private String resMsg;
    //系统类型:IOS|AD|PC|WAP
    private String sysType;
    //cp响应状态,0:未通知cp，1:cp响应成功,2:cp响应失败
    private Integer callbackStatus = 0;
    //游戏角色id
    private String roleId;
    //游戏区服Id
    private String zoneId;
    //游戏区服Name
    private String zoneName;

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public Integer getCallbackStatus() {
        return callbackStatus;
    }

    public void setCallbackStatus(Integer callbackStatus) {
        this.callbackStatus = callbackStatus;
    }

	public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	public Long getPayTransId() {
		return payTransId;
	}

	public void setPayTransId(Long payTransId) {
		this.payTransId = payTransId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPayTransCode() {
		return payTransCode;
	}

	public void setPayTransCode(String payTransCode) {
		this.payTransCode = payTransCode;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Integer getMerchantUserId() {
		return merchantUserId;
	}

	public void setMerchantUserId(Integer merchantUserId) {
		this.merchantUserId = merchantUserId;
	}

	public BigDecimal getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getTransIntro() {
		return transIntro;
	}

	public void setTransIntro(String transIntro) {
		this.transIntro = transIntro;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public Date getTransCloseTime() {
		return transCloseTime;
	}

	public void setTransCloseTime(Date transCloseTime) {
		this.transCloseTime = transCloseTime;
	}

	public String getSignCode() {
		return signCode;
	}

	public void setSignCode(String signCode) {
		this.signCode = signCode;
	}

	public Long getPayUserId() {
		return payUserId;
	}

	public void setPayUserId(Long payUserId) {
		this.payUserId = payUserId;
	}

	public BigDecimal getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(BigDecimal accountAmount) {
		this.accountAmount = accountAmount;
	}

	public BigDecimal getBqAccountAmount() {
		return bqAccountAmount;
	}

	public void setBqAccountAmount(BigDecimal bqAccountAmount) {
		this.bqAccountAmount = bqAccountAmount;
	}

	public String getAccountFlowId() {
		return accountFlowId;
	}

	public void setAccountFlowId(String accountFlowId) {
		this.accountFlowId = accountFlowId;
	}

	public String getAccountChildflowId() {
		return accountChildflowId;
	}

	public void setAccountChildflowId(String accountChildflowId) {
		this.accountChildflowId = accountChildflowId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getAppSource() {
		return appSource;
	}

	public void setAppSource(Integer appSource) {
		this.appSource = appSource;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getChargingPointCode() {
		return chargingPointCode;
	}

	public void setChargingPointCode(String chargingPointCode) {
		this.chargingPointCode = chargingPointCode;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getBbResCode() {
		return bbResCode;
	}

	public void setBbResCode(String bbResCode) {
		this.bbResCode = bbResCode;
	}

	public String getBbResMsg() {
		return bbResMsg;
	}

	public void setBbResMsg(String bbResMsg) {
		this.bbResMsg = bbResMsg;
	}

	public String getBqResCode() {
		return bqResCode;
	}

	public void setBqResCode(String bqResCode) {
		this.bqResCode = bqResCode;
	}

	public String getBqResMsg() {
		return bqResMsg;
	}

	public void setBqResMsg(String bqResMsg) {
		this.bqResMsg = bqResMsg;
	}

	public String getBqBizId() {
		return bqBizId;
	}

	public void setBqBizId(String bqBizId) {
		this.bqBizId = bqBizId;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

    public String getBqUserId() {
        return bqUserId;
    }

    public void setBqUserId(String bqUserId) {
        this.bqUserId = bqUserId;
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

    public String getExtraField2() {
        return extraField2;
    }

    public void setExtraField2(String extraField2) {
        this.extraField2 = extraField2;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPayTradeNo() {
        return payTradeNo;
    }

    public void setPayTradeNo(String payTradeNo) {
        this.payTradeNo = payTradeNo;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }
}
