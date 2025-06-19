package com.damian34.notes.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Controller to test api with Kubernetes
 */
@SuppressWarnings({"InfiniteLoopStatement", "MismatchedQueryAndUpdateOfCollection", "java:S112"})
@RestController
@RequestMapping("test")
class PerformanceTestController {

    @GetMapping("hello")
    String hello(@RequestParam(required = false) String name) {
        String displayName = Objects.requireNonNullElse(name, "friend");
        return MessageFormat.format("Hello {0}! now is: {1}", displayName, System.currentTimeMillis());
    }

    @GetMapping("memory")
    void memory(@RequestParam(required = false) Integer chunkSize) {
        int size = Objects.requireNonNullElse(chunkSize, 10); // default 10 MB
        List<byte[]> memoryAllocation = new ArrayList<>();
        while (true) {
            memoryAllocation.add(new byte[size * 1024 * 1024]);
        }
    }

    @GetMapping("cpu")
    String overload(@RequestParam(required = false) Integer time) {
        long minutes = Objects.requireNonNullElse(time, 2); // default 2 minutes
        long endTime = System.currentTimeMillis() + minutes * 60 * 1000;
        double sum = 0.0;
        while (System.currentTimeMillis() < endTime) {
            sum += Math.pow(Math.random(), Math.random());
        }
        return "CPU overload finished..." + sum;
    }
}
