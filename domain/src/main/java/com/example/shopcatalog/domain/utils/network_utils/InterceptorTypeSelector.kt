package com.example.shopcatalog.domain.utils.network_utils

import com.example.student_tasks.network.utils.InterceptorType

class InterceptorTypeSelector : InterceptorType.Provider {
    override var value = InterceptorType.APPLICATION
}
