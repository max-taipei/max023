/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.livehereandnow.ages.engine;

import com.livehereandnow.ages.card.Card;
import com.livehereandnow.ages.exception.AgesException;
import com.livehereandnow.ages.field.Field;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mark
 */
public class NewEngine {

//    private EngineCore core;
    private Field field;

    public Field getField() {
        return field;
    }

    public NewEngine() throws AgesException {
        init();

    }

    private void init() {
        field = new Field();
    }

    String returnStr = " return str...";

    public String getFeedback() {
//        return core.getCardRowInfo();
//        return core.getFeedback();
//        return returnStr;
        return "...";
    }

    public void setFeedback(String str) {
        returnStr = str;
    }

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
            return doCmd(keyword);
        }
        if (tokenCnt == 2) {//如果輸入的是2個字的話
            try {
                p1 = Integer.parseInt(tokens.get(1));
            } catch (Exception ex) {
                System.out.println("Parameter must be integer!");
                return false;
            }
            return doCmd(keyword, p1);
        }

        if (tokenCnt == 3) {//如果輸入的是2個字的話
            try {
                p1 = Integer.parseInt(tokens.get(1));
                p2 = Integer.parseInt(tokens.get(2));
            } catch (Exception ex) {
                System.out.println("Parameter must be integer!");
                return false;
            }
            return doCmd(keyword, p1, p2);
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
            return doCmd(keyword, p1, p2, p3);
        }

        //
//        System.out.println("Cureently command must be one or two words only!");
        setFeedback("   unknown command," + cmd + ", just ignore it!");
//        setFeedback();

        return false;

    }

//    public EngineCore getCore() {
//        return core;
//    }
//    public Player getCurrentPlayer() {
//        return core.get當前玩家();
//    }
//    public String doProtocol(String cmd) throws IOException, AgesException {
////        core.getRoundNum();
//        switch (cmd) {
//            case "history":
//                return core.getHistory();
//            default:
//                return core.getCardRowInfo();
//        }
//
////        return core.NOCARD.toString();
//    }
//    public String doUserCmd(String user, String cmd) throws IOException, AgesException {
//
//        if (user.equalsIgnoreCase("admin")) {
//            parser(cmd);
//            return core.getFeedback();
//        }
//
//        if (core.get當前玩家().getName().equalsIgnoreCase(user)) {
//            if (parser(cmd)) {
//                return core.getFeedback();
//
//            } else {
//                return "unknown command, " + cmd;
//            }
//
//        } else {
//            return "   " + user + ", not your turn!";
//        }
//    }
    public boolean doCmd(String keyword) throws IOException, AgesException {
        switch (keyword) {
//           case "new-game"://v0.52
//               doNewGame();

//            case "brief":
//                return core.doBrief();
//            case "d"://v0.59
//            case "debug"://v0.59
//                return core.doDebug();
            case "start":
                return doStart();
            case "increase-population"://v0.52
            case "population"://v0.52
//                return core.doIncreasePopulation();
            case "revolution"://v0.39
//                return core.doRevolution();
            case "govt"://v0.39
            case "change-government"://v0.39
//                return core.doChangeGovernment();

            case "construct-wonder":
            case "wonder":
            case "w":
//                return core.doConstructWonder();

            case "farm": {
                System.out.println("請改用b 3 0");
//                return core.doFarm();
                return true;
            }
            case "help":
//                return core.doHelp();
            case "h":
//                return core.doHelpShort();
            case "s":
            case "status":
//                return core.doStatus();
                field.show();
                return true;
            case "version":
                return doVersion();

            case "change-turn":
            case "c":
            case "":
                return doChangeTurn();

            default:
                System.out.println("Unknown keyword, " + keyword);
                return false;
        }
    }

    public boolean doCmd(String keyword, int val) throws IOException, AgesException {
        switch (keyword) {
            case "打":
            case "p":
            case "o":
            case "out":

            case "play":
            case "play-card":
            case "out-card":
                return doPlayCard(val);
            case "oo":
//                return core.doPlayCard革命(val);
            case "拿"://在我的環境NetBeans無法執行，但是在DOS可以
            case "拿牌":
            case "t":
            case "take":
            case "take-card":
                return doTakeCard(val);

            default:
                System.out.println("Unknown keyword, " + keyword);
                return false;
        }
    }

    public boolean doCmd(String keyword, int p1, int p2) throws IOException, AgesException {
        switch (keyword) {
            case "build": // build a Mine, Farm, Urban Building, Military Unit 
            case "b":
//                return core.doBuild(p1, p2);
            case "destroy": // DESTROY a Mine, Farm, Urban Building
            case "disband": // DISBAND a Military Unit 
            case "d":
//                return core.doDestroy(p1, p2);

            case "produce":
//                case "p":
            case "打":
            case "out":
            case "o":

            case "play":
            case "play-card":   // PUT a LEADER INTO PLAY, PLAY AN ACTION CARD 
            //
            case "out-card":
//                return core.doPlayCard(p1, p2);

            default:
                System.out.println("Unknown keyword, " + keyword);
                return false;
        }
    }

    public boolean doCmd(String keyword, int p1, int p2, int p3) throws IOException, AgesException {
        switch (keyword) {
            case "upgrade":
            case "u":
//                return core.doUpgrade(p1, p2, p3);

            default:
                System.out.println("Unknown keyword, " + keyword);
                return false;
        }
    }

    public boolean doVersion() {
        //  System.out.println(" TODO   [A內政-亞歷山大圖書館 科技生產+1，文化生產+1，內政手牌上限+1，軍事手牌上限+1]  ");
        //getBuildingLimit()
        System.out.println("  === ver 0.3 ===  2014-5-12, 11:45, by Max　");
        System.out.println("    1.準備作交換玩家");
        System.out.println("  === ver 0.2 ===  2014-5-12, 10:05, by Max　");
        System.out.println("    Done 需要增加setCurrentPlayer");
        System.out.println("    1.建立騎兵區、炮兵區、飛機區、劇院區、圖書館區、競技場區");
        System.out.println("  === ver 0.1 ===  2014-5-10, 16:47, by Max　");
        System.out.println("    1. 建立遊戲引擎的變量，能夠運作的地方");

        return true;
    }

    public String getCurrentPlayer() {
        return field.getCurrentPlayer().getName();
    }

    private void doNewGame() {
        field = new Field();
//        field.init();

//        field.getP1().get政府區().get(0).setTokenYellow(2);
    }

