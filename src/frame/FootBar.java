package frame;

import javax.swing.*;

/**
 * Created by lyd on 2017/5/10.
 */
public class FootBar extends JToolBar {
    private JLabel conninfo;
    private JLabel connstate;
    private JLabel errorinfo;

    public FootBar() {
        init();
    }
    private void init(){
        conninfo=new JLabel("RSA证书工具");
        //connstate=new JLabel("CopyRight@保融科技");
        connstate=new JLabel("CopyRight");
        errorinfo=new JLabel("");
        this.add(conninfo);
        this.addSeparator();
        this.add(connstate);
        this.addSeparator();
        this.add(errorinfo);
    }
}
