package com.splunk.k8smanager.controller

import com.splunk.k8smanager.model.response.PodsResponse
import com.splunk.k8smanager.service.ClusterService
import com.splunk.k8smanager.toResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/clusters")
class ClustersController(
    @Autowired val clusterService: ClusterService
) {

    @RequestMapping("/pods")
    @ResponseBody
    fun getPodsInfo(): PodsResponse = clusterService.getPodList().toResponse()
}