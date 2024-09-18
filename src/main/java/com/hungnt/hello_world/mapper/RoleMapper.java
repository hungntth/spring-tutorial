package com.hungnt.hello_world.mapper;

import com.hungnt.hello_world.dto.request.RoleRequest;
import com.hungnt.hello_world.dto.response.RoleResponse;
import com.hungnt.hello_world.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
   @Mapping(target = "permissions", ignore = true)
   Role toRole(RoleRequest roleRequest);

   RoleResponse toRoleResponse(Role role);
}
