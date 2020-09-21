package com.lxf.springsecurity.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @author: lxf
 * @create: 2020-09-11 14:24
 * @description:
 *          mockserver用于模拟虚拟请求-----重新定义更通用的请求方式
 *          ------结合swagger文档
 *          （现在只需要定义需要的请求，响应json即可通过postman测试）
 */
public class MockServer {
    public static void main(String[] args) throws IOException {
        WireMock.configureFor("127.0.0.1", 8081);
        WireMock.removeAllMappings();
        mock_get("/user/1", "getUserByIdResponse.json");
        mock_post("/deliveries/view/all.json", "all_deliveries.json","request_body.json");
    }
    private static String getContent(String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource("mock/response/" + fileName);
        String content = StringUtils.join(FileUtils.readLines(resource.getFile(), "UTF-8"),"\n");
        return  content;
    };
    private static void mock_get(String url, String fileName) throws IOException {
        String response_content =getContent(fileName);
        WireMock.stubFor(
                WireMock.get(WireMock.urlEqualTo(url))
                        .willReturn(
                                WireMock.aResponse()
                                        // 返回的状态码
                                        .withStatus(200)
                                        // 返回的JSON串
                                        .withBody(response_content)));

    }
    private static  void mock_post(String url,String fileName,String requestFile) throws IOException {
        String response_content =getContent(fileName);
        String request_content =getContent(requestFile);
        WireMock.stubFor(
                WireMock.post(WireMock.urlEqualTo(url))
                        .withRequestBody(WireMock.equalToJson(request_content))
                        .willReturn(
                                WireMock.aResponse()
                                .withStatus(200)
                                .withBody(response_content)
                        )
        );

    }

}
