package com.ruanjie.camp;

/**
 * @author by DELL
 * @date on 2017/12/22
 * @describe
 */

public interface HostUrl {
    /**
     * app接口Host
     */
    String HOST_URL = "http://luyingdi.app.ruanjiekeji.com";

//    String HOST_URL = "";//正式

    String IMAG_HOST_URL = HOST_URL;

    String PREFIX = "app";//前缀

    /**
     * Set
     */
    String SET_APP_CONFIG = PREFIX + "/Set/appConfig";//官方配置信息
    String SET_HELPLIST = PREFIX +"/Set/helpList";//帮助列表

    String USER_INDEX = PREFIX + "/User/index";//用户主页信息
    String USER_USER_INFO = PREFIX + "/User/userInfo";//个人用户信息
    String USER_USER_HEAD_SET = PREFIX + "/User/userHeadSet";//用户头像
    String USER_USER_SET = PREFIX + "/User/userSet";//用户信息设置
    String USER_PHONE_BIND = PREFIX + "/User/phoneBind";//手机绑定修改,第一步先Login-checkCode
    String USER_EMAIL_BIND = PREFIX + "/User/emailBind";//邮箱绑定修改,第一步先Login-checkEmailCode
    String USER_LOGIN_BIND = PREFIX +"/User/loginBind";//APP第三方登录绑定

    String DATA_DATA = PREFIX + "/Data/data";//数据

    /*** Upload模块*/
    String UPLOAD_IMAGE_UPLOAD = PREFIX + "/Upload/imageUpLoad";//图片上传
    String UPLOAD_VIDEO_UPLOAD = PREFIX + "/Upload/videoUpLoad";//图片上传
    String UPLOAD_IMMAGE_MULTI_UPLOAD = PREFIX + "/Upload/imageMultiUpLoad";//base64多图片上传

    /*** Region */
    String REGION_REGION = PREFIX + "/Region/region";//地区数据

    /*** Message */
    String MESSAGE_UN_READ_COUNT = PREFIX + "/Message/unReadCount";//未读消息
    String MESSAGE_MESSAGELIST = PREFIX +"/Message/messageList";//消息列表
    String MESSAGE_MESSAGE_ONE = PREFIX +"/Message/messageOne";//消息详情
    String MESSAGE_MESSAGE_READ = PREFIX + "/Message/messageRead";//消息读取
    String MESSAGE_MESSAGE_DEL = PREFIX +"/Message/messageDel";//消息删除

    /*** Main */
    String MAIN_INDEX = PREFIX + "/Main/index";//主页信息
    String MAIN_USER_VIEW = PREFIX + "/Main/userView ";//用户查看
    String MAIN_USER_PUBLISH = PREFIX + "/Main/userPublish";//用户发布

    /*** Login */
    String LOGIN_RESET_PWD = PREFIX +"/Login/resetPwd";//重置密码
    String LOGIN_RESET_PWD_BY_EMAIL = PREFIX +"/Login/resetPwdByEmail";//邮箱重置密码
    String LOGIN_PHONECODE = PREFIX + "/Login/phoneCode";//获取手机验证码
    String LOGIN_EMAILCODE = PREFIX + "/Login/emailCode";//获取邮箱验证码
    String LOGIN_CHECK_CODE = PREFIX + "/Login/checkCode";//如需下一步前先检查手机验证码
    String LOGIN_CHECK_EMAIL_CODE = PREFIX + "/Login/checkEmailCode";//如需下一步前先检查手机验证码
    String LOGIN_CHANGE_PWD = PREFIX + "/Login/changePwd";//安全设置-修改密码
    String LOGIN_REGISTER = PREFIX + "/Login/register";//用户注册
    String LOGIN_LOGIN = PREFIX + "/Login/login";//用户登录
    String LOGIN_LOGINOUT = PREFIX + "/Login/loginOut";//退出用户登录

    /*** Tour */
    String TOUR_TOURVIEWLIST = PREFIX + "/Tour/tourViewList";//展示游记列表
    String TOUR_TOURLIST = PREFIX + "/Tour/tourList";//我的游记列表
    String TOUR_TOURDETAIL = PREFIX + "/Tour/tourDetail";//游记详细
    String TOUR_TOUR_DEL = PREFIX + "/Tour/tourDel";//游记删除

