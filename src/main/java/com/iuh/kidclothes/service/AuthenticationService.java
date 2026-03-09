package com.iuh.kidclothes.service;

import com.iuh.kidclothes.dto.request.AuthenticationRequest;
import com.iuh.kidclothes.dto.request.IntrospectRequest;
import com.iuh.kidclothes.dto.respone.AuthenticationRespone;
import com.iuh.kidclothes.dto.respone.IntrospectRespone;
import com.iuh.kidclothes.entity.InvalidatedToken;
import com.iuh.kidclothes.entity.User;
import com.iuh.kidclothes.exception.AppException;
import com.iuh.kidclothes.exception.ErrorCode;
import com.iuh.kidclothes.mapper.UserMapper;
import com.iuh.kidclothes.repository.InvalidateTokenRepository;
import com.iuh.kidclothes.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNED_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    UserRepository userRepository;
    UserMapper userMapper;
    InvalidateTokenRepository invalidateTokenRepository;

    public AuthenticationRespone signin(AuthenticationRequest request){
        User user=userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new AppException(ErrorCode.USER_UN_EXISTED));

        PasswordEncoder password = new BCryptPasswordEncoder(10);
        boolean authenticed=password.matches(request.getPassword(),user.getPassword());
        if(!authenticed) {
            throw new AppException(ErrorCode.UNAUTHENTICATION);
        }
        String token=generateToken(user);
        return AuthenticationRespone.builder().token(token).authenticated(authenticed).build();
    }
    public IntrospectRespone introspect(IntrospectRequest request)
            throws JOSEException, ParseException{
        var token=request.getToken();
        boolean isValid=true;
        try{
            verifyToken(token,false);
        }catch (Exception e){
            isValid=false;
        }
        return IntrospectRespone.builder().valid(isValid).build();
    }
    private SignedJWT verifyToken(String token, boolean isRefresh)
            throws JOSEException, ParseException {
        JWSVerifier jwsVerifier = new MACVerifier(SIGNED_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        var verified = signedJWT.verify(jwsVerifier);

        Date exp =
                isRefresh
                        ? new Date(
                        signedJWT
                                .getJWTClaimsSet()
                                .getIssueTime()
                                .toInstant()
                                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                                .toEpochMilli())
                        : signedJWT.getJWTClaimsSet().getExpirationTime();

        if (!(verified && exp.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATION);
        }
        if (invalidateTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATION);
        }
        return signedJWT;
    }
    private String generateToken(User user){
        JWSHeader header=new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet=
                new JWTClaimsSet.Builder()
                        .subject(user.getEmail())
                        .issuer("bill.com")
                        .issueTime(new Date())
                        .expirationTime(new Date(
                                Instant.now()
                                        .plus(VALID_DURATION, ChronoUnit.HOURS)
                                        .toEpochMilli()))
                        .jwtID(UUID.randomUUID().toString())
                        .claim("scope","ROLE_"+user.getRole().toString())
                        .build();
        Payload payload=new Payload(jwtClaimsSet.toJSONObject());
        JWSObject token=new JWSObject(header,payload);
        try {
            token.sign(new MACSigner(SIGNED_KEY.getBytes()));
            return token.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token");
            throw new AppException(ErrorCode.CANNOT_CREATE_TOKEN);
        }
    }

}
