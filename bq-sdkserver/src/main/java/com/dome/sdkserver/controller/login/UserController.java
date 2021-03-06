package com.dome.sdkserver.controller.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.enumeration.ErrorCodeEnum;
import com.dome.sdkserver.bq.enumeration.SysTypeEnum;
import com.dome.sdkserver.bq.login.domain.user.User;
import com.dome.sdkserver.bq.util.IPUtil;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.controller.BaseController;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.service.BqSdkConstants;
import com.dome.sdkserver.service.login.LoginResultNotifyService;
import com.dome.sdkserver.service.login.UserLoginService;
import com.dome.sdkserver.service.order.OrderService;
import com.dome.sdkserver.service.redis.RedisService;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.RSACoder;
import com.dome.sdkserver.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Controller
@RequestMapping("/bqsdkuser/")
public class UserController extends BaseController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PropertiesUtil domainConfig;

    // 国内
    private static String regMobile = "^1(3[0-9]|4[0-9]|5[0-9]|8[0-9]|7[0-9])\\d{8}$";

    // private static String regMobile =
    // "^[1][3-8]\\d{9}$|^([6|9])\\d{7}$|^[0][9]\\d{8}$|^[6]([8|6])\\d{5}$";

    // 港澳手机正则
    private static String otherMobile = "^[0-9]*$";

    private static String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    private static String regIP = "^" + num + "\\." + num + "\\." + num + "\\." + num + "$";

    private static String regGmail = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

    @Value("${rsa_private_key_4_sdk}")
    private String RSA_PRIVATE_KEY_4_SDK;

    @Autowired
    private LoginResultNotifyService loginResultNotifyService;

    @RequestMapping(value = "getSmsCode")
    @ResponseBody
    public SdkOauthResult getSmsCode(String appCode, String mobile, HttpServletRequest request, String bizType) {
        log.info(">>>>>>>>sdk获取用户注册验证码");

        try {
            // 国别码非必填
            String countryCode = request.getParameter("countryCode");
            log.info("传入的国别码为：{}", countryCode);
            if (StringUtils.isBlank(countryCode)) {
                countryCode = "86";
            }
            if (!checkMobile(mobile, countryCode) && !checkGmail(mobile)) {
                log.error(">>>>>>>>邮箱或手机格式错误:");
                return SdkOauthResult.failed(ErrorCodeEnum.邮箱或手机格式不对.code, ErrorCodeEnum.邮箱或手机格式不对.name());
            }

            String ip = IPUtil.getIpAddr(request);
            if (!checkIp(ip)) {
                log.error(">>>>>>>>客户端ip格式错误 IP = " + ip);
                return SdkOauthResult.failed(ErrorCodeEnum.客户端IP格式错误.code, ErrorCodeEnum.客户端IP格式错误.name());
            }

            if (StringUtils.isBlank(bizType)) {
                log.error(">>>>>>>>bizType错不能为空");
                return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
            }

            SdkOauthResult result = checkClient(appCode);
            if (!result.isSuccess()) {
                return result;
            }
            JSONObject response = null;
            String buId = request.getHeader("buId");
            // 海外渠道CHA000004
            String channel = request.getHeader("channel");

            if (checkMobile(mobile, countryCode)) {
                response = userLoginService.getRegisterCode(mobile, appCode, ip, bizType, buId, countryCode);
            } else if (checkGmail(mobile)) {
                response = userLoginService.getGmailCode(mobile, appCode, ip, bizType, buId, channel);
            }
            return parserUcResponse(response);

        } catch (Exception e) {
            log.error(">>>>>>>>获取注册码失败:", e);
            return SdkOauthResult.failed(ErrorCodeEnum.非预期错误.code, ErrorCodeEnum.非预期错误.name());
        }

    }

    /**
     * 用户注册
     * <p/>
     * 应用app编码
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "registerUser")
    @ResponseBody
    public SdkOauthResult registerUser(HttpServletRequest request, User user) {
        AppInfoEntity clientDetails = null;
        try {
            String validResult = this.dataValid(user); // 校验提交数据
            if (StringUtils.isNotEmpty(validResult)) {
                return SdkOauthResult.failed(validResult);
            }
            String buId = request.getHeader("buId");
            if (StringUtils.isNotBlank(buId)) {
                user.setBuId(buId);
            }
            SysTypeEnum sysTypeEnum = SysTypeEnum.getSysType(request.getHeader("devType"));
            user.setSysType(sysTypeEnum != null ? sysTypeEnum.name() : SysTypeEnum.AD.name()); //系统类型
            String channelCode = StringUtils.isBlank(request.getParameter("channelId")) ? request.getHeader("channel") : request.getParameter("channelId");
            log.info("getParameter-channelCode:{},getHeader-channelCode:{},channelCode:{}", request.getParameter("channelId"), request.getHeader("channel"), channelCode);
            user.setChannelCode(channelCode);
            if (!checkMobile(user.getLoginName(), user.getCountryCode()) && !checkGmail(user.getLoginName())) {
                return SdkOauthResult.failed(ErrorCodeEnum.无效的用户名.code, ErrorCodeEnum.无效的用户名.name());
            }
            SdkOauthResult result = checkClient(request.getParameter("appCode"));
            if (!result.isSuccess()) {
                return result;
            } else {
                clientDetails = (AppInfoEntity) result.getData();
            }
            String srcPassword = new String(
                    RSACoder.decryptByPrivateKey(RSACoder.decryptBASE64(user.getPassword()), RSA_PRIVATE_KEY_4_SDK));
            user.setPassword(srcPassword);

            JSONObject response = null;
            if (checkMobile(user.getLoginName(), user.getCountryCode())) {
                response = userLoginService.registerUser(user, clientDetails.getAppCode(), user.getRegisterCode(), user.getCountryCode());
            } else if (checkGmail(user.getLoginName())) {
                response = userLoginService.gmailUser(user, clientDetails.getAppCode(), user.getRegisterCode());
            }
            result = parserUcResponse(response);
            // 增加注册回调(海外有传buId)
            if (response != null) {
                if (response.getJSONObject("data") != null && (StringUtils.isEmpty(buId) || !("DOME005").equals(buId))) {
                    registerCallback(response, request, clientDetails, user);
                }
            }
            return result;
        } catch (Exception e) {
            log.error(">>>>>>>>非预期错误", e);
            return SdkOauthResult.failed(ErrorCodeEnum.非预期错误.code, ErrorCodeEnum.非预期错误.name());
        }

    }

    @RequestMapping(value = "verifySmsCode")
    @ResponseBody
    public SdkOauthResult verifySmsCode(String appCode, String mobile, HttpServletRequest request, String bizType) {
        log.info(">>>>>>>>sdk验证smscode");

        try {
            // 国别码
            String countryCode = request.getParameter("countryCode");
            log.info("传入的国别码为：{}", countryCode);
            if (StringUtils.isBlank(countryCode)) {
                countryCode = "86";
            }
            if (!checkMobile(mobile, countryCode) && !checkGmail(mobile)) {
                log.error(">>>>>>>>手机号码或邮箱格式错误:");
                return SdkOauthResult.failed(ErrorCodeEnum.邮箱或手机格式不对.code, ErrorCodeEnum.邮箱或手机格式不对.name());
            }

            String ip = IPUtil.getIpAddr(request);
            if (!checkIp(ip)) {
                log.error(">>>>>>>>客户端ip格式错误 IP = " + ip);
                return SdkOauthResult.failed(ErrorCodeEnum.客户端IP格式错误.code, ErrorCodeEnum.客户端IP格式错误.name());
            }

            if (StringUtils.isBlank(bizType)) {
                log.error(">>>>>>>>bizType错不能为空");
                return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
            }

            String registerCode = request.getParameter("smsCode");
            if (StringUtils.isBlank(registerCode)) {
                log.error(">>>>>>>>注册码不能为空");
                return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
            }

            SdkOauthResult result = checkClient(appCode);
            if (!result.isSuccess()) {
                return result;
            }

            JSONObject response = null;
            String buId = request.getHeader("buId");
            if (checkMobile(mobile, countryCode)) {
                response = userLoginService.verifySmsCode(mobile, appCode, registerCode, ip, bizType, buId,
                        countryCode);
            } else if (checkGmail(mobile)) {
                response = userLoginService.verfyGmailCode(mobile, appCode, registerCode, ip, bizType, buId);
            }
            return parserUcResponse(response);

        } catch (Exception e) {
            log.error(">>>>>>>>验证注册码失败:", e);
            return SdkOauthResult.failed(ErrorCodeEnum.非预期错误, ErrorCodeEnum.非预期错误.name());
        }
    }

    /**
     * 重置密码
     *
     * @param appCode 应用app编码
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "resetPassword")
    @ResponseBody
    public SdkOauthResult resetPassword(String appCode, HttpServletRequest request, User user, String smsCode,
                                        String smsToken) {
        try {
            String loginName = user.getLoginName();
            String password = user.getPassword();
            String buId = request.getHeader("buId");
            // 国别码
            String countryCode = request.getParameter("countryCode");
            log.info("传入的国别码为：{}", countryCode);
            if (StringUtils.isBlank(countryCode)) {
                countryCode = "86";
            }
            if (StringUtils.isNotBlank(buId)) {
                log.info(">>>>>>>>>>>buId:" + buId);
                user.setBuId(buId);
            }
            if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
                log.error(">>>>>>>>>有必填参数为空");
                return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
            }

            if (!checkMobile(loginName, countryCode) && !checkGmail(loginName)) {
                log.error(">>>>>>>>手机号码或邮箱格式错误:");
                return SdkOauthResult.failed(ErrorCodeEnum.邮箱或手机格式不对.code, ErrorCodeEnum.邮箱或手机格式不对.name());
            }

            SdkOauthResult result = checkClient(appCode);
            if (!result.isSuccess()) {
                return result;
            }

            String srcPassword = new String(
                    RSACoder.decryptByPrivateKey(RSACoder.decryptBASE64(password), RSA_PRIVATE_KEY_4_SDK));
            user.setPassword(srcPassword);

            JSONObject response = null;
            if (checkMobile(loginName, countryCode)) {
                response = userLoginService.resetPassword(user, appCode, smsCode, smsToken, countryCode);
            } else if (checkGmail(loginName)) {
                response = userLoginService.gmailResetPassword(user, appCode, smsCode, smsToken);
            }

            result = parserUcResponse(response);

            return result;
        } catch (Exception e) {
            log.error(">>>>>>>>非预期错误", e);
            return SdkOauthResult.failed(ErrorCodeEnum.非预期错误.code, ErrorCodeEnum.非预期错误.name());
        }

    }

    // /**
    // * 获取授权code接口
    // *
    // * @param appCode
    // * 应用app编码
    // * @return 授权code
    // */
    // @RequestMapping(value = "modifyUser")
    // @ResponseBody
    // public SdkOauthResult modifyUser(String appCode, HttpServletRequest
    // request, User user) {
    // return SdkOauthResult.success();
    // }

    /**
     * 获取授权code接口
     *
     * @return 授权code
     * @throws SQLException
     */
    @RequestMapping(value = "executeSql")
    @ResponseBody
    public void execute() throws SQLException {
        orderService.createTable();
    }

    private boolean checkMobile(String mobile, String countryCode) {
        if (StringUtils.isBlank(mobile)) {
            log.info("登录名不能为空：{}", mobile);
            return false;
        }
        if (mobile.matches(regMobile) && ("86").equals(countryCode)) {
            log.info("国内sdk渠道countryCode：{}", countryCode);
            return true;
        } else if (mobile.matches(otherMobile) && !("86").equals(countryCode)) {
            log.info("海外sdk渠道countryCode：{}", countryCode);
            return true;
        }
        return false;
    }

    private boolean checkGmail(String mobile) {
        if (mobile.matches(regGmail)) {
            return true;
        }
        return false;
    }

    private boolean checkIp(String ip) {
        if (ip.matches(regIP)) {
            return true;
        }
        return false;
    }

    /**
     * 修改密码
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "modifyPassword")
    @ResponseBody
    public SdkOauthResult modifyPassword(HttpServletRequest request) {
        JSONObject response = null;
        try {
            String accessToken = request.getParameter("accessToken");
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String appCode = request.getParameter("appCode");
            String buId = request.getHeader("buId");

            log.info("修改密码传来的参数信息为：{}", JSON.toJSONString(request.getParameterMap()));
            if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(oldPassword)
                    || StringUtils.isBlank(newPassword)) {
                log.error(">>>>>>>>>有必填参数为空");
                return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
            }

            SdkOauthResult result = checkClient(appCode);
            if (!result.isSuccess()) {
                return result;
            }

            String srcOldPassword = new String(
                    RSACoder.decryptByPrivateKey(RSACoder.decryptBASE64(oldPassword), RSA_PRIVATE_KEY_4_SDK));
            log.info("解密后的srcOldPassword：{}", srcOldPassword);

            String srcNewPassword = new String(
                    RSACoder.decryptByPrivateKey(RSACoder.decryptBASE64(newPassword), RSA_PRIVATE_KEY_4_SDK));
            log.info("解密后的srcNewPassword：{}", srcNewPassword);

            response = userLoginService.modifyUser(accessToken, srcOldPassword, srcNewPassword, appCode, buId);

            result = parserUcResponse(response);

            return result;
        } catch (Exception e) {
            log.error(">>>>>>>>非预期错误", e);
            return SdkOauthResult.failed(ErrorCodeEnum.非预期错误.code, ErrorCodeEnum.非预期错误.name());
        }

    }

    private void registerCallback(JSONObject jsonObject, HttpServletRequest request, AppInfoEntity clientDetails,
                                  User user) {
        JSONObject data = jsonObject.getJSONObject("data");
        log.info("注册响应信息data：{}", data);
        String appCode = request.getParameter("appCode");
        user.setUserId(data.getString(BqSdkConstants.domeUserId));
        user.setLoginName(request.getParameter("loginName"));
        String loginNotifyUrl = null;
        // sdk线上环境,默认是线上环境
        if ("1".equals(domainConfig.getString("sdk.notify.environment", "1"))) {
            loginNotifyUrl = clientDetails.getRegistCallBackUrl();
        } else {
            loginNotifyUrl = clientDetails.getTestRegistCallBackUrl();
        }
        log.info("注册回调地址为：{}", loginNotifyUrl);
        if (StringUtils.isNotBlank(loginNotifyUrl)) {
            loginResultNotifyService.startNotify(data.getString(BqSdkConstants.domeUserId), appCode, loginNotifyUrl);
        }
    }
}
