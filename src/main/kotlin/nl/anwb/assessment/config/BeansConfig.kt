package nl.anwb.assessment.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import nl.anwb.assessment.handler.BikeParkingHandler
import nl.anwb.assessment.service.ClosestBikeParkingService
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.beans


object BeansConfig {
    fun getBeans() : BeanDefinitionDsl = beans {
        bean<BikeParkingHandler>()
        bean(::appRouter)
        bean {
            ClosestBikeParkingService(jacksonObjectMapper())
        }
    }
}