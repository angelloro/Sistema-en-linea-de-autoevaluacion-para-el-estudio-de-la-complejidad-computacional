/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaporware.GeneradorComplejidad;

import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.math4.analysis.polynomials.PolynomialFunction;

/**
 *
 * @author Angel
 */
public class Method {

    private String name;
    private String codigo;
    ArrayList<String> variables = new ArrayList<String>();
    ArrayList<String> valores = new ArrayList<String>();
    private boolean type;
    private PolynomialFunction tn;
    private PolynomialFunction tnLog;
    private PolynomialFunction tnExp;
    private boolean log;
    private boolean exp;
    private boolean nlog;
    ArrayList<String> lista = new ArrayList();

    public Method(String name, ArrayList<String> variables, boolean type, PolynomialFunction tn, PolynomialFunction tnLog, boolean log, PolynomialFunction tnExp, boolean exp, boolean funcnLog, ArrayList<String> valores) {
        this.name = name;
        this.variables = variables;
        this.type = type;
        this.tn = tn;
        this.codigo = "";
        this.tnLog = tnLog;
        this.log = log;
        this.tnExp = tnExp;
        this.log = exp;
        this.nlog = funcnLog;
        this.valores = valores;
    }

    public boolean isNlog() {
        return nlog;
    }

    public boolean isType() {
        return type;
    }

    public String getCodigo() {
        return codigo;
    }

    public PolynomialFunction getTn() {
        return tn;
    }

    public boolean isLog() {
        return log;
    }

    public PolynomialFunction getTnExp() {
        return tnExp;
    }

    public boolean isExp() {
        return exp;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getValores() {
        return valores;
    }

    public ArrayList<String> getVariables() {
        return variables;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setTn(PolynomialFunction tn) {
        this.tn = tn;
    }

    public void setTnLog(PolynomialFunction tnLog) {
        this.tnLog = tnLog;
    }

    public void setLog(boolean log) {
        this.log = log;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVariables(ArrayList<String> variables) {
        this.variables = variables;
    }

    public void setTnExp(PolynomialFunction tnExp) {
        this.tnExp = tnExp;
    }

    public void setExp(boolean exp) {
        this.exp = exp;
    }

    public void setValores(ArrayList<String> valores) {
        this.valores = valores;
    }

    public PolynomialFunction getTnLog() {
        return tnLog;
    }

    @Override
    public String toString() {
        if (log) {
            return "Method{" + "name=" + name + ", type=" + type + ", tn=" + tn + "+ Log(" + tnLog + "n)" + '}' + '\n' + codigo + "\n" + mostrarValores();
        } else if (exp) {
            return "Method{" + "name=" + name + ", type=" + type + ", tn=" + tn + "+ 2^n*" + '(' + tnExp + ')' + '}' + '\n' + codigo + "\n" + mostrarValores();
        } else {
            return "Method{" + "name=" + name + ", type=" + type + ", tn=" + tn + '}' + '\n' + codigo + "\n" + mostrarValores();
        }

    }

    public String correccion() {
        if (nlog) {
            return "Method{" + "Tn=" + tn + "Log(" + tnLog + "n)" + '}' + '\n' + mostrarValores();
        } else if (log) {
            return "Method{" + "Tn=" + tn + "+ Log(" + tnLog + "n)" + '}' + '\n' + mostrarValores();
        } else if (exp) {
            return "Method{" + "Tn=" + tn + "+ 2^n*" + '(' + tnExp + ')' + '}' + "\n" + mostrarValores();
        } else {
            return "Method{" + "Tn=" + tn + '}' + "\n" + mostrarValores();
        }
    }

    public String valor() {
        if (nlog) {
            return tn + "Log(" + tnLog + "n)";
        } else if (log) {
            return tn + "+ Log(" + tnLog + "n)";
        } else if (exp) {
            return tn + "+ 2^n*" + '(' + tnExp + ')';
        } else {
            return tn + "";
        }
    }

    public ArrayList<String> valorModificado() {

        PolynomialFunction g;

        PolynomialFunction g2;
        PolynomialFunction g3;

        double[] c1 = {3};
        g = new PolynomialFunction(c1);

        double[] c3 = {25};
        g2 = new PolynomialFunction(c3);
        double[] c4 = new double[tn.degree() + 1];
        for (int i = 0; i <= tn.degree(); i++) {
            c4[i] = 20;
        }
        g3 = new PolynomialFunction(c4);

        if (nlog) {
            lista.add(valor());
            lista.add(tn.multiply(g) + "Log(" + tnLog + "n)");
            lista.add(tn.add(g3) + "Log(" + tnLog.add(g2) + "n)");
            lista.add(tn.subtract(g3) + "Log(" + tnLog.subtract(g2) + "n)");

        } else if (log) {
            lista.add(valor());
            lista.add(tn.multiply(g) + "+ Log(" + tnLog + "n)");
            lista.add(tn.add(g3) + "+ Log(" + tnLog.add(g2) + "n)");
            lista.add(tn.subtract(g3) + "+ Log(" + tnLog.subtract(g2) + "n)");

        } else if (exp) {
            lista.add(valor());
            lista.add(tn.multiply(g) + "+ 2^n*" + '(' + tnExp + ')');
            lista.add(tn.add(g3) + "+ 2^n*" + '(' + tnExp.add(g2) + ')');
            lista.add(tn.subtract(g3) + "+ 2^n*" + '(' + tnExp.subtract(g2) + ')');

        } else {
            lista.add(valor());
            lista.add(tn.multiply(g) + "");
            lista.add(tn.add(g3) + "");
            lista.add(tn.subtract(g3) + "");

        }
        Collections.shuffle(lista);
        return lista;
    }

    public ArrayList<String> getLista() {
        return lista;
    }

    public String separador() {
        return "\n============================================================================\n";
    }

    public String mostrarValores() {
        String valor = "";

        for (String e : valores) {
            valor = e + "\n" + valor;
        }
        return valor;
    }

}
