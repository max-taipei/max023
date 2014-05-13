/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.livehereandnow.ages.engine;

import com.livehereandnow.ages.card.Card;
import com.livehereandnow.ages.exception.AgesException;
import com.livehereandnow.ages.field.Field;
import com.livehereandnow.ages.field.Field.FieldPlayer;
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
    FieldPlayer[] player = new FieldPlayer[2];

    public Field getField() {
        return field;
    }

    public NewEngine() throws AgesException {
        init();

    }

    private void init() {
        field = new Field();
        player[0] = field.getP1();
        player[1] = field.getP2();
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
                return 出牌(val);
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
                return NewEngine.this.出牌(p1, p2);

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

        System.out.println("  === ver 0.6 ===  2014-5-13, 12:32, by Max　");
        System.out.println("    1.新增方法 打牌");
        System.out.println("    2.新增方法 拿牌，奇蹟牌完成，領袖牌完成一半");
        
        System.out.println("  === ver 0.5 ===  2014-5-12, 12:32, by Max　");
        System.out.println("    1.處理回合數內的生產");

        System.out.println("  === ver 0.4 ===  2014-5-12, 12:32, by Max　");
        System.out.println("    1.要處理回合數");

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

    private void 執行生產() {
        System.out.println("執行生產");
        int points;
        field.getCurrentPlayer().get文化().setPoints(field.getCurrentPlayer().get文化().getPoints() + field.getCurrentPlayer().get文化生產_當回合().getPoints());
        field.getCurrentPlayer().get科技().setPoints(field.getCurrentPlayer().get科技().getPoints() + field.getCurrentPlayer().get科技生產_當回合().getPoints());
        for (int k = 0; k < 1; k++) {
            field.getCurrentPlayer().get農場區().get(k).produce();
            field.getCurrentPlayer().get礦山區().get(k).produce();
        }
    }

    private void 交換玩家() {
        System.out.println("交換玩家");
        if (field.getCurrentPlayer().equals(player[0])) {
            field.setCurrentPlayer(field.getP2());
        } else if (field.getCurrentPlayer() == field.getP2()) {
            field.setCurrentPlayer(field.getP1());
        }
    }
//    移除卡牌列前三張牌();
//        移動卡牌列();
//        補充新牌();

    private void 移除卡牌列前三張牌() {
        field.getCardRow().remove(0);
        field.getCardRow().remove(1);
        field.getCardRow().remove(2);
    }

    private void 移動卡牌列() {
        Card card;//= field.getCardRow().get(0);//實例化一張牌
        for (int k = 0; k < 13; k++) {//檢視卡牌列
            if (field.getCardRow().get(k).getId() == 1000) {//如果有空牌
                for (int x = k + 1; x < 13; x++) {//找他後面
                    if (field.getCardRow().get(x).getId() != 1000) {//不是空牌
                        //放到card內
//                        card=field.getCardRow().get(k);
                        //移除卡牌列上第x張
                        //把card放在第k張
                        //移除card內的牌
                    }
                }
            }
        }
    }
//        Card card = field.getCardRow().get(val);//宣告 card，並將卡牌列裡的指定編號的卡牌指定到這個card變量裡
//            field.getCurrentPlayer().get手牌內政牌區().add(card);//當前玩家的手牌加入上一行的card
//            field.getCardRow().remove(val);//從卡牌列拿掉剛剛那張card
//            field.getCardRow().add(val, field.getAllCards().get(0));//在卡牌列同樣的位子，補上一張空卡

    private void 補充新牌() {

    }

    private boolean 出牌(int val) {

        System.out.println("...play-card ...");
//        if(field.getCurrentPlayer().get手牌內政牌區().wait(val, val))
        System.out.println("這張是" + field.getCurrentPlayer().get手牌內政牌區().get(val).getTag());
        switch (field.getCurrentPlayer().get手牌內政牌區().get(val).getTag()) {
            case "行動":
                System.out.println("我是行動牌");
                break;
            case "領袖":
                System.out.println("我是領袖牌");
                break;
            case "奇蹟":
                System.out.println("這裡不該有奇蹟牌");
                break;
            //↓灰色區塊
            case "農場":
                System.out.println("我是農場牌");
//                field.getCurrentPlayer().get步兵區().get(0).
                break;
            case "礦山":
                System.out.println("我是礦山牌");
                break;
            case "實驗室":
                System.out.println("我是實驗室牌");
                break;
            case "神廟":
                System.out.println("我是神廟牌");
                break;
            case "競技場":
                System.out.println("我是競技場牌");
                break;
            case "圖書館":
                System.out.println("我是圖書館牌");
                break;
            case "劇院":
                System.out.println("我是劇院牌");
                break;
            //↑程式建築物灰色區塊
            case "政府":
                System.out.println("我是政府牌");
                break;
            //↓特殊科技藍色區塊
            case "軍事":
                System.out.println("我是特殊科技-軍事牌");
                break;
            case "內政":
                System.out.println("我是特殊科技-內政牌");
                break;
            case "殖民":
                System.out.println("我是特殊科技-殖民牌");
                break;
            case "建築":
                System.out.println("我是特殊科技-建築牌");
                break;
            //↑特殊科技藍色區塊
            //↓軍事科技紅色區塊
            case "步兵":
                System.out.println("我是步兵牌");
                break;
            case "騎兵":
                System.out.println("我是騎兵牌");
                break;
            case "炮兵":
                System.out.println("我是炮兵牌");
                break;
            case "飛機":
                System.out.println("我是飛機牌");
                break;
            //↑軍事科技紅色區塊
            default:
//                System.out.println("Unknown keyword, " + keyword);
                return false;
        }

        return true;
    }

    private boolean doTakeCard(int val) {
        Card card = field.getCardRow().get(val);//宣告 card，並將卡牌列裡的指定編號的卡牌指定到這個card變量裡
        System.out.println("拿牌中...拿取" + card.getName());

        switch (card.getTag()) {
            case "行動":
                System.out.println("我是行動牌");
                break;

            case "領袖":
                System.out.println("我是領袖牌");
                if (field.getCurrentPlayer().get領袖區().get(card.getAge()).getId() == 1000) {
                    System.out.println("這是空的可以擺置該時代領袖牌");
//                    field.getCurrentPlayer().get領袖區().remove(card.getAge());

//                    #######################################################################
//                    romove吃不到card.getAge()
                    System.out.println(card.getAge());
                    field.getCurrentPlayer().get領袖區().remove(0);

                    System.out.println(field.getCurrentPlayer().get領袖區().get(0));
                    field.getCurrentPlayer().get領袖區().add(card.getAge(), card);
                    System.out.println("時代" + card.getAge() + "拿了" + card.getName());
                    field.getCurrentPlayer().get手牌內政牌區().add(card);//當前玩家的手牌加入上一行的card
                    field.getCardRow().remove(val);//從卡牌列拿掉剛剛那張card
                    field.getCardRow().add(val, field.getAllCards().get(0));//在卡牌列同樣的位子，補上一張空卡
                } else {
                    System.out.println("你已經拿過該時代領袖牌");
                }
                //檢測該牌的時代的領袖區是不是為空
//                System.out.println(field.getCurrentPlayer().get領袖區().get(0));
                break;
            case "奇蹟":
                System.out.println("我是奇蹟牌");
                if (field.getCurrentPlayer().get建造中的奇蹟區().size() == 0) {
                    field.getCurrentPlayer().get建造中的奇蹟區().add(card);//當前玩家的手牌加入上一行的card
                    field.getCardRow().remove(val);//從卡牌列拿掉剛剛那張card
                    field.getCardRow().add(val, field.getAllCards().get(0));//在卡牌列同樣的位子，補上一張空卡
                } else {
                    System.out.println("你有未建造完成的奇蹟");
                }
                break;
            //↓灰色區塊
            case "農場":
                System.out.println("我是農場牌");
                break;
            case "礦山":
                System.out.println("我是礦山牌");
                break;
            case "實驗室":
                System.out.println("我是實驗室牌");
                break;
            case "神廟":
                System.out.println("我是神廟牌");
                break;
            case "競技場":
                System.out.println("我是競技場牌");
                break;
            case "圖書館":
                System.out.println("我是圖書館牌");
                break;
            case "劇院":
                System.out.println("我是劇院牌");
                break;
            //↑程式建築物灰色區塊
            case "政府":
                System.out.println("我是政府牌");
                break;
            //↓特殊科技藍色區塊
            case "軍事":
                System.out.println("我是特殊科技-軍事牌");
                break;
            case "內政":
                System.out.println("我是特殊科技-內政牌");
                break;
            case "殖民":
                System.out.println("我是特殊科技-殖民牌");
                break;
            case "建築":
                System.out.println("我是特殊科技-建築牌");
                break;
            //↑特殊科技藍色區塊
            //↓軍事科技紅色區塊
            case "步兵":
                System.out.println("我是步兵牌");
                break;
            case "騎兵":
                System.out.println("我是騎兵牌");
                break;
            case "炮兵":
                System.out.println("我是炮兵牌");
                break;
            case "飛機":
                System.out.println("我是飛機牌");
                break;
            default:
//                System.out.println("Unknown keyword, " + keyword);
                return false;
        }

//        Card card = field.getCardRow().get(val);//宣告 card，並將卡牌列裡的指定編號的卡牌指定到這個card變量裡
//        if (!card.getTag().equals("奇蹟")) {
//
//            field.getCurrentPlayer().get手牌內政牌區().add(card);//當前玩家的手牌加入上一行的card
//            field.getCardRow().remove(val);//從卡牌列拿掉剛剛那張card
//            field.getCardRow().add(val, field.getAllCards().get(0));//在卡牌列同樣的位子，補上一張空卡
//        }
//        field.
        return true;
    }

    private boolean doStart() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        for (int k = 0; k < 2; k++) {

            player[k].get步兵區().get(0).setTokenYellow(1);
            player[k].get實驗室().get(0).setTokenYellow(1);
            player[k].get礦山區().get(0).setTokenYellow(2);
            player[k].get農場區().get(0).setTokenYellow(2);
            player[k].get人力庫_黃點().setPoints(18);
            player[k].get工人區_黃點().setPoints(1);
            player[k].get資源庫_藍點().setPoints(18);

            player[k].get領袖區().add(0, field.getAllCards().get(0));
            player[k].get領袖區().add(1, field.getAllCards().get(0));
            player[k].get領袖區().add(2, field.getAllCards().get(0));
            player[k].get領袖區().add(3, field.getAllCards().get(0)); //                .getAllCards().get(0));
        }
        System.out.println(player[1].get領袖區());
        return true;
    }

    private boolean doChangeTurn() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

//        檢測暴動();
        執行生產();
//        腐敗();
        交換玩家();
//        移除卡牌列前三張牌();
//        移動卡牌列();
//        補充新牌();
        //        field.get
//    private List<Card> ageICivilCards;
//    ageICivilCards  = new ArrayList<>();

        return true;
    }

    private boolean 出牌(int p1, int p2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        
//        field.getCurrentPlayer().get手牌內政牌區()
    }

}
