/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaporware.GeneradorComplejidad;

import java.util.ArrayList;
import java.util.Random;
import org.apache.commons.math4.analysis.polynomials.PolynomialFunction;

/**
 *
 * @author Angel Loro Mosqueda
 */
public class Generador {

    private String codigo, codigoFinal, codigoF;
    private Random r;
    private int x, k1, j1;
    private PolynomialFunction repeticionFor;
    private PolynomialFunction funcLog, funcExp;

    public String Complex;

    private int cantidadMethod, contMethod;
    private boolean type;
    private boolean funcnLog;
    private boolean obligatorio;
    ArrayList<Method> methodCollection = new ArrayList<Method>();
    

    private int cantidadVar, cantidadVarA, maxVar, maxVarA;

    private int numeroMaximo;
    private int numeroMinimo;
    private int cantIfElse;
    private int cantIf;
    private int cantWhile;
    private int cantFor;
    private int cantSwitch;

    final private double[] valorU = {1, 0};
    final private PolynomialFunction valorIdent = new PolynomialFunction(valorU);
    final private double[] valorC = {0, 0};
    final private PolynomialFunction valorIdentC = new PolynomialFunction(valorC);

    private int complex1;

    public Generador(int methodN, int var, int varA, int cantMax, int cantMin, String Complex) {

        r = new Random();
        cantidadMethod = methodN;
        maxVar = var;
        maxVarA = varA;
        numeroMaximo = cantMax;
        numeroMinimo = cantMin;
        restartStats(numeroMaximo,numeroMinimo);
        this.Complex = Complex;
        constructor(Complex);

    }

    public String imprimir() {
        codigoFinal = cambiarCodigo(codigoFinal);
        codigoFinal = "";
        for (Method f : methodCollection) {
            codigoFinal += (f.toString());

        }
        //System.out.println(codigoFinal);
        return codigoFinal;
    }

    public String mostrarCodigo() {
        String codigoFinal = "";
        for (Method f : methodCollection) {
            codigoFinal += (f.getCodigo());

        }

        return codigoFinal;
    }

    public String mostrarCodigoMetodo(int x) {
        String codigoFinal = "";

        codigoFinal = (methodCollection.get(x).getCodigo());

        return codigoFinal;
    }

    public String cambiarCodigo(String codigo) {
        ArrayList<Character> ca = cadenatoArray(codigo);
        ArrayList<Character> ca2 = tabCod(ca);
        String codigo1 = arraytoCad(ca2);

        return codigo1;
    }

    public ArrayList tabCod(ArrayList<Character> list) {
        ArrayList<Character> list2 = new ArrayList();
        int i = 0;
        int tab = 0;
        for (char c : list) {

            if (c == '{') {
                list2.add(c);
                tab++;
            } else if (c == '}') {
                list2.add(c);
                tab--;
            } else if (c == '\n') {
                list2.add(c);
                for (int y = 0; y < tab; y++) {

                    list2.add('\t');

                }

            } else {
                list2.add(c);
            }

        }
        return list2;
    }

    public ArrayList<Character> cadenatoArray(String cadena) {
        ArrayList<Character> c = new ArrayList<Character>();
        for (int i = 0; i < cadena.length(); i++) {
            c.add(cadena.charAt(i));
        }

        return c;
    }

    public String arraytoCad(ArrayList<Character> list) {
        String codigo = "";
        for (char c : list) {
            codigo += c;
        }

        return codigo;
    }

    public PolynomialFunction constructor(String complex) {
        // complex=this.Complex;
        PolynomialFunction valorTn;
        codigo = "";
        codigoFinal = "";
        switch (complex) {
            case "constante":
                complex1 = 0;
                valorTn = methodDeclaration(0);
                break;
            case "logaritmica":
                complex1 = 0;
                valorTn = methodDeclaration(1);
                break;
            case "lineal":
                complex1 = 1;
                valorTn = methodDeclaration(0);
                break;
            case "nlog":
                complex1 = 0;
                valorTn = methodDeclaration(2);
                break;
            case "cuadratica":
                complex1 = 2;
                valorTn = methodDeclaration(0);
                break;
            case "cubica":
                obligatorio=true;
                complex1 = 3;
                valorTn = methodDeclaration(0);
                break;
            case "exponencial":
                complex1 = r.nextInt(4);
                valorTn = methodDeclaration(4);
                break;
            default:
                valorTn = valorIdentC;
                break;
        }

        return valorTn;

    }

    public PolynomialFunction methodDeclaration(int n) {
        contMethod = 0;

        PolynomialFunction valorTn = new PolynomialFunction(valorC);
        for (int i = 0; i < cantidadMethod; i++) {
            cantidadVar = 0;
            cantidadVarA = 0;
            ArrayList<String> methodParams = new ArrayList<String>();
            ArrayList<String> variables = new ArrayList<String>();
            ArrayList<String> variablesArray = new ArrayList<String>();
            ArrayList<String> valores = new ArrayList<String>();

            type = true;
            valorTn = valorTn.add(methodHeader(variables, variablesArray));

            for (String s : variables) {
                methodParams.add(s);

            }
            if (n == 2) {
                funcnLog = true;
            } else {
                funcnLog = false;
            }

            Method m = new Method("metodo" + contMethod, methodParams, type, valorIdentC, funcLog, false, funcExp, false, funcnLog, valores);
            methodCollection.add(m);
            funcLog = valorIdentC;
            valorTn = valorTn.add(blockMethod(variables, variablesArray, n));
            m.setTn(valorTn);
            m.setCodigo(cambiarCodigo(codigo));

            codigoFinal += codigo;
            codigo = "";
            m.setTnLog(funcLog);
            m.setTnExp(funcExp);

            m.valorModificado();
            restartStats(numeroMaximo,numeroMinimo);
            valorTn = valorIdentC;

        }

        return valorTn;
    }

    public PolynomialFunction methodHeader(ArrayList<String> variables, ArrayList<String> variablesArray) {

        codigo += methodModifiers() + resultType();
        PolynomialFunction valorTn = methodDeclarator(variables, variablesArray);

        return valorTn;
    }

    public String methodModifiers() {

        String codigo = "public static ";

        return codigo;
    }

    public String resultType() {
        x = r.nextInt(100);
        String codigo;
        if (x <= 50) {
            type = true;
            codigo = primitiveType();

        } else {
            type = false;
            codigo = "void ";
        }

        return codigo;
    }

    public PolynomialFunction methodDeclarator(ArrayList<String> variables, ArrayList<String> variablesArray) {
        x = r.nextInt(100);

        codigo += IDMethodG() + "(" + primitiveType() + "n, ";
        PolynomialFunction valorTn = formalParameterList(variables, variablesArray).add(valorIdent);
        codigo += ")";

        return valorTn;
    }

