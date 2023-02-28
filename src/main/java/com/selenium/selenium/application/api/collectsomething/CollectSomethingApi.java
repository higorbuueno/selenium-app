/**
 * 
 */
package com.selenium.selenium.application.api.collectsomething;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selenium.selenium.domain.collector.CollectSomethingUseCase;

/**
 * @author Higor Pereira
 *
 */
@RestController
@RequestMapping("api/collect")
public class CollectSomethingApi {

    private CollectSomethingUseCase collectSomethingUseCase;

    public CollectSomethingApi(CollectSomethingUseCase collectSomethingUseCase) {
	this.collectSomethingUseCase = collectSomethingUseCase;
    }

    @GetMapping
    public String activateCollect() {
	return this.collectSomethingUseCase.collectSomething();
    }

}
