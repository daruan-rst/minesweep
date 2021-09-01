package br.com.minesweep.Model;

import br.com.minesweep.Exception.ExplosaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTeste {

    private Field field;

    @BeforeEach
    void iniciarCampo(){
        field = new Field(2,2);
    }

    @Test
    void testeVizinhoRealDistanciaEsquerda(){
        Field vizinhoEsquerda = new Field(2,1);
        boolean resultadoEsquerda = field.addAdjacentFields(vizinhoEsquerda);
        assertTrue(resultadoEsquerda);
    }

    @Test
    void testeVizinhoRealDistanciaDireita(){

        Field vizinhoDireita = new Field(2,3);
        boolean resultadoDireita = field.addAdjacentFields(vizinhoDireita);
        assertTrue(resultadoDireita);
    }

    @Test
    void globalTesteVizinho(){
        for (int linha = field.line -1; linha< field.line -1 ; linha++){
            for (int coluna = field.column -1; linha< field.column -1 ; coluna++){
                Field vizinhoTeste = new Field(linha,coluna);
                boolean podeSerVizinho = field.addAdjacentFields(vizinhoTeste);
                assertTrue(podeSerVizinho);
            }
      }
    }

    @Test
    void testeValorPadraoAtributoMarcado(){
        assertFalse(field.isFlagged());
    }

    @Test
    void testeAlternarMarcacao(){
        field.changeFlagged();
        assertTrue(field.isFlagged());
    }

    @Test
    void testeAlternarMarcacaoDuasChamadas(){
        field.changeFlagged();
        assertTrue(field.isFlagged());
    }

    @Test
    void testeAbrirNaoMinadoNaoMarcado(){
        assertTrue(field.open());
    }

    @Test
    void testeAbrirNaoMinadoMarcado(){
        field.changeFlagged();
        assertFalse(field.open());
    }

    @Test
    void testeAbrirMinadoMarcado(){
        field.changeFlagged();
        field.putAMine();
        assertFalse(field.open());
    }

    @Test
    void testeAbrirMinadoNaoMarcado(){
        field.putAMine();
        //deve gerar ExplosaoException
        assertThrows(ExplosaoException.class, ()->
        {
            field.open();
        });
    }

    @Test
    void abrirComVizinhos(){
        Field field33 = new Field(3,3); // Field vizinho
        Field field44 = new Field(4,4); // Field vizinho do vizinho

        field33.addAdjacentFields(field44);
        field.addAdjacentFields(field33);

        field.open();

        assertTrue(field33.isOpen() && field44.isOpen());


    }

    @Test
    void abrirComVizinhosMinados(){
        Field field33 = new Field(3,3); // Field vizinho


        Field field44 = new Field(4,4); // Field vizinho do vizinho
        Field field43 = new Field(4,3); // Field que está minado
        field43.putAMine();

        field33.addAdjacentFields(field44);
        field33.addAdjacentFields(field43);

        field.addAdjacentFields(field33);

        field.open();

        assertTrue(field33.isOpen() && field44.isClosed());


    }
}
