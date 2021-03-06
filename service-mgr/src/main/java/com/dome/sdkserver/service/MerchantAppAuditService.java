package com.dome.sdkserver.service;

import java.util.List;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.bo.SearchMerchantAppBo;
import com.dome.sdkserver.metadata.entity.AppVo;

/**
 * 商户应用审核
 * @author hexiaoyi
 */
public interface MerchantAppAuditService {
	
	/**
	 * 根据查询条件得到应用列表
	 * @param conditionMap
	 * @return
	 */
	Integer getAppInfoCountByCondition(SearchMerchantAppBo searchMerchantAppBo);
	
	/**
	 * 根据查询条件得到应用列表
	 * @param conditionMap
	 * @return
	 */
	List<AppVo> getAppInfoByCondition(SearchMerchantAppBo searchMerchantAppBo);
	
	public MerchantAppInfo queryApp(String appCode);
	
	public String updateAppStatus(MerchantAppInfo app);
	
	/**
	 * 修改登录和支付回调地址
	 * @param app
	 * @return
	 */
	public int modfiyCallbackUrl(MerchantAppInfo app);
	
	String updateYeyouStatus(MerchantAppInfo app);
	
	String updateH5GameStatus(MerchantAppInfo app);

    public String getAppChannel(String appCode, int status);

    public String syncCallbackUrl (Object obj);

    //开放平台前台申请接入后修改回调及游戏icon
	public String synModify(String appCode);
}
