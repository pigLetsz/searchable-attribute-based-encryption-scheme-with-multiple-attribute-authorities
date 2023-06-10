import it.unisa.dia.gas.jpbc.Element;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class DOClass {
    Element uid;
    Element beta;
    Element pk;
    Element kappa;

    public DOClass(){
        this.uid = UI.Zp.newRandomElement();
        this.beta = UI.Zp.newRandomElement();
        this.pk = UI.g.duplicate().powZn(beta);
    }

    public Element getPK(){
        return pk.duplicate();
    }

    public Element[] calculateI(String[] keywords) throws NoSuchAlgorithmException {
        Element sigma = UI.Zp.newRandomElement();
        Element[] I = new Element[2];
        I[1] = UI.bp.pairing(UI.g.duplicate(), UI.g.duplicate()).powZn(sigma);
        Element hgkwi = UI.Zp.newElement(0);
        for(int i = 0; i < keywords.length; i++){
            hgkwi.add(Functions.Hash2(keywords[i]));
        }
        hgkwi = beta.duplicate().sub(hgkwi);
        hgkwi = UI.Zp.newOneElement().div(hgkwi);
        Element hgsgm = UI.h.duplicate().mul(UI.g.duplicate().powZn(sigma.duplicate().mul(-1)));
        hgkwi = hgsgm.duplicate().powZn(hgkwi);
        I[0] = hgkwi;
        return I;
    }

    public String gainEk(String m){
        this.kappa = UI.GT.newRandomElement();
        String pw = kappa.toString();
        String Ekm = Functions.encrypt(m, pw);
        return Ekm;
    }

    public Element gainHE(String Ekm){
        Element HE = UI.Zp.newOneElement();
        try {
            HE = Functions.Hash1(Ekm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HE;
    }

    public Element[][] gainM(String l, ArrayList<List<String>> accessStructureList){
        Element[][] M = Functions.getLSSSM(l, accessStructureList);
        return M;
    }

    public String[] gainRou(ArrayList<List<String>> accessStructureList){
        String[] rou = Functions.getrou(accessStructureList);
        return rou;
    }

    public Element[] gainN(int len, Element[][] M, Element HE){
        Element[] N = new Element[len];
        Element[] tVector = new Element[M[0].length];
        tVector[0] = HE.duplicate();
        for(int i = 1; i < M[0].length; i++){
            tVector[i] = UI.Zp.newRandomElement();
        }
        Element count;
        for(int i = 0; i < M.length; i++){
            count = UI.Zp.newZeroElement();
            for(int j = 0; j < M[0].length; j++){
                count.add(M[i][j].duplicate().mulZn(tVector[j]));
            }
            N[i] = count.duplicate();
        }
        return N;
    }

    public Element[] gainCT(int row, int col, String[] rou, Element[][] M){
        Element[] lamda = new Element[row];
        Element[] vVector = new Element[col];
        for(int i = 0; i < col; i++){
            vVector[i] = UI.Zp.newRandomElement();
        }
        Element count;
        for(int i = 0; i < row; i++){
            count = UI.Zp.newZeroElement();
            for(int j = 0; j < col; j++){
                count.add(M[i][j].duplicate().mulZn(vVector[j]));
            }
            lamda[i] = count.duplicate();
        }
        Element[] r = new Element[row];
        for(int i = 0; i < row; i++){
            r[i] = UI.Zp.newRandomElement();
        }

        Element[] CT = new Element[2 + row * 2];
        CT[0] = UI.egga.duplicate().powZn(vVector[0]);
        CT[0].mul(kappa);
        CT[1] = UI.g.duplicate().powZn(vVector[0]);
        Element hrr;
        int index;
        for(int i = 0; i < row; i++){
            index = UI.U_set.indexOf(rou[i]);
            CT[2 + i] = UI.g.duplicate().powZn(UI.alpha);
            CT[2 + i].powZn(lamda[i]);
            hrr = UI.hList[index].duplicate().powZn(r[i].duplicate().mul(-1));
            CT[2 + i] = hrr.mul(CT[2 + i]);
        }
        for(int i = 0; i < row; i++){
            CT[2 + row + i] = UI.g.duplicate().powZn(r[i]);
        }
        return CT;
    }

}
