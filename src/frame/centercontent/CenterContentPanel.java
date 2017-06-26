package frame.centercontent;



import frame.BaseJPanel;
import frame.listener.MyActionListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lyd on 2017/5/11.
 */
public class CenterContentPanel extends BaseJPanel {
    private JTabbedPane contentTab;
    public CenterContentPanel(MyActionListener myActionListener) {
        this.myActionListener = myActionListener;
        init();
    }

    private void init(){
        setLayout(new BorderLayout());
        contentTab=new JTabbedPane();
        //todo title 样式
        //contentTab.setFont(FontFactory.getContentTabTitle());

        contentTab.addTab("证书生成",new GenerateCert(myActionListener));
        contentTab.addTab("签名验证",new SignTest(myActionListener));
        this.add(contentTab,BorderLayout.CENTER);
    }
}
