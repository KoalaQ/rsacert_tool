package frame.constant;

/**
 * Created by lyd on 2017/1/4.
 */
public enum EnActionEvent {
    /**
     * 登录点击
     */
    LOGINCLICK("loginclick"),
    /**
     *断开点击
     */
    LOGOUTCLICK("logoutclick"),
    /**
     *退出点击
     */
    CLOSECLICK("closeclick"),
    /**
     *关于点击
     */
    ABOUTCLICK("aboutclick"),
    /**
     *新窗口点击
     */
    NEWTABCLICK("newtabclick"),
    /**
     *关闭所有点击
     */
    CLOSEALLTABCLICK("closealltabclick"),
    /**
     *uc定义插入按钮点击
     */
    UCDEFINE_INSERTCLICK("insertclick"),
    /**
     *uc定义尾加按钮点击
     */
    UCDEFINE_TAILINSERTCLICK("tailinsertclick"),
    /**
     *uc定义删除按钮点击
     */
    UCDEFINE_DELCLICK("delclick"),
    /**
     *uc定义保存按钮点击
     */
    UCDEFINE_SAVECLICK("saveclick"),
    /**
     *uc定义查询按钮点击
     */
    UCDEFINE_QUERYCLICK("queryclick"),

    ;
    private String cmd;


    EnActionEvent(String cmd) {
        this.cmd = cmd;
    }
    public String getCmd(){
        return this.cmd;
    }
}
