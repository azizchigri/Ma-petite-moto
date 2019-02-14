package com.epitech.mylittlebike.security;

/**
 * The type Security constants.
 */
public class SecurityConstants {
    /**
     * The constant SECRET.
     */
    public static final String SECRET = "SecretKeyToGenJWTs";
    /**
     * The constant EXPIRATION_TIME.
     */
    public static final long EXPIRATION_TIME = 86_400_000;
    /**
     * The constant TOKEN_PREFIX.
     */
    public static final String TOKEN_PREFIX = "Bearer ";
    /**
     * The constant HEADER_STRING.
     */
    public static final String HEADER_STRING = "Authorization";
    /**
     * The constant SIGN_UP_URL.
     */
    public static final String SIGN_UP_URL = "/users/sign-up";
}