    public PolynomialFunction formalParameterList(ArrayList<String> variables, ArrayList<String> variablesArray) {
        x = r.nextInt(100);
        PolynomialFunction valorTn = null;
        if (x <= 50) {
            valorTn = formalParameter(variables, variablesArray);

        } else {
            valorTn = formalParameter(variables, variablesArray);
            codigo += ",";
            valorTn = valorTn.add(formalParameterList(variables, variablesArray));

        }

        return valorTn;
    }

    public PolynomialFunction formalParameter(ArrayList<String> variables, ArrayList<String> variablesArray) {

        PolynomialFunction valorTn = valorIdent;

        codigo += primitiveType() + variableDeclaratorId(variables);

        return valorTn;
    }

    public PolynomialFunction variableDeclarators(ArrayList<String> variables, ArrayList<String> variablesArray) {
        x = r.nextInt(100);
        PolynomialFunction valorTn;

        if (x <= 50) {

            valorTn = variableDeclarator(variables, variablesArray);
            codigo += ",";
            valorTn = valorTn.add(variableDeclarators(variables, variablesArray));

        } else {
            valorTn = variableDeclarator(variables, variablesArray);
        }

        return valorTn;
    }

    public PolynomialFunction variableDeclarator(ArrayList<String> variables, ArrayList<String> variablesArray) {

        PolynomialFunction valorTn = valorIdent;
        codigo += variableDeclaratorId(variables) + "=" + variableInitializer(variables, variablesArray);

        return valorTn;
    }

    public String variableDeclaratorId(ArrayList<String> variables) {
        String codigo;

        codigo = IDCreator(variables);

        return codigo;
    }

    public String variableDeclaratorIdArray(ArrayList<String> variablesArray) {

        String codigo = IDArrayCreator(variablesArray);

        return codigo;
    }

    public String arrayInitializer(ArrayList<String> variables, ArrayList<String> variablesArray) {
        x = r.nextInt(100);
        String codigo;

        codigo = "{" + variableInitializers(variables, variablesArray) + "}";

        return codigo;
    }

    public String variableInitializers(ArrayList<String> variables, ArrayList<String> variablesArray) {
        String codigo = "";
        for (int i = 0; i < 4; i++) {
            codigo += variableInitializer(variables, variablesArray) + ",";
        }
        codigo += variableInitializer(variables, variablesArray);

        return codigo;
    }

    public String variableInitializer(ArrayList<String> variables, ArrayList<String> variablesArray) {

        x = r.nextInt(100);
        String codigo;
        if (x <= 50 || variables.size() < 1) {
            codigo = literal();

        } else {
            codigo = shiftInit(variables, variablesArray);

        }

        return codigo;

    }

    public String shiftInit(ArrayList<String> variables, ArrayList<String> variablesArray) {

        x = r.nextInt(100);
        String codigo;

        if (x <= 30 || variables.size() < 2) {
            codigo = literal();

        } else if (x > 30 && x <= 50) {
            codigo = "++" + IDInit(variables);
        } else if (x > 50 && x <= 70) {
            codigo = IDInit(variables) + "++";
        } else if (x > 70 && x <= 90) {
            codigo = "--" + IDInit(variables);
        } else if (x > 90 && x <= 110 && variablesArray.size() > 1) {
            codigo = arrayAccessInit(variablesArray);
        } else if (x > 110 && x <= 130) {
            codigo = IDInit(variables) + "--";
        } else {
            codigo = IDInit(variables);
        }
        return codigo;
    }

    public PolynomialFunction arrayCreation(ArrayList<String> variables, ArrayList<String> variablesArray) {
        x = r.nextInt(100);
        PolynomialFunction valorTn = valorIdent;

        codigo += arrayType() + variableDeclaratorIdArray(variablesArray) + "=" + arrayInitializer(variables, variablesArray);

        return valorTn;
    }


    /*Tipo*/
    public String type() {
        x = r.nextInt(100);
        String codigo;
        if (x <= 80) {
            codigo = primitiveType();

        } else {
            codigo = arrayType();
        }

        return codigo;
    }

    public String primitiveType() {

        return numericType();
    }

    public String numericType() {

        return integralType();
    }

    public String integralType() {

        return "int ";
    }

    public String arrayType() {

        return numericType() + "[]";
    }

    /*Bloques y sentencias*/
    public PolynomialFunction blockMethod(ArrayList<String> variables, ArrayList<String> variablesArray, int n) {
        PolynomialFunction valorTn = null;
        if (n == 1) {
            Method m = methodCollection.get(methodCollection.size() - 1);

            valorTn = block(variables, variablesArray, 0, true, 0);

        } else if (n == 2) {
            valorTn = block(variables, variablesArray, 3, false, 0);
        } else if (n == 4) {
            Method m = methodCollection.get(methodCollection.size() - 1);
            if (m.isType()) {

                valorTn = (block(variables, variablesArray, 5, false, 0));

            } else {

                valorTn = block(variables, variablesArray, 4, false, 0);

            }

        } else {
            Method m = methodCollection.get(methodCollection.size() - 1);
            if (m.isType()) {

                valorTn = (block(variables, variablesArray, 2, false, 0));

            } else {

                valorTn = block(variables, variablesArray, 0, false, 0);

            }

        }

        return valorTn;
    }

    public PolynomialFunction block(ArrayList<String> variables, ArrayList<String> variablesArray, int opt, boolean log, int n) {
        x = r.nextInt(100);
        ArrayList<String> variables2 = new ArrayList<String>();
        ArrayList<String> variablesArray2 = new ArrayList<String>();
        for (String s : variables) {
            variables2.add(s);

        }
        for (String s : variablesArray) {
            variablesArray2.add(s);

        }
        String codF = codigoF;
        codigo += "{\n";
        PolynomialFunction valorTn = valorIdentC;
        if (opt == 3) {
            valorTn = valorTn.add(whileStatementN(variables, variablesArray, 0, true));
        }
        if (opt == 4 || opt == 5) {

            valorTn = valorTn.add(forStatementE(variables, variablesArray, 0));
        }
        if (log) {

            funcLog = whileStatementLog(variables, variablesArray);
            valorTn = valorTn.add(valorIdent).add(valorIdent);//Esta se añade por la expression de inicio creada en el while y la ultima comprobacion

        }
        valorTn = valorTn.add(blockStatements(variables2, variablesArray2, n, 4));

        if (opt == 1) {
            codigo += codF + "*=" + "2" + ";\n";
            valorTn = valorTn.add(valorIdent);

        } else if (opt == 2 || opt == 5) {
            valorTn = valorTn.add(returnStatement(variables, variablesArray));
        }
        codigo += "}\n";

        return valorTn;
    }

