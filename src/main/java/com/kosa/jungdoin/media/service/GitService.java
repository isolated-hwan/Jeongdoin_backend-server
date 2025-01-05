package com.kosa.jungdoin.media.service;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class GitService {

    @Value("${custom.git.remote}")
    private String REPO_URI;
    @Value("${custom.git.local}")
    private String LOCAL_REPO_PATH;
    private static final String BRANCH = "main";
    @Value("${custom.git.username}")
    private String USERNAME;
    @Value("${custom.git.personal-access-token}")
    private String TOKEN;
    @Value("${custom.git.cdn-url}")
    private String CDN_URL;

    public String commitAndPushFile(MultipartFile file) throws GitAPIException {

        String uniqueFileName = UUID.randomUUID() + getFileExtension(file.getOriginalFilename());
        String filePath = LOCAL_REPO_PATH + File.separator + uniqueFileName;

        try {
            File destFile = new File(filePath);
            file.transferTo(destFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }

        try (Git git = Git.open(new File(LOCAL_REPO_PATH))) {
            git.add().addFilepattern(uniqueFileName).call();
            git.commit().setMessage("Add media file").call();
            git.push()
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(USERNAME, TOKEN))
                    .call();
        } catch (IOException e) {
            throw new RuntimeException("Failed to open Git repository", e);
        }
        // Construct the URL to the file in the GitHub repository
        return  CDN_URL + "/" + uniqueFileName;
    }

    private String getFileExtension(String originalFilename) {
        if (originalFilename != null && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return ""; // No extension
    }
}
