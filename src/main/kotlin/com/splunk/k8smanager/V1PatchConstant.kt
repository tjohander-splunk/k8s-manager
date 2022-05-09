package com.splunk.k8smanager

enum class OPERATION(value: String) {
    REPLACE("replace");
    val value: String? = null
}

enum class PATH(value: String) {
    REPLICAS("/spec/replicas");
    val value: String? = null
}