    public PolynomialFunction blockStatements(ArrayList<String> variables, ArrayList<String> variablesArray, int n, int cont) {
        x = r.nextInt(100);
        PolynomialFunction valorTn;

        if (x <= 70 || cont > 0) {
            cont--;
            valorTn = blockStatement(variables, variablesArray, n);
            valorTn = valorTn.add(blockStatements(variables, variablesArray, n, cont));

        } else {

            valorTn = blockStatement(variables, variablesArray, n);

        }

        return valorTn;
    }

    public PolynomialFunction blockStatement(ArrayList<String> variables, ArrayList<String> variablesArray, int n) {
        x = r.nextInt(100);

        PolynomialFunction valorTn = null;
        if (x <= 25 && maxVar > variables.size() && maxVarA > variablesArray.size() || variables.size() < 1 || variablesArray.size() < 1) {
            valorTn = localVariableDeclarationStatement(variables, variablesArray);

        } else {
            valorTn = statement(variables, variablesArray, n);
        }

        return valorTn;
    }

    public PolynomialFunction localVariableDeclarationStatement(ArrayList<String> variables, ArrayList<String> variablesArray) {
        x = r.nextInt(100);
        PolynomialFunction valorTn = valorIdentC;

        valorTn = localVariableDeclaration(variables, variablesArray);
        codigo += ";\n";

        return valorTn;
    }

    public PolynomialFunction localVariableDeclaration(ArrayList<String> variables, ArrayList<String> variablesArray) {
        x = r.nextInt(100);
        PolynomialFunction valorTn;
        if (x <= 50) {
            valorTn = arrayCreation(variables, variablesArray);

        } else {
            codigo += primitiveType();
            valorTn = variableDeclarators(variables, variablesArray);
        }

        return valorTn;
    }

    public PolynomialFunction statement(ArrayList<String> variables, ArrayList<String> variablesArray, int n) {
        x = r.nextInt(100);
        PolynomialFunction valorTn;

        if (n < complex1 && cantFor > 0) {
            cantFor--;
            n++;

            valorTn = forStatement(variables, variablesArray, n);

        } else if (x > 20 && x <= 30 && cantIf > 0) {
            cantIf--;

            valorTn = ifThenStatement(variables, variablesArray, n);
        } else if (x > 30 && x <= 60 && cantIfElse > 0) {
            cantIfElse--;

            valorTn = ifThenElseStatement(variables, variablesArray, n);
        } else if (n < complex1 && cantWhile > 0) {
            cantWhile--;
            n++;

            valorTn = whileStatementN(variables, variablesArray, n, false);
        } else {
           //comprobacion para asegurar llegar al grado minimo
            if (complex1 > n && obligatorio) {
                n++;
                obligatorio=false;
                valorTn = whileStatementN(variables, variablesArray, n, false);

            } else {
                valorTn = statementWithoutTrailingSubstatement(variables, variablesArray, n);
            }
        }

        return valorTn;
    }

    public PolynomialFunction statementWithoutTrailingSubstatement(ArrayList<String> variables, ArrayList<String> variablesArray, int n) {
        x = r.nextInt(100);

        PolynomialFunction valorTn;
        if (x <= 70) {
            valorTn = expressionStatement(variables, variablesArray, n);

        } else if (x > 70 && x <= 90 && cantSwitch > 0) {
            cantSwitch--;
            valorTn = switchStatement(variables, variablesArray, n);

        } else {
            valorTn = blockStatement(variables, variablesArray, n);
        }

        return valorTn;
    }

    public PolynomialFunction emptyStatement() {

        PolynomialFunction valorTn = valorIdentC;

        codigo += ";\n";

        return valorTn;
    }

    public PolynomialFunction expressionStatement(ArrayList<String> variables, ArrayList<String> variablesArray, int n) {

        PolynomialFunction valorTn = statementExpression(variables, variablesArray, n);

        codigo += ";\n";

        return valorTn;
    }

    public PolynomialFunction statementExpression(ArrayList<String> variables, ArrayList<String> variablesArray, int n) {
        x = r.nextInt(100);
        PolynomialFunction valorTn;
        if (x <= 40) {
            valorTn = assignment(variables, variablesArray);

        } else if (x > 40 && x <= 60 && methodCollection.size() > 1 && n == 0) {
            valorTn = methodInvocationStatement(variables, variablesArray);
        } else if (x > 60 && x <= 70) {
            valorTn = valorIdent;
            codigo += postIncrementExpression(variables, variablesArray);
        } else if (x > 70 && x <= 80) {
            valorTn = valorIdent;
            codigo += preDecrementExpression(variables, variablesArray);
        } else if (x > 80 && x <= 90) {
            valorTn = valorIdent;
            codigo += postDecrementExpression(variables, variablesArray);
        } else {
            valorTn = valorIdent;
            codigo += preIncrementExpression(variables, variablesArray);
        }

        return valorTn;
    }

    public PolynomialFunction methodInvocationStatement(ArrayList<String> variables, ArrayList<String> variablesArray) {
        x = r.nextInt(100);
        boolean type = false, log = false;
        PolynomialFunction valorTn;
        int i = 0;
        for (Method s : methodCollection) {

            if (s.isType() && i < contMethod - 1 && !s.isLog()) {
                type = true;
            }
            if (!s.isLog()) {
                log = true;

            }
            i++;

        }
        if (log) {
            if (x <= 50 && type) {

                codigo += ID(variables) + "=";
                valorTn = methodInvocationType(variables, variablesArray);

            } else {
                valorTn = methodInvocation(variables, variablesArray);

            }
        } else {
            valorTn = valorIdentC;
        }

        return valorTn;
    }

    public PolynomialFunction ifThenElseStatement(ArrayList<String> variables, ArrayList<String> variablesArray, int n) {

        PolynomialFunction valorTn, valorTI, valorTE;

        codigo += "\nif" + "(";
        valorTn = conditionalExpression(variables, variablesArray);
        codigo += ")\n";
        valorTI = block(variables, variablesArray, 0, false, n);
        codigo += "else\n";
        valorTE = block(variables, variablesArray, 0, false, n);
        valorTn = valorTn.add(maxFunction(valorTI, valorTE));

        methodCollection.get(methodCollection.size() - 1).getValores().add("IfElse->" + valorTn.toString());

        return valorTn;
    }

