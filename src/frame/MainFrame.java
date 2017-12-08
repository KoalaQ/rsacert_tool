package frame;

import frame.listener.MyActionListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lyd on 2017-06-20.
 */
public class MainFrame extends JFrame {
    private FootBar footBar;
    private CenterPanel centerPanel;
    private MyActionListener myActionListener;
    public MainFrame() throws HeadlessException {
        super("RSA证书工具");
        initFrame();
    }
    private  void initFrame(){
        setLayout(new BorderLayout());

        footBar=new FootBar();
        this.add(footBar,BorderLayout.SOUTH);
        //region center中间
        centerPanel=new CenterPanel(myActionListener);
        this.add(centerPanel,BorderLayout.CENTER);
        //endregion

        //region frame基础设置
        setSize(800,530);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        //endregion

    }
    public static void main(String[] args) {
        MainFrame mainFrame=new MainFrame();
        //增加一个默认窗口
        mainFrame.addTab("RSA证书工具");
    }
    public void addTab(String titleName){
        centerPanel.addTab(titleName);
    }
    public void closeAllTab(){
        centerPanel.closeAllTab();
        //关闭后增加一个默认的
        centerPanel.addTab("RSA签名验签");
    }

}
