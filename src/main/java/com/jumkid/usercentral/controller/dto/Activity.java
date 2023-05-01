package com.jumkid.usercentral.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Activity{

    @Min(0L)
    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private String entityId;

    @NotNull
    private String entityModule;

    private String title;

    private boolean unread;

}
