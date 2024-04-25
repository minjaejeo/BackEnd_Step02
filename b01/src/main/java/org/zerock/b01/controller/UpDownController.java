package org.zerock.b01.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.b01.dto.upload.UploadFileDTO;
import org.zerock.b01.dto.upload.UploadResultDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@Log4j2
public class UpDownController {

    @Value("${org.zerock.upload.path}")
    private String uploadPath;


    @ApiOperation(value = "Upload POST", notes = "POST 방식으로 파일 등록")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadResultDTO> upload(UploadFileDTO uploadFileDTO){
        log.info(uploadFileDTO);

        // 업로드된 파일이 존재하면
        if(uploadFileDTO.getFiles()!=null){

            final List<UploadResultDTO > list = new ArrayList<>();
            // 업로드된 파일명을 출력하라.
            uploadFileDTO.getFiles().forEach(multipartFile -> {
                String originalFilename = multipartFile.getOriginalFilename();
                log.info(multipartFile.getOriginalFilename());

                // 파일명은 중복가능성이 높기 때문에 중복되지 않는 이름이 필요하다.
                // 그래서 중복확률이 천문학적으로 낮은 UUID를 생성해서 합쳐서 저장할 것이다.
                String uuid = UUID.randomUUID().toString();

                Path savePath = Paths.get(uploadPath, uuid+"_"+originalFilename);

                boolean image = false;

                // 해당 경로에 파일 저장
                try{
                    multipartFile.transferTo(savePath);

                    // 이미지 타입의 파일이라면 썸네일 파일(작은 이미지 파일)을 만든다.
                    // 썸네일 이미지는 기존 이미지 앞에 s_를 붙인 이름이다.
                    if(Files.probeContentType(savePath).startsWith("image")){

                        image = true;

                        File thumbFile = new File(uploadPath, "s_" + uuid + "_" + originalFilename);

                        // 200 x 200 크기의 파일을 동일한 경로에 저장한다.
                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);
                    }

                }catch (IOException e){
                    e.printStackTrace();
                }
                list.add(UploadResultDTO.builder()
                        .uuid(uuid)
                        .fileName(originalFilename)
                        .img(image).build()
                );

            });

            return list;
        }

        return null;
    }
    @ApiOperation(value = "view파일", notes = "GET 방식으로 첨부파일 조회")
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName){
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));

        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @ApiOperation(value = "remove 파일", notes = "DELETE 방식으로 파일 삭제")
    @DeleteMapping("/remove/{fileName}")
    public Map<String,Boolean> removeFile(@PathVariable String fileName){
        Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);

        String resourceName = resource.getFilename();

        Map<String, Boolean> resultMap = new HashMap<>();
        boolean removed = false;

        try{
            String contentType = Files.probeContentType(resource.getFile().toPath());
            removed = resource.getFile().delete();

            if(contentType.startsWith("image")){
                File thumbnailFile = new File(uploadPath+File.separator + "s_" + fileName);
                 thumbnailFile.delete();

            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        resultMap.put("result", removed);

        return resultMap;
    }
}
