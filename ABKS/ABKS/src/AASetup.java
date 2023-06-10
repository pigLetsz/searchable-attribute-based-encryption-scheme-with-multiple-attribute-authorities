import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.math.BigInteger;

public class AASetup {
    public static Element[] buildFx(int t, Element a){

        Element[] randnum = new Element[t];
        randnum[0] = a;
        for(int i = 1; i < t; i++){
            randnum[i] = UI.Zp.newRandomElement().getImmutable();
        }
        return randnum;
    }

    public static Element getS(Element[] fx, Element aid, int t){
        Element result = fx[0].getImmutable();
        for(int i = 1; i < t; i++){
            result = result.add(fx[i].mul(aid.pow(BigInteger.valueOf(i))));
        }
        return result;
    }

    public static Element getPk(Element g, Element sk){
        Element egg = UI.bp.pairing(g, g);
        Element Pk = egg.powZn(sk).getImmutable();
        return Pk;
    }
    //730750818665451621361119245571504901405976559617
}
