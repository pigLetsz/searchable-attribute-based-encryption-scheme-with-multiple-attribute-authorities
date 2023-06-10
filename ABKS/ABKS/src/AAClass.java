import it.unisa.dia.gas.jpbc.Element;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class AAClass {
    Element aid;
    Element a;
    Element sk;
    Element pk;
    Element[] coefficients;


    public AAClass(){
        this.aid = UI.Zp.newRandomElement();
        this.a = UI.Zp.newRandomElement();
        setCoefficients();
        getSelfS();

    }

    public void otherSK(){
        for(int i = 0; i < UI.n; i++) {
            if (!this.aid.equals(UI.node[i].aid)) {
                UI.node[i].addSk(getS(UI.node[i].getAid()));
            }
        }
    }

    public void setCoefficients(){
        this.coefficients = new Element[UI.t];
        this.coefficients[0] = a.duplicate();
        for(int i = 1; i < UI.t; i++){
            this.coefficients[i] = UI.Zp.newRandomElement();
        }
    }

    public Element getAid(){
        return aid.duplicate();
    }

    public Element getPk(){
        return pk.duplicate();
    }

    public void getSelfS(){
        this.sk = coefficients[0].duplicate();
        for(int i = 1; i < UI.t; i++){
            this.sk.add(coefficients[i].duplicate().mulZn(aid.duplicate().pow(BigInteger.valueOf(i))));
        }
    }

    public Element getS(Element aid){
        Element s = coefficients[0].duplicate();
        for(int i = 1; i < UI.t; i++){
            s.add(coefficients[i].duplicate().mulZn(aid.duplicate().pow(BigInteger.valueOf(i))));
        }
        return s;
    }

    public void addSk(Element s){
        this.sk.add(s);
    }

    public void savePk(){
        Element egg = UI.bp.pairing(UI.g.duplicate(), UI.g.duplicate());
        this.pk = egg.powZn(sk);
    }

    public Element calculateEgga(){
        int[] randChoiceTAAs = Functions.gainRandomT(UI.t, UI.n);
        Element egga = UI.GT.newRandomElement();
        Element pow;
        for(int i = 0; i < UI.t; i++){
            pow = UI.Zp.newElement(1);
            for(int j = 0; j < UI.t; j++){
                if(i != j){
                    pow.mulZn(UI.node[randChoiceTAAs[j]].getAid().div(UI.node[randChoiceTAAs[j]].getAid().sub(UI.node[randChoiceTAAs[i]].getAid())));
                }
            }
            if(i == 0){
                egga = UI.node[randChoiceTAAs[i]].getPk().powZn(pow);
            }
            else{
                egga.mul(UI.node[randChoiceTAAs[i]].getPk().powZn(pow));
            }
        }
        return egga;
    }

    public Element[] genKeyShare(List<String> atts){
        Element[] keyShare = new Element[2 + atts.size()];
        Element bi = UI.Zp.newRandomElement();
        Element Ki = UI.g.duplicate().powZn(sk).mul(UI.g.duplicate().powZn(UI.alpha.duplicate().mul(bi.duplicate())));
        Element Li = UI.g.duplicate().powZn(bi);
        keyShare[0] = Ki;
        keyShare[1] = Li;
        for(int i = 0; i < atts.size(); i++){
            int index = UI.U_set.indexOf(atts.get(i));
            Element h = UI.hList[index].duplicate();
            keyShare[2 + i] = h.duplicate().powZn(bi);
        }
        return keyShare;
    }

    public Element[] getTW(Element pkDO, Element theta, String[] KW) throws NoSuchAlgorithmException {
        Element[] TW = new Element[3];
        TW[0] = theta;
        TW[2] = UI.bp.pairing(UI.h.duplicate(), UI.g.duplicate()).powZn(theta);
        Element gkwi = UI.Zp.newElement(0);
        for(int i = 0; i < KW.length; i++){
            gkwi.add(Functions.Hash2(KW[i]));
        }
        gkwi.mul(-1);
        TW[1] = UI.g.duplicate().powZn(gkwi);
        TW[1].mul(pkDO);
        TW[1].powZn(theta);
        return TW;
    }

}
