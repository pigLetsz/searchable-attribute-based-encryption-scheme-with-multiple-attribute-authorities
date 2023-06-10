import it.unisa.dia.gas.jpbc.Element;

public class dataInCSP {
    Element[][] M;
    String[] rou;
    Element[] CT;
    Element[] I;
    String Ekm;
    Element HE;
    Element[] N;

    public dataInCSP(Element[] I, String Ekm, Element HE, Element[] N, Element[] CT, Element[][] M, String[] rou){
        this.I = I;
        this.Ekm = Ekm;
        this.HE = HE;
        this.N = N;
        this.CT = CT;
        this.M = M;
        this.rou = rou;
    }

    public Element[] getI(){
        Element[] res = new Element[2];
        res[0] = I[0].duplicate();
        res[1] = I[1].duplicate();
        return res;
    }

    public Element[] getCT(){
        Element[] res = new Element[CT.length];
        for(int i = 0; i < CT.length; i++){
            res[i] = CT[i].duplicate();
        }
        return res;
    }

    public String getEkm(){
        return Ekm;
    }

    public Element[] getN(){
        Element[] res = new Element[N.length];
        for(int i = 0; i < N.length; i++){
            res[i] = N[i].duplicate();
        }
        return res;
    }

    public String[] getRou(){
        return rou;
    }

    public Element getHE(){
        return HE.duplicate();
    }

    public Element[][] getM(){
        Element[][] res = new Element[M.length][M[0].length];
        for(int i = 0; i < M.length; i++){
                for(int j = 0; j < M[0].length; j++){
                    res[i][j] = M[i][j].duplicate();
            }
        }
        return res;
    }
}
