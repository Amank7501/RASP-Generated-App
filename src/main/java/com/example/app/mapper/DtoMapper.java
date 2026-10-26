package com.example.app.mapper;

import com.example.app.dto.CreateAdminCrudRequest;
import com.example.app.dto.CreateGrievanceAttachmentCrudRequest;
import com.example.app.dto.CreateGrievanceCrudRequest;
import com.example.app.dto.CreateGrievanceStatusHistoryCrudRequest;
import com.example.app.dto.CreateUserCrudRequest;
import com.example.app.dto.UpdateAdminCrudRequest;
import com.example.app.dto.UpdateGrievanceAttachmentCrudRequest;
import com.example.app.dto.UpdateGrievanceCrudRequest;
import com.example.app.dto.UpdateGrievanceStatusHistoryCrudRequest;
import com.example.app.dto.UpdateUserCrudRequest;
import com.example.app.entity.Admin;
import com.example.app.entity.Grievance;
import com.example.app.entity.GrievanceAttachment;
import com.example.app.entity.GrievanceStatusHistory;
import com.example.app.entity.User;
import org.springframework.stereotype.Component;

@Component()
public class DtoMapper {

    public User toUserFromCreateUserCrudRequest(CreateUserCrudRequest dto) {
        if (dto == null)
            return null;
        User entity = new User();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setMobileNumber(dto.getMobileNumber());
        return entity;
    }

    public User toUserFromUpdateUserCrudRequest(UpdateUserCrudRequest dto) {
        if (dto == null)
            return null;
        User entity = new User();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setMobileNumber(dto.getMobileNumber());
        return entity;
    }

    public Admin toAdminFromCreateAdminCrudRequest(CreateAdminCrudRequest dto) {
        if (dto == null)
            return null;
        Admin entity = new Admin();
        entity.setEmail(dto.getEmail());
        return entity;
    }

    public Admin toAdminFromUpdateAdminCrudRequest(UpdateAdminCrudRequest dto) {
        if (dto == null)
            return null;
        Admin entity = new Admin();
        entity.setEmail(dto.getEmail());
        return entity;
    }

    public Grievance toGrievanceFromCreateGrievanceCrudRequest(CreateGrievanceCrudRequest dto) {
        if (dto == null)
            return null;
        Grievance entity = new Grievance();
        entity.setCategory(dto.getCategory());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setResolutionRemarks(dto.getResolutionRemarks());
        return entity;
    }

    public Grievance toGrievanceFromUpdateGrievanceCrudRequest(UpdateGrievanceCrudRequest dto) {
        if (dto == null)
            return null;
        Grievance entity = new Grievance();
        entity.setCategory(dto.getCategory());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setResolutionRemarks(dto.getResolutionRemarks());
        return entity;
    }

    public GrievanceAttachment toGrievanceAttachmentFromCreateGrievanceAttachmentCrudRequest(CreateGrievanceAttachmentCrudRequest dto) {
        if (dto == null)
            return null;
        GrievanceAttachment entity = new GrievanceAttachment();
        entity.setFileName(dto.getFileName());
        entity.setFileType(dto.getFileType());
        entity.setFilePath(dto.getFilePath());
        entity.setFileSize(dto.getFileSize());
        return entity;
    }

    public GrievanceAttachment toGrievanceAttachmentFromUpdateGrievanceAttachmentCrudRequest(UpdateGrievanceAttachmentCrudRequest dto) {
        if (dto == null)
            return null;
        GrievanceAttachment entity = new GrievanceAttachment();
        entity.setFileName(dto.getFileName());
        entity.setFileType(dto.getFileType());
        entity.setFilePath(dto.getFilePath());
        entity.setFileSize(dto.getFileSize());
        return entity;
    }

    public GrievanceStatusHistory toGrievanceStatusHistoryFromCreateGrievanceStatusHistoryCrudRequest(CreateGrievanceStatusHistoryCrudRequest dto) {
        if (dto == null)
            return null;
        GrievanceStatusHistory entity = new GrievanceStatusHistory();
        entity.setPreviousStatus(dto.getPreviousStatus());
        entity.setNewStatus(dto.getNewStatus());
        return entity;
    }

    public GrievanceStatusHistory toGrievanceStatusHistoryFromUpdateGrievanceStatusHistoryCrudRequest(UpdateGrievanceStatusHistoryCrudRequest dto) {
        if (dto == null)
            return null;
        GrievanceStatusHistory entity = new GrievanceStatusHistory();
        entity.setPreviousStatus(dto.getPreviousStatus());
        entity.setNewStatus(dto.getNewStatus());
        return entity;
    }
}