    public PolynomialFunction ifThenStatement(ArrayList<String> variables, ArrayList<String> variablesArray, int n) {

        PolynomialFunction valorTn;

        codigo += "\nif" + "(";
        valorTn = conditionalExpression(variables, variablesArray);
        codigo += ")\n";
        valorTn = valorTn.add(block(variables, variablesArray, 0, false, n));

        methodCollection.get(methodCollection.size() - 1).getValores().add("If->" + valorTn.toString());

        return valorTn;
    }

    public PolynomialFunction switchStatement(ArrayList<String> variables, ArrayList<String> variablesArray, int n) {

        PolynomialFunction valorTn;

        codigo += "\nswitch" + "(";
        valorTn = switchExpression(variables, variablesArray);
        codigo += ")\n";
        valorTn = valorTn.add(switchBlock(variables, variablesArray, n));
        codigo += "\n";

        methodCollection.get(methodCollection.size() - 1).getValores().add("Switch->" + valorTn.toString());

        return valorTn;
    }

    public PolynomialFunction switchExpression(ArrayList<String> variables, ArrayList<String> variablesArray) {

        PolynomialFunction valorTn = valorIdent;
        codigo += "n";

        return valorTn;
    }

    public PolynomialFunction switchBlock(ArrayList<String> variables, ArrayList<String> variablesArray, int n) {

        PolynomialFunction valorTn = valorIdentC, valorTB, valorTD;

        codigo += "{";
        valorTB = switchBlockStatementgroups(variables, variablesArray, n);
        valorTD = switchLabelDefault(variables, variablesArray, n);
        codigo += "}";
        valorTn = maxFunction(valorTB, valorTD);

        return valorTn;
    }

    public PolynomialFunction switchBlockStatementgroups(ArrayList<String> variables, ArrayList<String> variablesArray, int n) {
        x = r.nextInt(100);

        PolynomialFunction valorTn = valorIdentC, valorTA, valorTB;

        if (x <= 75) {
            valorTn = switchBlockStatementgroup(variables, variablesArray, n);

        } else {
            valorTA = switchBlockStatementgroup(variables, variablesArray, n);
            valorTB = switchBlockStatementgroups(variables, variablesArray, n);
            valorTn = maxFunction(valorTB, valorTA);

        }

        return valorTn;
    }

    public PolynomialFunction switchBlockStatementgroup(ArrayList<String> variables, ArrayList<String> variablesArray, int n) {
        PolynomialFunction valorTn;

        ArrayList<String> variables2 = new ArrayList<String>();
        ArrayList<String> variablesArray2 = new ArrayList<String>();
        for (String s : variables) {
            variables2.add(s);

        }
        for (String s : variablesArray) {
            variablesArray2.add(s);

        }
        valorTn = switchLabel();
        valorTn = valorTn.add(blockStatements(variables2, variablesArray2, n, 4).add(breakStatement()));

        return valorTn;
    }

    public PolynomialFunction switchLabel() {
        PolynomialFunction valorTn = valorIdent;

        codigo += "\ncase " + integerLiteral() + ":\n";

        return valorTn;
    }

    public PolynomialFunction switchLabelDefault(ArrayList<String> variables, ArrayList<String> variablesArray, int n) {
        x = r.nextInt(100);
        PolynomialFunction valorTn = null;
        ArrayList<String> variables2 = new ArrayList<String>();
        ArrayList<String> variablesArray2 = new ArrayList<String>();
        for (String s : variables) {
            variables2.add(s);

        }
        for (String s : variablesArray) {
            variablesArray2.add(s);

        }
        if (x <= 75) {
            codigo += "\ndefault:\n";
            valorTn = blockStatements(variables2, variablesArray2, n, 4);

        } else {
            codigo += "";
            valorTn = valorIdentC;
        }

        return valorTn;
    }

    public PolynomialFunction whileStatementN(ArrayList<String> variables, ArrayList<String> variablesArray, int n, boolean log) {

        PolynomialFunction valorTn, valorTe, valorTB, valorTs;

        codigo += primitiveType() + IDFCreator(variables) + "=" + "0" + ";";

        valorTs = valorIdent;

        codigo += "\nwhile" + "(";
        valorTe = expressionWhile(variables, variablesArray, 2);
        codigo += ")\n";

        if (log) {
            valorTB = valorTe.multiply(block(variables, variablesArray, 0, true, n));
        } else {
            valorTB = valorTe.multiply(block(variables, variablesArray, 0, false, n));
        }

        valorTn = valorTe.add(valorTB).add(valorIdent).add(valorTs);

        methodCollection.get(methodCollection.size() - 1).getValores().add("whileN->" + valorTn.toString());

        return valorTn;
    }

    public PolynomialFunction whileStatementLog(ArrayList<String> variables, ArrayList<String> variablesArray) {

        PolynomialFunction valorTn, valorTe;

        codigo += primitiveType() + IDFCreator(variables) + "=" + "1" + ";";

        //valorTs = valorIdent;
        codigo += "\nwhile" + "(";
        valorTe = expressionWhile(variables, variablesArray, 1);
        codigo += ")\n";
        methodCollection.get(methodCollection.size() - 1).setLog(true);

        funcLog = valorTe.add(block(variables, variablesArray, 1, false, 0));

        methodCollection.get(methodCollection.size() - 1).getValores().add("whileL->" + funcLog.toString());

        return funcLog;

    }

    public PolynomialFunction expressionWhile(ArrayList<String> variables, ArrayList<String> variablesArray, int opt) {

        PolynomialFunction valorTn;
        if (opt == 1) {
            codigo += codigoF + "<" + "n";
            double[] c = {1};
            valorTn = new PolynomialFunction(c);
            Method m = methodCollection.get(methodCollection.size() - 1);
            m.setLog(true);
        } else {

            codigo += codigoF + "++" + "<" + "n";
            double[] c = {0, 1};
            valorTn = new PolynomialFunction(c);
        }

        return valorTn;
    }

    public PolynomialFunction forStatement(ArrayList<String> variables, ArrayList<String> variablesArray, int n) {
        PolynomialFunction valorTn;

        ArrayList<String> variables2 = new ArrayList<String>();
        ArrayList<String> variablesArray2 = new ArrayList<String>();
        for (String s : variables) {
            variables2.add(s);

        }
        for (String s : variablesArray) {
            variablesArray2.add(s);

        }
        codigo += "for" + "(";
        valorTn = forOption(variables2, variablesArray2);
        codigo += ")\n";

        valorTn = valorTn.add(repeticionFor.multiply(block(variables2, variablesArray2, 0, false, n)));

        methodCollection.get(methodCollection.size() - 1).getValores().add("For->" + valorTn.toString());

        return valorTn;
    }

