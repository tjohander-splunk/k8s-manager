package com.splunk.k8smanager.controller

import com.splunk.k8smanager.model.Deployment
import com.splunk.k8smanager.model.response.PodsResponse
import com.splunk.k8smanager.service.ClusterService
import com.splunk.k8smanager.toResponse
import io.kubernetes.client.custom.V1Patch
import jdk.jfr.ContentType
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

    @PostMapping(value = ["/deployment/scale"], params = ["name", "namespace"])
    @ResponseBody
    fun scaleDeployment(
        @RequestParam("name") name: String,
        @RequestParam("namespace") namespace: String?,
        @RequestBody body: String
    ): Deployment {
        return clusterService.scaleDeployment(name, namespace ?: "default", V1Patch(body))
    }
}