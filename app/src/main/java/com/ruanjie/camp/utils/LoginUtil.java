package com.ruanjie.camp.utils;

import com.ruanjie.camp.bean.UserBean;

/**
 * @author Moligy
 * @date 2019/6/13.
 */
public class LoginUtil {
    public static void Logout() {
        UserBean.cleanLogin();
    }
}
