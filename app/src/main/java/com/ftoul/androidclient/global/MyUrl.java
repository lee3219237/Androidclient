package com.ftoul.androidclient.global;

/**
 * Created by ftoul106 on 2017/6/8 0008.
 */

public class MyUrl {
    public static boolean tag = true;//加解密开关
    public static boolean md5Tag = false;//md5加盐开关
    //    public static final String SERVICE_ADDRESS = "http://192.168.52.103:8080/p2pFinance";
    //  public static final String SERVICE_ADDRESS = "http://192.168.52.104:8088/p2pFinance";//孙工地址
//   public static final String SERVICE_ADDRESS = "http://192.168.52.125:8080/p2pFinance";//肖拓地址
    // public static final String SERVICE_ADDRESS = "http://192.168.52.209:8080/p2pFinance";//周杰地址
   public static final String SERVICE_ADDRESS = "http://192.168.56.12:8084/p2pFinance";
    // public static final String SERVICE_ADDRESS = "https://hxbank.ftoul.com/p2pFinance";
    //  public static final String SERVICE_ADDRESS = "http://192.168.52.106:8080/p2pFinance";
 //   public static final String SERVICE_ADDRESS = "http://192.168.52.134:8080/p2pFinance";//戴颖鑫地址
    public static final String SERVICE_MAIN = SERVICE_ADDRESS + "/services/app/service";//APP接口主地址
    public static final String SERVICE_UPLOAD = SERVICE_ADDRESS + "/services/api/v2/upload";//APP上传地址
    public static final String WEB_VIEW_BID_INFOR = SERVICE_ADDRESS + "/services/bid";//项目详情地址
    // public static final String WEB_VIEW_BID_INFOR = "http://192.168.52.104:8080/p2pFinance" + "/services/bid";//项目详情地址
    public static final String ACCOUNT_CREATE = SERVICE_ADDRESS + "/services/hxbank/account/create";//开户 GET
    public static final String DOCHARGE = SERVICE_ADDRESS + "/services/hxbank/charge/";//充值 GET
    public static final String WITH_DRAW = SERVICE_ADDRESS + "/services/hxbank/withdraw/";//提现 GET
    public static final String BIND_CARD = SERVICE_ADDRESS + "/services/hxbank/account/bindCard";// 绑卡 GET
    public static final String REPAYMENT = SERVICE_ADDRESS + "/services/hxbank/repayment/<billId>";// 还款 POST
    public static final String INVEST = SERVICE_ADDRESS + "/services/hxbank/invest/";// 投资 POST
    public static final String REGISTER_AGREEMENT = SERVICE_ADDRESS + "/services/special/agreement/888888";//注册协议
    public static final String BID_AGREEMENT = SERVICE_ADDRESS + "/services/special/agreement/888889";//标的协议
    public static final String FENG_COIN_RULE = SERVICE_ADDRESS + "/services/special/useRule";//蜂币使用规则
    public static final String HUA_XIN_CALL_BACK_1 = SERVICE_ADDRESS + "/services/special/personal/home";//华兴银行返回商户地址
    public static final String HUA_XIN_CALL_BACK_2 = SERVICE_ADDRESS + "/services/personal/home";//华兴银行返回商户地址
    public static final String QQ_URL = "https://q.url.cn/s/RAPa8Jm";//qq客服地址
    public static final String FTOUL_DATA = SERVICE_ADDRESS + "/services/cms/publish/data";//蜂投数据
    public static final String SECURITY = SERVICE_ADDRESS + "/services/cms/publish/security";//安全保障
    public static final String ABOUT_FTOUL = SERVICE_ADDRESS + "/services/cms/about/realize";//了解蜂投
    public static final String WEB_VIEW_DISCOVERY = SERVICE_ADDRESS + "/services/found/index";//发现页
    public static final String WEB_VIEW_HELP = SERVICE_ADDRESS + "/services/cms/help/mobile";//帮助中心
    //  public static final String WEB_VIEW_HELP = "http://192.168.52.125:8080/p2pFinance" + "/services/cms/help/mobile";//帮助中心
    //  public static final String WEB_VIEW_DISCOVERY = "http://192.168.52.125:8080/p2pFinance" + "/services/found/index";//发现页
//    public static final String FTOUL_DATA = "http://192.168.52.125:8080/p2pFinance/services/cms/publish/data";//蜂投数据
//    public static final String SECURITY = "http://192.168.52.125:8080/p2pFinance/services/cms/publish/security";//安全保障
//    public static final String ABOUT_FTOUL = "http://192.168.52.125:8080/p2pFinance/services/cms/about/realize";//了解蜂投

//   开户 GET： services/hxbank/account/create
//    充值 GET： services/personal/charge/doCharge/<amount> 充值金额
//    提现 GET： services/personal/withdraw/doWithdraw/<amount> 提现金额
//    绑卡 GET： services/hxbank/account/bindCard
//    投资 POST： services/hxbank/invest/<bidId>?investAmount=xxxx
//    还款 POST： services/hxbank/repayment/<billId>

}
