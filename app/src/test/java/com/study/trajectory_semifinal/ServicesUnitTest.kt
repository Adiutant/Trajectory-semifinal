package com.study.trajectory_semifinal

import com.study.trajectory_semifinal.fragments.FirstFragment
import com.study.trajectory_semifinal.model.ServiceInfo
import com.study.trajectory_semifinal.model.ServiceViewInfo
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ServicesUnitTest {
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun buildViewInfoCorrect() {
        val info = ServiceInfo()
        info.icon_url = "url"
        info.name = "name"
        val viewInfo = ServiceViewInfo(info)
        Assert.assertEquals(info.name, viewInfo.name)
        Assert.assertEquals(info.icon_url, viewInfo.icon)
    }
}