    public PolynomialFunction forStatementE(ArrayList<String> variables, ArrayList<String> variablesArray, int n) {
        PolynomialFunction valorTn;

        ArrayList<String> variables2 = new ArrayList<String>();
        ArrayList<String> variablesArray2 = new ArrayList<String>();
        for (String s : variables) {
            variables2.add(s);

        }
        for (String s : variablesArray) {
            variablesArray2.add(s);

        }
        codigo += "for(i = 1 ; i <= Math.pow( 2, n ) ; i++)\n";
        valorTn = valorIdent;
        methodCollection.get(methodCollection.size() - 1).setExp(true);
        funcExp = valorTn.add(valorIdent.multiply(block(variables2, variablesArray2, 0, false, complex1)));
        methodCollection.get(methodCollection.size() - 1).getValores().add("forE->" + valorTn.toString());

        return valorTn;
    }

    public PolynomialFunction forOption(ArrayList<String> variables, ArrayList<String> variablesArray) {
        x = r.nextInt(125);
        PolynomialFunction valorTn, valorTa, valorTa2;
        if (x <= 25) {
            if (x <= 12) {
                double[] c = {0, 1};
                PolynomialFunction f = new PolynomialFunction(c);
                repeticionFor = f;
                valorTn = forInit(variables, variablesArray, 1);
                codigo += ";";
                valorTn = valorTn.add(expressionF(variables, variablesArray, 1).multiply(f));
                valorTn = valorTn.add(valorIdent);
                codigo += ";";
                valorTn = valorTn.add(forUpdate(variables, variablesArray, 1).multiply(f));
            } else {
                double[] c = {1, 1};
                PolynomialFunction f = new PolynomialFunction(c);
                repeticionFor = f;
                valorTn = forInit(variables, variablesArray, 1);
                codigo += ";";
                valorTn = valorTn.add(expressionF(variables, variablesArray, 2).multiply(f));
                valorTn = valorTn.add(valorIdent);
                codigo += ";";
                valorTn = valorTn.add(forUpdate(variables, variablesArray, 1).multiply(f));

            }

        } else if (x > 25 && x <= 50) {
            if (x <= 37) {

                valorTn = forInit(variables, variablesArray, 2);
                codigo += ";";
                double[] c = {-k1, 1};
                PolynomialFunction f = new PolynomialFunction(c);
                repeticionFor = f;
                valorTn = valorTn.add(expressionF(variables, variablesArray, 1).multiply(f));
                valorTn = valorTn.add(valorIdent);
                codigo += ";";
                valorTn = valorTn.add(forUpdate(variables, variablesArray, 1).multiply(f));

            } else {

                valorTn = forInit(variables, variablesArray, 2);
                codigo += ";";
                double[] c = {-k1, 1};
                PolynomialFunction f = new PolynomialFunction(c);
                repeticionFor = f;
                valorTn = valorTn.add(expressionF(variables, variablesArray, 2).multiply(f));
                valorTn = valorTn.add(valorIdent);
                codigo += ";";
                valorTn = valorTn.add(forUpdate(variables, variablesArray, 1).multiply(f));

            }

        } else if (x > 50 && x <= 75) {
            if (x <= 62) {
                double[] c = {0, 1};
                PolynomialFunction f = new PolynomialFunction(c);
                repeticionFor = f;
                valorTn = forInit(variables, variablesArray, 3);
                codigo += ";";
                valorTn = valorTn.add(expressionF(variables, variablesArray, 3).multiply(f));
                valorTn = valorTn.add(valorIdent);
                codigo += ";";
                valorTn = valorTn.add(forUpdate(variables, variablesArray, 3).multiply(f));

            } else {
                double[] c = {1, 1};
                PolynomialFunction f = new PolynomialFunction(c);
                repeticionFor = f;
                valorTn = forInit(variables, variablesArray, 3);
                codigo += ";";
                valorTn = valorTn.add(expressionF(variables, variablesArray, 4).multiply(f));
                valorTn = valorTn.add(valorIdent);
                codigo += ";";
                valorTn = valorTn.add(forUpdate(variables, variablesArray, 2).multiply(f));

            }
        } else {
            if (x <= 87) {

                valorTn = forInit(variables, variablesArray, 3);
                codigo += ";";
                valorTa = expressionF(variables, variablesArray, 5);
                double[] c = {-k1, 1};
                PolynomialFunction f = new PolynomialFunction(c);
                repeticionFor = f;
                valorTa = valorTa.multiply(f);
                valorTn = valorTn.add(valorIdent).add(valorTa);
                codigo += ";";
                valorTn = valorTn.add(forUpdate(variables, variablesArray, 2).multiply(f));

            } else {

                valorTn = forInit(variables, variablesArray, 3);
                codigo += ";";
                valorTa = expressionF(variables, variablesArray, 6);
                double[] c = {-k1 + 1, 1};
                PolynomialFunction f = new PolynomialFunction(c);
                repeticionFor = f;
                valorTa = valorTa.multiply(f);
                valorTn = valorTn.add(valorIdent).add(valorTa);
                codigo += ";";
                valorTn = valorTn.add(forUpdate(variables, variablesArray, 2).multiply(f));

            }
        }

        /*else {
            if (x <= 112) {

                valorTn = forInit(variables, variablesArray, 1);
                codigo += ";";
                valorTa = expressionF(variables, variablesArray, 1);
                valorTn = valorTn.add(valorIdent);
                codigo += ";";
                valorTa2 = forUpdate(variables, variablesArray, 3);
                double[] c = {0, 1 / j1};
                PolynomialFunction f = new PolynomialFunction(c);
                repeticionFor = f;
                valorTn = valorTn.add(valorTa.multiply(f));
                valorTn = valorTn.add(valorTa2.multiply(f));
                System.out.println("ss");

            } else {

                valorTn = forInit(variables, variablesArray, 2);
                codigo += ";";
                valorTa = expressionF(variables, variablesArray, 1);
                valorTn = valorTn.add(valorIdent);
                codigo += ";";
                valorTa2 = forUpdate(variables, variablesArray, 3);
                double[] c = {-k1 / j1, 1 / j1};
                PolynomialFunction f = new PolynomialFunction(c);
                repeticionFor = f;
                valorTn = valorTn.add(valorTa.multiply(f));
                valorTn = valorTn.add(valorTa2.multiply(f));
                System.out.println("ll");

            }
        }*/
        return valorTn;
    }

    public PolynomialFunction forInit(ArrayList<String> variables, ArrayList<String> variablesArray, int opt) {

        PolynomialFunction valorTn = localVariableDeclarationF(variables, variablesArray, opt);

        return valorTn;
    }

