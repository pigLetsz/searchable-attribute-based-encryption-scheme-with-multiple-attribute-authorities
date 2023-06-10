import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UI extends Application {

    public static Pairing bp = PairingFactory.getPairing("a.properties");

    public static Field Zp = bp.getZr();
    public static Field G1 = bp.getG1();
    public static Field GT = bp.getGT();

    public static int t;
    public static int n;
    public static AAClass[] node;
    public static Element alpha = Zp.newRandomElement();
    public static Element g = G1.newRandomElement();
    Element egg = bp.pairing(g, g);
    public static Element h = G1.newRandomElement();
    public static Element egga = GT.newRandomElement();
    //public static Element w = G1.newRandomElement();
    //public static Element v = G1.newRandomElement();
    public static Element[] hList = new Element[10];
    public static List<String> U_set = new ArrayList<String>(){{
        add("属性1");
        add("属性2");
        add("属性3");
        add("属性4");
        add("属性5");
        add("属性6");
        add("属性7");
        add("属性8");
        add("属性9");
        add("属性10");
    }};

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws NoSuchAlgorithmException {
        ArrayList<List<String>> accessStructureList = new ArrayList<List<String>>();
        ArrayList<DUClass> dataUsers = new ArrayList<DUClass>();
        ArrayList<dataInCSP> datas = new ArrayList<dataInCSP>();
        for(int i = 0; i < 10; i++){
            hList[i] = G1.newRandomElement();
        }

        DOClass DO1 = new DOClass();

        primaryStage.setTitle("可搜索属性基加密");
        String absolutePath = System.getProperty("user.dir").replace("\\", "/");
        primaryStage.getIcons().add(new Image("file:" + absolutePath + "/1.jpg"));
        //  ————————————————————左侧的菜单按钮
        Button mainViewButton = new Button("初始化");
        mainViewButton.setPrefSize(120, 120);
        mainViewButton.setStyle("-fx-font: 20 arial");

        Button DOButton = new Button("访问结构");
        DOButton.setPrefSize(120, 120);
        DOButton.setStyle("-fx-font: 20 arial");

        Button searchButton = new Button("搜索");
        searchButton.setPrefSize(120, 120);
        searchButton.setStyle("-fx-font: 20 arial");

        Button DUButton = new Button("创建用户");
        DUButton.setPrefSize(120, 120);
        DUButton.setStyle("-fx-font: 20 arial");

        Button enButton = new Button("加密");
        enButton.setPrefSize(120, 120);
        enButton.setStyle("-fx-font: 20 arial");

        VBox viewChoiceBar = new VBox();
        viewChoiceBar.setPrefSize(120, 680);
        viewChoiceBar.setSpacing(20);
        viewChoiceBar.getChildren().addAll(mainViewButton, DOButton, enButton, DUButton, searchButton);

        Button set_t_n = new Button("初始化");
        set_t_n.setLayoutX(480);
        set_t_n.setLayoutY(420);
        set_t_n.setPrefSize(100, 60);
        set_t_n.setStyle("-fx-font: 20 arial");

        TextField text_t = new TextField();
        text_t.setPrefSize(100, 30);
        text_t.setLayoutX(380);
        text_t.setLayoutY(250);
        text_t.setStyle("-fx-font: 20 arial");

        Label label_t = new Label("t:");
        label_t.setPrefSize(100, 30);
        label_t.setLayoutX(330);
        label_t.setLayoutY(250);
        label_t.setStyle("-fx-font: 20 arial");

        TextField text_n = new TextField();
        text_n.setPrefSize(100, 30);
        text_n.setLayoutX(600);
        text_n.setLayoutY(250);
        text_n.setStyle("-fx-font: 20 arial");

        Label label_n = new Label("n:");
        label_n.setPrefSize(100, 30);
        label_n.setLayoutX(550);
        label_n.setLayoutY(250);
        label_n.setStyle("-fx-font: 20 arial");

        BorderPane defaultLayout = new BorderPane();
        AnchorPane defaultPlace = new AnchorPane();
        Scene defaultScene = new Scene(defaultLayout, 1280, 710);
        defaultLayout.setLeft(viewChoiceBar);
        defaultLayout.setCenter(defaultPlace);
        defaultPlace.getChildren().addAll(set_t_n, label_n, label_t, text_n, text_t);

        Label pleaseChooseAtt2 = new Label("选择属性:");
        pleaseChooseAtt2.setPrefSize(150, 30);
        pleaseChooseAtt2.setLayoutX(300);
        pleaseChooseAtt2.setLayoutY(350);
        pleaseChooseAtt2.setStyle("-fx-font: 20 arial");

        ChoiceBox<String> choiceAtt = new ChoiceBox();
        for (String s : U_set) {
            choiceAtt.getItems().add(s);
        }
        choiceAtt.setPrefSize(240, 40);
        choiceAtt.setLayoutX(400);
        choiceAtt.setLayoutY(400);choiceAtt.setStyle("-fx-font: 20 arial");

        Button addAtt = new Button("添加属性");
        addAtt.setLayoutX(450);
        addAtt.setLayoutY(500);
        addAtt.setPrefSize(120, 30);
        addAtt.setStyle("-fx-font: 20 arial");

        Button createDU = new Button("创建用户");
        createDU.setLayoutX(450);
        createDU.setLayoutY(600);
        createDU.setPrefSize(120, 30);
        createDU.setStyle("-fx-font: 20 arial");

        Label DOChoosedAtts2 = new Label("已选择属性:");
        DOChoosedAtts2.setPrefSize(150, 30);
        DOChoosedAtts2.setLayoutX(800);
        DOChoosedAtts2.setLayoutY(150);
        DOChoosedAtts2.setStyle("-fx-font: 20 arial");

        ListView<String> listViewAtts = new ListView<String>();
        listViewAtts.setLayoutX(850);
        listViewAtts.setLayoutY(200);
        listViewAtts.setPrefWidth(100);
        listViewAtts.setPrefHeight(300);
        listViewAtts.setStyle("-fx-font: 20 arial");

        Label label_name = new Label("用户名称:");
        label_name.setPrefSize(100, 30);
        label_name.setLayoutX(200);
        label_name.setLayoutY(150);
        label_name.setStyle("-fx-font: 20 arial");

        TextField DUname = new TextField();
        DUname.setPrefSize(150, 30);
        DUname.setLayoutX(300);
        DUname.setLayoutY(200);
        DUname.setStyle("-fx-font: 20 arial");

        Button clearChoosenAtts = new Button("清空列表");
        clearChoosenAtts.setLayoutX(850);
        clearChoosenAtts.setLayoutY(600);
        clearChoosenAtts.setPrefSize(120, 30);
        clearChoosenAtts.setStyle("-fx-font: 20 arial");

        BorderPane createUserLayout = new BorderPane();
        AnchorPane createUserPlace = new AnchorPane();
        Scene createUserScene = new Scene(createUserLayout, 1280, 710);
        createUserPlace.getChildren().addAll(pleaseChooseAtt2, DOChoosedAtts2, choiceAtt, addAtt, createDU, listViewAtts, label_name, DUname, clearChoosenAtts);

        Label kwList_label2 = new Label("关键词列表:");
        kwList_label2.setPrefSize(150, 30);
        kwList_label2.setLayoutX(850);
        kwList_label2.setLayoutY(210);
        kwList_label2.setStyle("-fx-font: 20 arial");

        ListView<String> listViewSearchKWs = new ListView<String>();
        listViewSearchKWs.setLayoutX(900);
        listViewSearchKWs.setLayoutY(260);
        listViewSearchKWs.setPrefWidth(100);
        listViewSearchKWs.setPrefHeight(300);
        listViewSearchKWs.setStyle("-fx-font: 20 arial");

        Button addKW = new Button("添加关键词");
        addKW.setLayoutX(850);
        addKW.setLayoutY(100);
        addKW.setPrefSize(150, 30);
        addKW.setStyle("-fx-font: 20 arial");

        Button clearKWList = new Button("清空列表");
        clearKWList.setLayoutX(850);
        clearKWList.setLayoutY(600);
        clearKWList.setPrefSize(150, 30);
        clearKWList.setStyle("-fx-font: 20 arial");

        Button createTW = new Button("搜索");
        createTW.setLayoutX(400);
        createTW.setLayoutY(600);
        createTW.setPrefSize(100, 30);
        createTW.setStyle("-fx-font: 20 arial");

        Label kw_label2 = new Label("关键词:");
        kw_label2.setPrefSize(150, 30);
        kw_label2.setLayoutX(700);
        kw_label2.setLayoutY(30);
        kw_label2.setStyle("-fx-font: 20 arial");

        TextField KeyWord = new TextField();
        KeyWord.setPrefSize(150, 30);
        KeyWord.setLayoutX(800);
        KeyWord.setLayoutY(30);
        KeyWord.setStyle("-fx-font: 20 arial");

        Label label_result = new Label("搜索结果:");
        label_result.setPrefSize(100, 30);
        label_result.setLayoutX(200);
        label_result.setLayoutY(450);
        label_result.setStyle("-fx-font: 20 arial");

        TextField deResult = new TextField();
        deResult.setPrefSize(300, 30);
        deResult.setLayoutX(300);
        deResult.setLayoutY(500);
        deResult.setEditable(false);
        deResult.setStyle("-fx-font: 20 arial");

        Label pleaseChooseDU = new Label("选择用户：");
        pleaseChooseDU.setPrefSize(300, 30);
        pleaseChooseDU.setLayoutX(80);
        pleaseChooseDU.setLayoutY(250);
        pleaseChooseDU.setStyle("-fx-font: 20 arial");

        ChoiceBox<String> chooseDU = new ChoiceBox();
        chooseDU.setPrefSize(240, 40);
        chooseDU.setLayoutX(150);
        chooseDU.setLayoutY(300);
        chooseDU.setStyle("-fx-font: 20 arial");

        Label nowDUAtts = new Label("当前用户属性：");
        nowDUAtts.setPrefSize(1500, 30);
        nowDUAtts.setLayoutX(80);
        nowDUAtts.setLayoutY(150);
        nowDUAtts.setStyle("-fx-font: 20 arial");

        BorderPane searchLayout = new BorderPane();
        AnchorPane searchPlace = new AnchorPane();
        Scene searchScene = new Scene(searchLayout, 1280, 710);
        searchPlace.getChildren().addAll(kwList_label2, kw_label2, pleaseChooseDU, listViewSearchKWs, addKW, createTW, KeyWord, label_result, deResult, chooseDU, nowDUAtts, clearKWList);

        Label accessStructure = new Label("访问结构:");
        accessStructure.setPrefSize(200, 30);
        accessStructure.setLayoutX(50);
        accessStructure.setLayoutY(150);
        accessStructure.setStyle("-fx-font: 20 arial");

        Label DOChoosedAtts = new Label("已选择属性:");
        DOChoosedAtts.setPrefSize(150, 30);
        DOChoosedAtts.setLayoutX(100);
        DOChoosedAtts.setLayoutY(300);
        DOChoosedAtts.setStyle("-fx-font: 20 arial");

        Label pleaseChooseAtt = new Label("选择属性:");
        pleaseChooseAtt.setPrefSize(150, 30);
        pleaseChooseAtt.setLayoutX(400);
        pleaseChooseAtt.setLayoutY(350);
        pleaseChooseAtt.setStyle("-fx-font: 20 arial");

        ChoiceBox<String> choiceAtt2 = new ChoiceBox();
        for (String s : U_set) {
            choiceAtt2.getItems().add(s);
        }
        choiceAtt2.setPrefSize(240, 50);
        choiceAtt2.setLayoutX(420);
        choiceAtt2.setLayoutY(400);
        choiceAtt2.setStyle("-fx-font: 20 arial");

        Label bottomPolicy = new Label("底层策略:");
        bottomPolicy.setPrefSize(150, 30);
        bottomPolicy.setLayoutX(830);
        bottomPolicy.setLayoutY(150);
        bottomPolicy.setStyle("-fx-font: 20 arial");

        ChoiceBox<String> and_or = new ChoiceBox();
        and_or.getItems().add("与");
        and_or.getItems().add("或");
        and_or.getSelectionModel().select(0);
        and_or.setPrefSize(240, 50);
        and_or.setLayoutX(850);
        and_or.setLayoutY(200);
        and_or.setStyle("-fx-font: 20 arial");

        TextField nowAccessStructure = new TextField();
        nowAccessStructure.setPrefSize(420, 50);
        nowAccessStructure.setLayoutX(200);
        nowAccessStructure.setLayoutY(150);
        nowAccessStructure.setEditable(false);
        nowAccessStructure.setStyle("-fx-font: 20 arial");

        ListView<String> listViewChosenAtts = new ListView<String>();
        listViewChosenAtts.setLayoutX(150);
        listViewChosenAtts.setLayoutY(350);
        listViewChosenAtts.setPrefWidth(100);
        listViewChosenAtts.setPrefHeight(300);
        listViewChosenAtts.setStyle("-fx-font: 20 arial");

        Button addtolist = new Button("添加到列表");
        addtolist.setLayoutX(460);
        addtolist.setLayoutY(480);
        addtolist.setPrefSize(130, 30);
        addtolist.setStyle("-fx-font: 20 arial");

        Button createE = new Button("添加访问结构");
        createE.setLayoutX(880);
        createE.setLayoutY(320);
        createE.setPrefSize(180, 50);
        createE.setStyle("-fx-font: 20 arial");

        Button resetAccessStructure = new Button("重置");
        resetAccessStructure.setLayoutX(880);
        resetAccessStructure.setLayoutY(450);
        resetAccessStructure.setPrefSize(180, 50);
        resetAccessStructure.setStyle("-fx-font: 20 arial");

        BorderPane ownerLayout = new BorderPane();
        AnchorPane ownerPlace = new AnchorPane();
        Scene ownerScene = new Scene(ownerLayout, 1280, 710);
        ownerPlace.getChildren().addAll(accessStructure, DOChoosedAtts, pleaseChooseAtt, bottomPolicy, choiceAtt2, and_or, nowAccessStructure, listViewChosenAtts, addtolist, createE, resetAccessStructure);

        Button encrypt = new Button("加密");
        encrypt.setLayoutX(400);
        encrypt.setLayoutY(500);
        encrypt.setPrefSize(80, 30);
        encrypt.setStyle("-fx-font: 20 arial");

        Label kwList_label = new Label("关键词列表:");
        kwList_label.setPrefSize(150, 30);
        kwList_label.setLayoutX(800);
        kwList_label.setLayoutY(260);
        kwList_label.setStyle("-fx-font: 20 arial");

        ListView<String> listViewKeywords = new ListView<String>();
        listViewKeywords.setLayoutX(850);
        listViewKeywords.setLayoutY(310);
        listViewKeywords.setPrefWidth(100);
        listViewKeywords.setPrefHeight(300);
        listViewKeywords.setStyle("-fx-font: 20 arial");

        Button clearKWList2 = new Button("清空列表");
        clearKWList2.setLayoutX(850);
        clearKWList2.setLayoutY(150);
        clearKWList2.setPrefSize(150, 30);
        clearKWList2.setStyle("-fx-font: 20 arial");

        Button addKW2 = new Button("添加关键词");
        addKW2.setLayoutX(350);
        addKW2.setLayoutY(150);
        addKW2.setPrefSize(150, 30);
        addKW2.setStyle("-fx-font: 20 arial");

        Label kw_label = new Label("关键词:");
        kw_label.setPrefSize(150, 30);
        kw_label.setLayoutX(200);
        kw_label.setLayoutY(50);
        kw_label.setStyle("-fx-font: 20 arial");

        TextField KeyWord2 = new TextField();
        KeyWord2.setPrefSize(150, 30);
        KeyWord2.setLayoutX(300);
        KeyWord2.setLayoutY(50);
        KeyWord2.setStyle("-fx-font: 20 arial");

        TextField text_en = new TextField();
        text_en.setPrefSize(300, 30);
        text_en.setLayoutX(250);
        text_en.setLayoutY(400);
        text_en.setStyle("-fx-font: 20 arial");

        Label label_en = new Label("明文:");
        label_en.setPrefSize(100, 30);
        label_en.setLayoutX(250);
        label_en.setLayoutY(350);
        label_en.setStyle("-fx-font: 20 arial");

        BorderPane enLayout = new BorderPane();
        AnchorPane enPlace = new AnchorPane();
        Scene enScene = new Scene(enLayout, 1280, 710);
        enPlace.getChildren().addAll(kwList_label, kw_label, encrypt, listViewKeywords, addKW2, KeyWord2, text_en, label_en, clearKWList2);

        mainViewButton.setOnAction(actionEvent -> {
            defaultLayout.setLeft(viewChoiceBar);
            defaultLayout.setCenter(defaultPlace);
            primaryStage.setScene(defaultScene);
        });

        DUButton.setOnAction(actionEvent -> {
            createUserLayout.setLeft(viewChoiceBar);
            createUserLayout.setCenter(createUserPlace);
            primaryStage.setScene(createUserScene);
        });

        searchButton.setOnAction(actionEvent -> {
            searchLayout.setLeft(viewChoiceBar);
            searchLayout.setCenter(searchPlace);
            primaryStage.setScene(searchScene);
        });

        DOButton.setOnAction(actionEvent -> {
            ownerLayout.setLeft(viewChoiceBar);
            ownerLayout.setCenter(ownerPlace);
            primaryStage.setScene(ownerScene);
        });

        enButton.setOnAction(actionEvent -> {
            enLayout.setLeft(viewChoiceBar);
            enLayout.setCenter(enPlace);
            primaryStage.setScene(enScene);
        });

        createE.setOnAction(actionEvent -> {
            String logic = and_or.getSelectionModel().getSelectedItem();
            String out_logic;
            Boolean first;
            String[] atts = listViewChosenAtts.getItems().toArray(new String[0]);
            List<String> choicedAtts = new ArrayList<String>();
            for(int i = 0; i < atts.length; i++){
                choicedAtts.add(atts[i]);
            }
            Boolean valid = true;
            for(int i = 0; i < accessStructureList.size(); i++){
                if(choicedAtts.containsAll(accessStructureList.get(i)) || accessStructureList.get(i).containsAll(choicedAtts)){
                    valid = false;
                }
            }
            /*for(int i = 0; i < accessStructureList.size(); i++){
                for(int j = 0; j < accessStructureList.get(i).size(); j++){
                    if(!choicedAtts.contains(accessStructureList.get(i).get(j))){
                        break;
                    }
                    else{
                        if(j == choicedAtts.size() - 1){
                            valid = false;
                            break;
                        }
                    }
                }
            }*/
            listViewChosenAtts.getItems().clear();
            if(valid){
                accessStructureList.add(choicedAtts);
                //listViewChosenAtts.getItems().clear();
                if(and_or.isDisabled()){
                    first = false;
                }
                else{
                    first = true;
                }
                and_or.setDisable(true);
                if(logic.equals("或")){
                    logic = "∨";
                    out_logic = "∧";
                }
                else{
                    logic = "∧";
                    out_logic = "∨";
                }
                if(first) {
                    nowAccessStructure.setText("(");
                }
                else{
                    nowAccessStructure.setText(nowAccessStructure.getText() + out_logic + "(");
                }

                for(int i = 0; i < choicedAtts.size(); i++){
                    if(i != choicedAtts.size() - 1){
                        nowAccessStructure.setText(nowAccessStructure.getText() + choicedAtts.get(i) + logic);
                    }
                    else{
                        nowAccessStructure.setText(nowAccessStructure.getText() + choicedAtts.get(i));
                    }
                }
                nowAccessStructure.setText(nowAccessStructure.getText() + ")");

            }


        });

        resetAccessStructure.setOnAction(actionEvent -> {
            nowAccessStructure.clear();
            and_or.setDisable(false);
            listViewChosenAtts.getItems().clear();
            accessStructureList.clear();
        });

        addtolist.setOnAction(actionEvent -> {
            String att = choiceAtt2.getSelectionModel().getSelectedItem();
            if (!listViewChosenAtts.getItems().contains(att)){
                listViewChosenAtts.getItems().add(att);
            }

        });

        addKW2.setOnAction(actionEvent -> {
            String s = KeyWord2.getText();
            listViewKeywords.getItems().add(s);
        });

        encrypt.setOnAction(actionEvent -> {
            String[] keywords = listViewKeywords.getItems().toArray(new String[0]);
            Element[] I = new Element[2];
            try{
                I = DO1.calculateI(keywords);
            } catch (Exception e){
                e.printStackTrace();
            }

            String m = text_en.getText();
            String Ekm = DO1.gainEk(m);
            Element HE = DO1.gainHE(Ekm);
            String logic = and_or.getSelectionModel().getSelectedItem();

            Element[][] M = DO1.gainM(logic, accessStructureList);
            String[] rou = DO1.gainRou(accessStructureList);
            Element[] N = DO1.gainN(M.length, M, HE);
            /*Element[] tVector = new Element[M[0].length];
            tVector[0] = HE.duplicate();
            for(int i = 1; i < M[0].length; i++){
                tVector[i] = Zp.newRandomElement();
            }
            Element count;
            for(int i = 0; i < M.length; i++){
                count = Zp.newZeroElement();
                for(int j = 0; j < M[0].length; j++){
                    count.add(M[i][j].duplicate().mulZn(tVector[j]));
                }
                N[i] = count.duplicate();
            }*/
            Element[] CT = DO1.gainCT(M.length, M[0].length, rou, M);

            dataInCSP newdata = new dataInCSP(I, Ekm, HE, N, CT, M, rou);
            datas.add(newdata);
        });

        addAtt.setOnAction(actionEvent -> {
            //ArrayList<String> choicedAtts = new ArrayList<String>();
            String att = choiceAtt.getSelectionModel().getSelectedItem();
            if (!listViewAtts.getItems().contains(att)){
                listViewAtts.getItems().add(att);
            }

        });

        createDU.setOnAction(actionEvent -> {
            String[] choicedAtts = listViewAtts.getItems().toArray(new String[0]);
            listViewAtts.getItems().clear();
            List<String> atts = new ArrayList<String>();
            for(int i = 0; i < choicedAtts.length; i++){
                atts.add(choicedAtts[i]);
            }
            int[] randAAs = Functions.gainRandomT(t, n);
            DUClass dataUser = new DUClass(atts, randAAs, DUname.getText());

            dataUser.getKeyShare();
            dataUser.calculateDeKey();
            chooseDU.getItems().add(dataUser.name);
            dataUsers.add(dataUser);
            DUname.clear();

        });

        addKW.setOnAction(actionEvent -> {
            String s = KeyWord.getText();
            listViewSearchKWs.getItems().add(s);
        });

        chooseDU.setOnAction(event -> {
            int num = chooseDU.getSelectionModel().getSelectedIndex();
            int t = dataUsers.get(num).Atts.size();
            String res = "当前用户属性：";
            for(int i = 0; i < t; i++){
                res += dataUsers.get(num).Atts.get(i);
                if(i != t - 1){
                    res += "、";
                }
            }
            nowDUAtts.setText(res);
        });

        createTW.setOnAction(actionEvent -> {
            int select = chooseDU.getSelectionModel().getSelectedIndex();
            String[] keywords = listViewSearchKWs.getItems().toArray(new String[0]);
            Element theta = dataUsers.get(select).getTheta();
            Random random = new Random();
            int i = random.nextInt(n);
            Element[] TW = new Element[3];
            try{
                TW = node[i].getTW(DO1.getPK(), theta, keywords);
            } catch (Exception e){
                e.printStackTrace();
            }
            int index = 0;
            Boolean had = false;
            for(i = 0; i < datas.size(); i++){
                Element[] I = datas.get(i).getI();
                if(bp.pairing(TW[1].duplicate(), I[0].duplicate()).mul(I[1].duplicate().powZn(TW[0])).equals(TW[2])){
                    index = i;
                    had = true;
                    break;
                }
            }
            if(!had){
                deResult.setText("无匹配密文");
            }
            else{
                String Ekm = datas.get(index).getEkm();
                Element HE = datas.get(index).getHE();
                Element[] N = datas.get(index).getN();
                Element[] CT = datas.get(index).getCT();
                Element[][] M = datas.get(index).getM();
                String[] rou = datas.get(index).getRou();

                List<String> Su = dataUsers.get(select).getAtts();
                ArrayList<Element> Nu = new ArrayList<Element>();
                ArrayList<Element[]> Mu = new ArrayList<Element[]>();
                ArrayList<Integer> is = new ArrayList<Integer>();
                ArrayList<String> rouu = new ArrayList<String>();
                for(i = 0; i < rou.length; i++){
                    if(Su.contains(rou[i])){
                        Nu.add(N[i]);
                        Mu.add(M[i]);
                        is.add(i);
                        rouu.add(rou[i]);
                    }
                }
                if(Mu.size() == 0){
                    deResult.setText("访问策略不匹配");
                }
                else{
                    Element HE1 = dataUsers.get(select).calculateHE(Mu, Nu, is, rouu);
                    String result;
                    if(HE1.equals(HE)){
                        result = dataUsers.get(select).decrypt(Ekm, CT, M.length);
                    }
                    else{
                        result = "访问策略不匹配";
                    }
                    deResult.setText(result);
                }
            }


        });

        set_t_n.setOnAction(actionEvent -> {
            t = Integer.parseInt(text_t.getText());
            n = Integer.parseInt(text_n.getText());
            node = new AAClass[n];//设置阈值

            for (int i = 0; i < n; i++){
                node[i] = new AAClass();
            };

            for (int i = 0; i < n; i++){
                node[i].otherSK();
            }
            for (int i = 0; i < n; i++){
                node[i].savePk();
            }

            Random random = new Random();
            int nodeNum = random.nextInt(n);
            egga = node[nodeNum].calculateEgga();
        });

        clearKWList.setOnAction(event -> {
            listViewSearchKWs.getItems().clear();
        });

        clearKWList2.setOnAction(event -> {
            listViewKeywords.getItems().clear();
        });

        clearChoosenAtts.setOnAction(event -> {
            listViewAtts.getItems().clear();
        });

        primaryStage.setScene(defaultScene);
        primaryStage.show();
    }

}
