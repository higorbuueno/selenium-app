package com.selenium.selenium.infraestructure.webclient;

import java.net.URI;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

/**
 * @author Gustavo Rubin
 *
 */
public class WebClientBuilder {

    private WebClientBuilder() {
	throw new IllegalStateException("Utility class");
    }

    public static WebClient build(String networkProxyHost, Integer networkProxyPort) {
	HttpClient client = null;

	if (StringUtils.hasLength(networkProxyHost)) {
	    validateUri(networkProxyHost);
	    final Integer validNetworkProxyPort = validatePort(networkProxyPort);

	    client = HttpClient.create().proxy(
		    spec -> spec.type(ProxyProvider.Proxy.HTTP).host(networkProxyHost).port(validNetworkProxyPort))
		    .secure(spec -> spec.sslContext(createSSLContextWithoutSecurity()));
	} else {
	    client = HttpClient.create().secure(spec -> spec.sslContext(createSSLContextWithoutSecurity()));
	}

	ClientHttpConnector connector = new ReactorClientHttpConnector(client);

	return WebClient.create().mutate().clientConnector(connector).build();
    }

    private static void validateUri(String uri) {
	try {
	    URI.create(uri);

	} catch (Exception e) {
	    throw new IllegalArgumentException("Invalid proxy URI");
	}
    }

    private static Integer validatePort(Integer port) {
	if (port != null) {
	    if (0 > port || port > 65536) {
		throw new IllegalArgumentException("Invalid proxy port");
	    }

	} else {
	    port = 8080;
	}

	return port;
    }

    private static SslContext createSSLContextWithoutSecurity() {
	try {
	    return SslContextBuilder.forClient().trustManager(new X509TrustManager() {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		    return null;
		}

		public void checkClientTrusted(X509Certificate[] certs, String authType) {
		}

		public void checkServerTrusted(X509Certificate[] certs, String authType) {
		}
	    }).build();

	} catch (Exception e) {
	    throw new IllegalStateException("Error creating TLS context with P12. Error=" + e.getMessage());
	}
    }

}
