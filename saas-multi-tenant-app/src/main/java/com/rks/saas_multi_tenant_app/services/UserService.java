package com.rks.saas_multi_tenant_app.services;

import com.rks.saas_multi_tenant_app.common.PageResponse;
import com.rks.saas_multi_tenant_app.requests.UserRequest;
import com.rks.saas_multi_tenant_app.responses.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void createUser(final UserRequest request);

    void updateUser(final String id, final UserRequest request);

    void deleteUser(final String id);

    UserResponse getUserById(final String userId);

    PageResponse<UserResponse> getAllUsers(final int page, final int size);

    void enableUser(final String userId);

    void disableUser(final String userId);

}
