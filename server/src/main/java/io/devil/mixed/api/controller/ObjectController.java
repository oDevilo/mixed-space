package io.devil.mixed.api.controller;

import com.google.common.collect.Lists;
import io.devil.mixed.api.Response;
import io.devil.mixed.api.request.ObjectSearchRequest;
import io.devil.mixed.api.request.ObjectUpdateRequest;
import io.devil.mixed.api.response.ObjectSearchResponse;
import io.devil.mixed.constant.ObjectConstants;
import io.devil.mixed.converter.ViewConverter;
import io.devil.mixed.domain.Cmd;
import io.devil.mixed.domain.Query;
import io.devil.mixed.domain.obj.StorageObjQuery;
import io.devil.mixed.domain.obj.StorageObjUpdateCmd;
import io.devil.mixed.domain.obj.StorageObjUploadCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

/**
 * @author Devil
 * @date 2024/8/2 17:26
 */
@Slf4j
@RestController
public class ObjectController {

    @RequestMapping(value = "/api/v1/object/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Response<Boolean> upload(
        @RequestParam(value = "files") MultipartFile[] files,
        @RequestParam(value = "tagIds", required = false) String[] tagIds
    ) {
        for (MultipartFile file : files) {
            try {
                Cmd.send(new StorageObjUploadCmd(file.getOriginalFilename(), file.getBytes(),
                    tagIds == null ? Collections.emptyList() : Lists.newArrayList(tagIds)));
            } catch (Exception e) {
                log.error("file upload fail name:{}", file.getOriginalFilename(), e);
            }
        }
        return Response.success(true);
    }

    @RequestMapping(value = "/api/v1/object/update")
    public Response<Boolean> update(@RequestBody ObjectUpdateRequest request) {
        Cmd.send(new StorageObjUpdateCmd(request.getObjectId(), request.getTagIds()));
        return Response.success(true);
    }

    @RequestMapping(value = "/api/v1/object/{filename}")
    public ResponseEntity<Resource> getObject(@PathVariable("filename") String filename) {
        try {
            Path file = Paths.get(ObjectConstants.STORAGE_DIR + filename);
            Resource resource = new InputStreamResource(Files.newInputStream(file));
            return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping("/api/v1/object/search")
    public Response<ObjectSearchResponse> search(@RequestBody ObjectSearchRequest request) {
        StorageObjQuery.Response queryResponse = Query.query(StorageObjQuery.builder().tagIds(request.getTagIds()).build(), StorageObjQuery.Response.class);
        return Response.success(ObjectSearchResponse.builder().objects(ViewConverter.convertObjs(queryResponse.getStorageObjs())).build());
    }

}
