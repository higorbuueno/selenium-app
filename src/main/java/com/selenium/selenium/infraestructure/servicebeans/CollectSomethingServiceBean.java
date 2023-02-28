/**
 * 
 */
package com.selenium.selenium.infraestructure.servicebeans;

import org.springframework.stereotype.Service;

import com.selenium.selenium.domain.collector.CollectSomethingService;
import com.selenium.selenium.domain.collector.GetSomethingFromWebSitePort;

/**
 * @author Higor Pereira
 *
 */
@Service
public class CollectSomethingServiceBean extends CollectSomethingService {

    public CollectSomethingServiceBean(GetSomethingFromWebSitePort getSomethingFromWebSitePort) {
	super(getSomethingFromWebSitePort);
    }


}
