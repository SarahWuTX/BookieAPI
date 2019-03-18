package com.tj.bookie.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tj.bookie.DAO.AdminRepository;
import com.tj.bookie.utility.model.Admin;
import com.tj.bookie.utility.request.AdminLogin;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


@Transactional
@Service
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    public ResponseEntity<?> login(AdminLogin adminLogin) throws JSONException {
        Admin admin = adminRepository.findByName(adminLogin.getUsername());
        if (admin == null) {
            return new ResponseEntity<>("用户名或密码错误", HttpStatus.BAD_REQUEST);
        }

        if (!adminLogin.getPwd().equals(admin.getPassword())) {
            return new ResponseEntity<>("用户名或密码错误", HttpStatus.BAD_REQUEST);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", this.createJwt(admin));

        JSONObject data = new JSONObject();
        data.put("name", admin.getName());
        data.put("nickname", admin.getName());
        data.put("email", admin.getName());
        return new ResponseEntity<>(data.toString(), headers, HttpStatus.OK);
    }


//    private String encrypt(String originPwd) throws NoSuchAlgorithmException{
//
//        byte[] result;
//        MessageDigest algo = MessageDigest.getInstance("SHA-1");// 得到一个SHA-1的消息摘要
//        algo.update(originPwd.getBytes());  // 添加加密信息
//        result = algo.digest(); // 得到该摘要
//        if (result == null) {
//            return null;
//        }
//        return new String(result);
//    }


    /**创建JWT*/
    private String createJwt(Admin admin) throws IllegalArgumentException {
        Algorithm al = Algorithm.HMAC256(admin.getPassword());
        return JWT.create()
                .withIssuer("Bookie")
                .withSubject(admin.getName())
                .withClaim("user_id", admin.getId().hashCode())
                .withExpiresAt(new Date(System.currentTimeMillis()+360000))
                .sign(al);
    }


    /**验证jwt*/
    public static void verifyJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret-key");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            System.out.println("校验失败");
        }
    }
}
