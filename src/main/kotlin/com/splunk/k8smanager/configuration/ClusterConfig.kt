package com.splunk.k8smanager.configuration

import io.kubernetes.client.openapi.ApiClient
import io.kubernetes.client.openapi.Configuration
import io.kubernetes.client.openapi.apis.AppsV1Api
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.util.ClientBuilder
import io.kubernetes.client.util.KubeConfig
import org.springframework.context.annotation.Bean
import java.io.FileReader

@org.springframework.context.annotation.Configuration
class ClusterConfig {

    private final val kubeConfigPath = "/Users/tjohander/.kube/config";

    @Bean
    fun coreApi(): CoreV1Api {
        val client: ApiClient = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(FileReader(kubeConfigPath))).build()
        Configuration.setDefaultApiClient(client)
        return CoreV1Api()
    }

    @Bean
    fun appsApi(): AppsV1Api {
        val client: ApiClient = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(FileReader(kubeConfigPath))).build()
        Configuration.setDefaultApiClient(client)
        return AppsV1Api()
    }
}