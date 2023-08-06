package app;

import java.io.FileNotFoundException;

import dk.DK1;
import grammar.Grammar;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        /*
        Original Grammar = 15845 States

        1) Reverse all the Recursions

        2) Pointer * -> `

        3)
        Problem:
        <DiLe> -> [<Di>] 1  [a, z, x, v, t, r, p, n, l, j, h, f, d, b, y, w, u, s, q, o, m, k, i, g, e, c]
        <DiS> -> [<Di>] 1  [<=, ==, >=, >, <, 9, 8, 7, 6, u, 5, 4, 3, 2, 1, 0, /, -, +, *]
        Solution:
        <DiLeS> -> <DiLeS><Le> | <DiLeS><Di> | <Le>

        4)
        Problem:
        <Na> -> [<Le>] 1  [a, [, z, x, v, t, r, p, ., n, l, j, h, &, f, d, b, `, =, y, w, u, s, q, o, m, k, i, g, e, c]
        <DiLeS> -> [<Le>] 1  [a, z, x, 8, v, 6, t, 4, r, 2, p, 0, n, l, j, h, f, d, b, y, 9, w, 7, u, 5, s, 3, q, 1, o, m, k, i, g, e, c]
        Solution:
        <Na> -> <DiLeS>

        5)
        Problem:
        <BF> -> [<id>] 1  [||, &&, ,, )]
        <F> -> [<id>] 1  [<=, ==, /, >=, >, -, ,, !=, <, +, *, )]
        Solution:
        <BF> -> (bool)<id> | <Atom> | !<BF> | (<BE>)

        6)
        Problem:
        <Na> -> [<DiLeS>] 1  [a, [, z, x, v, t, r, p, n, ., l, j, h, &, f, d, b, `, =, y, w, u, s, q, o, m, k, i, g, e, c]
        <Le> -> [m] 0  [a, [, z, x, 8, v, 6, t, 4, r, 2, p, 0, n, ., l, j, h, &, f, d, b, `, =, y, 9, w, 7, u, 5, s, 3, q, 1, o, m, k, i, g, e, c]
        Solution:
        <Na> -> <Na><Le> | <Na><Di> | <Le>

        7)
        Problem:
        <Ty> -> [<Na>] 1  [x, W, t, S, p, O, l, K, h, G, d, C, y, X, u, T, q, P, m, L, i, H, e, D, a, z, Y, v, U, r, Q, n, M, j, I, f, E, b, A, Z, w, V, s, R, o, N, k, J, g, F, c, B]
        <Le> -> [N] 0  [x, W, t, S, p, O, l, K, h, G, d, C, y, X, u, T, q, P, m, L, i, H, e, D, a, z, Y, v, U, r, Q, n, M, j, I, f, E, b, A, Z, w, V, s, R, o, N, k, J, g, F, c, B]
        Solution:
        <Ty> -> int | bool | char | uint | <Na>_
         */

        //String grammarFilePath = "C:\\Users\\Student\\Desktop\\CFG_2\\C0_Mini\\Grammar.txt";
        //String terminalsFilePath = "C:\\Users\\Student\\Desktop\\CFG_2\\C0_Mini\\Terminals.txt";
        String grammarFilePath = "C:\\Users\\Student\\Desktop\\CFG_2\\C0_Modified\\Grammar.txt";
        String terminalsFilePath = "C:\\Users\\Student\\Desktop\\CFG_2\\C0_Modified\\Terminals.txt";
        //String grammarFilePath = "C:\\Users\\Student\\Desktop\\CFG_2\\DiLe\\Grammar.txt";
        //String terminalsFilePath = "C:\\Users\\Student\\Desktop\\CFG_2\\DiLe\\Terminals.txt";


        Grammar g = new Grammar(grammarFilePath, terminalsFilePath);
        //System.out.println(g);


		DK1 dk1 = new DK1(g);

        //System.out.println(dk1.getStates());

        System.out.println("The Number of States: " + dk1.getStates().size() + "\n");


        System.out.println("\n The DK_1 Test Passed = " + dk1.Test() + "\n");


		//Parsing

		//String validString1 = "int A (){return 1}~";
		//String validString2 = "typedef int` main; int x; int y; int main (){return 0}~";
        String validString2 = "typedef int` pInt; int x; int y; int main (int albama, int tesla){return hello}~";
        dk1.parseString(validString2);




    }

}
