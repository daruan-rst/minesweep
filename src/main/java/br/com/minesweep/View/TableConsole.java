package br.com.minesweep.View;

import br.com.minesweep.Exception.ExplosaoException;
import br.com.minesweep.Exception.SairException;
import br.com.minesweep.Model.Table;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class TableConsole {

    private Table table;

    private Scanner input = new Scanner(System.in);

    public TableConsole(Table table){
        this.table = table;
        startTheGame();
    }


    private void startTheGame() {
        try{
            boolean continues = true;
            while(continues){
                gameFlux();
                System.out.println("Wanna play again? (Y/N)");
                String resposta = input.nextLine();
                if("n".equalsIgnoreCase(resposta)){
                    continues = false;
                }else{
                    table.restartTheGame();
                }
            }


        }catch(SairException e){
            System.out.println("See you later!");
        }finally{
            input.close();
        }

    }

    private void gameFlux() {
        try{

            while (!table.goalAchieved()){
                System.out.println(table.toString());

                String digitado = captureField("Type (x, y): ");

                Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                                            .map(e-> Integer.parseInt(e.trim())).iterator();

                digitado = captureField("1 - To open OR 2 - To mark");
                if("1".equalsIgnoreCase(digitado)){
                    table.open(xy.next() , xy.next());
                }else if("2".equalsIgnoreCase(digitado)){
                    table.changeMark(xy.next(), xy.next());
                }
            }
            System.out.println(table);
            System.out.println("You've won!");
        }catch(ExplosaoException e){
            System.out.println(table);
            System.out.println("You've lost!");
        }
    }

    private String captureField(String text) {
        System.out.print(text);
        String digitado = input.nextLine();

        if("out".equalsIgnoreCase(digitado)) {
            throw new SairException();
        }

        return digitado;
    }


}
