package frame.centercontent;



import frame.BaseJPanel;
import frame.listener.MyActionListener;
import util.CertUtil;
import util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.sun.deploy.util.SessionState.save;

/**
 * Created by lyd on 2017/5/11.
 */
public class GenerateCert extends BaseJPanel   implements ActionListener {
    private JLabel filepathLabel=new JLabel("*保存目录：");
    private JTextField filepath = new JTextField(20);
    private JButton filechooserbtn=new JButton("选择目录");
    private JLabel pubkyenameLabel=new JLabel("*公钥证书名：");
    private JTextField pubkyename=new JTextField("pubkey.cer",20);
    private JLabel privatekeynameLabel=new JLabel("*私钥名：");
    private JTextField privatekeyname=new JTextField("prikey.keystore",20);
    private JLabel aliasLabel=new JLabel("证书别名：");
    private JTextField alias=new JTextField("jnsb",20);
    private JLabel storePwdLabel=new JLabel("*私钥存储密码：");
    private JTextField storePwd=new JTextField("123456",20);
    private JLabel keyPwdLabel=new JLabel("*私钥密码：");
    private JTextField keyPwd=new JTextField("123456",20);
    private JLabel effecdayLabel=new JLabel("*有效天数：");
    private JTextField effecday=new JTextField("3650",20);

    private JButton savebtn=new JButton("保存");
    public GenerateCert(MyActionListener myActionListener) {
        this.myActionListener= myActionListener;
        init();
    }
    private void init(){

        filechooserbtn.setActionCommand("CHOOSEFILE");
        filechooserbtn.addActionListener(this);

        savebtn.setActionCommand("SAVE");
        savebtn.addActionListener(this);


       GroupLayout layout=new GroupLayout(this);
        this.setLayout(layout);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(5);//添加间隔
        hGroup.addGroup(layout.createParallelGroup().addComponent(filepathLabel)
                .addComponent(pubkyenameLabel).addComponent(storePwdLabel).addComponent(aliasLabel).addComponent(savebtn));
        hGroup.addGap(5);//添加间隔
        hGroup.addGroup(layout.createParallelGroup().addComponent(filepath)
                .addComponent(pubkyename).addComponent(storePwd).addComponent(alias));
        hGroup.addGap(5);//添加间隔
        hGroup.addGroup(layout.createParallelGroup().addComponent(filechooserbtn)
                .addComponent(privatekeynameLabel).addComponent(keyPwdLabel).addComponent(effecdayLabel));
        hGroup.addGap(5);//添加间隔
        hGroup.addGroup(layout.createParallelGroup().addComponent(privatekeyname)
                .addComponent(keyPwd).addComponent(effecday));
        hGroup.addGap(5);//添加间隔
        layout.setHorizontalGroup(hGroup);
        //创建GroupLayout的垂直连续组，，越先加入的ParallelGroup，优先级级别越高。
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(filepathLabel)
                .addComponent(filepath)
                .addComponent(filechooserbtn));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(pubkyenameLabel)
                .addComponent(pubkyename)
                .addComponent(privatekeynameLabel)
                .addComponent(privatekeyname));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(storePwdLabel)
                .addComponent(storePwd)
                .addComponent(keyPwdLabel)
                .addComponent(keyPwd));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(aliasLabel)
                .addComponent(alias)
                .addComponent(effecdayLabel)
                .addComponent(effecday));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(savebtn)
                );
        vGroup.addGap(10);
        layout.setVerticalGroup(vGroup);


/*
        this.add(filepathLabel);
        this.add(filepath);
        this.add(filechooserbtn);
        this.add(pubkyenameLabel);
        this.add(pubkyename);
        this.add(privatekeynameLabel);
        this.add(privatekeyname);
        this.add(aliasLabel);
        this.add(alias);
        this.add(storePwdLabel);
        this.add(storePwd);
        this.add(keyPwdLabel);
        this.add(keyPwd);
        this.add(effecdayLabel);
        this.add(effecday);
        this.add(savebtn);*/

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("CHOOSEFILE")){
            JFileChooser jfc=new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY  );
            jfc.showDialog(new JLabel(), "选择");
            File file=jfc.getSelectedFile();
            if(file!=null){
                filepath.setText(file.getAbsolutePath());
            }
        }else if(e.getActionCommand().equals("SAVE")){
            savecert();
        }

    }

    private void savecert() {
        String filepathstr=filepath.getText();
        String pubkyenamestr=pubkyename.getText();
        String privatekeynamestr=privatekeyname.getText();
        String aliasstr=alias.getText();
        String storePwdstr=storePwd.getText();
        String keyPwdstr=keyPwd.getText();
        String effecdaystr=effecday.getText();
        int effecdays;
        //region 字段验证必填和赋值
        if (StringUtils.isNullOrEmpty(filepathstr)) {
            showWarningMsg("保存目录必填！");
            return;
        }
        if (StringUtils.isNullOrEmpty(pubkyenamestr)) {
            showWarningMsg("公钥证书名必填！");
            return;
        }
        if (StringUtils.isNullOrEmpty(privatekeynamestr)) {
            showWarningMsg("私钥证书名必填！");
            return;
        }
        if (StringUtils.isNullOrEmpty(storePwdstr)) {
            showWarningMsg("私钥存储密码必填！");
            return;
        }
        if (StringUtils.isNullOrEmpty(keyPwdstr)) {
            showWarningMsg("私钥密码必填！");
            return;
        }
        if (StringUtils.isNullOrEmpty(effecdaystr)) {
            showWarningMsg("有效天数必填！");
            return;
        }else{
            try {
                effecdays=Integer.parseInt(effecdaystr);
            } catch (NumberFormatException e) {
                showWarningMsg("有效天数应为整数！");
                return;
            }
        }
        if (StringUtils.isNullOrEmpty(aliasstr)) {
            aliasstr="jnsb";
        }
        //endregion

        try {
            if(!validFile(new String[]{filepathstr+File.separator+pubkyenamestr,filepathstr+File.separator+privatekeynamestr})){
                 //验证不通过
                 return;
            }
        } catch (Exception e) {
            showWarningMsg(e.getMessage());
            return;
        }

        try {
            boolean ret=CertUtil.generateCert(filepathstr,pubkyenamestr,privatekeynamestr,aliasstr,storePwdstr,keyPwdstr,effecdays);
            if(ret){
                showMsg("生成成功！");
            }else{
                showWarningMsg("生成失败！");
            }
        } catch (Exception e) {
            showWarningMsg("生成证书异常："+e.getMessage());
        }
    }

    private void showWarningMsg(String msg){
         JOptionPane.showMessageDialog(this,msg,"提示",JOptionPane.WARNING_MESSAGE);

    }
    private void showMsg(String msg){
        JOptionPane.showMessageDialog(this,msg,"提示",JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * 验证通过，会删除源文件
     * @param filePathName
     * @return
     */
    private  boolean validFile(String [] filePathName) throws Exception {
        List<File> files=new ArrayList<File>();
        for(String name : filePathName){
            File file=new File(name);
            if(validFile(file)){
                files.add(file);
            }else{
                return false;
            }
        }
        //删除file
        for(File file :files){
            if(file.exists()){
                if(!file.delete()){
                   throw new Exception("无法覆盖文件："+file.getName());
                }
            }
        }
        return true;
    }
    private boolean validFile(File file){
        if(file.exists()){
            int i=JOptionPane.showConfirmDialog(this,"文件："+file.getName()+",已存在，是否覆盖","警告",JOptionPane.WARNING_MESSAGE);
            if(i==2){
                //取消
                return false;
            }
        }
        return true;
    }
}
