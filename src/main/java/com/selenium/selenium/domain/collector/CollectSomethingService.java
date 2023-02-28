/**
 * 
 */
package com.selenium.selenium.domain.collector;

import java.io.InputStream;

/**
 * @author Higor Pereira
 *
 */
public class CollectSomethingService implements CollectSomethingUseCase {

    private GetSomethingFromWebSitePort getSomethingFromWebSitePort;

    public CollectSomethingService(GetSomethingFromWebSitePort getSomethingFromWebSitePort) {
	this.getSomethingFromWebSitePort = getSomethingFromWebSitePort;
    }

    @Override
    public String collectSomething() {
	return this.getSomethingFromWebSitePort.getSomething();
    }

}
