package com.splunk.k8smanager

import com.splunk.k8smanager.model.Pod
import com.splunk.k8smanager.model.response.PodsResponse


fun List<Pod>.toResponse(): PodsResponse = PodsResponse(
    data = this
)