    /*** Tourdraft */
    String TOURDRAFT_TOUR_DRAF_SAVE = PREFIX + "/Tourdraft/tourDraftSave";//游记草稿添加保存
    String TOURDRAFT_TOUR_DRAF_DETAIL = PREFIX + "/Tourdraft/tourDraftDetail";//游记草稿详细
    String TOURDRAFT_TOUR_DRAFT_POS = PREFIX + "/Tourdraft/tourDraftPos";//游记上报轨迹位置
    String TOURDARFT_TOUR_DRAFT_DEL = PREFIX + "/Tourdraft/tourDraftDel";//游记草稿删除

    /*** Site */
    String SITE_SITE_CONFIG = PREFIX + "/Site/siteConfig";//窝窝和营地添加页面的配置
    String SITE_SITEADD = PREFIX + "/Site/siteAdd";//窝窝和营地添加
    String SITE_INTEREST_CONFIG = PREFIX + "/Site/interestConfig";//兴趣添加页面的配置
    String SITE_SITE_VIEW_LIST = PREFIX + "/Site/siteViewList";//查看窝窝和营地列表
    String SITE_SITE_LIST = PREFIX + "/Site/siteList";//我的窝窝和营地列表
    String SITE_INTEREST_ADD = PREFIX + "/Site/interestAdd";//兴趣添加
    String SITE_INTEREST_LIST = PREFIX + "/Site/interestList";//我的兴趣列表
    String SITE_INTEREST_VIEW_LIST = PREFIX + "/Site/interestViewList";//查看兴趣列表
    String SITE_SITE_DETAIL = PREFIX + "/Site/siteDetail";//窝窝和营地详细
    String SITE_SITE_MORE_PIC_ADD = PREFIX + "/Site/morePicAdd";//补图添加
    String SITE_ERROR_ADD = PREFIX + "/Site/errorAdd";//报错添加
    String SITE_INTEREST_DETAIL = PREFIX + "/Site/interestDetail";//兴趣详细
    String SITE_ERROR_LIST = PREFIX + "/Site/errorList";//我的报错列表
    String SITE_MORE_PIC_LIST = PREFIX + "/Site/morePicList";//我的补图列表
    String SITE_REPORT_ADD = PREFIX + "/Site/reportAdd";//举报添加
    String SITE_SITEVIEW_LIST_INDEX = PREFIX +"/Site/siteViewListIndex";//查看窝窝和营地列表广告数据
    String SITE_ALL_LIST = PREFIX + "/Site/siteAllList";//我的全部窝窝和营地,兴趣

    /*** Road */
    String ROAD_SEARCH = PREFIX + "/Road/search";//路线搜索
    String ROAD_ROADLIST = PREFIX + "/Road/roadList";//路线列表
    String ROAD_ROAD_DETIAL = PREFIX + "/Road/roadDetail";//路线详细
    String ROAD_ROAD_ADD = PREFIX + "/Road/roadAdd";//路线添加
    String ROAD_ROAD_DEL = PREFIX +"/Road/roadDel";//路线删除

    /*** Comment */
    String COMMENT_SITE_COMMENTLIST = PREFIX + "/Comment/siteCommentList";//窝窝营地评论列表
    String COMMENT_COMMENTLIST = PREFIX + "/Comment/commentList";//我的评论列表
    String COMMENT_COMMENT_ADD = PREFIX + "/Comment/commentAdd";//评论添加
    String COMMENT_INS_COMMENT_INS_LIST = PREFIX + "/Comment/insCommentInsList";//兴趣评论列表
    String COMMENT_COMMENT_INS_LIST = PREFIX + "/Comment/commentInsList";//我的兴趣评论列表
    String COMMENT_COMMENT_INS_ADD = PREFIX + "/Comment/commentInsAdd";//兴趣评论添加
    String COMMENT_INS_COMMENT_TOUR_LIST = PREFIX + "/Comment/insCommentTourList";//游记评论列表
    String COMMENT_TOUR_COMMNENT_TOUR_LIST = PREFIX + "/Comment/tourCommentTourList";//游记评论列表
    String COMMENT_COMMENT_TOUR_LIST = PREFIX + "/Comment/commentTourList";//我的游记评论列表
    String COMMENT_COMMENT_TOUR_ADD = PREFIX + "/Comment/commentTourAdd";//游记评论添加
    String COMMENT_STAR_TOUR_ADD = PREFIX + "/Comment/starTourAdd";//游记评星添加

