import it.unisa.dia.gas.jpbc.Element;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DUClass {
    List<String> Atts;
    String name;
    Element uid;
    Element xi;
    Element[] kiList;
    Element[] LiList;
    Element[] kattList;
    Element K;
    Element L;
    Element[] Katt;
    int[] randAAs;
    ArrayList<Element> omega;
    ArrayList<Integer> iList;
    ArrayList<String> rouu;

    public DUClass(List<String> Atts, int[] randAAs, String name){
        this.Atts = Atts;
        this.name = name;
        this.xi = UI.Zp.newRandomElement();
        this.uid = UI.Zp.newRandomElement();
        this.kiList = new Element[UI.t];
        this.LiList = new Element[UI.t];
        this.kattList = new Element[UI.t * Atts.size()];
        this.Katt = new Element[Atts.size()];
        this.randAAs = randAAs;
    }

    public List<String> getAtts(){
        return Atts;
    }

    public void saveKeyShare(Element[] keyShares, int i) {
        this.kiList[i] = keyShares[0];
        this.LiList[i] = keyShares[1];
        for(int k = 0; k < Atts.size(); k++){
            this.kattList[k + Atts.size() * i] = keyShares[2 + k];
        }
    }

    public void getKeyShare(){
        for(int i = 0; i < UI.t; i++){
            saveKeyShare(UI.node[randAAs[i]].genKeyShare(Atts), i);
        }
    }

    public void calculateDeKey(){
        Element pow;
        for(int i = 0; i < UI.t; i++){
            pow = UI.Zp.newElement(1);
            for(int j = 0; j < UI.t; j++){
                if(i != j){
                    pow.mulZn(UI.node[randAAs[j]].getAid().div(UI.node[randAAs[j]].getAid().sub(UI.node[randAAs[i]].getAid())));
                }
            }
            if(i == 0){
                this.K = kiList[i].duplicate().powZn(pow);
                this.L = LiList[i].duplicate().powZn(pow);
                for(int k = 0; k < Atts.size(); k++){
                    this.Katt[k] = kattList[k].duplicate().powZn(pow);
                }
            }
            else{
                this.K.mul(kiList[i].duplicate().powZn(pow));
                this.L.mul(LiList[i].duplicate().powZn(pow));
                for(int k = 0; k < Atts.size(); k++){
                    this.Katt[k].mul(kattList[k + i * Atts.size()].duplicate().powZn(pow));
                }
            }
        }
    }

    public Element getTheta(){
        Element miu = UI.Zp.newRandomElement();
        return miu.mulZn(xi);
    }

    public Element calculateHE(ArrayList<Element[]> Mu, ArrayList<Element> Nu, ArrayList<Integer> iList, ArrayList<String> rouu){
        this.iList = iList;
        this.rouu = rouu;
        int column = Mu.get(0).length;
        Boolean rep = false;
        for(int i = 0; i < Mu.size(); i++){
            for(int j = 0; j < Mu.size(); j++){
                rep = false;
                if(i != j){
                    for(int k = 0; k < Mu.get(0).length; k++){
                        if(!Mu.get(i)[k].equals(Mu.get(j)[k])){
                            break;
                        }
                        if(k == Mu.get(0).length - 1){
                            rep = true;
                        }
                    }
                    if(rep){
                        Mu.remove(j);
                        Nu.remove(j);
                        this.iList.remove(j);
                        this.rouu.remove(j);
                        j--;
                    }
                }

            }
        }

        Element[] eVector = new Element[column];
        eVector[0] = UI.Zp.newOneElement();
        for(int i = 1; i < column; i++){
            eVector[i] = UI.Zp.newZeroElement();
        }

        this.omega = Functions.getOmega(Mu);

        Element HE = UI.Zp.newZeroElement();
        for(int i = 0 ; i < Mu.size(); i++){
            HE.add(omega.get(i).duplicate().mulZn(Nu.get(i)));
        }

        return HE;

    }

    public String decrypt(String Ekm, Element[] CT, int row){
        Element ec2k = UI.bp.pairing(CT[1].duplicate(), K.duplicate());
        Element ec3c4k;
        int index;
        Element ec3c4komega = UI.GT.newRandomElement();
        for(int k = 0; k < iList.size(); k++){
            index = Atts.indexOf(rouu.get(k));
            ec3c4k = UI.bp.pairing(CT[2 + iList.get(k)].duplicate(), L.duplicate()).mul(UI.bp.pairing(CT[2 + row + iList.get(k)].duplicate(), Katt[index].duplicate()));
            ec3c4k.powZn(omega.get(k));
            if(k == 0){
                ec3c4komega = ec3c4k;
            }
            else{
                ec3c4komega.mul(ec3c4k);
            }
        }
        Element Cu = ec2k.duplicate().div(ec3c4komega);
        Element kappa = CT[0].duplicate().div(Cu);

        String c = Functions.decrypt(Ekm, kappa.toString());
        return c;

    }

}
