package com.splunk.k8smanager.service

import com.splunk.k8smanager.OPERATION
import com.splunk.k8smanager.extension.toDeployment
import com.splunk.k8smanager.extension.toDeploymentSpec
import com.splunk.k8smanager.extension.toV1Patch
import com.splunk.k8smanager.model.Deployment
import com.splunk.k8smanager.model.Pod
import io.kubernetes.client.custom.V1Patch
import io.kubernetes.client.openapi.apis.AppsV1Api
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.openapi.models.V1Deployment
import io.kubernetes.client.openapi.models.V1PodList
import io.kubernetes.client.openapi.models.V1Scale
import org.springframework.stereotype.Service


@Service
class ClusterService(
    val coreApi: CoreV1Api,
    val appsV1Api: AppsV1Api
) {

    fun getPodList(): List<Pod> {
        val list: V1PodList =
            coreApi.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null)
        return parsePodList(list)
    }


    fun getDeployments(): List<Deployment>? {
        val deploymentList =
            appsV1Api.listDeploymentForAllNamespaces(null, null, null, null, null, "pretty", null, null, null, null)
        return deploymentList?.items?.map { item ->
            Deployment(
                name = item.metadata?.name!!,
                spec = item.spec?.toDeploymentSpec()
            )
        }
    }

    fun getDeployment(name: String, namespace: String): Deployment {
        return appsV1Api.readNamespacedDeployment(name, namespace, null).toDeployment()
    }

    fun scaleDeployment(name: String, namespace: String?, body: V1Patch): Deployment {
        return appsV1Api.patchNamespacedDeployment(
            name,
            namespace ?: "default",
            body,
            null,
            null,
            null,
            "Warn",
            null
        ).toDeployment()
    }

    // assumes only one element is coming back in the list of deployments
    fun scaleDeploymentByLabelSelector(namespace: String?, labelSelector: String, replicas: Int): V1Scale? {
        /// Get the deployment with the matching label
        val deployment: V1Deployment? = appsV1Api.listNamespacedDeployment(
            namespace ?: "default",
            null,
            false,
            null,
            null,
            labelSelector,
            null,
            null,
            null,
            5,
            null
        ).items.firstOrNull()

        val foo = OPERATION.REPLACE.value

        val patch: V1Patch = "[{\"op\": \"replace\", \"path\": \"/spec/replicas\",\"value\": $replicas}]".toV1Patch()

        val result: V1Scale? = deployment.let { it ->
            appsV1Api.patchNamespacedDeploymentScale(
                it!!.metadata?.name,
                it.metadata?.namespace ?: "default",
                patch,
                null,
                null,
                null,
                "strict",
                null
            )
        }
        return result
    }

    fun updateImage(name: String, namespace: String?, body: V1Patch): Deployment {
        val updated: V1Deployment = appsV1Api.patchNamespacedDeployment(
            name,
            namespace ?: "default",
            body,
            null,
            null,
            null,
            "Warn",
            null
        )
        return updated.toDeployment()
    }

    private fun parsePodList(list: V1PodList): List<Pod> {
        return list.items.map { pod ->
            Pod(
                pod.metadata?.name ?: "unknown",
                pod.metadata?.namespace ?: "unknown"
            )
        }
    }

}