    /*** Collect */
    String COLLECT_COLLECT_LIST = PREFIX + "/Collect/collectList";//收藏列表
    String COLLECT_COLLECT_ADD = PREFIX + "/Collect/collectAdd";//收藏添加
    String COLLECT_COLLECT_DEL = PREFIX + "/Collect/collectDel";//收藏删除
    String COLLECT_COLLECT_INS_LIST = PREFIX + "/Collect/collectInsList";//收藏兴趣列表
    String COLLECT_COLLECT_INS_ADD = PREFIX + "/Collect/collectInsAdd";//收藏兴趣添加
    String COLLECT_COLLECT_INS_DEL = PREFIX + "/Collect/collectInsDel";//收藏兴趣删除
    String COLLECT_COLLECT_TOUR_LIST = PREFIX + "/Collect/collectTourList";//我的收藏游记列表
    String COLLECT_COLLECT_TOUR_ADD = PREFIX + "/Collect/collectTourAdd";//收藏游记添加
    String COLLECT_COLLECT_TOUR_DEL = PREFIX + "/Collect/collectTourDel";//收藏游记删除

    /*** Like */
    String LIKE_LIKE_TOUR_ADD = PREFIX + "/Like/likeTourAdd";//游记点赞添加
    String LIKE_LIKE_TOUR_DEL = PREFIX + "/Like/likeTourDel";//游记点赞删除

    /*** Relation */
    String RELATION_FOLLOW_LIST = PREFIX + "/Relation/followList";//关注列表
    String RELATION_FOLLOW_ADD = PREFIX + "/Relation/followAdd";//关注添加
    String RELATION_FOLLOW_DEL = PREFIX + "/Relation/followDel";//关注删除
    String RELATION_FAN_LIST = PREFIX + "/Relation/fanList";//粉丝列表
    String RELATION_FAN_DEL = PREFIX + "/Relation/fanDel";//粉丝移除
    String RELATION_BLACKLIST = PREFIX + "/Relation/blacklistList";//黑名单列表
    String RELATION_BLACKLIST_ADD = PREFIX + "/Relation/blacklistAdd";//黑名单添加
    String RELATION_BLACKLIST_DEL = PREFIX +"/Relation/blacklistDel";//黑名单删除
    String RELATION_GROUP_LIST = PREFIX + "/Relation/groupList";//小组列表
    String RELATION_GROUP_ADD = PREFIX +"/Relation/groupAdd";//小组添加
    String RELATION_GROUP_EDIT = PREFIX + "/Relation/groupEdit";//小组修改
    String RELATION_GROUP_DEL = PREFIX + "/Relation/groupDel";//小组删除
    String RELATION_GROUP_MEMBER_LIST = PREFIX + "/Relation/groupMemberList";//小组成员列表
    String RELATION_GROUP_MEMBER_ADD = PREFIX + "/Relation/groupMemberAdd";//小组成员添加
    String RELATION_GROUP_MEMBER_DEL = PREFIX +"/Relation/groupMemberDel";//小组成员删除

    /**
     * Driver
     */
    String USER_DRIVER_DRIVER_DETAIL = PREFIX +"/Userdriver/driverDetail";//用户车主认证详情
    String USER_DRIVER_DRIVER_ADD_INDEX = PREFIX +"/Userdriver/driverAddIndex ";//用户车主认证
    String USER_DRIVER_DRIVER_ADD = PREFIX + "/Userdriver/driverAdd";//用户车主认证

    /**
     * Vip
     */
    String VIP_VIP_LIST = PREFIX + "/Vip/vipList";//会员价格列表
    String VIP_RECHARGE = PREFIX + "/Vip/recharge";//会员购买

    /**
     * Score
     */
    String USER_SCORE_CENTER = PREFIX +"/Userscore/center";//积分中心
    String USER_HISTORY_LIST = PREFIX +"/Userscore/historyList";//余额记录列表
}
