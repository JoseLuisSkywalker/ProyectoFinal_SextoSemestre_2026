/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Seguridad;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;

public class Seguridad {

    public static String encriptarSHA256(String texto) {
        //a7177c8f1132f5f7ebf3637a727ca49757be5dd6f1719d86f293c78b8f753736
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(texto.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();

            for (byte b : hash) {

                sb.append(String.format("%02x", b));}

            return sb.toString();

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

}