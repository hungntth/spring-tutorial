package com.hungnt.hello_world.mapper;

import com.hungnt.hello_world.dto.request.PermissionRequest;
import com.hungnt.hello_world.dto.response.PermissionResponse;
import com.hungnt.hello_world.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
   Permission toPermission(PermissionRequest permissionRequest);
   PermissionResponse toPermissionResponse(Permission permission);
}
