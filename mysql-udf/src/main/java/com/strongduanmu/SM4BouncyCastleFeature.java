package com.strongduanmu;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeClassInitialization;

import java.security.Security;

@SuppressWarnings("unused")
public final class SM4BouncyCastleFeature implements Feature {
    
    @Override
    public void afterRegistration(final AfterRegistrationAccess access) {
        RuntimeClassInitialization.initializeAtBuildTime("org.bouncycastle");
        RuntimeClassInitialization.initializeAtRunTime("org.bouncycastle.jcajce.provider.drbg.DRBG$Default");
        RuntimeClassInitialization.initializeAtRunTime("org.bouncycastle.jcajce.provider.drbg.DRBG$NonceAndIV");
        Security.addProvider(new BouncyCastleProvider());
    }
}
