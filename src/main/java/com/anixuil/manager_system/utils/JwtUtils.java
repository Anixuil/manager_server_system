package com.anixuil.manager_system.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtils{
    //有效期
    public static final long EXPIRE = 60 * 60 * 1000L;
    //秘钥
    public static final String SECRETKEY = "anixuil";

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    //生成jwt
    public static String createJWT(String subject){
        JwtBuilder builder = getJwtBuilder(subject,null,getUUID());//设置过期时间
        return builder.compact();
    }

    //生成jwt
    public static String createJWT(String subject, long ttlMillis){
        JwtBuilder builder = getJwtBuilder(subject,ttlMillis,getUUID());//设置过期时间
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if(ttlMillis == null){
            ttlMillis = EXPIRE;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)                            //唯一的id
                .setSubject(subject)                    //主题 可以是JSON数据
                .setIssuer("anixuil")                   //签名是有谁生成 例如 服务器
                .setIssuedAt(now)                       //签发时间
                .signWith(signatureAlgorithm, secretKey)//签名算法以及密匙
                .setExpiration(expDate);                //设置过期时间
    }

    //创建token
    public static String createJWT(String id,String subject,long ttlMillis){
        JwtBuilder builder = getJwtBuilder(subject,ttlMillis,id);//设置过期时间
        return builder.compact();
    }

//    public static void main(String[] args) throws Exception{
//        String jwt = createJWT("liuxin");
//        System.out.println(parseJWT(jwt));
//    }

    //生成加密的密钥secretKey
    public static SecretKey generalKey(){
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtils.SECRETKEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    //解析jwt
    public static Claims parseJWT(String jwt) throws Exception{
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt).getBody();
    }
}
