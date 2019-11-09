package com.alanginger.moments.network

import android.content.Context

import java.io.ByteArrayInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

import okhttp3.OkHttpClient

object HTTPSCerUtils {

    //信任所有证书
    fun setTrustAllCertificate(okHttpClientBuilder: OkHttpClient.Builder) {
        try {
            val sc = SSLContext.getInstance("TLS")
            val trustAllManager = object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {

                }

                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {

                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return emptyArray()
                }
            }
            sc.init(null, arrayOf<TrustManager>(trustAllManager), SecureRandom())
            okHttpClientBuilder.sslSocketFactory(sc.socketFactory, trustAllManager)
            okHttpClientBuilder.hostnameVerifier { hostname, session -> true }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    //只信任指定证书（传入字符串）
    fun setCertificate(
        context: Context,
        okHttpClientBuilder: OkHttpClient.Builder,
        cerStr: String
    ) {
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val byteArrayInputStream = ByteArrayInputStream(cerStr.toByteArray())
            val ca = certificateFactory.generateCertificate(byteArrayInputStream)

            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null, null)
            keyStore.setCertificateEntry("ca", ca)

            byteArrayInputStream.close()

            val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            tmf.init(keyStore)

            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, tmf.trustManagers, SecureRandom())
            okHttpClientBuilder.sslSocketFactory(
                sslContext.socketFactory,
                tmf.trustManagers[0] as X509TrustManager
            )
            okHttpClientBuilder.hostnameVerifier { hostname, session -> true }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    //只信任指定证书（传入raw资源ID）
    fun setCertificate(context: Context, okHttpClientBuilder: OkHttpClient.Builder, cerResID: Int) {
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val inputStream = context.resources.openRawResource(cerResID)
            val ca = certificateFactory.generateCertificate(inputStream)

            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null, null)
            keyStore.setCertificateEntry("ca", ca)

            inputStream.close()

            val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            tmf.init(keyStore)

            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, tmf.trustManagers, SecureRandom())
            okHttpClientBuilder.sslSocketFactory(
                sslContext.socketFactory,
                tmf.trustManagers[0] as X509TrustManager
            )
            okHttpClientBuilder.hostnameVerifier { hostname, session -> true }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    //批量信任证书
    fun setCertificates(
        context: Context,
        okHttpClientBuilder: OkHttpClient.Builder,
        vararg cerResIDs: Int
    ) {
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")

            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null, null)
            for (i in cerResIDs.indices) {
                val ca = certificateFactory.generateCertificate(
                    context.resources.openRawResource(cerResIDs[i])
                )
                keyStore.setCertificateEntry("ca$i", ca)
            }

            val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            tmf.init(keyStore)

            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, tmf.trustManagers, SecureRandom())
            okHttpClientBuilder.sslSocketFactory(
                sslContext.socketFactory,
                tmf.trustManagers[0] as X509TrustManager
            )
            okHttpClientBuilder.hostnameVerifier { hostname, session -> true }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