    public PolynomialFunction expressionF(ArrayList<String> variables, ArrayList<String> variablesArray, int opt) {
        PolynomialFunction valorTn = valorIdentC;
        x = r.nextInt(100);

        switch (opt) {
            case 1:
                valorTn = valorIdent;
                codigo += IDForU(variables) + "<" + "n";
                break;
            case 2:
                valorTn = valorIdent;
                codigo += IDForU(variables) + "<=" + "n";
                break;
            case 3:
                valorTn = valorIdent;
                codigo += IDForU(variables) + ">" + "0";
                break;
            case 4:
                valorTn = valorIdent;
                codigo += IDForU(variables) + ">=" + "0";
                break;
            case 5:
                valorTn = valorIdent;
                codigo += IDForU(variables) + ">";
                k1 = r.nextInt(50) + 1;
                codigo += k1 + "";
                break;
            case 6:
                valorTn = valorIdent;
                codigo += IDForU(variables) + ">=";
                k1 = r.nextInt(50) + 1;
                codigo += k1 + "";
                break;

        }

        return valorTn;
    }

    public PolynomialFunction localVariableDeclarationF(ArrayList<String> variables, ArrayList<String> variablesArray, int opt) {
        PolynomialFunction valorTn;

        Method m = methodCollection.get(methodCollection.size() - 1);
        switch (opt) {
            case 1:
                valorTn = valorIdent;
                codigo += primitiveType() + IDCreator(variables) + "=" + "0";
                break;
            case 2:
                valorTn = valorIdent;
                codigo += primitiveType() + IDCreator(variables) + "=";

                k1 = r.nextInt(50) + 1;
                codigo += k1 + "";
                break;
            case 3:
                valorTn = valorIdent;
                codigo += primitiveType() + IDCreator(variables) + "=" + "n";
                break;
            default:
                valorTn = valorIdent;
                codigo += primitiveType() + IDCreator(variables) + "=" + literal();

        }

        return valorTn;
    }

    public PolynomialFunction forUpdate(ArrayList<String> variables, ArrayList<String> variablesArray, int opt) {
        PolynomialFunction valorTn = valorIdentC;
        x = r.nextInt(100);

        switch (opt) {
            case 1:
                valorTn = valorIdent;
                codigo += IDForU(variables) + "++";
                break;
            case 2:
                valorTn = valorIdent;
                codigo += IDForU(variables) + "--";
                break;
            case 3:
                valorTn = valorIdent;
                codigo += IDForU(variables) + "+=";
                j1 = r.nextInt(50) + 1;
                codigo += j1 + "";
                break;
            default:

        }

        return valorTn;
    }

    public PolynomialFunction returnStatement(ArrayList<String> variables, ArrayList<String> variablesArray) {
        x = r.nextInt(100);

        PolynomialFunction valorTn = valorIdent;
        if (x <= 33 && variablesArray.size() > 1) {
            codigo += "return " + arrayAccess(variablesArray) + ";\n";

        } else if (x > 33 && x <= 67 && variables.size() > 1) {
            codigo += "return " + ID(variables) + ";\n";
        } else {

            codigo += "return " + shiftExpression(variables, variablesArray) + ";\n";
        }

        return valorTn;
    }

    public PolynomialFunction breakStatement() {

        PolynomialFunction valorTn = valorIdent;

        codigo += "break" + ";\n";

        return valorTn;
    }

    /*Expressiones*/
    public PolynomialFunction expression(ArrayList<String> variables, ArrayList<String> variablesArray) {

        PolynomialFunction valorTn;
        valorTn = assignmentExpression(variables, variablesArray);

        return valorTn;
    }

    public PolynomialFunction assignmentExpression(ArrayList<String> variables, ArrayList<String> variablesArray) {
        x = r.nextInt(100);
        PolynomialFunction valorTn;

        if (x <= 90) {
            valorTn = conditionalExpression(variables, variablesArray);

        } else {
            valorTn = assignment(variables, variablesArray);
        }

        return valorTn;
    }

    public PolynomialFunction assignment(ArrayList<String> variables, ArrayList<String> variablesArray) {

        PolynomialFunction valorTn = valorIdent;

        codigo += leftHandSide(variables, variablesArray) + assignmentOperator() + shiftExpression(variables, variablesArray);

        return valorTn;
    }

    public String leftHandSide(ArrayList<String> variables, ArrayList<String> variablesArray) {

        x = r.nextInt(100);
        String codigo;
        if (x <= 70 || variablesArray.size() < 1) {
            codigo = typeName(variables);

        } else {
            codigo = arrayAccess(variablesArray);
        }

        return codigo;
    }

    public String assignmentOperator() {
        x = r.nextInt(100);
        String codigo;
        if (x <= 50) {
            codigo = "=";

        } else if (x > 50 && x <= 60) {
            codigo = "*=";
        } else if (x > 60 && x <= 70) {
            codigo = "/=";
        } else if (x > 70 && x <= 80) {
            codigo = "%=";
        } else if (x > 80 && x <= 90) {
            codigo = "+=";
        } else {
            codigo = "-=";
        }

        return codigo;
    }

    public PolynomialFunction conditionalExpression(ArrayList<String> variables, ArrayList<String> variablesArray) {
        x = r.nextInt(100);

        PolynomialFunction valorTn = valorIdent;
        if (x <= 90) {
            codigo += conditionalOrExpression(variables, variablesArray, 0);

        } else {

            codigo += booleanLiteral();
        }

        return valorTn;
    }

    public String conditionalOrExpression(ArrayList<String> variables, ArrayList<String> variablesArray, int contador) {

        x = r.nextInt(100);
        String codigo;

        if (x <= 20 && contador < 1) {
            ++contador;
            codigo = "(" + conditionalOrExpression(variables, variablesArray, contador) + ")" + "||" + "(" + conditionalAndExpression(variables, variablesArray, contador) + ")";

        } else {

            codigo = conditionalAndExpression(variables, variablesArray, contador);
        }

        return codigo;
    }

    public String conditionalAndExpression(ArrayList<String> variables, ArrayList<String> variablesArray, int contador) {

        x = r.nextInt(100);
        String codigo;

        if (x <= 20 && contador < 1) {

            codigo = "(" + conditionalAndExpression(variables, variablesArray, 1) + ")" + "&&" + "(" + equalityExpression(variables, variablesArray, 0) + ")";

        } else {

            codigo = equalityExpression(variables, variablesArray, 0);
        }

        return codigo;
    }

