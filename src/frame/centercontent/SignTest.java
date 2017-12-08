package frame.centercontent;



import frame.BaseJPanel;
import frame.factory.ColorFactory;
import frame.listener.MyActionListener;
import util.SignatureUtils;
import util.StringUtils;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by lyd on 2017/5/11.
 */
public class SignTest extends BaseJPanel   implements ActionListener {
    public static final int BOTTOM = 5;
    private JLabel filepathpubLabel=new JLabel("公钥文件：");
    private JTextField filepubpath = new JTextField(20);
    private JButton filepubchooserbtn=new JButton("选择目录");

    private JLabel filepathpriLabel=new JLabel("私钥文件：");
    private JTextField filepripath = new JTextField(20);
    private JButton fileprichooserbtn=new JButton("选择目录");

    private JLabel pristorepwdLabel=new JLabel("私钥存储密码：");
    private JTextField pristorepwdField = new JTextField("123456",20);

    private JLabel prikeypwdLabel=new JLabel("私钥密码：");
    private JTextField prikeypwdField = new JTextField("123456",20);



    private JLabel testStrLabel=new JLabel("测试串：");
    private JTextArea testArea=new JTextArea("测试串！！1123",5, 62);
    private JLabel signAreaLabel=new JLabel("签名串：");
    private JTextArea signArea=new JTextArea("这是签名符串",6, 62);
    private JTextArea privateKeyArea=new JTextArea("这是私钥base64编码后字符串",6, 62);
    private JLabel verifyLabel=new JLabel("验签结果：");
    private JTextField verifyField = new JTextField("这是签名结果",20);

    private JButton signbtn=new JButton("签名");
    private JButton verifybtn=new JButton("验签");