//private void 執行生產(){
//    field.getCurrentPlayer().
//            for(int k=0;k<4;k++){
//                
//            }
//    
//}
    private void 交換玩家() {
        System.out.println("交換");
        System.out.println(field.getCurrentPlayer());
        System.out.println(field.getP1());
        if (field.getCurrentPlayer().equals(field.getP1())) {
            field.setCurrentPlayer(field.getP2());
        }
//         System.out.println("交換");

        if (field.getCurrentPlayer() == field.getP2()) {
            field.setCurrentPlayer(field.getP1());
        }
    }

    private boolean doPlayCard(int val) {

        System.out.println("...play-card ...");
//        if(field.getCurrentPlayer().get手牌內政牌區().wait(val, val))
        return true;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean doTakeCard(int val) {
        Card card = field.getCardRow().get(val);//宣告 card，並將卡牌列裡的指定編號的卡牌指定到這個card變量裡
        field.getCurrentPlayer().get手牌內政牌區().add(card);//當前玩家的手牌加入上一行的card
        field.getCardRow().remove(val);//從卡牌列拿掉剛剛那張card
        field.getCardRow().add(val, field.getAllCards().get(0));//在卡牌列同樣的位子，補上一張空卡

//        field.
        return true;
    }

    private boolean doStart() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        field.getP1().get步兵區().get(0).setTokenYellow(1);
        field.getP1().get實驗室().get(0).setTokenYellow(1);
        field.getP1().get礦山區().get(0).setTokenYellow(2);
        field.getP1().get農場區().get(0).setTokenYellow(2);
        field.getP1().get人力庫_黃點().setPoints(18);
        field.getP1().get工人區_黃點().setPoints(1);
        field.getP1().get資源庫_藍點().setPoints(18);

        field.getP2().get步兵區().get(0).setTokenYellow(1);
        field.getP2().get實驗室().get(0).setTokenYellow(1);
        field.getP2().get礦山區().get(0).setTokenYellow(2);
        field.getP2().get農場區().get(0).setTokenYellow(2);
        field.getP2().get人力庫_黃點().setPoints(18);
        field.getP2().get工人區_黃點().setPoints(1);
        field.getP2().get資源庫_藍點().setPoints(18);
        return true;
    }

    private boolean doChangeTurn() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

//        檢測暴動();
//        this.執行生產();
//        腐敗();
        field.getCurrentPlayer();
//        field.setCurrentPlayer(field.getP2());
        交換玩家();
        return true;
    }

}
