package com.sofia.uni.fmi.travelly.controller;

import com.sofia.uni.fmi.travelly.dto.ActivityCreateUpdateDto;
import com.sofia.uni.fmi.travelly.mapper.ActivityMapper;
import com.sofia.uni.fmi.travelly.dto.ActivityDto;
import com.sofia.uni.fmi.travelly.model.Activity;
import com.sofia.uni.fmi.travelly.service.ActivityService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("activities")
public class ActivityController {
    private ActivityService activityService;
    private ActivityMapper activityMapper;

    public ActivityController(ActivityService activityService, ActivityMapper activityMapper) {
        this.activityService = activityService;
        this.activityMapper = activityMapper;
    }

    @PatchMapping("{activityId}")
    public Long updateActivity(
            @PathVariable Long activityId,
            @RequestBody ActivityCreateUpdateDto activityCreateUpdateDto) {
        Activity activity = activityMapper.toEntity(activityCreateUpdateDto);
        activity.setId(activityId);
        Activity updatedActivity = activityService.updateActivity(activity);
        ActivityDto updatedActivityDto = activityMapper.toDto(updatedActivity);

        return updatedActivityDto.getId();
    }

    @DeleteMapping("{activityId}")
    public void deleteActivity(@PathVariable Long activityId) {
        activityService.deleteActivity(activityId);
    }
}
