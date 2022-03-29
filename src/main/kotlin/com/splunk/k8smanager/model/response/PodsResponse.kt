package com.splunk.k8smanager.model.response

import com.splunk.k8smanager.model.Pod

data class PodsResponse(
    val data: List<Pod>
)