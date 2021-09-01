package br.com.minesweep.Model;

import br.com.minesweep.Exception.ExplosaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Table {

    private int lines;
    private int columns;
    private int mines;

    public List<Field> fields = new ArrayList<>();

    public Table(int lines, int columns, int mines) {
        this.lines = lines;
        this.columns = columns;
        this.mines = mines;
        generateFields();
        createAdjacent();
        sortMines();
    }

    private void generateFields() {
        for (int i = 0; i< lines; i++){
            for (int j = 0; j< columns; j++){
                fields.add(new Field(i,j));
            }

        }
    }

    private void createAdjacent() {
        for(Field c1 : fields){
            for  (Field c2: fields){
                c1.addAdjacentFields(c2);
            }
        }
    }



    private void sortMines() {
        /**
         * 1) A quantidade de minas do jogo é predeterminada
         * 2) Coloca-se um contador das minas atuais
         * 3) Escolhe-se ao acaso um dentre todos os fields para se colocar uma mina
         */
        long placedMines = 0;
        Predicate<Field> minado = c -> c.isHasMine();
        do{

            int aleatorio = (int) (Math.random() * fields.size());
            fields.get(aleatorio).putAMine();
            placedMines = fields.stream().filter(minado).count();

        }while (placedMines < mines);
    }

    public boolean goalAchieved(){
        return fields.stream().allMatch(c -> c.Acomplished());
    }

    public void restartTheGame(){
        fields.stream().forEach(c-> c.restart());
        sortMines();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        int k = 0;
        sb.append("  ");
        for(int i = 0; i < columns; i++){
            sb.append(" ").append(i).append(" ");

        }
        sb.append("\n");
        for (int i = 0; i< lines; i++){
            sb.append(i).append(" ");
            for (int j = 0; j< columns; j++){

                sb.append(" ");
                sb.append(fields.get(k));
                sb.append(" ");
            }
            sb.append("\n");

    }   return sb.toString();
    }

    public void open(int line, int column){
        try {
            fields.parallelStream()
                    .filter(c -> c.getLine() == line && c.getColumn() == column)
                    .findFirst()
                    .ifPresent(c -> c.open());
            }catch(ExplosaoException e){
            fields.forEach(c -> c.setOpen(true));
        }
        }

    public void changeMark(int line, int column) {
        fields.parallelStream()
                .filter(c -> c.getLine() == line && c.getColumn() == column)
                .findFirst()
                .ifPresent(c -> c.changeFlagged());
    }
}
