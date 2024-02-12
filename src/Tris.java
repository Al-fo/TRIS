public class Tris{
    Boolean[][] griglia;

    Tris(){
        griglia = new Boolean[3][3];
    }

    public void inserisciPunto(boolean valore, int x, int y) throws TrisException{
        x--; y--;
        if(x < 0 || y < 0 || x > 2 || y > 2) throw new TrisException("1");
        if(griglia[x][y] != null) throw new TrisException("2");
        griglia[x][y] = valore;
        if(controllaVittoria() == 0) throw new TrisException("P");
        if(controllaVittoria() == 1) throw new TrisException("V1");
        if(controllaVittoria() == 2) throw new TrisException("V2");
    }

    private int controllaVittoria(){
        if(griglia[0][0] != null){
            if((griglia[0][0] == griglia[0][1] && griglia[0][0] == griglia[0][2]) || 
                (griglia[0][0] == griglia[1][0] && griglia[0][0] == griglia[2][0]) ||
                (griglia[0][0] == griglia[1][1] && griglia[0][0] == griglia[2][2])) return griglia[0][0] ? 1 : 2;
        }
        if(griglia[1][1] != null){
            if((griglia[1][1] == griglia[0][1] && griglia[1][1] == griglia[2][1] || 
                (griglia[1][1] == griglia[1][0] && griglia[1][1] == griglia[1][2]) || 
                (griglia[1][1] == griglia[0][2] && griglia[1][1] == griglia[2][0]))) return griglia[1][1] ? 1 : 2;
        }
        if(griglia[2][2] != null){
            if((griglia[2][2] == griglia[2][1] && griglia[2][2] == griglia[2][0]) || 
            (griglia[2][2] == griglia[1][2] && griglia[2][2] == griglia[0][2])) return griglia[2][2] ? 1 : 2;
        }
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(griglia[i][j] == null) return -1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        String s = "| "; 
        for(int i = 0; i < 3; i++){
            for(int ii = 0; ii < 3; ii++){
                if(griglia[i][ii] == null) s += "  | ";
                else s += griglia[i][ii] ? "o | " : "x | ";   
            }
            s += "\n| ";
        }
        return s;
    }
}