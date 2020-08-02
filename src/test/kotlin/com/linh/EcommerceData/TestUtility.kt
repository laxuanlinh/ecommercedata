package com.linh.EcommerceData

import org.mockito.Mockito

class TestUtility{

    companion object {
        fun <T> any(type : Class<T>): T {
            Mockito.any(type)
            return uninitialized()
        }

        private fun <T> uninitialized(): T = null as T
    }

}