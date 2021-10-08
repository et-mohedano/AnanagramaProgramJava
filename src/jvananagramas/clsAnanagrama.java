/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jvananagramas;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

/**
 *
 * @author efras
 */
public class clsAnanagrama {
    // Atributos
    int numPalabras;
    String[]arrPalabras;
    String[]arrAnagramas;
    // Arreglos auxiliares
    String[]arrAuxOrdenado;
    String[]arrEntrada;
    public clsAnanagrama() {
        numPalabras = 0;
        arrPalabras = new String[2];
        arrAnagramas = new String[2];
    }
    // Propiedades get y set
    public int getNumPalabras(){
        return numPalabras;
    }
    public void setNumPalabras(int numN){
        numPalabras = numN;
    }
    public void setArrPalabras(String []arrP){
        arrPalabras = arrP;
    }
    public String[] getArrPalabras(){
        return arrPalabras;
    }
    public void setArrAnanagrama(String []arrP){
        arrAnagramas = arrP;
    }
    public String[] getArrAnanagrama(){
        return arrAnagramas;
    }
    // Metodos
    public void generarPalabras(){
        int n = ThreadLocalRandom.current().nextInt(2, 11);
        setNumPalabras(n);
    }
    public int validadPalabra(String palabra, String[]arrP){
        // 0 -> Valida
        // 1 -> error longitud
        // 2 -> error espacio
        // 3 -> error repetida
        // 4 -> valor númerico
        int valida = 0, contRep = 0;
        boolean espacio = palabra.contains(" ");
        if ((palabra.length()>20) || (palabra.length()<3)) {
            valida = 1;
        }else if(espacio){
            valida = 2;
        }else if(Pattern.matches("[a-zA-Z]+", palabra) == false){
            valida = 4;
        }else{
            for (int i = 0; i < arrP.length; i++) {
                if (arrP[i].equals(normalizarPalabra(palabra))) {
                    contRep++;
                }
            }
            if (contRep > 0) {
                valida = 3;
            }
        }
        return valida;
    }
    public String normalizarPalabra(String palabra) { 
        palabra = palabra.toLowerCase();
        palabra = Normalizer.normalize(palabra, Form.NFD) 
        .replaceAll("\\p{InCombiningDiacriticalMarks}+", ""); 
        return palabra;
    }
    public String[] ordenarPalabras(String[] arrP) {
        arrEntrada = new String[numPalabras];
        arrEntrada = arrP;
        char[] letrasPalabra;
        String palabra = "";
        arrAuxOrdenado = new String[numPalabras];
        for (int i = 0; i < arrP.length; i++) {
            letrasPalabra = new char[arrP[i].length()];
            letrasPalabra = arrP[i].toCharArray();
            for (int j = 0; j < letrasPalabra.length; j++) {
                for (int k = 0; k < letrasPalabra.length; k++) {
                    if (letrasPalabra[j] < letrasPalabra[k]) {
                        char variableauxiliar = letrasPalabra[j];
                        letrasPalabra[j] = letrasPalabra[k];
                        letrasPalabra[k] = variableauxiliar;
                    }
                }
            }
            for (int j = 0; j < letrasPalabra.length; j++) {
                palabra += letrasPalabra[j];
            }
            arrAuxOrdenado[i] = palabra;
            palabra = "";
        }
        return arrAuxOrdenado;
    }
    public void limpiarObjetos() {
        numPalabras = 0;
        arrPalabras = new String[2];
        arrAnagramas = new String[2];
    }
    public void validarAnagrama(){
        char[] letrasPalabra; // Arreglo para la palabra buscada
        char[] letrasPalabra2; // Arreglo para descomponer las demás palabras
        boolean letraRep = true; // Revisar si la letra se repite vs las letras de otra palabra
        boolean palabraRep = true; // Revidar si todas sus letras de esa palabra se repiten en otra
        boolean vAnanagrama = true;
        int contAnag = 0;// Cuenta cuantos ananagramas hay
        // --- Buscar el número de ananagramas para crear el arreglo ---
        for (int i = 0; i < arrPalabras.length; i++) {
            letrasPalabra = new char[arrPalabras[i].length()];
            letrasPalabra = arrPalabras[i].toCharArray();
            for (int j = 0; j < arrPalabras.length; j++) {
                if (i!=j) {
                    letrasPalabra2 = new char[arrPalabras[j].length()];
                    letrasPalabra2 = arrPalabras[j].toCharArray();
                    for (int k = 0; k < letrasPalabra.length; k++) {
                        for (int l = 0; l < letrasPalabra2.length; l++) {
                            if (letrasPalabra[k] == letrasPalabra2[l]) {
                                letrasPalabra2[l] = '0';
                                letraRep = false;
                                break;
                            }
                        }
                        if (letraRep) {
                            palabraRep = false;
                            break;
                        }
                        letraRep = true;
                    }
                    if (palabraRep) {
                        palabraRep = true;
                        vAnanagrama = false;
                    }else{
                        contAnag++;
                        palabraRep = true;
                    }
                }
            }
            vAnanagrama = true; 
        }
        // --- Ingresar ananagramas al arreglo ---
        arrAnagramas = new String[contAnag];
        contAnag = 0;
        letraRep = true; // Revisar si la letra se repite vs las letras de otra palabra
        palabraRep = true; // Revidar si todas sus letras de esa palabra se repiten en otra
        vAnanagrama = true;
        for (int i = 0; i < arrPalabras.length; i++) {
            letrasPalabra = new char[arrPalabras[i].length()];
            letrasPalabra = arrPalabras[i].toCharArray();
            for (int j = 0; j < arrPalabras.length; j++) {
                if (i!=j) {
                    letrasPalabra2 = new char[arrPalabras[j].length()];
                    letrasPalabra2 = arrPalabras[j].toCharArray();
                    for (int k = 0; k < letrasPalabra.length; k++) {
                        for (int l = 0; l < letrasPalabra2.length; l++) {
                            if (letrasPalabra[k] == letrasPalabra2[l]) {
                                letrasPalabra2[l] = '0';
                                letraRep = false;
                                break;
                            }
                        }
                        if (letraRep) {
                            palabraRep = false;
                            break;
                        }
                        letraRep = true;
                    }
                    if (palabraRep) {
                        palabraRep = true;
                        vAnanagrama = false;
                    }else{
                        palabraRep = true;
                    }
                }
            }
            if (vAnanagrama) {
                arrAnagramas[contAnag] = arrEntrada[i];
                contAnag++;
            }
            vAnanagrama = true;
        }
    }
}
