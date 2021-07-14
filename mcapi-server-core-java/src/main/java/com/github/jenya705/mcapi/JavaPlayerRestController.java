package com.github.jenya705.mcapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
@RestController
@RequestMapping("/player")
public class JavaPlayerRestController {

    @Autowired
    private JavaServerCore core;

    @GetMapping("/{name}")
    public Mono<JavaPlayer> getPlayer(@PathVariable String name) {
        return Mono.just(core.getPlayer(name));
    }

}
