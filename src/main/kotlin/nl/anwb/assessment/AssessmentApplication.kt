package nl.anwb.assessment

import nl.anwb.assessment.config.BeansConfig
import nl.anwb.assessment.handler.BikeParkingHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.coRouter

@SpringBootApplication
class AssessmentApplication

fun main(args: Array<String>) {
	runApplication<AssessmentApplication>(*args) {
		addInitializers(
			BeansConfig.getBeans()
		)
	}
}