    public String equalityExpression(ArrayList<String> variables, ArrayList<String> variablesArray, int contador) {

        x = r.nextInt(100);
        String codigo;

        if (x <= 35 && contador < 1) {
            ++contador;
            codigo = "(" + relationalExpression(variables, variablesArray, contador) + ")" + "==" + "(" + equalityExpression(variables, variablesArray, contador) + ")";

        } else if (x > 35 && x <= 70 && contador < 1) {
            ++contador;
            codigo = "(" + relationalExpression(variables, variablesArray, contador) + ")" + "!=" + "(" + equalityExpression(variables, variablesArray, contador) + ")";
        } else {

            codigo = relationalExpression(variables, variablesArray, contador);
        }

        return codigo;
    }

    public String relationalExpression(ArrayList<String> variables, ArrayList<String> variablesArray, int contador) {

        x = r.nextInt(100);
        String codigo;

        if (x <= 20 && contador < 1) {
            codigo = "(" + relationalExpression(variables, variablesArray, ++contador) + ")" + ">=" + "(" + shiftExpression(variables, variablesArray) + ")";

        } else if (x > 20 && x <= 40 && contador < 1) {
            codigo = "(" + relationalExpression(variables, variablesArray, ++contador) + ")" + "<" + "(" + shiftExpression(variables, variablesArray) + ")";
        } else if (x > 40 && x <= 60 && contador < 1) {
            codigo = "(" + relationalExpression(variables, variablesArray, ++contador) + ")" + ">" + "(" + shiftExpression(variables, variablesArray) + ")";
        } else if (x > 60 && x <= 80 && contador < 1) {
            codigo = "(" + relationalExpression(variables, variablesArray, ++contador) + ")" + "<=" + "(" + shiftExpression(variables, variablesArray) + ")";
        } else {
            if (contador == 0) {
                codigo = equalityExpression(variables, variablesArray, 0);
            } else {
                codigo = shiftExpression(variables, variablesArray);
            }

        }

        return codigo;
    }

    public String shiftExpression(ArrayList<String> variables, ArrayList<String> variablesArray) {

        String codigo;

        codigo = additiveExpression(variables, variablesArray);

        return codigo;
    }

    public String additiveExpression(ArrayList<String> variables, ArrayList<String> variablesArray) {

        x = r.nextInt(100);
        String codigo;

        if (x <= 90) {
            codigo = multiplicativeExpression(variables, variablesArray);

        } else if (x > 90 && x <= 95) {
            codigo = "(" + additiveExpression(variables, variablesArray) + ")" + "+" + "(" + multiplicativeExpression(variables, variablesArray) + ")";
        } else {
            codigo = "(" + additiveExpression(variables, variablesArray) + ")" + "-" + "(" + multiplicativeExpression(variables, variablesArray) + ")";
        }

        return codigo;
    }

    public String multiplicativeExpression(ArrayList<String> variables, ArrayList<String> variablesArray) {

        x = r.nextInt(100);
        String codigo;

        if (x <= 90) {
            codigo = uExpression(variables, variablesArray);

        } else if (x > 90 && x <= 93) {
            codigo = "(" + multiplicativeExpression(variables, variablesArray) + ")" + "*" + uExpression(variables, variablesArray);
        } else if (x > 93 && x <= 96) {
            codigo = "(" + multiplicativeExpression(variables, variablesArray) + ")" + "/" + uExpression(variables, variablesArray);
        } else {
            codigo = "(" + multiplicativeExpression(variables, variablesArray) + ")" + "%" + uExpression(variables, variablesArray);
        }
        return codigo;
    }

    public String uExpression(ArrayList<String> variables, ArrayList<String> variablesArray) {

        x = r.nextInt(100);
        String codigo;

        if (x <= 40 || variables.size() < 1 || variablesArray.size() < 1) {
            codigo = literal();

        } else if (x > 40 && x <= 45) {
            codigo = preDecrementExpression(variables, variablesArray);
        } else if (x > 45 && x <= 55) {
            codigo = postfixExpression(variables, variablesArray);
        } else if (x > 55 && x <= 65) {
            codigo = preIncrementExpression(variables, variablesArray);
        } else if (x > 65 && x <= 75 && variablesArray.size() > 1) {
            codigo = arrayAccess(variablesArray);
        } else {
            codigo = ID(variables);
        }

        return codigo;
    }

    public String preDecrementExpression(ArrayList<String> variables, ArrayList<String> variablesArray) {

        String codigo;

        codigo = "--" + name(variables, variablesArray);

        return codigo;
    }

    public String preIncrementExpression(ArrayList<String> variables, ArrayList<String> variablesArray) {

        String codigo;

        codigo = "++" + name(variables, variablesArray);

        return codigo;
    }

    public String postDecrementExpression(ArrayList<String> variables, ArrayList<String> variablesArray) {

        String codigo;

        codigo = name(variables, variablesArray) + "--";

        return codigo;
    }

    public String postIncrementExpression(ArrayList<String> variables, ArrayList<String> variablesArray) {

        String codigo;

        codigo = name(variables, variablesArray) + "++";

        return codigo;
    }

    public String postfixExpression(ArrayList<String> variables, ArrayList<String> variablesArray) {

        x = r.nextInt(100);
        String codigo;

        if (x <= 50) {
            codigo = postIncrementExpression(variables, variablesArray);
        } else {
            codigo = postDecrementExpression(variables, variablesArray);
        }

        return codigo;
    }

    public PolynomialFunction methodInvocation(ArrayList<String> variables, ArrayList<String> variablesArray) {

        PolynomialFunction valorTn = valorIdentC;

        int i = 0;
        for (Method s : methodCollection) {

            if (!s.isLog() && i < methodCollection.size() - 1) {
                codigo += s.getName();

                codigo += "(";
                valorTn = s.getTn();
                codigo += argumentList(variables, variablesArray, i, 0);
                codigo += ")";
                break;
            }
            i++;

        }

        return valorTn;
    }

    public PolynomialFunction methodInvocationType(ArrayList<String> variables, ArrayList<String> variablesArray) {
        PolynomialFunction valorTn = valorIdentC;
        int i = 0;
        for (Method s : methodCollection) {

            if (s.isType()) {
                codigo += s.getName();

                codigo += "(";
                valorTn = s.getTn();
                codigo += argumentList(variables, variablesArray, i, 0);
                codigo += ")";
                break;
            }
            i++;

        }

        return valorTn;
    }

    public String argumentList(ArrayList<String> variables, ArrayList<String> variablesArray, int val, int cantArguments) {

        x = r.nextInt(100);
        String codigo = "";
        while (cantArguments < methodCollection.get(val).getVariables().size()) {

            codigo += ID(variables) + ",";
            cantArguments++;
        }

        codigo += ID(variables);

        return codigo;
    }

    public String arrayCreationExpression() {

        String codigo;

        codigo = "new " + arrayCreationExpression2();

        return codigo;
    }

