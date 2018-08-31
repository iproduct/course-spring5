package org.iproduct.spring.restmvc.service;

import org.iproduct.spring.restmvc.model.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission>  getPermissions();
    Permission createPermissionIfNotExist(Permission permission);
    Permission updatePermission(Permission permission);
    List<Permission> getPermissionsByAsset(String id);
    void deletePermission(Permission permission);
}