    public SignTest(MyActionListener myActionListener) {
        this.myActionListener= myActionListener;
        init();
    }
    private void init(){
        filepubchooserbtn.setActionCommand("PUBCERTFILE");
        fileprichooserbtn.setActionCommand("PRICERTFILE");
        signbtn.setActionCommand("SIGN");
        verifybtn.setActionCommand("VERIFY");

        filepubchooserbtn.addActionListener(this);
        fileprichooserbtn.addActionListener(this);
        signbtn.addActionListener(this);
        verifybtn.addActionListener(this);
        //testArea.setSize(670, 50);
        //testArea.setPreferredSize(new Dimension(670, 50));
        testArea.setLineWrap(true);
        testArea.setEditable(true);
        //signArea.setPreferredSize(new Dimension(670, 50));
        signArea.setLineWrap(true);
        privateKeyArea.setLineWrap(true);
        JPanel jp=new JPanel();
        jp.setLayout(new BorderLayout(5,5));
        JPanel jp1=new JPanel();
        jp1.setPreferredSize(new Dimension(0, 300));
        JPanel jp2=new JPanel();
        jp2.setPreferredSize(new Dimension(400,0 ));
        JPanel jp3=new JPanel();
        jp3.setPreferredSize(new Dimension(400, 0));
        jp1.setBorder(new MatteBorder(1,1,5,1, ColorFactory.getNormalColor()));
        jp2.setBorder(new MatteBorder(3,1,1,3, ColorFactory.getNormalColor()));
        jp3.setBorder(new MatteBorder(3,3, 1,1, ColorFactory.getNormalColor()));

        jp1.add(testStrLabel);
        JPanel jpp=new JPanel();
        jp1.add(new JScrollPane(testArea));
        jp1.add(signAreaLabel);
        jp1.add(new JScrollPane(signArea));
        jp1.add(new JLabel("私钥base64"));
        jp1.add(new JScrollPane(privateKeyArea));
        jp1.add(verifyLabel);
        jp1.add(verifyField);

        jp2.setLayout(new GridLayout(4,3));
        jp2.add(filepathpriLabel);
        jp2.add(filepripath);
        jp2.add(fileprichooserbtn);
        jp2.add(pristorepwdLabel);
        jp2.add(pristorepwdField);
        jp2.add(new JLabel());
        jp2.add(prikeypwdLabel);
        jp2.add(prikeypwdField);
        jp2.add(new JLabel());
        jp2.add(signbtn);
        jp3.setLayout(new GridLayout(4,3));
        jp3.add(filepathpubLabel);
        jp3.add(filepubpath);
        jp3.add(filepubchooserbtn);
        jp3.add(verifybtn);
        jp3.add(new JLabel());
        jp3.add(new JLabel());
        jp3.add(new JLabel());
        jp3.add(new JLabel());
        jp3.add(new JLabel());
        jp3.add(new JLabel());
        jp3.add(new JLabel());
        jp3.add(new JLabel());


        jp.add(jp2,BorderLayout.WEST);
        jp.add(jp3,BorderLayout.CENTER);
        BorderLayout layout = new BorderLayout(5,5);
        this.setLayout(layout);


        this.add(jp1,BorderLayout.NORTH);
        this.add(jp,BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("PUBCERTFILE")){
            JFileChooser jfc=new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY  );
            jfc.showDialog(new JLabel(), "选择");
            File file=jfc.getSelectedFile();
            if(file!=null){
                filepubpath.setText(file.getAbsolutePath());
            }
        }else if(e.getActionCommand().equals("PRICERTFILE")){
            JFileChooser jfc=new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY  );
            jfc.showDialog(new JLabel(), "选择");
            File file=jfc.getSelectedFile();
            if(file!=null){
                filepripath.setText(file.getAbsolutePath());
            }
        } else if(e.getActionCommand().equals("SIGN")){
            sign();
        }else if(e.getActionCommand().equals("VERIFY")){
            verify();
        }
    }

    private void sign(){
        String filepripathstr=filepripath.getText();
        String testAreastr=testArea.getText();
        String pristorepwdFieldstr=pristorepwdField.getText();
        String prikeypwdFieldstr=prikeypwdField.getText();
        //region 验证必填
        if(StringUtils.isNullOrEmpty(filepripathstr)){
            showWarningMsg("请选择私钥文件！");
            return;
        }
        if(StringUtils.isNullOrEmpty(testAreastr)){
            showWarningMsg("请填写测试字符串！");
            return;
        }
        if(StringUtils.isNullOrEmpty(pristorepwdFieldstr)){
            showWarningMsg("请填写私钥存储密码！");
            return;
        }
        if(StringUtils.isNullOrEmpty(prikeypwdFieldstr)){
            showWarningMsg("请填写私钥密码！");
            return;
        }
        //endregion
        String singstr= null;
        String prikey="";
        try {
             prikey= SignatureUtils.getBase64PrivateKey(testAreastr,filepripathstr,pristorepwdFieldstr,prikeypwdFieldstr);
            singstr = SignatureUtils.signature(testAreastr,filepripathstr,pristorepwdFieldstr,prikeypwdFieldstr);
        } catch (Exception e) {
            showWarningMsg("签名异常："+e.getMessage());
        }
        signArea.setText(singstr);
        privateKeyArea.setText(prikey);
    }
    private void verify(){
        String filepubpathstr=filepubpath.getText();
        String testAreastr=testArea.getText();
        String signAreastr=signArea.getText();

        //region 验证必填
        if(StringUtils.isNullOrEmpty(filepubpathstr)){
            showWarningMsg("请选择公钥文件！");
            return;
        }
        if(StringUtils.isNullOrEmpty(testAreastr)){
            showWarningMsg("请填写测试字符串！");
            return;
        }
        if(StringUtils.isNullOrEmpty(signAreastr)){
            showWarningMsg("请填写签名串！");
            return;
        }
        //endregion

        boolean ret= false;
        try {
            ret = SignatureUtils.verify(testAreastr,signAreastr,filepubpathstr);
        } catch (Exception e) {
            showWarningMsg("验签异常："+e.getMessage());
        }
        if(ret){
            verifyField.setText("true");
        }else{
            verifyField.setText("false");
        }

    }
    private void showWarningMsg(String msg){
        JOptionPane.showMessageDialog(this,msg,"提示",JOptionPane.WARNING_MESSAGE);

    }
    private void showMsg(String msg){
        JOptionPane.showMessageDialog(this,msg,"提示",JOptionPane.INFORMATION_MESSAGE);
    }
}
