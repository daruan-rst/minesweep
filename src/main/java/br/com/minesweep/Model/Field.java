package br.com.minesweep.Model;

import br.com.minesweep.Exception.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Field {

    protected final int line;
    protected final int column;
    private boolean hasMine = false;
    private boolean open = false;
    private boolean flagged = false;

    private List<Field> adjacent = new ArrayList<>(); // só a classe campo deve dizer quem deve ou não ser vizinho

    /**
     * O nome disto é Autorrelacionamento: uma relação 1-N consigo mesmo
     * Exemplo: Uma (classe) Pessoa pode ter N filhos da mesma classe
     */

    Field(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public boolean isFlagged(){return flagged;}

    public boolean isHasMine(){return hasMine;}

    public boolean isOpen(){return open;}

    void setOpen(boolean open){
        open = this.open;}

    public boolean isClosed(){return !isOpen();}

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }

    void putAMine(){
        hasMine = true;
    }

    boolean addAdjacentFields(Field adjcent){

        /**
         * 11 12 13
         * 21 22 23
         * 31 32 33
         *
         * -  Nas regiões cardinais à 22: se uma das coordenadas é subtraida, sempre retornará math.abs(1)
         * Ex: i[2,2] - i[1,2] = 1 ; j[2,2] - j[2,3] = 1;
         *  (ou também: se i ou j for igual)
         *
         * -Nas regiões diagonais à 22: subtraindo uma das coordenadas, teremos sempre 2*math.abs(1)
         * Ex: [3,1] - [2,2] = math.abs(1) + math.abs(-1) = 2
         */

        int deltaLine = Math.abs(this.line - adjcent.line) ;
        int deltaColumn = Math.abs(this.column - adjcent.column);
        int deltaGlobal = deltaColumn + deltaLine;

        boolean differentLine = line != adjcent.line;
        boolean differentColumn = column !=adjcent.column;
        boolean diagonal = differentLine && differentColumn;

        if(deltaGlobal == 1 && !diagonal ){
            adjacent.add(adjcent);
                    return true;
        }else if(deltaGlobal == 2 && diagonal) {
            adjacent.add(adjcent);
            return true;
        }else{
            return false;
        }
    }

    void changeFlagged(){
        if(!open){
            flagged = !flagged;
        }
    }

    boolean open(){
        if(!open && !flagged){
            open = true;
            if(hasMine){
              throw new ExplosaoException();
            }
            if(safeAdjacent()){
                adjacent.forEach(v -> v.open());
            }
        return true;
            }
        else{
        return false;
        }
    }

    boolean safeAdjacent(){
        return adjacent.stream().noneMatch(v -> v.hasMine);
    }

   public boolean Acomplished() {
        boolean revealled = !hasMine && open;
        boolean safeFlag = hasMine && flagged;
        return revealled || safeFlag;
    }

    long minesOnAdjacent(){
        return adjacent.stream().filter(v -> v.hasMine).count();
    }

    void restart(){
        open = false;
        hasMine = false;
        flagged = false;
    }

    public String toString(){
        if(flagged){
            return "x";
        } else if(open && hasMine){
            return "*";
        }else if (open && minesOnAdjacent()>0){
            return Long.toString(minesOnAdjacent());
        }else if(open){
            return " ";
        }else {
            return"?";
        }
        }
    }


