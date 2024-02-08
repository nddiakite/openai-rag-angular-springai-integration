package com.prasannjeet.social.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderUtil {
    public static void main() {
        System.out.println("\n\n ---- NDI => Mot de passe encodé ---- \n\n");

        PasswordEncoderUtil.encode("admin");

        PasswordEncoderUtil.encode("openai");

        PasswordEncoderUtil.encode("nddiakite");

        System.out.println("\n\n ------------------------------------- \n\n");
    }

    public static void encode(String passwordClear) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String passwordEncoded = encoder.encode(passwordClear);

        System.out.println("Mot de passe encodé : " + passwordEncoded);
    }

    public static void encodeLegacy(String passwordClear) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordEncoded = encoder.encode(passwordClear);

        System.out.println("Mot de passe encodé : " + passwordEncoded);
    }
}
