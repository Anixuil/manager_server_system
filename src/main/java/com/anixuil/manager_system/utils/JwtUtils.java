package com.anixuil.manager_system.utils;

import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.UserTable;
import io.jsonwebtoken.*;
import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Configuration
public class JwtUtils {
    private static String secret = "anixuil";

    private static long expire = 3600000;

    private static String aesKey = "anixuil";

    //生成token
    public String createToken(Map UserTable){
        //拿取用户信息
        String userUuid = UserTable.get("user_uuid").toString();
        String userName = UserTable.get("user_name").toString();

        //jwt配置
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;   //指定签名算法
        long nowMillis = System.currentTimeMillis();    //时间
        Date now = new Date(nowMillis);     //时间

        //创建payload的私有声明
        Map<String,Object> claims = new HashMap<String,Object>();
        claims.put("userUuid",userUuid);
        claims.put("userName",userName);

        Map<String,Object> header = new HashMap();
        header.put("typ","JWT");
        header.put("alg","HS256");
        JwtBuilder builder = Jwts.builder()
                .setHeader(header)
                .setId(UUID.randomUUID().toString())
                .setSubject(String.valueOf(userUuid))
                .setIssuedAt(now)
                .setExpiration(new Date(nowMillis + expire))
                .signWith(signatureAlgorithm,secret)
                .setClaims(claims);

        //为了避免中间一段base64被解析出来，这里进行了aes二次加密
        String token = builder.compact();
        //返回二次加密的token
        return AESUtil.encrypt(token,aesKey);

    }

//    校验token
    public static Rest verifyToken(HttpServletRequest httpServletRequest, String token){
        String msg = "token验证";
        try{
            //先进行二次解密获取原来的token
            String aesToken = AESUtil.decrypt(token,aesKey);
            //指定签名算法
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            //得到
//            Claims claims = Jwts.parser()
//                    .setSigningKey(secret)
//                    .parseClaimsJws(aesToken).getBody();

//            System.out.println(claims);
            //验证成功后 将解密的token设置到request中的header中去
            reflectSetTokenValue(httpServletRequest,"token",aesToken);
            return Rest.success(msg,true);
        }catch (SignatureException | MalformedJwtException e){
            return Rest.error(msg,e);
        }catch (ExpiredJwtException e){
            return Rest.error(msg,e);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //解析token
    public static Claims parseJWT(String token){
        try{
            //先进行二次解密获取原来的token
            String aesToken = AESUtil.decrypt(token,aesKey);
//            指定签名算法
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            //得到
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(aesToken).getBody();

//            System.out.println(claims);
            return claims;
        }catch (SignatureException | MalformedJwtException e){
            System.out.println(e);
        }catch (ExpiredJwtException e){
            System.out.println(e);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    private static void reflectSetTokenValue(HttpServletRequest request,String key,String value){
        Class<? extends HttpServletRequest> requestClass = request.getClass();
        try{
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o = request1.get(request);
            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o1 = coyoteRequest.get(o);
            Field headers = o1.getClass().getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders o2 = (MimeHeaders)headers.get(o1);
            //将AES格式的token替换成JWT格式token
            o2.getValue(key).setString(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

