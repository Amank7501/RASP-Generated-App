package com.example.app.dto;

import jakarta.validation.constraints.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data()
@Builder()
@NoArgsConstructor()
@AllArgsConstructor()
public class SubmitGrievanceRequest {

    @NotBlank
    private String category;

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @NotBlank
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotBlank
    private String description;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private List<String> attachmentFileNames;

    public List<String> getAttachmentFileNames() {
        return this.attachmentFileNames;
    }

    public void setAttachmentFileNames(List<String> attachmentFileNames) {
        this.attachmentFileNames = attachmentFileNames;
    }

    private List<String> attachmentFileTypes;

    public List<String> getAttachmentFileTypes() {
        return this.attachmentFileTypes;
    }

    public void setAttachmentFileTypes(List<String> attachmentFileTypes) {
        this.attachmentFileTypes = attachmentFileTypes;
    }

    private List<String> attachmentFilePaths;

    public List<String> getAttachmentFilePaths() {
        return this.attachmentFilePaths;
    }

    public void setAttachmentFilePaths(List<String> attachmentFilePaths) {
        this.attachmentFilePaths = attachmentFilePaths;
    }

    private List<String> attachmentFileSizes;

    public List<String> getAttachmentFileSizes() {
        return this.attachmentFileSizes;
    }

    public void setAttachmentFileSizes(List<String> attachmentFileSizes) {
        this.attachmentFileSizes = attachmentFileSizes;
    }
}
