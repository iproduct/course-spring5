package org.iproduct.spring.restmvc.service;

import org.iproduct.spring.restmvc.model.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission>  getPermissions();
    List<Permission> getPermissionsByAsset(String asset);
    Permission createPermissionIfNotExist(Permission permission);
    void deletePermission(Permission permission);
}
