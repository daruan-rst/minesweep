package br.com.minesweep;

import br.com.minesweep.Model.Table;
import br.com.minesweep.View.TableConsole;

public class App {
    public static void main(String[] args) {

        Table table = new Table(6,6,6);

      new TableConsole(table);




    }
}
