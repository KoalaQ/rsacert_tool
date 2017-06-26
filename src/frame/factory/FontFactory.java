package frame.factory;

import java.awt.*;

/**
 * Created by lyd on 2017/5/11.
 */
public class FontFactory {


    public static Font getTabtitleName(){
        Font font=new Font("Default",Font.BOLD,13);
        return font;
    }
    public static Font getTabtitleXOn(){
        Font font=new Font("Default",Font.TYPE1_FONT,13);
        return font;
    }
    public static Font getTabtitleXOut(){
        Font font=new Font("Default",Font.BOLD,13);
        return font;
    }
    public static Font getContentTabTitle(){
        Font font=new Font("Default",Font.BOLD,13);
        return font;
    }

}
