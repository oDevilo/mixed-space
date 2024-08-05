package io.devil.mixed.api.controller;

import io.devil.mixed.api.Response;
import io.devil.mixed.converter.ViewConverter;
import io.devil.mixed.api.request.TagCreateRequest;
import io.devil.mixed.api.response.TagSearchResponse;
import io.devil.mixed.domain.Cmd;
import io.devil.mixed.domain.Query;
import io.devil.mixed.domain.tag.TagCreateCmd;
import io.devil.mixed.domain.tag.TagQuery;
import io.devil.mixed.utils.json.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Devil
 * @date 2024/1/31 18:38
 */
@Slf4j
@RestController
public class TagController {

    @RequestMapping("/api/v1/tag/create")
    public Response<Boolean> create(@RequestBody TagCreateRequest request) {
        Cmd.send(new TagCreateCmd(request.getName()));
        return Response.success(true);
    }

    @RequestMapping("/api/v1/tag/search")
    public Response<TagSearchResponse> search() {
        TagQuery.Response response = Query.query(TagQuery.builder().build(), TagQuery.Response.class);
        return Response.success(TagSearchResponse.builder().tags(ViewConverter.convertTags(response.getTags())).build());
    }

    public static void main(String[] args) {
        System.out.println(JacksonUtils.toJSONString(TagQuery.builder().build()));
    }

}
