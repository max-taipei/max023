package com.livehereandnow.ages;


import com.livehereandnow.ages.engine.NewEngine;
import com.livehereandnow.ages.exception.AgesException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private NewEngine engine;
  
    public Main() throws AgesException {
        engine = new NewEngine();
    }

    public static void main(String[] args) throws IOException, AgesException {
        new Main().run();
    }

    public void run() throws AgesException, IOException {
        InputStreamReader cin = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(cin);
//        engine.getField().show();
        while (true) {
            System.out.print("" + engine.getCurrentPlayer()+ " >> ");
            parser(in.readLine());
        }
    }

    /**
     * design command as type 1, single word, version help status type 2,
 keyword + p1, take-card 1 take 1 play-card 1 play 1
     *
     * @param cmd
     * @return
     * @throws IOException
     */
    public boolean parser(String cmd) throws IOException, AgesException {
        //
        // 1. init
        //
        int tokenCnt = 0;//命令行裡共有幾個字，給予初值為0
        String keyword = "";//指令是什麼，給予初值空字符串
        int p1 = -1;//指令的參數是什麼，給予初值為-1，-1通常是指不能用的值
        int p2 = -1;
        int p3 = -1;
        
        //
        // 2. parser to words 
        //
        //將命令行的句子拆解為字，以空格格開為依據，空格都不記
        String[] strTokens = cmd.split(" ");
        List<String> tokens = new ArrayList<>();
        for (String item : strTokens) {
            if (item.length() > 0) {
                tokens.add(item);
            }
        }
        tokenCnt = tokens.size();//賦予變量tokenCnt真正的值，真正的值是指到底打個幾個字

        //
        // 3. to execute command based on size
        //
        if (tokenCnt == 0) {//when simple enter
            return true; // silently ignore it
        }
        // 
        keyword = tokens.get(0);//指令的關鍵字是第0個字，例如take 3的take

        if (tokenCnt == 1) {//如果輸入的是一個字的話
            return engine.doCmd(keyword);
        }
        if (tokenCnt == 2) {//如果輸入的是2個字的話
            try {
                p1 = Integer.parseInt(tokens.get(1));
            } catch (Exception ex) {
                System.out.println("Parameter must be integer!");
                return false;
            }
            return engine.doCmd(keyword, p1);
        }
        
          if (tokenCnt == 3) {//如果輸入的是2個字的話
            try {
                p1 = Integer.parseInt(tokens.get(1));
                p2 = Integer.parseInt(tokens.get(2));
            } catch (Exception ex) {
                System.out.println("Parameter must be integer!");
                return false;
            }
            return engine.doCmd(keyword, p1,p2);
        }
        
        // ver 0.62 for upgrad 3 0 1, Upgrad Farm from Age A to Age I
        if (tokenCnt == 4) {//如果輸入的是3個字的話
            try {
                p1 = Integer.parseInt(tokens.get(1));
                p2 = Integer.parseInt(tokens.get(2));
                p3 = Integer.parseInt(tokens.get(3));
            } catch (Exception ex) {
                System.out.println("Parameter must be integer!");
                return false;
            }
            return engine.doCmd(keyword, p1,p2,p3);
        }
        
        

        //
        System.out.println("Cureently command must be one or two words only!");
        return false;

    }

}
