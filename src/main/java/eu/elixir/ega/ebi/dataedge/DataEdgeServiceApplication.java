/*
 * Copyright 2016 ELIXIR EGA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.elixir.ega.ebi.dataedge;

import eu.elixir.ega.ebi.dataedge.config.MyConfiguration;
import eu.elixir.ega.ebi.dataedge.config.OAuth2ResourceConfig;
import eu.elixir.ega.ebi.dataedge.config.SecurityConfig;
import eu.elixir.ega.ebi.dataedge.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableCaching
//@EnableCircuitBreaker
//@EnableHystrix
@SpringBootApplication
@Import({MyConfiguration.class, OAuth2ResourceConfig.class,
         WebConfig.class, SecurityConfig.class})
@EnableSwagger2
@EnableEurekaClient
public class DataEdgeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataEdgeServiceApplication.class, args);
    }

}
