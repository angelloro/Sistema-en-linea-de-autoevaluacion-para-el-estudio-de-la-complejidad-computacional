/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaporware.GeneradorComplejidad;

import java.util.ArrayList;
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

    public Method(String name, ArrayList<String> variables, boolean type, PolynomialFunction tn, PolynomialFunction tnLog, boolean log,PolynomialFunction tnExp, boolean exp,ArrayList<String> valores) {
        this.name = name;
        this.variables = variables;
        this.type = type;
        this.tn = tn;
        this.codigo = "";
        this.tnLog = tnLog;
        this.log = log;
        this.tnExp = tnExp;
        this.log = exp;
        this.valores=valores;
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
            return "Method{" + "name=" + name + ", type=" + type + ", tn=" + tn + "+ Log("+tnLog+"n)"  + '}' + '\n' + codigo+"\n"+mostrarValores();
        } else if(exp) {
             return "Method{" + "name=" + name + ", type=" + type + ", tn=" + tn + "+ 2^n*" +'('+tnExp+')' + '}' + '\n' + codigo+"\n"+mostrarValores();
        }else{
            return "Method{" + "name=" + name + ", type=" + type + ", tn=" + tn + '}' + '\n' + codigo+"\n"+mostrarValores();
        }

    }
    public String correccion(){
        if (log) {
            return "Method{" + "name=" + name + ", Tn=" + tn + "+ Log("+tnLog+"n)"  + '}' + '\n'+mostrarValores();
        } else if(exp) {
             return "Method{" + "name=" + name + ", Tn=" + tn + "+ 2^n*" +'('+tnExp+')' + '}' + "\n"+mostrarValores();
        }else{
            return "Method{" + "name=" + name +  ", Tn=" + tn + '}' + "\n"+mostrarValores();
        }
    }
    
    public String separador(){
        return "\n============================================================================\n";
    }

 
     public String mostrarValores(){
         String valor="";
         
         for(String e: valores){
             valor=e+"\n"+valor;
         }
        return valor;
    }
     
    

}
