package org.myeonjeobjjang.bootapi;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.test.TestDomainService;
import org.myeonjeobjjang.domain.core.test.dto.TestDomainRequest;
import org.myeonjeobjjang.domain.core.test.dto.TestDomainResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {

    @GetMapping("hello")
    public String helloWorld() {
        return "hello world";
    }

    private final TestDomainService testDomainService;

    @PostMapping("test")
    public Long testSave(@RequestBody TestApiRequest.TestSaveApiRequest request) {
        return testDomainService.saveData(new TestDomainRequest.TestSaveDomainRequest(request.data(), request.writer()));
    }

    @GetMapping("test/{id}")
    public TestApiResponse.TestLoadApiResponse testLoad(@PathVariable("id") Long id) {
        TestDomainResponse.TestLoadDomainResponse response = testDomainService.getData(id);
        return new TestApiResponse.TestLoadApiResponse(response.data(), response.writer());
    }
}