    public String arrayCreationExpression2() {
        x = r.nextInt(100);
        String codigo;

        codigo = primitiveType() + dimExpr();

        return codigo;
    }

    public String dimExpr() {
        x = r.nextInt(100);
        String codigo;
        codigo = "[" + 9 + "]";

        return codigo;
    }

    public String arrayAccess(ArrayList<String> variablesArray) {

        x = r.nextInt(5);
        String codigo;

        codigo = typeNameArray(variablesArray) + "[" + x + "]";

        return codigo;
    }

    public String arrayAccessInit(ArrayList<String> variablesArray) {

        x = r.nextInt(5);
        String codigo;

        codigo = IDArrayInit(variablesArray) + "[" + x + "]";

        return codigo;
    }

    /*Tokens*/
    public String name(ArrayList<String> variables, ArrayList<String> variablesArray) {

        x = r.nextInt(100);
        String codigo;
        if (x < 50) {
            codigo = typeName(variables);
        } else {
            codigo = arrayAccess(variablesArray);
        }

        return codigo;
    }

    public String typeName(ArrayList<String> variables) {

        x = r.nextInt(100);
        String codigo;

        codigo = ID(variables);

        return codigo;
    }

    public String typeNameArray(ArrayList<String> variablesArray) {

        String codigo;

        codigo = IDArray(variablesArray);

        return codigo;
    }

    public String methodPowName() {

        x = r.nextInt(100);
        String codigo;

        codigo = "Math.pow(" + literal() + ",n)";

        return codigo;
    }

    public String literal() {

        x = r.nextInt(100);
        String codigo;
        if (x <= 50) {
            codigo = integerLiteral();

        } else {
            codigo = "-" + integerLiteral();
        }

        return codigo;
    }

    public String integerLiteral() {

        String codigo;

        codigo = nonZeroDigit() + digits();

        return codigo;
    }

    public String digits() {

        x = r.nextInt(100);
        String codigo;

        if (x <= 25) {
            codigo = digits() + digit();

        } else {
            codigo = digit();
        }

        return codigo;
    }

    public String digit() {

        x = r.nextInt(100);
        String codigo;

        if (x <= 10) {
            codigo = "0";

        } else if (x > 10 && x <= 20) {
            codigo = "1";
        } else if (x > 20 && x <= 30) {
            codigo = "2";
        } else if (x > 30 && x <= 40) {
            codigo = "3";
        } else if (x > 40 && x <= 50) {
            codigo = "4";
        } else if (x > 50 && x <= 60) {
            codigo = "5";
        } else if (x > 60 && x <= 70) {
            codigo = "6";
        } else if (x > 70 && x <= 80) {
            codigo = "7";
        } else if (x > 80 && x <= 90) {
            codigo = "8";
        } else {
            codigo = "9";
        }

        return codigo;
    }

    public String nonZeroDigit() {

        x = r.nextInt(100);
        String codigo;

        if (x <= 11) {
            codigo = "1";
        } else if (x > 11 && x <= 22) {
            codigo = "2";
        } else if (x > 22 && x <= 33) {
            codigo = "3";
        } else if (x > 33 && x <= 44) {
            codigo = "4";
        } else if (x > 44 && x <= 55) {
            codigo = "5";
        } else if (x > 55 && x <= 66) {
            codigo = "6";
        } else if (x > 66 && x <= 77) {
            codigo = "7";
        } else if (x > 77 && x <= 88) {
            codigo = "8";
        } else {
            codigo = "9";
        }

        return codigo;
    }

    public String booleanLiteral() {

        x = r.nextInt(100);
        String codigo;

        if (x <= 50) {
            codigo = "true";

        } else {
            codigo = "false";
        }
        return codigo;
    }

    public String nullLiteral() {

        String codigo;

        codigo = "null";

        return codigo;
    }

    public String ID(ArrayList<String> variables) {

        String codigo;
        x = r.nextInt(variables.size());
        codigo = variables.get(x);

        return codigo;

    }

    public String IDForU(ArrayList<String> variables) {

        String codigo;

        codigo = variables.get(variables.size() - 1);

        return codigo;

    }

    public String IDInit(ArrayList<String> variables) {

        String codigo;
        x = r.nextInt(variables.size() - 1);
        codigo = variables.get(x);

        return codigo;

    }

    public String IDCreator(ArrayList<String> variables) {

        String codigo;

        codigo = "var" + ++cantidadVar;
        variables.add(codigo);
        return codigo;

    }

    public String IDFCreator(ArrayList<String> variables) {

        String codigo;

        codigo = "var" + ++cantidadVar;
        codigoF = codigo;
        return codigo;

    }

    public String IDArrayCreator(ArrayList<String> variablesArray) {

        String codigo;

        codigo = "varA" + ++cantidadVarA;
        variablesArray.add(codigo);
        return codigo;

    }

    public String IDArray(ArrayList<String> variablesArray) {
        String codigo;
        x = r.nextInt(variablesArray.size());
        codigo = variablesArray.get(x);

        return codigo;
    }

    public String IDArrayInit(ArrayList<String> variablesArray) {
        String codigo;
        x = r.nextInt(variablesArray.size() - 1);
        codigo = variablesArray.get(x);

        return codigo;
    }

    public String IDMethod() {

        String codigo;
        x = r.nextInt(cantidadMethod);

        codigo = methodCollection.get(x).getName();

        return codigo;
    }

    public String IDMethodG() {

        String codigo = "";

        if (contMethod < cantidadMethod) {

            codigo = "metodo" + ++contMethod;

        }

        return codigo;
    }

    public void restartStats(int max,int min) {
       
        cantIfElse = r.nextInt(max-min+1)+min;
        cantIf = r.nextInt(max-min+1)+min;
        cantWhile = r.nextInt(max-min+1)+min;
        cantFor = r.nextInt(max-min+1)+min;
        cantSwitch = r.nextInt(max-min+1)+min;
        
    }

    public PolynomialFunction maxFunction(PolynomialFunction f1, PolynomialFunction f2) {
        PolynomialFunction valorTn = valorIdentC;

        if (f1.degree() > f2.degree()) {
            valorTn = valorTn.add(f1);
        } else if (f1.degree() < f2.degree()) {
            valorTn = valorTn.add(f2);
        } else {
            double[] c = f1.getCoefficients();
            double[] d = f2.getCoefficients();
            for (int i = f1.degree(); i >= 0; i--) {

                if (c[i] >= d[i]) {
                    valorTn = (f1);
                    break;

                } else {
                    valorTn = (f2);
                    break;
                }
            }

        }

        return valorTn;
    }

    public ArrayList<Method> getMethodCollection() {
        return methodCollection;
    }



}
