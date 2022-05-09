package com.splunk.k8smanager.controller

import com.splunk.k8smanager.model.Deployment
import com.splunk.k8smanager.model.response.PodsResponse
import com.splunk.k8smanager.service.ClusterService
import com.splunk.k8smanager.toResponse
import io.kubernetes.client.custom.V1Patch
import io.kubernetes.client.openapi.models.V1Scale
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/clusters")
class ClustersController(
    @Autowired val clusterService: ClusterService
) {

    @GetMapping("/pods")
    @ResponseBody
    fun getPodsInfo(): PodsResponse = clusterService.getPodList().toResponse()

    @GetMapping("/deployments")
    @ResponseBody
    fun getDeployments(): List<Deployment>? {
        return clusterService.getDeployments()
    }

    @GetMapping(value = ["/deployment"], params = ["name", "namespace"])
    @ResponseBody
    fun getDeploymentsByName(
        @RequestParam("name") name: String,
        @RequestParam("namespace") namespace: String?): Deployment? {
        return clusterService.getDeployment(name, namespace ?: "default")
    }


    @Deprecated("Old Way", replaceWith = ReplaceWith("scaleDeployment()"))
    @ResponseBody
    private fun scaleDeploymentWithPostBody(
        @RequestParam("name") name: String,
        @RequestParam("namespace") namespace: String?,
        @RequestBody body: String
    ): Deployment {
        return clusterService.scaleDeployment(name, namespace ?: "default", V1Patch(body))
    }

    @PostMapping(value = ["/deployment/scale"], params = ["namespace", "label", "size"])
    @ResponseBody
    fun scaleDeployment(
        @RequestParam("namespace") namespace: String?,
        // query parameter value must be in the format '<labelKey>=<labelValue>' see: https://www.baeldung.com/java-kubernetes-namespaces-selectors#using_label_selectors
        @RequestParam("label") label: String,
        @RequestParam("size") size: Int
    ): V1Scale {
        return clusterService.scaleDeploymentByLabelSelector(namespace, label, size) ?: throw Exception("oh no!")
    }

    @PostMapping(value = ["/deployment/image"], params = ["name", "namespace"])
    @ResponseBody
    fun updateImage(
        @RequestParam("name") name: String,
        @RequestParam("namespace") namespace: String?,
        @RequestBody body: String
    ): Deployment {
        return clusterService.updateImage(name, namespace ?: "default", V1Patch(body))
    }
}