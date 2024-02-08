package com.prasannjeet.social.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.prasannjeet.social.jpa.enums.PermissionStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "openai_permissions")
@Data
@NoArgsConstructor
public class OpenAIPermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userId;
    @Enumerated(EnumType.STRING)
    private PermissionStatusEnum permissionStatus;

    public OpenAIPermissionEntity(String userId, PermissionStatusEnum permissionStatus) {
        this.userId = userId;
        this.permissionStatus = permissionStatus;
    }
}

