package com.splunk.k8smanager.service

import com.splunk.k8smanager.model.Pod
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.openapi.models.V1PodList
import org.springframework.stereotype.Service


@Service
class ClusterService(
    val k8sApi: CoreV1Api
) {

    fun getPodList(): List<Pod> {
        val list: V1PodList = k8sApi.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null)
        return parsePodList(list)
    }

    private fun parsePodList(list: V1PodList): List<Pod> {
        return list.items.map { pod ->
            Pod(
                pod.metadata?.name ?: "unknown",
                pod.metadata?.namespace ?: "unknown"
            )
        }
    }

    fun getClusterInfo() = "Clusters!"
}